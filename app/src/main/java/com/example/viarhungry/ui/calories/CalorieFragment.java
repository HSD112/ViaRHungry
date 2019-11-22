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

    private RecyclerView.Adapter foodAdapter;
    private EditText foodName;
    private EditText calories;
    private TextView calorie_total;
    private MainActivity mainActivity;





    private CalorieViewModel CalorieViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CalorieViewModel =
                ViewModelProviders.of(this).get(CalorieViewModel.class);

        View root = inflater.inflate(R.layout.fragment_calories, container, false);

        mainActivity = (MainActivity) getActivity();

        return root;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        RecyclerView recyclerView = view.findViewById(R.id.rv);
        recyclerView.hasFixedSize();
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        foodAdapter = new myAdaptor(new ArrayList<Food>());
        recyclerView.setAdapter(foodAdapter);

        Button addDrinkButton = (Button) view.findViewById(R.id.addFood);
        addDrinkButton.setOnClickListener(this);

        Button removeDrinkButton = (Button) view.findViewById(R.id.removeFood);
        removeDrinkButton.setOnClickListener(this);

        foodName = (EditText) view.findViewById(R.id.foodName);
        calories = (EditText) view.findViewById(R.id.caloriesNumber);
        calorie_total = (TextView) view.findViewById(R.id.totalCalories);

    }

    public void onResume() {
        super.onResume();
        calorie_total.setText(mainActivity.getCalories());

        foodAdapter.notifyDataSetChanged();
        foodAdapter.notifyItemInserted(CalorieViewModel.getFoods().size()-1);
    }

    public void onPause(){
        super.onPause();

        updateCalorieTotal();
        mainActivity.setCalories(calorie_total.getText().toString());

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
        mainActivity.setFoods(CalorieViewModel.getFoods());
    }

    private void addFood() {
        String tempFName=foodName.getText().toString();
        String tempCalories=calories.getText().toString();

        if(!CalorieViewModel.addFood(tempFName,tempCalories))
            foodToast();
        foodAdapter.notifyItemInserted(CalorieViewModel.getFoodSize()-1);
        updateCalorieTotal();
        mainActivity.setFoods(CalorieViewModel.getFoods());
    }

    private void foodToast() {

        String text = "Ya can't eat air, dummy";

        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();


    }

    public void updateCalorieTotal(){
        calorie_total.setText(CalorieViewModel.updateCalorieTotal());
    }

    public String getCalorieTotal(){
        return CalorieViewModel.updateCalorieTotal();
    }


    private class myAdaptor extends RecyclerView.Adapter {

        public myAdaptor(ArrayList<Food> newFoods){
            CalorieViewModel.setFoods(newFoods);
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(parent.getContext());

            View view = inflater.inflate(R.layout.food_list_item,parent,false); //foodListItem stands for recycler layout

            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ViewHolder.foodName.setText(CalorieViewModel.getFood(position).getName());
            ViewHolder.calorieCount.setText(CalorieViewModel.getFood(position).getCalories());
        }

        @Override
        public int getItemCount() {
            return CalorieViewModel.getFoods().size();
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

    public void removeLastFood(){
        if(CalorieViewModel.getFoods().size()>0) {
            CalorieViewModel.removeLastFood();
            foodAdapter.notifyItemRemoved(CalorieViewModel.getFoods().size());
            foodAdapter.notifyItemRangeChanged(CalorieViewModel.getFoods().size(), CalorieViewModel.getFoods().size());

        }



    }
}