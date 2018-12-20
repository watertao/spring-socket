package net.watertao.springsock.infras.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;

@Component
public class SMSPServer implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(SMSPServer.class);


    @Autowired
    private SMSRequestHandler smsRequestHandler;

    @Autowired
    private SMSPChannelInitializer smspChannelInitializer;

    @Autowired
    private Environment env;

    private NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);
    private NioEventLoopGroup workerGroup = new NioEventLoopGroup();




    @Override
    public void run(String... args) {
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap()
                    .group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(smspChannelInitializer)
                    .option(ChannelOption.SO_BACKLOG, 512)
                    .childOption(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000);
            Integer port = Integer.parseInt(env.getProperty("server.port"));
            ChannelFuture future = serverBootstrap.bind(port);
            future.sync();
            future.channel().closeFuture().sync();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            shutdown();
        }

    }


    @PreDestroy
    public void shutdown() {
        logger.info("Shutdown server...");
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();
    }


}
