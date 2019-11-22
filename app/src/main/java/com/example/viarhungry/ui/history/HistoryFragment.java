package com.example.viarhungry.ui.history;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viarhungry.MainActivity;
import com.example.viarhungry.R;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private HistoryViewModel historyViewModel;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter trackedDayAdapter;
    private MainActivity mainActivity;



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

        historyViewModel.addToday(mainActivity.getFoods(),mainActivity.getCupsDrunk());

        trackedDayAdapter.notifyItemInserted(historyViewModel.getSize()-1);

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