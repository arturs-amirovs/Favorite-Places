package com.example.android.favoriteplaces.Places;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.android.favoriteplaces.R;

import static com.example.android.favoriteplaces.MyApplication.dataSource;
import static com.example.android.favoriteplaces.Places.PlaceInformationActivity.PLACE_NAME;

public class PlacesActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private PlaceListAdapter listAdapter;
    private ListView categoryLVList;
    private String calledCategory;

    public static final String CALLED_CATEGORY = "categoryName";
    private static final String DIALOG_TITLE = "Add new place";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        //TODO change variable called category
        calledCategory = getIntent().getStringExtra(CALLED_CATEGORY);
        setTitle(calledCategory);

        categoryLVList = (ListView) findViewById(R.id.categoryLVList);

        listAdapter = new PlaceListAdapter(this, dataSource.getPlacesList(calledCategory));
        categoryLVList.setAdapter(listAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabList);
        fab.setOnClickListener(onAddingListener());
        categoryLVList.setOnItemClickListener(this);
    }


    private View.OnClickListener onAddingListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(PlacesActivity.this);
                dialog.setContentView(R.layout.dialog_add); //layout for dialog
//                dialog.setTitle(DIALOG_TITLE);
                dialog.setCancelable(false); //none-dismiss when touching outside Dialog

                // set the custom dialog components - texts and image
                EditText name = (EditText) dialog.findViewById(R.id.name);
                View btnAdd = dialog.findViewById(R.id.addButton);
                View btnCancel = dialog.findViewById(R.id.cancelButton);
                TextView title = (TextView) dialog.findViewById(R.id.TitleID);
                title.setText(DIALOG_TITLE);

                btnAdd.setOnClickListener(onConfirmListener(name, dialog));
                btnCancel.setOnClickListener(onCancelListener(dialog));

                dialog.show();
            }
        };
    }

    private View.OnClickListener onConfirmListener(final EditText name, final Dialog dialog) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                addPlace(name.getText().toString().trim());
                dialog.dismiss();
            }
        };
    }
    private View.OnClickListener onCancelListener(final Dialog dialog) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        };
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView text = (TextView)view.findViewById(R.id.textViewRVPlace);
        startActivity(new Intent(this, PlaceInformationActivity.class).putExtra(PLACE_NAME, text.getText().toString()));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }

    private void addPlace(String name) {
        dataSource.newPlace(new Place(name, calledCategory));
        listAdapter = new PlaceListAdapter(this, dataSource.getPlacesList(calledCategory));
        categoryLVList.setAdapter(listAdapter);
    }


}