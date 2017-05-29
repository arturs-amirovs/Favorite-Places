package com.example.android.favoriteplaces.Categories;

/**
 * Created by arturs.amirovs on 12/04/17.
 */

public class Category {

    private int _id;
    private String _categoryname;
    private int img = 0;

    public Category(String categoryname) {

        this._categoryname = categoryname;

    }


    public void setImg(int img) { this.img = img; }

    public int getImg() { return img; }

    public void set_id(int _id){
        this._id = _id;
    }

    public void set_categoryname(String _categoryname){
        this._categoryname = _categoryname;
    }

    public int get_id(){
        return _id;
    }

    public String get_categoryname(){
        return _categoryname;
    }


}


