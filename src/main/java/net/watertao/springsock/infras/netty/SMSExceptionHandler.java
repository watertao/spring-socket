package net.watertao.springsock.infras.netty;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.DecoderException;
import io.netty.handler.timeout.ReadTimeoutException;
import net.watertao.springsock.infras.framework.APIException;
import net.watertao.springsock.infras.framework.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMSExceptionHandler extends ChannelDuplexHandler {

    private static final Logger logger = LoggerFactory.getLogger(SMSExceptionHandler.class);

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception {

        APIResponse response = new APIResponse();
        if (cause instanceof ReadTimeoutException) {
            logger.warn("request read time out");
            response.setCode(APIResponse.Status.BAD_REQUEST.getCode());
            response.setMsg("request read time out");
        } else if (cause instanceof APIException) {
            logger.warn(((APIException)cause).getMsg());
            response.setCode(((APIException) cause).getCode());
            response.setMsg(((APIException) cause).getMsg());
        }  else if (cause instanceof DecoderException && ((DecoderException) cause).getCause() instanceof APIException) {
            logger.warn(((APIException) cause.getCause()).getMsg());
            response.setCode(((APIException) cause.getCause()).getCode());
            response.setMsg(((APIException) cause.getCause()).getMsg());
        } else {
            logger.error(cause.getMessage(), cause);
            response.setCode(APIResponse.Status.INTERNAL_SERVER_ERR.getCode());
            response.setMsg(APIResponse.Status.INTERNAL_SERVER_ERR.getMsg());
        }

        ctx.writeAndFlush(response)
                .addListener(ChannelFutureListener.CLOSE);

    }

}