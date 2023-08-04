package lt.monikos.meepley.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lt.monikos.meepley.entity.Token;

import java.util.Base64;

public class ExtractJWT {

    public static Token payloadJWTExtraction(String token) {

        String[] chunks = token.split("\\.");
        Base64.Decoder decoder = Base64.getUrlDecoder();

        String payload = new String(decoder.decode(chunks[1]));

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Token extraction = null;

        try {
            extraction = objectMapper.readValue(payload, Token.class);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
        return extraction;
    }

}
