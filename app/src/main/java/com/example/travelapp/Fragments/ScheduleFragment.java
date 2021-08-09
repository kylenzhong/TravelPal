package com.example.travelapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.travelapp.Activities.DailyActivity;
import com.example.travelapp.R;

import java.util.Calendar;

public class ScheduleFragment extends Fragment {

    public static final String TAG="ScheduleFragment";
    public static final String SELECTED_DATE="selected_date";
    CalendarView cvSchedule;



    public ScheduleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_schedule, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        cvSchedule=view.findViewById(R.id.cvSchedule);
        cvSchedule.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                //converting to date to long (to be shipped via intent)
                Calendar c3=Calendar.getInstance();
                c3.set(year,month,dayOfMonth);
                long selected_date_in_long= c3.getTime().getTime();

                Intent i = new Intent(getContext(), DailyActivity.class);
                i.putExtra(SELECTED_DATE,selected_date_in_long);
                Log.i(TAG, "onSelectedDayChange: "+ selected_date_in_long);
                startActivity(i);
            }
        });



    }
}