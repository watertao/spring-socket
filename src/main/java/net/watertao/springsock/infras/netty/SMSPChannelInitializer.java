package net.watertao.springsock.infras.netty;


import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Channel Initializer for SMS Protocol.
 *
 * - REQUEST -
 *
 * The request should be composed of 3 parts:
 * - length field (4 bytes，self-excluded)
 * - function code (4 bytes ASCII)
 * - payload (dynamic size, UTF8 JSON)
 *
 * +------------+-----------------+----------------+
 * | Length     | Function Code   | Payload        |
 * | 0x000010F6 | "0001" (ASCII)  | "{...}" (UTF8) |
 * +------------+-----------------+----------------+
 *
 *
 * - RESPONSE -
 *
 * The response would be composed of 2 parts:
 * - length field (4 bytes, self-excluded)
 * - payload (dynamic size, UTF8 JSON)
 *
 * +------------+----------------+
  * | 0x000010F6 | "{...}" (UTF8) |
 * +------------+----------------+
 *
 */
@Component
public class SMSPChannelInitializer extends ChannelInitializer {

    /** Timeout for reading data from client */
    private static final Integer READ_TIMEOUT_SECOND = 3;

    /** Timeout for writing data to client */
    private static final Integer WRITE_TIMEOUT_SECOND = 5;

    /** The max length of request body (do not include length field) */
    private static final Integer MAX_FRAME_LENGTH = 0x7FFFFFFF;

    /** We use 4 bytes to indicate the length of request body */
    private static final Integer LENGTH_FIELD_SIZE = 4;


    @Autowired
    private SMSRequestHandler smsRequestHandler;


    @Override
    protected void initChannel(Channel channel) throws Exception {
        channel.pipeline()
                .addLast(new SMSResponseEncoder())
                .addLast(new ReadTimeoutHandler(READ_TIMEOUT_SECOND))
                .addLast(new LengthFieldBasedFrameDecoder(MAX_FRAME_LENGTH, 0, LENGTH_FIELD_SIZE, 0, 0))
                .addLast(new SMSRequestDecoder())
                .addLast(smsRequestHandler)
                .addLast(new SMSExceptionHandler());
    }


}
