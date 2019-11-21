package com.example.viarhungry.ui.calories;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viarhungry.MainActivity;
import com.example.viarhungry.R;

import java.util.ArrayList;

public class CalorieFragment extends Fragment implements View.OnClickListener {

    private RecyclerView foodList;
    private RecyclerView.Adapter foodAdapter;
    private MainActivity mainActivity;
    private EditText foodName;
    private EditText calories;
    private TextView calorie_total;





    private CalorieViewModel dashboardViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                ViewModelProviders.of(this).get(CalorieViewModel.class);

        View root = inflater.inflate(R.layout.fragment_calories, container, false);
        mainActivity = (MainActivity) getActivity();
        return root;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        foodList = view.findViewById(R.id.rv);
        foodList.hasFixedSize();
        foodList.setLayoutManager(new LinearLayoutManager(getActivity()));

        foodAdapter = new myAdaptor(new ArrayList<Food>());
        foodList.setAdapter(foodAdapter);

        Button addDrinkButton = (Button) view.findViewById(R.id.addFood);
        addDrinkButton.setOnClickListener(this);

        Button removeDrinkButton = (Button) view.findViewById(R.id.removeFood);
        removeDrinkButton.setOnClickListener(this);

        foodName = (EditText) view.findViewById(R.id.foodName);
        calories = (EditText) view.findViewById(R.id.caloriesNumber);
        calorie_total = (TextView) view.findViewById(R.id.totalCalories);


    }

    public void onPause() {
        mainActivity.setFoods(dashboardViewModel.getFoods());
        updateCalorieTotal();
        super.onPause();
    }

    public void onResume() {
        dashboardViewModel.setFoods(mainActivity.getFoods());
        updateCalorieTotal();
        super.onResume();
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.addFood: {
                addFood();
                break;
            }
            case R.id.removeFood: {
                removeFood();
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }
    }

    private void removeFood() {
        removeLastFood();
        updateCalorieTotal();
    }

    private void addFood() {
        String tempFName=foodName.getText().toString();
        String tempCalories=calories.getText().toString();

        if(tempFName.equals("")) tempFName = "Food";


        if(!tempCalories.equals("")) {

            Food newFood = new Food(tempFName, tempCalories);

            addFood(newFood);
            updateCalorieTotal();
        }
        else foodToast();
    }

    private void foodToast() {

        String text = "Ya can't eat air, dummy";

        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();


    }

    public void updateCalorieTotal(){
        calorie_total.setText(dashboardViewModel.updateCalorieTotal());
    }


    private class myAdaptor extends RecyclerView.Adapter {

        public myAdaptor(ArrayList<Food> newFoods){
            dashboardViewModel.setFoods(newFoods);
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            View view = inflater.inflate(R.layout.rec_layout,parent,false); //rec_layout stands for recycler layout

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ViewHolder.foodName.setText(dashboardViewModel.getFood(position).getName());
            ViewHolder.calorieCount.setText(dashboardViewModel.getFood(position).getCalories());
        }

        @Override
        public int getItemCount() {
            return dashboardViewModel.getFoods().size();
        }





    }

    static class ViewHolder extends RecyclerView.ViewHolder{

        static TextView foodName;
        static TextView calorieCount;

        ViewHolder(View itemView){
            super(itemView);
            foodName = itemView.findViewById(R.id.foodName);
            calorieCount = itemView.findViewById(R.id.caloriesNumber);
        }
    }


    public void addFood(Food Food){
        dashboardViewModel.addFood(Food);
        foodAdapter.notifyItemInserted(dashboardViewModel.getFoods().size()-1);
    }

    public void removeLastFood(){
        if(dashboardViewModel.getFoods().size()>0) {
            dashboardViewModel.removeLastFood();
            foodAdapter.notifyItemRemoved(dashboardViewModel.getFoods().size());
            foodAdapter.notifyItemRangeChanged(dashboardViewModel.getFoods().size(),dashboardViewModel.getFoods().size());

        }



    }
}