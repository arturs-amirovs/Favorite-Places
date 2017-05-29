package com.example.android.favoriteplaces.ParsingJSON;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import static com.example.android.favoriteplaces.MyApplication.dataSource;

/**
 * Created by arturs.amirovs on 03/05/17.
 */

public class GetPlaceInformation extends AsyncTask<Void, Void, Void> {

    private String TAG = "State messsage: ";
    private String placeID;

    private String placeAddress = "";
    private String phoneNumber;
    private String workHours = "";
    private String website;
    private String rating;
    private NetworkListenerInterface listener;

    public GetPlaceInformation(String placeID, NetworkListenerInterface listener){
        this.placeID = placeID;
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
//        Toast.makeText(MainActivity.this,"Json Data is downloading",Toast.LENGTH_LONG).show();

    }

    @Override
    protected Void doInBackground(Void... arg0) {
        HttpHandler sh = new HttpHandler();
        // Making a request to url and getting response


        String url = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + placeID + "&key=AIzaSyDHxmC5MPYxCxj5Mk3jN3j6BUKUzIV1Ako";
        String jsonStr = sh.makeServiceCall(url);
        System.out.println("Url = " + url);
        Log.e(TAG, "Response from url: " + jsonStr);
        if (jsonStr != null) {
            try {
                JSONObject jsonObj = new JSONObject(jsonStr);


                JSONObject currentPlace = jsonObj.getJSONObject("result");

                // Getting JSON Array node

                    JSONArray address = currentPlace.optJSONArray("address_components");

                    for (int i = 1; i >= 0; i--) {
                        JSONObject p = address.optJSONObject(i);
                        placeAddress += p.getString("long_name");
                        if (i == 1) placeAddress += " ";
                    }


                    phoneNumber = currentPlace.optString("formatted_phone_number");

                    try {
                    JSONObject openingHours = currentPlace.getJSONObject("opening_hours");

                        JSONArray workingHours = openingHours.getJSONArray("weekday_text");

                        for (int i = 0; i < workingHours.length(); i++) {
                            workHours += workingHours.optString(i) + "\n";
                        }
                    } catch (final JSONException e){

                    }

                rating = currentPlace.optString("rating");
                website = currentPlace.optString("website");

            } catch (final JSONException e) {
                Log.e(TAG, "Json parsing error: " + e.getMessage());

            }

        } else {
            Log.e(TAG, "Couldn't get json from server.");

        }



        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        dataSource.addDetails(placeID, placeAddress, phoneNumber, workHours, website, rating);
        Log.e("added Details: ", ">>>>---------------------------------------------<<<<");
        listener.onTaskCompleted();


    }
}
