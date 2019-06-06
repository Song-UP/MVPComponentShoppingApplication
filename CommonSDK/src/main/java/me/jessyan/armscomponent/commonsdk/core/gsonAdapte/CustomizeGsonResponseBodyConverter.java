package me.jessyan.armscomponent.commonsdk.core.gsonAdapte;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;

import java.io.IOException;

import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * @Description：描述信息
 * @Author：Song UP
 * @Date：2019/4/24 18:07
 * 修改备注：
 */
public class CustomizeGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private final Gson gson;
    private final TypeAdapter<T> adapter;
    private final TypeToken typeToken;

    CustomizeGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter, TypeToken typeToken) {
        this.gson = gson;
        this.adapter = adapter;
        this.typeToken = typeToken;
    }

    @Override public T convert(ResponseBody value) throws IOException {

        try {
            Class<? extends T> clz = typeToken.getRawType();
            if (String.class.isAssignableFrom(clz)){
                return (T)value.string();
            }

            JsonReader jsonReader = gson.newJsonReader(value.charStream());
            T result = adapter.read(jsonReader);
            if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonIOException("JSON document was not fully consumed.");
            }
            return result;
        } finally {
            value.close();
        }
    }
}