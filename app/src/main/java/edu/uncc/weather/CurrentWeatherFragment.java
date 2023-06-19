package edu.uncc.weather;
/*
InClass08
CurrentWeatherFragment
Andrew Brown
Mehk Heath
*/

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.squareup.picasso.Picasso;

import java.net.URL;

import edu.uncc.weather.databinding.FragmentCurrentWeatherBinding;

public class CurrentWeatherFragment extends Fragment {
    private static final String ARG_PARAM_CITY = "ARG_PARAM_CITY";
    private DataService.City mCity;
    final String TAG = "demo";
    Weather weatherHolder;
    FragmentCurrentWeatherBinding binding;

    public CurrentWeatherFragment() {
        // Required empty public constructor
    }

    public static CurrentWeatherFragment newInstance(DataService.City city) {
        CurrentWeatherFragment fragment = new CurrentWeatherFragment();
        Bundle args = new Bundle();
        //args.putSerializable(ARG_PARAM_CITY, city);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Log.d(TAG, "onCreate: CurrentWeatherFragment WORKING");
        if (getArguments() != null) {
            mCity = (DataService.City) getArguments().getSerializable(ARG_PARAM_CITY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCurrentWeatherBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getContext().getString(R.string.title_filler_01));
        try {
            //Log.d(TAG, "onViewCreated: test02");
            weatherHolder = mCurrentWeatherFragment.getWeather();
            //Log.d(TAG, "onViewCreated: Test03 " + weatherHolder.city);
        } catch (Exception e) {

        }
        //Log.d(TAG, "onViewCreated: " + weatherHolder.getCity());
        binding.textViewCityName.setText(weatherHolder.getCity());
        binding.textViewTemp.setText(weatherHolder.getTemp());
        binding.textViewTempMax.setText(weatherHolder.getTempMax());
        binding.textViewTempMin.setText(weatherHolder.getTempMin());
        binding.textViewDescription.setText(weatherHolder.getDescription());
        binding.textViewHumidity.setText(weatherHolder.getHumidity());
        binding.textViewWindSpeed.setText(weatherHolder.getWindSpeed());
        binding.textViewWindDegree.setText(weatherHolder.getWindDegree());
        binding.textViewCloudiness.setText(weatherHolder.getCloudiness());
        String icon = weatherHolder.getIcon();

        //Using picasso to load image
        Picasso.get()
                .load("https://openweathermap.org/img/wn/" + icon + "@2x.png")
                .resize(40, 40)
                .centerCrop()
                .into(binding.imageViewCloud);

        //button to go to forcast page
        binding.buttonCheckForecast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCurrentWeatherFragment.goToForecastFragment();
            }
        });
    }

    ICurrentWeatherFragment mCurrentWeatherFragment;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mCurrentWeatherFragment = (CurrentWeatherFragment.ICurrentWeatherFragment) context;
    }

    interface ICurrentWeatherFragment{
        Weather getWeather();
        void goToForecastFragment();
    }
}