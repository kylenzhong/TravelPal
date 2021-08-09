package com.example.travelapp.Classes;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class Flight {

    private static final String TAG = "Flight";
    public static ArrayList<Flight> result_collection;
    static HashMap<Integer,String> carrier_lookup;

    String carrier;
    String departure_date;
    int price;
    String depCity;
    String arrCity;


    public Flight(int price,String departure_date,String carrier,String depCity,String arrCity) {
        this.price = price;
        this.carrier=carrier;
        this.departure_date=departure_date;
        this.arrCity=arrCity;
        this.depCity=depCity;
    }

    public static ArrayList<Flight> fromJobjToFlightArr(JSONObject jobj) throws JSONException {
        //populate hashmap code for airline carrier
        JSONArray jarr_carrier=jobj.getJSONArray("Carriers");
        from_carrier_arr_to_hm(jarr_carrier);

        //get departing and arriving city information
        JSONArray jarr_places=jobj.getJSONArray("Places");
        JSONObject jobj_depcity=jarr_places.getJSONObject(1);
        String departing_city= jobj_depcity.getString("CityName");
        JSONObject jobj_arrcity=jarr_places.getJSONObject(0);
        String arriving_city= jobj_arrcity.getString("CityName");


        //get all parameters ready to be constructed into a Flight object
        JSONArray jarr_quote=jobj.getJSONArray("Quotes");
        result_collection =new ArrayList<>();               //todo: static method or array okay? what happen to next search? => need to clear after each use.

        for(int i=0;i<jarr_quote.length();i++){
            //get price info
            JSONObject individual_flight= jarr_quote.getJSONObject(i);
            int price= individual_flight.getInt("MinPrice");

            //get departure date
            JSONObject outbound_obj= individual_flight.getJSONObject("OutboundLeg");
            String departure_date= outbound_obj.getString("DepartureDate");
            //formating date to yyyy-mm-dd with substring.
            String processed_d_date= departure_date.substring(0,10);


            //get carrier and convert to corresponding airline name
            JSONArray c_id_arr= outbound_obj.getJSONArray("CarrierIds");
            int carrier_id=c_id_arr.getInt(0);
            String airline= carrierIdToName(carrier_id);


            //construct Flight object
            Flight single_flight_object= new Flight(price,processed_d_date,airline,departing_city,arriving_city);

            //add Flight object to result_collection
            result_collection.add(single_flight_object);
        }
        return result_collection;
    }

    private static String carrierIdToName(int carrier_id) {
        //this function looks up the corresponding airline give a airline code.
        String name= carrier_lookup.get(carrier_id);
        return  name;
    }

    private static void from_carrier_arr_to_hm(JSONArray jarr_carrier) throws JSONException {
        //this function store airline and its code into HashMap.
        carrier_lookup=new HashMap<>();
        for (int i=0;i<jarr_carrier.length();i++){
            JSONObject item= jarr_carrier.getJSONObject(i);
            String airline=item.getString("Name");
            int code= item.getInt("CarrierId");
            carrier_lookup.put(code,airline);
        }
    }

    public String getCarrier() {
        return carrier;
    }

    public String getDeparture_date() {
        return departure_date;
    }

    public int getPrice() {
        return price;
    }

    public String getDepCity() {
        return depCity;
    }

    public String getArrCity() {
        return arrCity;
    }


}
