package org.lendi.umtapo.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.fge.jsonpatch.JsonPatch;
import com.github.fge.jsonpatch.JsonPatchException;

import java.io.IOException;
import java.text.SimpleDateFormat;

/**
 * The type Json dynamic patch.
 *
 * @param <T> the type parameter
 */
public class JsonDynamicPatch<T> {


    /**
     * Instantiates a new Json dynamic patch.
     */
    public JsonDynamicPatch() {
    }

    /**
     * Dynamic patch t.
     *
     * @param t        the t
     * @param jsonNode the json node
     * @return the t
     * @throws IOException        the io exception
     * @throws JsonPatchException the json patch exception
     */
    public T dynamicPatch(T t, JsonNode jsonNode) throws IOException, JsonPatchException {

        ObjectMapper objectMapper = new ObjectMapper();
        setTimeFormat(objectMapper);
        JsonNode source = objectMapper.valueToTree(t);
        JsonPatch patch = JsonPatch.fromJson(jsonNode.get("patch"));
        final JsonNode patched = patch.apply(source);
        return objectMapper.treeToValue(patched, (Class<T>) t.getClass());
    }

    private void setTimeFormat(ObjectMapper objectMapper) {
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"));
        objectMapper.registerModule(javaTimeModule);
    }
}
