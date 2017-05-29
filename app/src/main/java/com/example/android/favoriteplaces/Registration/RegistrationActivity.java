package com.example.android.favoriteplaces.Registration;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.android.favoriteplaces.Categories.CategoryActivity;
import com.example.android.favoriteplaces.ParsingJSON.GetPlaces;
import com.example.android.favoriteplaces.ParsingJSON.NetworkListenerInterface;
import com.example.android.favoriteplaces.R;

import static com.example.android.favoriteplaces.MyApplication.loginCommunicator;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener, NetworkListenerInterface {

    EditText name, email, registrationUsername, registrationPassword;
    Button registrationButton;
    ProgressDialog progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        name = (EditText) findViewById(R.id.registrationName);
        email = (EditText) findViewById(R.id.registrationEmail);
        registrationUsername = (EditText) findViewById(R.id.registrationUsername);
        registrationPassword = (EditText) findViewById(R.id.registrationPassword);
        registrationButton = (Button) findViewById(R.id.registrationButton);

        registrationButton.setOnClickListener(this);
    }



    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.registrationButton) {

            progressBar = ProgressDialog.show(RegistrationActivity.this, "Setting up a new account", "This might take a while...");

            loginCommunicator.onRegister(name.getText().toString(),
                    email.getText().toString(),
                    registrationUsername.getText().toString(),
                    registrationPassword.getText().toString());

            new GetPlaces(RegistrationActivity.this).execute();


        }

    }

    @Override
    public void onTaskCompleted() {
        startActivity(new Intent(this, CategoryActivity.class));
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

        if (progressBar.isShowing()) {
            progressBar.dismiss();
        }
        finish();

    }
}
