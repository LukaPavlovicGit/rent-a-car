package raf.rent.a.car.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import raf.rent.a.car.MainFrame;
import raf.rent.a.car.dto.*;

import javax.swing.*;
import java.io.IOException;

public class UserService {

    //public static final String URL = "http://localhost:8084/user-service/api";
    public static final String URL = "http://localhost:8000/api";
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();
    ObjectMapper objectMapper = new ObjectMapper();

    public void createClient(ClientDto clientDto) throws IOException {
        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(clientDto));

        Request request = new Request.Builder()
                .url(URL + "/users/create-client")
                .post(body)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        response.body().close();

        if (!response.isSuccessful())
            throw new IOException();
    }

    public void createManager(ManagerDto managerCreateDto) throws IOException {
        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(managerCreateDto));

        Request request = new Request.Builder()
                .url(URL + "/users/create-manager")
                .post(body)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        response.body().close();

        if (!response.isSuccessful())
            throw new IOException();
    }

    public String login(TokenRequestDto tokenRequestDto) throws IOException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(tokenRequestDto));
        Request request = new Request.Builder()
                .url(URL + "/users/login")
                .post(body)
                .build();

        Call call = client.newCall(request);

        Response response = call.execute();
        String json = response.body().string();
        response.body().close();

        if (response.code() == 200) {
            TokenResponseDto dto = objectMapper.readValue(json, TokenResponseDto.class);
            return dto.getToken();
        }
        throw new IOException();
    }

    public UsersListDto getUsers() throws IOException{
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String token = MainFrame.getInstance().getToken();

        Request request = new Request.Builder()
                .url(URL + "/users")
                .addHeader("authorization", "Bearer " + token)
                .get()
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        String json = response.body().string();
        response.body().close();

        if (response.code() == 200)
            return objectMapper.readValue(json, UsersListDto.class);

        throw new IOException();
    }

    public UserDto updateAdmin(UpdateAdminDto updateAdminDto) throws IOException {
        String token = MainFrame.getInstance().getToken();
        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(updateAdminDto));

        Request request = new Request.Builder()
                .url(URL + "/users/update-admin")
                .addHeader("authorization", "Bearer " + token)
                .put(body)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        String json = response.body().string();
        response.body().close();

        if (response.code() == 200)
            return objectMapper.readValue(json, UserDto.class);

        throw new IOException();
    }

    public void passwordChange(PasswordDto passwordDto) throws IOException {
        String token = MainFrame.getInstance().getToken();
        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(passwordDto));

        Request request = new Request.Builder()
                .url(URL + "/users/change-password")
                .addHeader("authorization", "Bearer " + token)
                .put(body)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        response.body().close();

        if (response.code() == 200)
            return;

        throw new IOException();
    }

    public void banUser(String userId) throws IOException {
        String token = MainFrame.getInstance().getToken();
        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(userId));

        Request request = new Request.Builder()
                .url(URL + "/users/ban-user")
                .addHeader("authorization", "Bearer " + token)
                .put(body)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        response.body().close();

        if (response.code() == 200)
            return ;

        throw new IOException();
    }

    public void removeBanUser(String userId) throws IOException {
        String token = MainFrame.getInstance().getToken();
        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(userId));

        Request request = new Request.Builder()
                .url(URL + "/users/remove-ban-user")
                .addHeader("authorization", "Bearer " + token)
                .put(body)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        response.body().close();

        if (response.code() == 200)
            return;

        throw new IOException();
    }

    public void deleteAccount(CredentialsDto credentialsDto) throws IOException {
        String token = MainFrame.getInstance().getToken();
        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(credentialsDto));

        Request request = new Request.Builder()
                .url(URL + "/users")
                .addHeader("authorization", "Bearer " + token)
                .delete(body)
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        response.body().close();

        if (response.code() == 200)
            return;

        throw new IOException();
    }
}
