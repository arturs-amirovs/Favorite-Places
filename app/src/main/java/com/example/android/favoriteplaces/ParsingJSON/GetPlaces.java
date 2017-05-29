package com.example.android.favoriteplaces.ParsingJSON;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.favoriteplaces.Places.Place;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.example.android.favoriteplaces.MyApplication.dataSource;

/**
 * Created by arturs.amirovs on 03/05/17.
 */

public class GetPlaces extends AsyncTask<Void, Void, Void> {

    private String TAG = "State messsage: ";

    ArrayList<Place> placesList = new ArrayList<>();
    NetworkListenerInterface listener;

    public GetPlaces(NetworkListenerInterface listener){
        this.listener = listener;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        HttpHandler sh = new HttpHandler();
        // Making a request to url and getting response

        String[] name = {"Bar", "Club", "Restraunt", "Hotel"};
        for(int z=0; z<name.length; z++) {
            String pageToken="";
            for(int a=0; a<3; a++) {

                String query = "Riga+" + name[z];
                String categoryName = name[z] + "s";
                String url = "https://maps.googleapis.com/maps/api/place/textsearch/json?query=" + query + "&key=AIzaSyDHxmC5MPYxCxj5Mk3jN3j6BUKUzIV1Ako&pagetoken=" + pageToken;
                String jsonStr = sh.makeServiceCall(url);
                System.out.println("Url = " + url);
                Log.e(TAG, "Response from url: " + jsonStr);
                if (jsonStr != null) {
                    try {
                        JSONObject jsonObj = new JSONObject(jsonStr);

                        pageToken = jsonObj.optString("next_page_token");

                        System.out.println("PageToken = " + pageToken);
                        // Getting JSON Array node
                        JSONArray places = jsonObj.optJSONArray("results");

                        // looping through All Contacts
                        for (int i = 0; i < places.length(); i++) {
                            JSONObject c = places.optJSONObject(i);
                            String placeName = c.optString("name", "");
                            String status = "";
                            String rating = c.optString("rating", "");
                            String placeId = c.optString("place_id", "");
                            try {
                                JSONObject openingHours = c.getJSONObject("opening_hours");
                                if (openingHours.getString("open_now").equals("true"))
                                    status = "Open";
                                else if ((openingHours.getString("open_now").equals("false")))
                                    status = "Closed";
                            }catch (final JSONException e){

                            }


                            placesList.add(new Place(placeName, categoryName, rating, status, placeId));
                        }
                    } catch (final JSONException e) {
                        Log.e(TAG, "Json parsing error: " + e.getMessage());
                    }

                } else {
                    Log.e(TAG, "Couldn't get json from server.");
                }

                try {
                    Thread.sleep(2000); //1000 milliseconds is one second.
                }
                catch (InterruptedException e)
                {
                    e.printStackTrace();
                }

                if(pageToken.equals("")) break;
            }

        }


        return null;
    }

    @Override
    protected void onPostExecute(Void result) {
        super.onPostExecute(result);
        dataSource.fillJSONPlaces(placesList);
        listener.onTaskCompleted();
    }
}
