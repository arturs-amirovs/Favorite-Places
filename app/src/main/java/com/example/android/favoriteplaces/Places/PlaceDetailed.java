package com.example.android.favoriteplaces.Places;

/**
 * Created by arturs.amirovs on 08/05/17.
 */

public class PlaceDetailed {

    private String placeAddress = "";
    private String phoneNumber;
    private String workHours = "";
    private String website;
    private String rating;
    private String imagePath;

    public PlaceDetailed(String placeAddress, String phoneNumber, String workHours, String website, String rating){
        this.placeAddress = placeAddress;
        this.phoneNumber = phoneNumber;
        this.workHours = workHours;
        this.website = website;
        this.rating = rating;
    }

    public String getPlaceAddress() {
        return placeAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getWorkHours() {
        return workHours;
    }

    public String getWebsite() {
        return website;
    }

    public String getRating() {
        return rating;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}
