package com.example.viarhungry.ui.water;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.viarhungry.MainActivity;
import com.example.viarhungry.R;
import com.github.jinatonic.confetti.CommonConfetti;


public class WaterFragment extends Fragment implements View.OnClickListener {

    private TextView cupsDrunk;
    private ProgressBar bar;
    private MainActivity mainActivity;


    // The onCreateView method is called when Fragment should create its View object hierarchy,
    // either dynamically or via XML layout inflation.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        //links the main activity with this fragment, because I want data persistence.
        mainActivity = (MainActivity) getActivity();
        // Defines the xml file for the fragment
        return inflater.inflate(R.layout.fragment_water, parent, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {

        // Setup any handles to view objects here
        // EditText etFoo = (EditText) view.findViewById(R.id.etFoo);

        Button addDrinkButton = (Button) view.findViewById(R.id.addButton);
        addDrinkButton.setOnClickListener(this);

        Button removeDrinkButton = (Button) view.findViewById(R.id.removeButton);
        removeDrinkButton.setOnClickListener(this);

        cupsDrunk = (TextView) view.findViewById(R.id.cupsDrunk);
        bar = (ProgressBar) view.findViewById(R.id.progressBar);


    }

    @Override
    public void onPause() {
        mainActivity.setCupsDrunk(cupsDrunk.getText().toString());
        mainActivity.setBar(bar.getProgress());
        super.onPause();
    }

    public void onResume() {
        cupsDrunk.setText(mainActivity.getCupsDrunk());
        bar.setProgress(mainActivity.getBar());
        super.onResume();
    }

    @Override
    public void onClick(View v) {

        //choose what happens on click based on the button id
        switch (v.getId()) {

            case R.id.addButton: {
                addWater();
                break;
            }
            case R.id.removeButton: {
                removeWater();
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + v.getId());
        }

        if (bar.getProgress() >= 8) {

            // do confetti whenever more than 8 cups of water have been drunk
            CommonConfetti.rainingConfetti(
                    ((ViewGroup) getView().getParent()) //gets parent to know where to display confetti
                    , new int[]{Color.BLACK, Color.RED, Color.GREEN, Color.BLUE}) // array of colors used
                    .stream(1000); //duration

        }
    }

    private void removeWater() {
        String temp = cupsDrunk.getText().toString();

        if (!temp.equals("")) {
            int tempInt = Integer.parseInt(temp);
            if (tempInt > 0) {
                tempInt--;
                cupsDrunk.setText("" + tempInt); // this is fine :) ; removes 1 from cup counter
                bar.setProgress(tempInt);
            } else {
                peeToast();
            }
        } else
            throw new IllegalStateException("Unexpected value: " + cupsDrunk.getId());
    }

    private void addWater() {
        String temp = cupsDrunk.getText().toString();

        if (!temp.equals("")) {
            int tempInt = Integer.parseInt(temp);
            tempInt++;
            cupsDrunk.setText("" + tempInt); // this is fine ; adds 1 to cup counter
            bar.setProgress(tempInt);

        } else
            throw new IllegalStateException("Unexpected value: " + cupsDrunk.getId());


    }

    private void peeToast() {

        String text = "This isn't a pee monitor app ...";

        Toast.makeText(getContext(), text, Toast.LENGTH_SHORT).show();


    }
}