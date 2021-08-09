package com.example.travelapp.Fragments;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.example.travelapp.Activities.FlightResult;
import com.example.travelapp.Classes.Flight;
import com.example.travelapp.R;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class FlightFragment extends Fragment {

    public static final String TAG ="FlightFragment";
    public static final String QUERY_URL = "QUERY_URL";

    DatePickerDialog d_selector;
    EditText etDepCity;
    EditText etDepDate;
    EditText etArrCity;
    Button btnSearch;


  public FlightFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_flight, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        etDepDate= view.findViewById(R.id.etDepDate);
        etDepCity=view.findViewById(R.id.etDepCity);
        etArrCity=view.findViewById(R.id.etArrCity);
        btnSearch=view.findViewById(R.id.btnSearch);

        etDepDate.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                final Calendar c1=Calendar.getInstance();
                int d= c1.get(Calendar.DAY_OF_MONTH);
                final int m=c1.get(Calendar.MONTH);
                int y=c1.get(Calendar.YEAR);
                d_selector=new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        //need to format date into yyyy-MM-dd => will be used for URL
                        Calendar cal= new GregorianCalendar(year,month,dayOfMonth);
                        Date myDate= cal.getTime();
                        String pattern = "yyyy-MM-dd";
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                        String date_with_zero= simpleDateFormat.format(myDate);
                        etDepDate.setText(date_with_zero);
                    }
                },y,m,d);
                d_selector.show();
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ArrCity=etArrCity.getText().toString();
                String DepCity=etDepCity.getText().toString();
                String d= etDepDate.getText().toString();

                if(ArrCity.isEmpty() || DepCity.isEmpty() || d.isEmpty()){
                    Toast.makeText(getContext(), "Provide cities and dates to search flight", Toast.LENGTH_SHORT).show();
                }else {
                    Intent i_flight = new Intent(getContext(), FlightResult.class);

                    String query_URL = "https://skyscanner-skyscanner-flight-search-v1.p.rapidapi.com/apiservices/browsedates/v1.0/US/USD/en-US/" + DepCity + "-sky/" + ArrCity + "-sky/" + d;
                    i_flight.putExtra(QUERY_URL, query_URL);
                    etArrCity.setText("");
                    etDepCity.setText("");
                    etDepDate.setText("");
                    startActivity(i_flight);
                }
            }
        });




    }


}