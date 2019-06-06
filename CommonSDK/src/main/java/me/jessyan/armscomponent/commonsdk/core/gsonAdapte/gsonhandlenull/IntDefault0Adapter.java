package me.jessyan.armscomponent.commonsdk.core.gsonAdapte.gsonhandlenull;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;

import java.lang.reflect.Type;

/**
 * Created by ASUS on 2017/8/11.
 */

public class IntDefault0Adapter implements JsonSerializer<Integer>, JsonDeserializer<Integer> {
    @Override
    public Integer deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        try {
            if (json.getAsString() == null || json.getAsString().equals("") || json.getAsString().equals("null") || json.getAsString().equals("false")) {//定义为int类型,如果后台返回""或者null,则返回0
                return 0;
            } else if(json.getAsString().equals("true")){
                return 1;
            }
        } catch (Exception ignore) {
        }
        try {
            return json.getAsInt();
        } catch (NumberFormatException e) {
            throw new JsonSyntaxException(e);
        }
    }

    @Override
    public JsonElement serialize(Integer src, Type typeOfSrc, JsonSerializationContext context) {
        return new JsonPrimitive(src);
    }
}