package com.mstiehr_dev.gitbrowser.net.api;

import com.mstiehr_dev.gitbrowser.model.Car;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Path;

public interface CarService
{
    @GET("/drivers/{driverId}/cars")
    List<Car> listCars(@Path("driverId") String driverId);
}
