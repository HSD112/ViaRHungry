package com.example.viarhungry.ui.history;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viarhungry.MainActivity;
import com.example.viarhungry.R;
import com.example.viarhungry.retrofit.NetworkClient;
import com.example.viarhungry.retrofit.WResponse;
import com.example.viarhungry.retrofit.WeatherAPIs;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.*;

public class HistoryFragment extends Fragment implements View.OnClickListener {

    private HistoryViewModel historyViewModel;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter trackedDayAdapter;
    private MainActivity mainActivity;

    private TextView responseText;

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = database.getReference();



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        historyViewModel =
                ViewModelProviders.of(this).get(HistoryViewModel.class);
        View root = inflater.inflate(R.layout.fragment_history, container, false);

        mainActivity = (MainActivity) getActivity();

        return root;
    }

    public void onViewCreated(View view, Bundle savedInstanceState) {

        recyclerView = view.findViewById(R.id.historyRecyclerView);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        trackedDayAdapter = new HistoryFragment.myAdaptor(new ArrayList<TrackedDay>());
        recyclerView.setAdapter(trackedDayAdapter);

        historyViewModel.addToday(mainActivity.getFoods(), mainActivity.getCupsDrunk());

        trackedDayAdapter.notifyItemInserted(historyViewModel.getSize() - 1);

        responseText = view.findViewById(R.id.weatherText);
        responseText.setOnClickListener(this);

        myRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String tempString = (String) dataSnapshot.getValue();

                Log.d("DEBUG", tempString);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }


        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.weatherText: {

                saveData(); // just to have a repeatable test

                fetchWeatherDetails(); // in my case ill only get wind speed and wind direction in degrees
                Log.e("Debug","Tapped");
                break;
            }
        }

    }


    //doesn't work. It fails at making, or receiving the call ...
            //Wait does my virtual phone even have internet ??
                //Yes it does. Ill try debugging after I implement all the mandatory stuff.

    private void fetchWeatherDetails() {
        //Obtain an instance of Retrofit by calling the static method.
        Retrofit retrofit = NetworkClient.getRetrofitClient();
        /*
        The main purpose of Retrofit is to create HTTP calls from the Java interface based on the annotation associated with each method. This is achieved by just passing the interface class as parameter to the create method
        */
        WeatherAPIs weatherAPIs = retrofit.create(WeatherAPIs.class);
        /*
        Invoke the method corresponding to the HTTP request which will return a Call object. This Call object will used to send the actual network request with the specified parameters
        */
        Call call = weatherAPIs.getWeatherByCity("London", "fdca55d0157703ce04764968d5435ee9");
        /*
        This is the line which actually sends a network request. Calling enqueue() executes a call asynchronously. It has two callback listeners which will invoked on the main thread
        */
        call.enqueue(new Callback() {
            @Override
            public void onResponse(Call call, Response response) {
                Log.e("Response","Success");
                /*This is the success callback. Though the response type is JSON, with Retrofit we get the response in the form of WResponse POJO class
                 */
                if (response.body() != null) {
                    WResponse wResponse = (WResponse) response.body();

                    String responseString = "Wind speed: " +wResponse.getMain().getSpeed()+
                            " Wind direction: " +wResponse.getMain().getDeg() + " degrees"; //android told me not to create the string directly in the set text :l

                    responseText.setText(responseString);

                    saveData();
                }
            }

            @Override
            public void onFailure(Call call, Throwable t) {

                Log.e("Response","Failure");
                /*
                Error callback */
            }
        });
    }

    private class myAdaptor extends RecyclerView.Adapter {

        public myAdaptor(ArrayList<TrackedDay> newList){
            historyViewModel.setList(newList);
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            View view = inflater.inflate(R.layout.history_list_item,parent,false); //foodListItem stands for recycler layout

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ViewHolder.waterCups.setText(historyViewModel.getDay(position).getWaterCups());
            ViewHolder.calorieCount.setText(historyViewModel.getDay(position).getCalories());
            ViewHolder.date.setText(historyViewModel.getDay(position).getDate());
        }

        @Override
        public int getItemCount() {
            return historyViewModel.getSize();
        }





    }

    private void saveData() { //save weather data whenever it is updated
        myRef.push().setValue(responseText.getText().toString());

    }


    static class ViewHolder extends RecyclerView.ViewHolder{

        static TextView calorieCount;
        static TextView waterCups;
        static TextView date;

        ViewHolder(View itemView){
            super(itemView);
            calorieCount = itemView.findViewById(R.id.historyTotalCalories);
            waterCups = itemView.findViewById(R.id.historyCupsOfWater);
            date = itemView.findViewById(R.id.date);
        }
    }


}