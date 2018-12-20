package net.watertao.springsock.infras.netty;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.timeout.ReadTimeoutHandler;
import net.watertao.springsock.infras.framework.APIException;
import net.watertao.springsock.infras.framework.APIMetadataHolder;
import net.watertao.springsock.infras.framework.APIResponse;
import org.hibernate.validator.HibernateValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;


public class SMSRequestDecoder extends ByteToMessageDecoder {

    private static final Logger logger = LoggerFactory.getLogger(SMSRequestDecoder.class);

    private static final String FUNCTION_CODE_ENCODING = "ISO8859-1";
    private static final String PAYLOAD_ENCODING = "UTF8";

    private static final Validator validator = Validation
            .byProvider(HibernateValidator.class).configure().failFast(true).buildValidatorFactory().getValidator();

    private ObjectMapper objectMapper = new ObjectMapper();

    private byte[] originBuff = null;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf buff, List<Object> out) throws Exception {

        originBuff = new byte[buff.readableBytes()];
        buff.getBytes(0, originBuff);
        logger.debug("RAW: " + encode(originBuff));


        // retrieve length field
        byte[] lengthBytes = new byte[4];
        buff.getBytes(0, lengthBytes);
        logger.debug("LENGTH: " + encode(lengthBytes));
        Integer length = buff.readInt();
        logger.debug("LENGTH(d): " + length);

        // retrieve function code
        String functionCode = null;
        if (buff.readableBytes() < 4) {
            throw new APIException(APIResponse.Status.BAD_REQUEST.getCode(),
                    "function code(5-8th bytes) required");
        }
        byte[] funcCodeBytes = new byte[4];
        buff.readBytes(funcCodeBytes);
        logger.debug("FUNCODE: " + encode(funcCodeBytes));
        try {
            functionCode = new String(funcCodeBytes, FUNCTION_CODE_ENCODING);
            logger.debug("FUNCODE(d): " + functionCode);
        } catch (UnsupportedEncodingException e) {
            throw new APIException(APIResponse.Status.BAD_REQUEST.getCode(),
                    "function code cannot decoding with ISO8859-1", e);
        }

        // retrieve payload
        String payload = null;
        byte[] payloadBytes = new byte[buff.readableBytes()];
        buff.readBytes(payloadBytes);
        logger.debug("PAYLOAD: " + encode(payloadBytes));
        try {
            payload = new String(payloadBytes, PAYLOAD_ENCODING);
            logger.debug("PAYLOAD(d): " + payload);
        } catch (UnsupportedEncodingException e) {
            throw new APIException(APIResponse.Status.BAD_REQUEST.getCode(),
                    "body cannot decoding with UTF-8", e);
        }

        // parse payload json
        Object requestObj = null;
        Class requestType = APIMetadataHolder.getRequestTypeViaFuncCode(functionCode);
        if (requestType == null) {
            throw new APIException(APIResponse.Status.BAD_REQUEST.getCode(),
                    "unknown function code: " + functionCode);
        }
        try {
            requestObj = objectMapper.readValue(payload, requestType);
        } catch (IOException e) {
            logger.warn("PAYLOAD: " + payload);
            logger.warn("REQ_CLASS: " + requestType.getName());
            throw new APIException(APIResponse.Status.BAD_REQUEST.getCode(),
                    "not a well-formed request: payload wrong json", e);
        }

        // validate payload object via bean validation
        Set<ConstraintViolation<Object>> violations = validator.validate(requestObj);
        if (violations.size() > 0) {
            ConstraintViolation<Object> violation = violations.iterator().next();
            throw new APIException(APIResponse.Status.BAD_REQUEST.getCode(),
                    "[" + violation.getPropertyPath() + "] " + violation.getMessage());
        }

        // stop checking read timeout
        ctx.pipeline().remove(ReadTimeoutHandler.class);

        // transfer request object to next handler
        SMSRequestWrapper requestWrapper = new SMSRequestWrapper(functionCode, requestObj, payload);
        out.add(requestWrapper);

    }




    private String encode(byte[] src) {
        String strHex = null;
        StringBuilder sb = new StringBuilder("");
        for (int n = 0; n < src.length; n++) {
            strHex = Integer.toHexString(src[n] & 0xFF);
            sb.append((strHex.length() == 1) ? "0" + strHex : strHex);
            sb.append(" ");
        }
        return sb.toString().trim();
    }

}
