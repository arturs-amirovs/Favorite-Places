package com.example.android.favoriteplaces.Places;

/**
 * Created by arturs.amirovs on 20/04/17.
 */

public class Place {

    private int _idplace;
    private String place = null;
    private String linkedCategoryName;
    private String rating = "";
    private String openNow = "";
    private String placeId;
    private String address;
    private String phoneNumber;
    private String url;
    private String workingTime;


    public Place(String place, String linkedCategoryName){

        this.place = place;
        this.linkedCategoryName = linkedCategoryName;

    }

    public Place(String place, String linkedCategoryName, String rating, String openNow, String placeId){

        this.place = place;
        this.linkedCategoryName = linkedCategoryName;
        this.rating = rating;
        this.openNow = openNow;
        this.placeId = placeId;

    }

    public void set_idplace(int _idplace){
        this._idplace = _idplace;
    }

    public void setPlace(String place){
        this.place = place;
    }

    public void setLinkedCategoryName(String linkedCategoryName) { this.linkedCategoryName = linkedCategoryName; }

    public void setRating(String rating) { this.rating = rating; }

    public void setAddress(String address) { this.address = address; }

    public void setPhoneNumber (String phoneNumber) { this.phoneNumber = phoneNumber; }

    public void setUrl(String url){
        this.url = url;
    }

    public void setWorkingTime(String workingTime) { this.workingTime = workingTime; }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getWorkingTime() {
        return workingTime;
    }

    public String getUrl() { return url; }

    public int get_idplace(){
        return _idplace;
    }

    public String getPlace(){
        return place;
    }

    public String getLinkedCategoryName() { return linkedCategoryName; }

    public String getRating() { return rating; }

    public String getOpenNow() { return openNow; }

    public String getPlaceId() { return placeId; }




}
