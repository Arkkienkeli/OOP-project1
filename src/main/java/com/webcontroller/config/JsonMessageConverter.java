package com.webcontroller.config;

/**
 * Created by Arkkienkeli on 02.03.14.
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.AbstractHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class JsonMessageConverter extends AbstractHttpMessageConverter<Object> {


    public static final Charset DEFAULT_CHARSET = Charset.forName("utf8");
    private final Gson gson = new GsonBuilder().serializeNulls().create();

    public JsonMessageConverter() {
        super(new MediaType("application", "json", DEFAULT_CHARSET));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object readInternal(Class<? extends Object> clazz,
                                  HttpInputMessage inputMessage) throws IOException,
            HttpMessageNotReadableException {
        InputStream stream = inputMessage.getBody();
        try (InputStreamReader reader = new InputStreamReader(stream, DEFAULT_CHARSET)) {
            return gson.fromJson(reader, clazz);
        }
    }

    @Override
    protected void writeInternal(Object t, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        OutputStream stream = outputMessage.getBody();
        try (OutputStreamWriter writer = new OutputStreamWriter(stream, DEFAULT_CHARSET)) {
            gson.toJson(t, writer);
        }
    }



}