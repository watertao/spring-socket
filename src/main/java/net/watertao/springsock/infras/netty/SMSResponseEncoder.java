package net.watertao.springsock.infras.netty;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import net.watertao.springsock.infras.framework.APIResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SMSResponseEncoder extends MessageToByteEncoder {

    private static final Logger logger = LoggerFactory.getLogger(SMSResponseEncoder.class);

    private static final String PAYLOAD_ENCODING = "UTF8";

    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        APIResponse response = (APIResponse) msg;
        String payload = objectMapper.writeValueAsString(response);

        logger.info("o--> - " + payload);

        byte[] payloadBytes = payload.getBytes(PAYLOAD_ENCODING);
        Integer length = payloadBytes.length;
        out.writeInt(length);
        out.writeBytes(payloadBytes);
    }

}
