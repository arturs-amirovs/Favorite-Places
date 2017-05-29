package com.example.android.favoriteplaces.data;

/**
 * Created by arturs.amirovs on 19/04/17.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.android.favoriteplaces.Categories.Category;
import com.example.android.favoriteplaces.MyApplication;
import com.example.android.favoriteplaces.Places.Place;
import com.example.android.favoriteplaces.Places.PlaceDetailed;
import com.example.android.favoriteplaces.R;

import java.util.ArrayList;
import java.util.List;

public class MyDBHandler extends SQLiteOpenHelper{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "favoriteplaces.db";

    //Database for list in CategoryActivity
    private static final String TABLE_CATEGORIES = "categories";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_CATEGORYNAME = "categoryname";
    private static final String COLUMN_IMAGEID = "imageid";

    //Database for list in PlacesActivity
    private static final String TABLE_PLACES = "places";
    private static final String COLUMN_IDPLACE = "_idplace";
    private static final String COLUMN_PLACENAME = "placename";
    private static final String COLUMN_OPENSTATUS = "openstatus";
    private static final String COLUMN_RATING = "rating";
    private static final String COLUMN_LINKEDCATEGORYNAME = "linkedcategoryname";
    private static final String COLUMN_PLACEID = "placeid";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_NUMBER = "phonenumber";
    private static final String COLUMN_OPENINGHOURS = "openinghours";
    private static final String COLUMN_WEBSITE = "website";
    private static final String TABLE_PLACESDETAILS = "details";
    private static final String COLUMN_DETAILSID = "detailsid";
    SQLiteDatabase db = null;

    private static final String TABLE1 = "CREATE TABLE IF NOT EXISTS " + TABLE_CATEGORIES + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_CATEGORYNAME + " TEXT " + ", " +
            COLUMN_IMAGEID + " INTEGER " +
            ");";

    private static final String TABLE2 = "CREATE TABLE IF NOT EXISTS " + TABLE_PLACES + " (" +
            COLUMN_IDPLACE + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PLACENAME + " TEXT " + ", " +
            COLUMN_LINKEDCATEGORYNAME + " TEXT " + ", " +
            COLUMN_OPENSTATUS + " TEXT " + ", " +
            COLUMN_RATING + " TEXT " + ", " +
            COLUMN_PLACEID + " TEXT " +
            ");";


    private static final String TABLE3 = "CREATE TABLE IF NOT EXISTS " + TABLE_PLACESDETAILS + " (" +
            COLUMN_DETAILSID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_PLACENAME + " TEXT " + ", " +
            COLUMN_RATING + " TEXT " + ", " +
            COLUMN_PLACEID + " TEXT " + ", " +
            COLUMN_ADDRESS + " TEXT " + ", " +
            COLUMN_NUMBER + " TEXT " + ", " +
            COLUMN_OPENINGHOURS + " TEXT " + ", " +
            COLUMN_WEBSITE + " TEXT " +
            ");";




    private static volatile MyDBHandler instance;

    public static synchronized MyDBHandler getInstance(){
        if (instance == null) {
            instance = new MyDBHandler(MyApplication.getContext(), DATABASE_NAME, null, DATABASE_VERSION);
        }
            return instance;
    }


    public  MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;
        db.execSQL(TABLE1);
        db.execSQL(TABLE2);
        db.execSQL(TABLE3);
        String[] name = {"Bars", "Clubs", "Restraunts", "Hotels"};
        Integer[] img =  {R.drawable.bar_img, R.drawable.concert_img, R.drawable.restraunt_img, R.drawable.hotel_img};

        for(int i=0; i<name.length; i++) {
            Category categoryName = new Category(name[i]);
            categoryName.setImg(img[i]);
            addCategory(categoryName);
        }
//        fillPlaces();

        Log.i("DATABASE: ", "onCreate");



    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i("DATABASE: ", "onUpgrade()");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACESDETAILS);
        onCreate(db);
    }

    public void formatDatabase(){


        db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PLACESDETAILS);


        onCreate(db);


    }

    //Add a new raw to the database
    public void addCategory(Category category){

        if(db == null) db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CATEGORYNAME, category.get_categoryname());
        values.put(COLUMN_IMAGEID, category.getImg());

        db.insert(TABLE_CATEGORIES, null, values);


    }

    public void addPlace(Place place){
db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PLACENAME, place.getPlace());
        values.put(COLUMN_LINKEDCATEGORYNAME, place.getLinkedCategoryName());
        if(place.getOpenNow() != null) {
            values.put(COLUMN_OPENSTATUS, place.getOpenNow());
        }
        if(place.getRating() != null) {
            values.put(COLUMN_RATING, place.getRating());
        }
        if(place.getPlaceId() != null){
            values.put(COLUMN_PLACEID, place.getPlaceId());
        }
        db.insert(TABLE_PLACES, null, values);

    }

//    //Delete a category from the database
//    public void deleteCategory(String categoryName){
//        SQLiteDatabase db = getWritableDatabase();
//        //Delete using categoryName|Probably we will use category ID
//        db.execSQL("DELETE FROM " + TABLE_CATEGORIES + " WHERE " + COLUMN_CATEGORYNAME + "=\"" + categoryName + "\";");
//    }

    //Print database
    public List<Category> databaseToList(){
        String dbString = "";
        db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CATEGORIES + " WHERE 1;";

        //Cursor point to a location in results
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();

        List<Category> names = new ArrayList<>();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(COLUMN_CATEGORYNAME)) != null){
                dbString = c.getString(c.getColumnIndex(COLUMN_CATEGORYNAME));
                Category category = new Category(dbString);
                if(c.getInt(c.getColumnIndex(COLUMN_IMAGEID)) != 0){
                    category.setImg(c.getInt(c.getColumnIndex(COLUMN_IMAGEID)));
                } else {
                    category.setImg(0);
                }
                names.add(category);
            }
            c.moveToNext();
        }
        c.close();
//        db.close();
        return names;

    }

    public List<Place> databasePlaceToList(String categoryname){
        SQLiteDatabase db = getWritableDatabase();

        String query = "SELECT * FROM " + TABLE_PLACES + " WHERE " + COLUMN_LINKEDCATEGORYNAME + " = '" + categoryname + "';";

        //Cursor point to a location in results
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        List<Place> names = new ArrayList<>();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(COLUMN_PLACENAME)) != null){
                names.add(new Place((c.getString(c.getColumnIndex(COLUMN_PLACENAME))), (c.getString(c.getColumnIndex(COLUMN_LINKEDCATEGORYNAME))),
                        (c.getString(c.getColumnIndex(COLUMN_RATING))), (c.getString(c.getColumnIndex(COLUMN_OPENSTATUS))), (c.getString(c.getColumnIndex(COLUMN_PLACEID)))));
            }
            c.moveToNext();
        }
        c.close();
        db.close();

        for(int i=0; i<names.size(); i++)
            Log.e("LIST OVERVIEW: ", names.get(i).getPlace() + names.get(i).getLinkedCategoryName() + names.get(i).getRating() + names.get(i).getOpenNow());

        return names;

    }

    public void fillPlaces(ArrayList<Place> placeNames){
        for(int i=0; i<placeNames.size(); i++){
            addPlace(placeNames.get(i));

        }

    }

    public void addDetails(String placeID, String address, String number, String openingHourse, String website, String rating){

        ContentValues values = new ContentValues();
        values.put(COLUMN_ADDRESS, address);
        values.put(COLUMN_NUMBER, number);
        values.put(COLUMN_OPENINGHOURS, openingHourse);
        values.put(COLUMN_WEBSITE, website);
        values.put(COLUMN_PLACEID, placeID);
        values.put(COLUMN_RATING, rating);

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_PLACESDETAILS, null, values);

        Log.e("addDetails ", address + number + openingHourse + website + "  " + placeID);
        db.close();
    }

    public PlaceDetailed getDetails(String placeID){
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PLACESDETAILS + " WHERE " + COLUMN_PLACEID + "='" + placeID + "';";
        Cursor c = db.rawQuery(query, null);
        PlaceDetailed details = new PlaceDetailed("a", "b", "c", "d", "f");
        c.moveToFirst();

        while(!c.isAfterLast()){
            if(c.getString(c.getColumnIndex(COLUMN_DETAILSID)) != null){
                details = new PlaceDetailed((c.getString(c.getColumnIndex(COLUMN_ADDRESS))),
                        (c.getString(c.getColumnIndex(COLUMN_NUMBER))),
                        (c.getString(c.getColumnIndex(COLUMN_OPENINGHOURS))),
                        (c.getString(c.getColumnIndex(COLUMN_WEBSITE))),
                        (c.getString(c.getColumnIndex(COLUMN_RATING))));
                Log.e("getDetails()", c.getString(c.getColumnIndex(COLUMN_ADDRESS))+
                        (c.getString(c.getColumnIndex(COLUMN_NUMBER)))+
                        (c.getString(c.getColumnIndex(COLUMN_OPENINGHOURS)))+
                        (c.getString(c.getColumnIndex(COLUMN_WEBSITE))));
            }
            c.moveToNext();
        }
        c.close();
        db.close();

        return details;
    }

    public String getPlaceID(String placeName){
        String query = "SELECT " + COLUMN_PLACEID + " FROM " + TABLE_PLACES + " WHERE " + COLUMN_PLACENAME + " ='" + placeName + "';";
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        String placeID = "";
        if(c.getString(c.getColumnIndex(COLUMN_PLACEID)) != null)
            placeID = c.getString(c.getColumnIndex(COLUMN_PLACEID));
        c.close();
        db.close();
        return placeID;

    }

    public Boolean alreadyExist(String placeID) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PLACESDETAILS + " WHERE " + COLUMN_PLACEID + "='" + placeID + "';";
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(COLUMN_DETAILSID)) != null)
                return true;
        }
        c.close();
        db.close();
        return false;


    }

}
