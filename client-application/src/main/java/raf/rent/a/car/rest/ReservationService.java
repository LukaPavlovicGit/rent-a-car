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

    public CompanyAverageRateList getTopRatedCompanies() throws IOException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String token = MainFrame.getInstance().getToken();

        Request request = new Request.Builder()
                .url(URL + "/reviews/top-rated-companies")
                .addHeader("authorization", "Bearer " + token)
                .get()
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        String json = response.body().string();
        response.body().close();

        if (response.code() == 200)
            return objectMapper.readValue(json, CompanyAverageRateList.class);

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

    public ReservationListDto getReservationsByClient() throws IOException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String token = MainFrame.getInstance().getToken();

        Request request = new Request.Builder()
                .url(URL + "/reservations/by-client")
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
        String token = MainFrame.getInstance().getToken();

        Request request = new Request.Builder()
                .url(URL + "/reservations/" + reservationId )
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

    public GetReviewListDto getReviews() throws IOException {
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
            return objectMapper.readValue(json, GetReviewListDto.class);

        throw new IOException();
    }

    public GetReviewListDto getReviewsByClient() throws IOException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String token = MainFrame.getInstance().getToken();


        Request request = new Request.Builder()
                .url(URL + "/reviews/by-client")
                .addHeader("authorization", "Bearer " + token)
                .get()
                .build();
        Call call = client.newCall(request);
        Response response = call.execute();
        String json = response.body().string();
        response.body().close();

        if (response.code() == 200)
            return objectMapper.readValue(json, GetReviewListDto.class);

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
    public void deleteCompany() throws IOException {
        String token = MainFrame.getInstance().getToken();

        Request request = new Request.Builder()
                .url(URL + "/companies")
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

    public void createVehicle(VehicleDto vehicleDto) throws IOException {
        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(vehicleDto));
        String token = MainFrame.getInstance().getToken();

        Request request = new Request.Builder()
                .url(URL + "/vehicles")
                .addHeader("authorization", "Bearer " + token)
                .post(body)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        response.body().close();

        if (!response.isSuccessful())
            throw new IOException();
    }

    public void deleteVehicle(String vehicleId) throws IOException {
        HttpUrl.Builder httpBuilder = HttpUrl.parse(URL + "/vehicles").newBuilder();
        httpBuilder.addQueryParameter("id", vehicleId);
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

    public void updateVehicle(String vehicleId, VehicleDto vehicleDto) throws IOException {
        HttpUrl.Builder httpBuilder = HttpUrl.parse(URL + "/vehicles").newBuilder();
        httpBuilder.addQueryParameter("id", vehicleId);
        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(vehicleDto));
        String token = MainFrame.getInstance().getToken();

        Request request = new Request.Builder()
                .url(httpBuilder.build())
                .addHeader("authorization", "Bearer " + token)
                .put(body)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        response.body().close();

        if (!response.isSuccessful())
            throw new IOException();
    }

    public VehiclesListDto getVehiclesByCompany() throws IOException {
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        String token = MainFrame.getInstance().getToken();

        Request request = new Request.Builder()
                .url(URL + "/vehicles/by-company")
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

    public void createReservation(ReservationDto reservationDto) throws IOException {
        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(reservationDto));
        String token = MainFrame.getInstance().getToken();

        Request request = new Request.Builder()
                .url(URL + "/reservations")
                .addHeader("authorization", "Bearer " + token)
                .post(body)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        response.body().close();

        if (!response.isSuccessful())
            throw new IOException();
    }

    public void createReview(PostReviewDto review) throws IOException {
        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(review));
        String token = MainFrame.getInstance().getToken();

        Request request = new Request.Builder()
                .url(URL + "/reviews")
                .addHeader("authorization", "Bearer " + token)
                .post(body)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        response.body().close();

        if (!response.isSuccessful())
            throw new IOException();
    }

    public void updateReview(String reviewId, PostReviewDto postReviewDto) throws IOException {
        RequestBody body = RequestBody.create(JSON, objectMapper.writeValueAsString(postReviewDto));
        String token = MainFrame.getInstance().getToken();

        Request request = new Request.Builder()
                .url(URL + "/reviews/" + reviewId )
                .addHeader("authorization", "Bearer " + token)
                .put(body)
                .build();

        Call call = client.newCall(request);
        Response response = call.execute();
        response.body().close();

        if (!response.isSuccessful())
            throw new IOException();
    }

    public void deleteReview(String reviewId) throws IOException {
        String token = MainFrame.getInstance().getToken();

        Request request = new Request.Builder()
                .url(URL + "/reviews/" + reviewId)
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
}
