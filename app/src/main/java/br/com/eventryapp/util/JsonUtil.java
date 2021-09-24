package br.com.eventryapp.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author Guilherme Mendes
 * @version 1.0.0
 * @since 1.0.0
 */
public class JsonUtil {

    /**
     * Este metodo transforma um linked treemap em json
     *
     * @param linkedTreeMap
     * @param object
     * @return string do json
     * @throws JSONException
     */
    public static String convertLinkedTreeToArray(LinkedTreeMap linkedTreeMap, String object) throws JSONException {
        Gson gson = new Gson();
        JSONObject jsonObject = new JSONObject(gson.toJson(linkedTreeMap));
        return jsonObject.getJSONArray(object).toString();
    }

    public static <T> T getEntity(String json, TypeReference<T> clazz) {
        return fromJson(json, clazz);
    }

    private static <T> T fromJson(String json, TypeReference<T> clazz) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()));
            return mapper.readValue(json, clazz);
        } catch (Exception e) {
            return null;
        }
    }

    public static <T> String toJson(T t) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
            mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()));
            return mapper.writeValueAsString(t);
        } catch (Exception e) {
            return null;
        }
    }

    public static String convertLinkedTreeToObject(LinkedTreeMap linkedTreeMap, String object) throws JSONException {
        Gson gson = new Gson();
        JSONObject jsonObject = new JSONObject(gson.toJson(linkedTreeMap));
        return jsonObject.get(object).toString();
    }
}