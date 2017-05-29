package com.example.android.favoriteplaces.data;

import com.example.android.favoriteplaces.Categories.Category;
import com.example.android.favoriteplaces.Places.Place;
import com.example.android.favoriteplaces.Places.PlaceDetailed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by arturs.amirovs on 28/04/17.
 */

public interface DataBaseInterface {
    List<Category> getCategoryList();
    void newCategory(Category categoryname);
    List<Place> getPlacesList(String categoryName);
    void newPlace(Place placeName);
    void formatDataBase();
    void fillJSONPlaces(ArrayList<Place> placeNames);
    void addDetails(String placeID, String address, String number, String openingHourse, String website, String rating);
    String getPlaceID(String placeName);
    PlaceDetailed getDetails(String placeID);
    Boolean alreadyExist(String placeID);
}
