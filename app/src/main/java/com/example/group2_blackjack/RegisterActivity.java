package com.example.group2_blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText username, password, password2;
    Button submit;

    private DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_register);

        username = findViewById(R.id.username_txt);
        password = findViewById(R.id.password_txt);
        password2 = findViewById(R.id.password_confirm_txt);
        submit = findViewById(R.id.submit_btn);

        DB = new DBHelper(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){

                String usernameTxt = username.getText().toString();
                String passwordTxt = password.getText().toString();
                String password2Txt = password2.getText().toString();

                if (passwordTxt.equals(password2Txt)) {
                    Boolean checkinsertData = DB.insertuserdata(usernameTxt, passwordTxt);
                    Intent intent = new Intent(RegisterActivity.this, GameActivity.class);
                    intent.putExtra("username", usernameTxt);
                    startActivity(intent);

                    if (!checkinsertData) {
                        Toast.makeText(RegisterActivity.this, "Username has been registered!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(RegisterActivity.this, "Password have to be the same!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}