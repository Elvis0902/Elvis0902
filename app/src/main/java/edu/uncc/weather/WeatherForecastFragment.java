package edu.uncc.weather;
/*
InClass08
WeatherForecastFragment
Andrew Brown
Mehk Heath
*/

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class WeatherForecastFragment extends Fragment {

    TextView forcastCity;
    LinearLayoutManager layoutManager;
    RecyclerView recyclerView;
    WeatherForecastAdapter adapter;
    ArrayList<Weather> weatherList = new ArrayList<>();

    public WeatherForecastFragment() {
        // Required empty public constructor
    }

    public static WeatherForecastFragment newInstance() {
        WeatherForecastFragment fragment = new WeatherForecastFragment();
        Bundle args = new Bundle();
       /* args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);*/
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_weather_forecast, container, false);

        weatherList = mWeatherForecastFragment.getWeatherArrayList();

        String city =  weatherList.get(0).getCity();

        forcastCity = view.findViewById(R.id.textViewForecastCity);
        forcastCity.setText(city);

        recyclerView = view.findViewById(R.id.recyclerViewForecast);
        //layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        //adapter = new WeatherForecastAdapter(expenses, this);//(this) is for importing itself for interface.
        adapter = new WeatherForecastAdapter(weatherList);
        recyclerView.setAdapter(adapter);

        return view;
    }

    WeatherForecastFragment.IWeatherForecastFragment mWeatherForecastFragment;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mWeatherForecastFragment = (WeatherForecastFragment.IWeatherForecastFragment) context;
    }

    interface IWeatherForecastFragment{
        ArrayList<Weather> getWeatherArrayList();
    }
}