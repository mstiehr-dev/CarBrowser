package com.mstiehr_dev.gitbrowser.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.mstiehr_dev.gitbrowser.R;
import com.mstiehr_dev.gitbrowser.model.Car;
import com.mstiehr_dev.gitbrowser.model.Driver;
import com.mstiehr_dev.gitbrowser.net.CarsDownloader;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";

    private List<String> carNames = new ArrayList<>();
    private List<Driver> drivers = new ArrayList<>();
    private CarAdapter carAdapter;
    private DriverSpinnerAdapter driverAdapter;
    ListView listView;
    Spinner spinner;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();

        initDriverIDs();

        carAdapter = new CarAdapter(this, android.R.layout.simple_list_item_1, carNames);
        driverAdapter = new DriverSpinnerAdapter(this, android.R.layout.simple_spinner_item);

        listView.setEmptyView(findViewById(R.id.tv_empty));
        listView.setAdapter(carAdapter);

        spinner.setAdapter(driverAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String idStr = Long.toString(drivers.get(position).getId());

                Log.d(TAG, "onItemClick: " + idStr);
                new BackgroundTask().execute(new String[]{idStr});
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onNothingSelected: ");
            }
        });

        progressBar.setVisibility(View.INVISIBLE);
    }

    private void initViews()
    {
        listView = (ListView) findViewById(R.id.listview);
        spinner = (Spinner) findViewById(R.id.spinner);
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
    }


    class DriverSpinnerAdapter extends ArrayAdapter<String>
    {
        public DriverSpinnerAdapter(@NonNull Context context, @LayoutRes int resource) {
            super(context, resource);
        }

        @Override
        public int getCount() {
            return drivers.size();
        }



        @Nullable
        @Override
        public String getItem(int position) {
            return drivers.get(position).getUsername();
        }
    }


    class BackgroundTask extends AsyncTask<String, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(String... ids) {
            String id = ids[0];

            carNames.clear();
            List<Car> allCars = CarsDownloader.downloadCars(id);
            for(Car c : allCars)
            {
                carNames.add(c.getDescription());
            }

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            carAdapter.notifyDataSetChanged();
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    class CarAdapter extends ArrayAdapter<String>
    {
        public CarAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
        }
    }

    private void initDriverIDs()
    {
        new DriverDownloadTask().execute();
    }

    class DriverDownloadTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Void doInBackground(Void... params)
        {
            drivers.clear();
            drivers.addAll(CarsDownloader.downloadDrivers());

            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            driverAdapter.notifyDataSetChanged();

            progressBar.setVisibility(View.INVISIBLE);
        }
    }
}
