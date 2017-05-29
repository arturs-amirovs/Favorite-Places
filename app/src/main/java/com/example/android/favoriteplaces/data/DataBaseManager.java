package com.example.android.favoriteplaces.data;

import com.example.android.favoriteplaces.Categories.Category;
import com.example.android.favoriteplaces.Places.Place;
import com.example.android.favoriteplaces.Places.PlaceDetailed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arturs.amirovs on 27/04/17.
 */

public class DataBaseManager implements DataBaseInterface{

    private static MyDBHandler myDBHandler;

    public DataBaseManager(){
        myDBHandler = MyDBHandler.getInstance();
    }

    @Override
    public List<Category> getCategoryList() {
        return myDBHandler.databaseToList();
    }

    @Override
    public void newCategory(Category categoryname) {
        myDBHandler.addCategory(categoryname);
    }

    @Override
    public List<Place> getPlacesList(String categoryName) {
        return myDBHandler.databasePlaceToList(categoryName);
    }

    @Override
    public void newPlace(Place placeName) {
        myDBHandler.addPlace(placeName);
    }


    @Override
    public void formatDataBase(){
        myDBHandler.formatDatabase();
    }


    public void fillJSONPlaces(ArrayList<Place> placeNames){ myDBHandler.fillPlaces(placeNames); }

    @Override
    public void addDetails(String placeID, String address, String number, String openingHourse, String website, String rating) {
        myDBHandler.addDetails(placeID, address, number, openingHourse, website, rating);
    }

    @Override
    public String getPlaceID(String placeName) {
        return myDBHandler.getPlaceID(placeName);
    }

    @Override
    public PlaceDetailed getDetails(String placeID) {
        return myDBHandler.getDetails(placeID);
    }

    @Override
    public Boolean alreadyExist(String placeID) {
        return myDBHandler.alreadyExist(placeID);
    }


}
