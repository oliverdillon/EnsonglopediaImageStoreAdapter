package com.ensonglodpedia.adapters.ensonglopedia.image.store.adapter.models;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import org.apache.tomcat.util.codec.binary.Base64;

import java.io.IOException;

public class BytesDeserializer extends StdDeserializer<ImagePostRequest> {

    public BytesDeserializer() {
        this(ImagePostRequest.class);
    }

    protected BytesDeserializer(Class<ImagePostRequest> vc) {
        super(vc);
    }

    protected BytesDeserializer(StdDeserializer<ImagePostRequest> src) {
        super(src);
    }


    @Override
    public ImagePostRequest deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        String vinyl_uuid = node.get("vinyl_uuid").asText();
        String filename = node.get("filename").asText();
        String fileString = node.get("file").asText();
        byte[] file = Base64.decodeBase64(fileString);
        return new ImagePostRequest(vinyl_uuid, filename, file);
    }
}

