package com.example.android.favoriteplaces.Places;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.favoriteplaces.ParsingJSON.GetPlaceInformation;
import com.example.android.favoriteplaces.ParsingJSON.NetworkListenerInterface;
import com.example.android.favoriteplaces.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.example.android.favoriteplaces.MyApplication.dataSource;
import static com.example.android.favoriteplaces.R.id.placePhoneNumber;
import static com.example.android.favoriteplaces.R.id.placeWebsite;
import static com.example.android.favoriteplaces.R.id.placeWorkhours;

public class PlaceInformationActivity extends AppCompatActivity implements NetworkListenerInterface{

    public static final String PLACE_NAME = "placeName";

    private String placeName;
    public PlaceDetailed details;
    public TextView xPlaceName, xPlaceAddress, xPlacePhoneNumber, xPlaceRating, xPlaceWebsite, xPlaceWorkhours;
    public ImageButton imageButton;
    ImageView imageV;
    static final int REQUEST_TAKE_PHOTO = 1337;
    String mCurrentPhotoPath;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_information);
        placeName = getIntent().getStringExtra(PLACE_NAME);

        xPlaceName = (TextView) findViewById(R.id.placeName);
        xPlaceAddress = (TextView) findViewById(R.id.placeAddress);
        xPlacePhoneNumber = (TextView) findViewById(placePhoneNumber);
        xPlaceRating = (TextView) findViewById(R.id.placeRate);
        xPlaceWebsite = (TextView) findViewById(placeWebsite);
        xPlaceWorkhours = (TextView) findViewById(placeWorkhours);

        imageV = (ImageView) findViewById(R.id.imageV);

        if (dataSource.alreadyExist(dataSource.getPlaceID(placeName))) {
            Log.e("Already exist ", "Already exist --------<");


            setViews();

        } else if (dataSource.getPlaceID(placeName).equals("")){
            Log.e("Blah Blah", "bbb");
            xPlaceName.setText(placeName);
            xPlaceAddress.setVisibility(View.GONE);
            xPlacePhoneNumber.setVisibility(View.GONE);
            xPlaceRating.setVisibility(View.GONE);
            xPlaceWebsite.setVisibility(View.GONE);
            xPlaceWorkhours.setVisibility(View.GONE);
            TextView label = (TextView) findViewById(R.id.workingHoursLabel);
            label.setVisibility(View.GONE);
        } else {
            Log.e("Creating new DB ", "...");
            new GetPlaceInformation(dataSource.getPlaceID(placeName), PlaceInformationActivity.this).execute();
        }



        imageV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (cameraIntent.resolveActivity(getPackageManager()) != null) {
                    // Create the File where the photo should go
                    File photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Error occurred while creating the File
                        Log.i("bu", "IOException");
                    }
                    // Continue only if the File was successfully created
                    if (photoFile != null) {
                        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
                        startActivityForResult(cameraIntent, REQUEST_TAKE_PHOTO);
                    }
                }
            }
        });

    }

    public void setViews(){
        details = dataSource.getDetails(dataSource.getPlaceID(placeName));
        xPlaceName.setText(placeName);
        xPlaceAddress.setText(details.getPlaceAddress());
        xPlacePhoneNumber.setText(details.getPhoneNumber());
        xPlaceRating.setText(details.getRating());
        xPlaceWebsite.setText(details.getWebsite());
        xPlaceWorkhours.setText(details.getWorkHours());
    }

    @Override
    public void onTaskCompleted() {

        setViews();

    }

//    public void onClick(View view){
//
//        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//                // Error occurred while creating the File
//                Log.i("bu", "IOException");
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
//                startActivityForResult(cameraIntent, REQUEST_TAKE_PHOTO);
//            }
//        }
//    }

    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
//        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = image.getAbsolutePath();
//        Log.e("Path = ", mCurrentPhotoPath);
//        return image;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(
                Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  // prefix
                ".jpg",         // suffix
                storageDir      // directory
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = "file:" + image.getAbsolutePath();
        return image;

    }

    private Bitmap mImageBitmap;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            try {
                mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), Uri.parse(mCurrentPhotoPath));
                imageV.setImageBitmap(mImageBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

//    private void setPic() {
//        // Get the dimensions of the View
//        int targetW = imagev.getWidth();
//        int targetH = imagev.getHeight();
//
//        // Get the dimensions of the bitmap
//        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
//        bmOptions.inJustDecodeBounds = true;
//        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
//        int photoW = bmOptions.outWidth;
//        int photoH = bmOptions.outHeight;
//
//        // Determine how much to scale down the image
//        int scaleFactor = Math.min(300, 300);
//
//        // Decode the image file into a Bitmap sized to fill the View
//        bmOptions.inJustDecodeBounds = false;
//        bmOptions.inSampleSize = scaleFactor;
//        bmOptions.inPurgeable = true;
//
//        Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
//        imagev.setImageBitmap(bitmap);
//    }

}
