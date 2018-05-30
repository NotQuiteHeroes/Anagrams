package com.eckstein.paige.anagrams;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EditInfo extends Activity {

    String firstName, lastName, userName, location;
    EditText firstNameField, lastNameField, userNameField, locationField;
    Button doneButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_info);

        firstNameField = findViewById(R.id.editFirstNameField);
        lastNameField = findViewById(R.id.editLastNameField);
        userNameField = findViewById(R.id.editUserNameField);
        locationField = findViewById(R.id.editLocationField);

        doneButton = findViewById(R.id.editInfoDoneButton);
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firstName = firstNameField.getText().toString();
                lastName = lastNameField.getText().toString();
                userName = userNameField.getText().toString();
                location = locationField.getText().toString();

                Intent intent = new Intent();
                intent.putExtra("firstName", firstName);
                intent.putExtra("lastName", lastName);
                intent.putExtra("userName", userName);
                intent.putExtra("location", location);
                setResult(999, intent);
                finish();
            }
        });
    }
}
