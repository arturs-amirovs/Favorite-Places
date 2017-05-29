package com.example.android.favoriteplaces.Categories;

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

import com.example.android.favoriteplaces.MyListAdapter;
import com.example.android.favoriteplaces.Places.PlacesActivity;
import com.example.android.favoriteplaces.R;

import static com.example.android.favoriteplaces.MyApplication.dataSource;
import static com.example.android.favoriteplaces.Places.PlacesActivity.CALLED_CATEGORY;

public class CategoryActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private MyListAdapter listAdapter;
    private ListView categoryLV;
    private TextView categoryTextView;
    private static final String DIALOG_TITLE = "Add a new category";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        categoryLV = (ListView) findViewById(R.id.categoryLV);

        listAdapter = new MyListAdapter(this, dataSource.getCategoryList());
        categoryLV.setAdapter(listAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(onAddingListener());
        categoryLV.setOnItemClickListener(this);
    }

    private View.OnClickListener onAddingListener() {
        return new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                final Dialog dialog = new Dialog(CategoryActivity.this);
                dialog.setContentView(R.layout.dialog_add); //layout for dialog
//                dialog.setTitle("Add a new category");
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

                addNewCategory(name.getText().toString().trim());
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



    private void addNewCategory(String name){
        Category categoryName = new Category(name);
        dataSource.newCategory(categoryName);

        listAdapter = new MyListAdapter(this, dataSource.getCategoryList());
        categoryLV.setAdapter(listAdapter);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        TextView text = (TextView)view.findViewById(R.id.textViewRV);
        startActivity(new Intent(this, PlacesActivity.class).putExtra(CALLED_CATEGORY, text.getText().toString()));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }
}
