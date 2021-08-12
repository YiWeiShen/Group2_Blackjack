package com.example.group2_blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    EditText username, password, password2;

    private DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username_txt);
        password = findViewById(R.id.password_txt);
        password2 = findViewById(R.id.password_confirl_txt);


    }
}