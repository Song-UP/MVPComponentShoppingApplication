package me.jessyan.armscomponent.commonsdk.core.gsonAdapte;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import me.jessyan.armscomponent.commonsdk.core.gsonAdapte.gsonhandlenull.DoubleDefault0Adapter;
import me.jessyan.armscomponent.commonsdk.core.gsonAdapte.gsonhandlenull.IntDefault0Adapter;
import me.jessyan.armscomponent.commonsdk.core.gsonAdapte.gsonhandlenull.LongDefault0Adapter;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * @Description：自定义Gson
 * @Author：Song UP
 * @Date：2019/4/24 18:05
 * 修改备注：
 */
public class CustomizeGsonConverterFactory extends Converter.Factory {
    public static CustomizeGsonConverterFactory create() {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(long.class, new LongDefault0Adapter())
                .registerTypeAdapter(Long.class, new LongDefault0Adapter())
                .registerTypeAdapter(int.class, new IntDefault0Adapter())
                .registerTypeAdapter(Integer.class, new IntDefault0Adapter())
                .registerTypeAdapter(Double.class, new DoubleDefault0Adapter())
                .registerTypeAdapter(double.class, new DoubleDefault0Adapter())
                .create();
        return create(gson);
    }

    public static CustomizeGsonConverterFactory create(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        return new CustomizeGsonConverterFactory(gson);
    }

    private final Gson gson;

    private CustomizeGsonConverterFactory(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        TypeToken typeToken = TypeToken.get(type);
        TypeAdapter<?> adapter = gson.getAdapter(typeToken);
        return new CustomizeGsonResponseBodyConverter<>(gson, adapter,typeToken);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new CustomizeGsonRequestBodyConverter<>(gson, adapter);
    }
}