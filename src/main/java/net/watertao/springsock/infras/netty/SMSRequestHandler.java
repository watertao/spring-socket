package net.watertao.springsock.infras.netty;


import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import net.watertao.springsock.infras.framework.APIException;
import net.watertao.springsock.infras.framework.APIMetadataHolder;
import net.watertao.springsock.infras.framework.APIResponse;
import net.watertao.springsock.infras.framework.AbstractAPIService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Handler for processing incoming request.
 *
 */
@ChannelHandler.Sharable
@Component
public class SMSRequestHandler extends SimpleChannelInboundHandler{

    private static final Logger logger = LoggerFactory.getLogger(SMSRequestHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws APIException {

        SMSRequestWrapper requestWrapper = (SMSRequestWrapper) msg;

        logger.info("<--o [ " + requestWrapper.getFunctionCode() + " ] - " + requestWrapper.getPayload());

        // retrieve api service
        AbstractAPIService service = APIMetadataHolder.getServiceBeanViaFuncCode(requestWrapper.getFunctionCode());

        APIResponse response = null;

        response = service.call(requestWrapper.getRequestPayload());
        if (response == null) {
            response = new APIResponse();
        }


        ctx.writeAndFlush(response)
                .addListener(ChannelFutureListener.CLOSE);

    }



}
