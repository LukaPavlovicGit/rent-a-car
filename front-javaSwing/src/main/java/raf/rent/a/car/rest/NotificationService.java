package raf.rent.a.car.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import raf.rent.a.car.MainFrame;
import raf.rent.a.car.dto.CompaniesListDto;
import raf.rent.a.car.dto.SentEmailFilterDto;
import raf.rent.a.car.dto.SentEmailsListDto;

import java.io.IOException;

public class NotificationService {
    public static final String URL = "http://localhost:8002/api";
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();
    ObjectMapper objectMapper = new ObjectMapper();

    public SentEmailsListDto getSentEmails(SentEmailFilterDto filterDto) throws IOException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(filterDto));
        String token = MainFrame.getInstance().getToken();

        Request request = new Request.Builder()
                .url(URL + "/sent-email/all")
                .addHeader("authorization", "Bearer " + token)
                .post(body)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        String json = response.body().string();
        response.body().close();

        if (response.code() == 200)
            return objectMapper.readValue(json, SentEmailsListDto.class);

        throw new IOException();
    }

    public SentEmailsListDto getSentEmailsByEmail(SentEmailFilterDto filterDto) throws IOException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(filterDto));
        String token = MainFrame.getInstance().getToken();

        Request request = new Request.Builder()
                .url(URL + "/sent-email/by-email")
                .addHeader("authorization", "Bearer " + token)
                .post(body)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        String json = response.body().string();
        response.body().close();

        if (response.code() == 200)
            return objectMapper.readValue(json, SentEmailsListDto.class);

        throw new IOException();
    }
}
