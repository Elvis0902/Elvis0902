package edu.uncc.weather;
/*
InClass08
MainActivity
Andrew Brown
Mehk Heath
*/

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class MainActivity extends AppCompatActivity implements CitiesFragment.CitiesFragmentListener, CurrentWeatherFragment.ICurrentWeatherFragment, WeatherForecastFragment.IWeatherForecastFragment {

    private final OkHttpClient client = new OkHttpClient();
    final String TAG = "demo";
    final String APIKEY = "6be1f9cd57b9855cdf252e533729c36f";
    ArrayList<Weather> weatherList = new ArrayList<>();

    DataService.City city;

    Weather weather;

    String tempMax;
    String temp;
    String tempMin;
    String description;
    String humidity;
    String windSpeed;
    String windDegree;
    String cloudiness;
    String cityHolder;
    String icon;
    String date;

    String latWeather;
    String lonWeather;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new CitiesFragment())
                .commit();

        //print out current weather PLEASE
        //getLatLon();
    }

    @Override
    public void gotoCurrentWeather(DataService.City city) {
        // disable to show temp in log screen
        this.city = city;

        cityHolder = city.getCity();
        getLatLon(cityHolder);
    }

    public void getLatLon(String city) {
        //Log.d(TAG, "getLatLon: Start");
        /*String city = "Charlotte";
        String country = "US";*/

        Request request = new Request.Builder()
                //.url("https://api.openweathermap.org/geo/1.0/direct?q=Charlotte,US&appid=6be1f9cd57b9855cdf252e533729c36f")
                .url("https://api.openweathermap.org/geo/1.0/direct?q=" + city + "&appid=" + APIKEY)
                .build();


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                //Log.d(TAG, "onFailure: Failed");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Log.d(TAG, "onResponse: Worked");
                //if response is successful get response body
                if (response.isSuccessful()) {

                    ///////////////new///////////////////////////////////////////////
                    //gets surronded in try catch on make.
                    try {

                        JSONArray jsonArray = new JSONArray(response.body().string());
                        JSONObject jsonObject = jsonArray.getJSONObject(0);
                        //JSONObject json = new JSONObject(response.body().string());
                        //Person person = new Person();
                        //person.setName(json.getString("name")); //using json to fill in info for person

                        ///to get a json object from within a json object
                        //JSONObject address = json.getJSONObject("address");
                        //Log.d(TAG, "onResponse: " + jsonObject.getString("name"));
                        //Log.d(TAG, "onResponse: " + jsonObject.getString("lat"));
                        //Log.d(TAG, "onResponse: " + jsonObject.getString("lon"));
                        //storing lat and log to use in another api
                        latWeather = jsonObject.getString("lat").toString();
                        lonWeather = jsonObject.getString("lon");
                        getCurrentWeather();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    public void getForecastWeather(){
        //Log.d(TAG, "getForecastWeather: Start");

        Request request = new Request.Builder()
                //api.openweathermap.org/data/2.5/forecast?lat={lat}&lon={lon}&appid={API key}
                .url("https://api.openweathermap.org/data/2.5/forecast?lat=" + latWeather + "&lon=" + lonWeather + "&units=imperial&appid=" + APIKEY)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
               // Log.d(TAG, "onFailure: Failed");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //Log.d(TAG, "onResponse: Worked");
                //if response is successful get response body
                if (response.isSuccessful()) {
                    weatherList = new ArrayList<>();
                    ///////////////new///////////////////////////////////////////////
                    //gets surronded in try catch on make.
                    try {
                        /*JSONArray jsonArray = new JSONArray(response.body().string());
                        JSONObject jsonObject = jsonArray.getJSONObject(0);*/

                        JSONObject jsonObject = new JSONObject(response.body().string());
                        JSONArray jsonArray = jsonObject.getJSONArray("list");
                        //Log.d(TAG, "onResponse: array length = " + jsonArray.length());
                        for (int i = 0; i < jsonArray.length(); i++) {

                            temp = jsonArray.getJSONObject(i).getJSONObject("main").getString("temp");
                            tempMax = jsonArray.getJSONObject(i).getJSONObject("main").getString("temp_max");
                            tempMin = jsonArray.getJSONObject(i).getJSONObject("main").getString("temp_min");
                            description = jsonArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("description");
                            humidity = jsonArray.getJSONObject(i).getJSONObject("main").getString("humidity");
                            windSpeed =  jsonArray.getJSONObject(i).getJSONObject("wind").getString("speed");
                            windDegree = jsonArray.getJSONObject(i).getJSONObject("wind").getString("deg");
                            cloudiness = jsonArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("main");
                            icon = jsonArray.getJSONObject(i).getJSONArray("weather").getJSONObject(0).getString("icon");
                            date = jsonArray.getJSONObject(i).getString("dt_txt");

                            weather = new Weather(cityHolder, temp, tempMax, tempMin, description, humidity, windSpeed, windDegree, cloudiness, icon);
                            weather.setDate(date);
                            weatherList.add(weather);

                            //Log.d(TAG, "onResponse: date = " + date);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    getCurrentWeather();
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.rootView, WeatherForecastFragment.newInstance())
                            .addToBackStack(null)
                            .commit();

                }
            }
        });
    }

    public void getCurrentWeather() {
        Log.d(TAG, "getCurrentWeather: Start");


        Request request = new Request.Builder()
                //.url("https://api.openweathermap.org/geo/1.0/direct?q=Charlotte,US&appid=6be1f9cd57b9855cdf252e533729c36f")
                //.url("https://api.openweathermap.org/data/2.5/weather?lat=" + latWeather + "&lon=" + lonWeather + "&units=imperial&appid=" + APIKEY)
                .url("https://api.openweathermap.org/data/2.5/weather?lat="+latWeather+"&lon="+lonWeather+"&units=imperial&appid=6be1f9cd57b9855cdf252e533729c36f")
                .build();
        Log.d(TAG, "getCurrentWeather: " +latWeather);

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Log.d(TAG, "onFailure: Failed");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG, "onResponse: Worked");
                //if response is successful get response body
                if (response.isSuccessful()) {

                    ///////////////new///////////////////////////////////////////////
                    //gets surronded in try catch on make.
                    try {

                        /*JSONArray jsonArray = new JSONArray(response.body().string());
                        JSONObject jsonObject = jsonArray.getJSONObject(0);*/

                        JSONObject jsonObject = new JSONObject(response.body().string());
                        temp = jsonObject.getJSONObject("main").getString("temp");
                        tempMax = jsonObject.getJSONObject("main").getString("temp_max");
                        tempMin = jsonObject.getJSONObject("main").getString("temp_min");
                        description = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
                        humidity = jsonObject.getJSONObject("main").getString("humidity");
                        windSpeed =  jsonObject.getJSONObject("wind").getString("speed");
                        windDegree = jsonObject.getJSONObject("wind").getString("deg");
                        cloudiness = jsonObject.getJSONArray("weather").getJSONObject(0).getString("main");
                        icon = jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");

                        weather = new Weather(cityHolder, temp, tempMax, tempMin, description, humidity, windSpeed, windDegree, cloudiness, icon);

                        Log.d(TAG, "onResponse: temp = " + icon);

                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.rootView, CurrentWeatherFragment.newInstance(city))
                                .addToBackStack(null)
                                .commit();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public Weather getWeather() {
        return weather;
    }

    @Override
    public void goToForecastFragment() {
        //this is in inside of the getForcastWeather so the api can run then swap.
        /*getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, WeatherForecastFragment.newInstance())
                .addToBackStack(null)
                .commit();*/
        setTitle("Weather Forecast");
        getForecastWeather();
    }

    @Override
    public ArrayList<Weather> getWeatherArrayList() {
        return weatherList;
    }

   /* private final OkHttpClient client = new OkHttpClient();

    public void run() throws Exception {
        Request request = new Request.Builder()
                .url("http://publicobject.com/helloworld.txt")
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override public void onResponse(Call call, Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                    System.out.println(responseBody.string());
                }
            }
        });
    }
*/
}