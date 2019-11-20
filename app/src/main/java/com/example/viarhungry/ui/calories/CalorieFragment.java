package com.example.viarhungry.ui.calories;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.viarhungry.MainActivity;
import com.example.viarhungry.R;

import java.text.CollationElementIterator;
import java.util.ArrayList;

public class CalorieFragment extends Fragment implements View.OnClickListener {

    private RecyclerView foodList;
    private RecyclerView.Adapter foodAdapter;
    private MainActivity mainActivity;
    private TextView foodName;
    private TextView calories;
    public ArrayList<food> newFoods = new ArrayList<>();




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

        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);

        foodList = view.findViewById(R.id.rv);
        foodList.hasFixedSize();
        foodList.setLayoutManager(new LinearLayoutManager(getActivity()));


        Log.e("onViewCreated",""+ newFoods.size());



        foodAdapter = new foodAdapterMethod(newFoods);
        foodList.setAdapter(foodAdapter);

        Button addDrinkButton = (Button) view.findViewById(R.id.addFood);
        addDrinkButton.setOnClickListener(this);

        Button removeDrinkButton = (Button) view.findViewById(R.id.removeFood);
        removeDrinkButton.setOnClickListener(this);

        foodName = (TextView) view.findViewById(R.id.foodName);
        calories = (TextView) view.findViewById(R.id.caloriesNumber);


    }

    public void onPause() {
        mainActivity.setFoods(newFoods);
        super.onPause();
    }

    public void onResume() {
        newFoods = mainActivity.getFoods();

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
        Log.e("REMOVE FOOD","" + newFoods.size());
        if(newFoods.size()>0){
            Log.e("REMOVE FOOD","" + newFoods.size());
            newFoods.remove(newFoods.size()-1);
            foodList.removeViewAt(newFoods.size()); // list already went down by one so removed "-1"
            foodAdapter.notifyItemRemoved(newFoods.size());
            foodAdapter.notifyItemRangeChanged(newFoods.size(),newFoods.size());
            //removes the top item from the list
        }
    }

    private void addFood() {
        //String fn = foodName.getText().toString();
        //String cal =calories.getText().toString();

        newFoods.add(new food("name", "100"));
        foodAdapter.notifyItemRangeChanged(newFoods.size(),newFoods.size());
    }


    private class foodAdapterMethod extends RecyclerView.Adapter {

        private ArrayList<food> foods;

        public foodAdapterMethod(ArrayList<food> foods){
            this.foods=foods;
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
            ViewHolder.foodName.setText(foods.get(position).getName());
            ViewHolder.calorieCount.setText(foods.get(position).getCalories());
        }

        @Override
        public int getItemCount() {
            return foods.size();
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

    public class food{
        private String name;
        private String calories;

        food(String name,String cal){
            this.name   =name;
            calories    =cal;
        }

        public String getName(){
            return name;
        }
        public String getCalories(){
            return calories;
        }
    }
}