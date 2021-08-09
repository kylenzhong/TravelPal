package com.example.travelapp.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.travelapp.Classes.Task;
import com.example.travelapp.Fragments.FlightFragment;
import com.example.travelapp.Fragments.ScheduleFragment;
import com.example.travelapp.R;
import com.example.travelapp.TaskDBHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    BottomNavigationView bnvMain;

    //create db manager and Task collection in MainActivity so they are accessible by both Flight and Calendar fragments.
    public static TaskDBHelper db_helper_instance;
    public static List<Task> task_collection;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        db_helper_instance=new TaskDBHelper(this);
        task_collection= new ArrayList<>();

        final FragmentManager fragmentManager = getSupportFragmentManager();
        // define your fragments here
        final Fragment flightFrag = new FlightFragment();
        final Fragment scheduleFrag = new ScheduleFragment();


        bnvMain=findViewById(R.id.bnvMain);



        bnvMain.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                switch (item.getItemId()) {
                    case R.id.menu_schedule:
                        fragment= scheduleFrag;
                        break;
                    case R.id.menu_flight:
                    default:
                        fragment=flightFrag;
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flMain,fragment).commit();
                return true;
            }
        });
        bnvMain.setSelectedItemId(R.id.menu_flight);
            }
}