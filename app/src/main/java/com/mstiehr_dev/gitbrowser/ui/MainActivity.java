package com.mstiehr_dev.gitbrowser.ui;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.IdRes;
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
import android.widget.SpinnerAdapter;

import com.mstiehr_dev.gitbrowser.R;
import com.mstiehr_dev.gitbrowser.model.Car;
import com.mstiehr_dev.gitbrowser.net.CarsDownloader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
{
    private static final String TAG = "MainActivity";

    private List<String> carNames = new ArrayList<>();
    private List<String> driverIds = new ArrayList<>();
    private CarAdapter carAdapter;
    ListView listView;
    Spinner spinner;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDriverIDs();

        carAdapter = new CarAdapter(this, android.R.layout.simple_list_item_1, carNames);

        listView = (ListView) findViewById(R.id.listview);
        listView.setEmptyView(findViewById(R.id.tv_empty));
        listView.setAdapter(carAdapter);

        spinner = (Spinner) findViewById(R.id.spinner);
        spinner.setAdapter(new DriverSpinnerAdapter(this, android.R.layout.simple_spinner_item, driverIds));
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String idStr = driverIds.get(position);

                Log.d(TAG, "onItemClick: " + idStr);
                new BackgroundTask().execute(new String[]{idStr});
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "onNothingSelected: ");
            }
        });

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);
    }



    class DriverSpinnerAdapter extends ArrayAdapter<String>
    {
        public DriverSpinnerAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<String> objects) {
            super(context, resource, objects);
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
        String allIds = "3\n" +
                "4\n" +
                "5\n" +
                "6\n" +
                "7\n" +
                "8\n" +
                "9\n" +
                "38\n" +
                "39\n" +
                "40\n" +
                "41\n" +
                "42\n" +
                "43\n" +
                "44\n" +
                "45\n" +
                "979\n" +
                "980\n" +
                "981\n" +
                "982\n" +
                "983\n" +
                "984\n" +
                "985\n" +
                "986\n" +
                "987\n" +
                "988\n" +
                "989\n" +
                "990\n" +
                "991\n" +
                "992\n" +
                "995\n" +
                "997\n";
        driverIds.addAll(Arrays.asList(allIds.split("\n")));
    }
}
