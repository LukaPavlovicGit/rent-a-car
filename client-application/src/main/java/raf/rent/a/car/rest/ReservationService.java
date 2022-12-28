package raf.rent.a.car.rest;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.*;
import raf.rent.a.car.MainFrame;
import raf.rent.a.car.dto.*;

import java.io.IOException;

public class ReservationService {

    public static final String URL = "http://localhost:8001/api";
    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");

    OkHttpClient client = new OkHttpClient();
    ObjectMapper objectMapper = new ObjectMapper();

    public CompaniesListDto getCompanies() throws IOException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String token = MainFrame.getInstance().getToken();

        Request request = new Request.Builder()
                .url(URL + "/companies")
                .addHeader("authorization", "Bearer " + token)
                .get()
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        String json = response.body().string();
        response.body().close();

        if (response.code() == 200)
            return objectMapper.readValue(json, CompaniesListDto.class);

        throw new IOException();
    }

    public ReservationListDto getReservations() throws IOException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String token = MainFrame.getInstance().getToken();

        Request request = new Request.Builder()
                .url(URL + "/reservations")
                .addHeader("authorization", "Bearer " + token)
                .get()
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        String json = response.body().string();
        response.body().close();

        if (response.code() == 200)
            return objectMapper.readValue(json, ReservationListDto.class);

        throw new IOException();
    }

    public ReservationListDto getReservationsByCompany() throws IOException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String token = MainFrame.getInstance().getToken();

        Request request = new Request.Builder()
                .url(URL + "/reservations/by-company")
                .addHeader("authorization", "Bearer " + token)
                .get()
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        String json = response.body().string();
        response.body().close();

        if (response.code() == 200)
            return objectMapper.readValue(json, ReservationListDto.class);

        throw new IOException();
    }
    public void deleteReservation(String reservationId) throws IOException {
        HttpUrl.Builder httpBuilder = HttpUrl.parse(URL + "/reservations").newBuilder();
        httpBuilder.addQueryParameter("id", reservationId);
        String token = MainFrame.getInstance().getToken();


        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .addHeader("authorization", "Bearer " + token)
                .delete()
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        response.body().close();

        if (response.code() == 200)
            return;

        throw new IOException();
    }

    public ReviewListDto getReviews() throws IOException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String token = MainFrame.getInstance().getToken();

        Request request = new Request.Builder()
                .url(URL + "/reviews")
                .addHeader("authorization", "Bearer " + token)
                .get()
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        String json = response.body().string();
        response.body().close();

        if (response.code() == 200)
            return objectMapper.readValue(json, ReviewListDto.class);

        throw new IOException();
    }

    public VehiclesListDto getVehicles() throws IOException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String token = MainFrame.getInstance().getToken();

        Request request = new Request.Builder()
                .url(URL + "/vehicles")
                .addHeader("authorization", "Bearer " + token)
                .get()
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        String json = response.body().string();
        response.body().close();

        if (response.code() == 200)
            return objectMapper.readValue(json, VehiclesListDto.class);

        throw new IOException();
    }

    public void createCompany(CompanyDto companyDto) throws IOException {
        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(companyDto));
        String token = MainFrame.getInstance().getToken();

        Request request = new Request.Builder()
                .url(URL + "/companies")
                .addHeader("authorization", "Bearer " + token)
                .post(body)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        response.body().close();

        if (!response.isSuccessful())
            throw new IOException();
    }
    public void updateCompany(CompanyDto companyDto) throws IOException {
        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(companyDto));
        String token = MainFrame.getInstance().getToken();

        Request request = new Request.Builder()
                .url(URL + "/companies")
                .addHeader("authorization", "Bearer " + token)
                .put(body)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        response.body().close();

        if (!response.isSuccessful())
            throw new IOException();
    }
}
