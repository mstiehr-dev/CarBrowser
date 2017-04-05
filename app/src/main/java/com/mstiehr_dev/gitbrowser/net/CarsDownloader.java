package com.mstiehr_dev.gitbrowser.net;

import com.mstiehr_dev.gitbrowser.model.Car;
import com.mstiehr_dev.gitbrowser.model.Driver;
import com.mstiehr_dev.gitbrowser.net.api.CarService;

import java.util.List;

import retrofit.RestAdapter;

public class CarsDownloader
{
    public static List<Car> downloadCars(String id)
    {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://192.168.178.45:8080").build();

        CarService carService = restAdapter.create(CarService.class);

        return carService.listCars(id);
    }

    public static List<Driver> downloadDrivers()
    {
        RestAdapter restAdapter = new RestAdapter.Builder().setEndpoint("http://192.168.178.45:8080").build();

        CarService carService = restAdapter.create(CarService.class);

        return carService.listDrivers();
    }
}
