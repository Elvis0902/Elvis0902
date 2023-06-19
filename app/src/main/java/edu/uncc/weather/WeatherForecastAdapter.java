package edu.uncc.weather;
/*
InClass08
WeatherForecastAdapter
Andrew Brown
Mehk Heath
*/
import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class WeatherForecastAdapter extends RecyclerView.Adapter<WeatherForecastAdapter.UserViewHolder> {

    //IRecycleViewExpensesFragAdapter mRecycleViewExpensesFragAdapter;
    ArrayList<Weather> weatherList;
    //RecycleViewExpensesFragAdapter.IRecycleViewExpensesFragAdapter mRecycleViewExpensesFragAdapter;

    public WeatherForecastAdapter(ArrayList<Weather> data) {
        //this.mRecycleViewExpensesFragAdapter = adapter;
        this.weatherList = data;
        //mRecycleViewExpensesFragAdapter = adapter;
        //mRecycleViewExpensesFragAdapter = adapter;
    }

    @NonNull
    @Override
    public WeatherForecastAdapter.UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.forecast_item, parent, false);
        WeatherForecastAdapter.UserViewHolder userViewHolder = new WeatherForecastAdapter.UserViewHolder(view);
        return userViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherForecastAdapter.UserViewHolder holder, @SuppressLint("RecyclerView") int position) {
        /*String user = users.get(position);
        holder.textView.setText(user);

        holder.sortViewContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Log.d("demo", "onClick: " + position);
                mUserRecyclerViewAdapter.sortByPositionClicked(position);
            }
        });*/
        if (!weatherList.isEmpty()) {
            //holder.textName.setText(users.get(position).name);
            holder.temp.setText(weatherList.get(position).getTemp()+holder.itemView.getContext().getString(R.string.f_filler));
            holder.tempMax.setText(holder.itemView.getContext().getString(R.string.text_temp_max_filler) + weatherList.get(position).getTempMax()+holder.itemView.getContext().getString(R.string.f_filler));
            holder.tempMin.setText(holder.itemView.getContext().getString(R.string.text_temp_min_filler) + weatherList.get(position).getTempMin()+holder.itemView.getContext().getString(R.string.f_filler));
            holder.humid.setText(holder.itemView.getContext().getString(R.string.humid_text_filler) + weatherList.get(position).getHumidity() + holder.itemView.getContext().getString(R.string.percent_filler));
            holder.cloud.setText(weatherList.get(position).getCloudiness());
            holder.date.setText(weatherList.get(position).getDate());

            String icon = weatherList.get(position).getIcon();

            //Using picasso to load image
            Picasso.get()
                    .load("https://openweathermap.org/img/wn/" + icon + "@2x.png")
                    .resize(40, 40)
                    .centerCrop()
                    .into(holder.weatherDisplay);
        }
    }

    @Override
    public int getItemCount() {
        return this.weatherList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        //TextView textView = itemView.findViewById(R.id.textViewSortText);
        // View sortViewContainer = itemView.findViewById(R.id.viewSortContainer);
        TextView temp = itemView.findViewById(R.id.textViewForecastTemp);
        TextView tempMax = itemView.findViewById(R.id.textViewForecastTempMax);
        TextView tempMin = itemView.findViewById(R.id.textViewForecastTempMin);
        TextView humid = itemView.findViewById(R.id.textViewForecastHumidity);
        TextView cloud = itemView.findViewById(R.id.textViewForecastCloudiness);
        ImageView weatherDisplay = itemView.findViewById(R.id.imageViewForecastDisplay);
        TextView date = itemView.findViewById(R.id.textViewForecastDate);

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}

