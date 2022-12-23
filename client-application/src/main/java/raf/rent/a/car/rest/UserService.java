package raf.rent.a.car.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import raf.rent.a.car.dto.ManagerDto;

import javax.swing.*;
import java.io.IOException;

public class UserService {

    //public static final String URL = "http://localhost:8084/user-service/api";
    public static final String URL = "http://localhost:8000/api";
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();
    ObjectMapper objectMapper = new ObjectMapper();

    public void createManager(ManagerDto managerCreateDto) throws IOException {
        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(managerCreateDto));

        Request request = new Request.Builder()
                .url(URL + "/users//create-manager")
                .post(body)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        response.body().close();

        if (!response.isSuccessful())
            throw new IOException();
    }
}
