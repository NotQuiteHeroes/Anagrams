package com.eckstein.paige.anagrams;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class userInfo extends Activity {

    String firstName, lastName, userName, location;

    TextView firstNameField, lastNameField, userNameField, locationField;
    Button editButton, doneButton;
    SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        firstNameField = findViewById(R.id.firstNameField);
        lastNameField = findViewById(R.id.lastNameField);
        userNameField = findViewById(R.id.userNameField);
        locationField = findViewById(R.id.locationField);

        loadInfo();
        setFields();

        editButton = findViewById(R.id.editButton);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userInfo.this, EditInfo.class);
                startActivityForResult(intent, 999);
            }
        });

        doneButton = findViewById(R.id.infoDoneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userInfo.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent intent)
    {
        super.onActivityResult(requestCode, resultCode, intent);
        if(requestCode == 999)
        {
            firstName = intent.getStringExtra("firstName");
            lastName = intent.getStringExtra("lastName");
            userName = intent.getStringExtra("userName");
            location = intent.getStringExtra("location");

            saveInfo();
            setFields();
        }
    }

    protected void saveInfo()
    {
        prefs = getSharedPreferences("privatePrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("FirstName", firstName);
        editor.putString("LastName", lastName);
        editor.putString("UserName", userName);
        editor.putString("Location", location);
        editor.apply();

    }

    protected void loadInfo()
    {
        prefs = getSharedPreferences("privatePrefs", Context.MODE_PRIVATE);
        firstName = prefs.getString("FirstName", "");
        lastName = prefs.getString("LastName", "");
        userName = prefs.getString("UserName", userName);
        location = prefs.getString("Location", location);
    }

    protected void setFields()
    {
        firstNameField.setText(firstName);
        lastNameField.setText(lastName);
        userNameField.setText(userName);
        locationField.setText(location);
    }
}
