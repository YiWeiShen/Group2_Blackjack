package com.example.group2_blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button reg_btn, login_btn, guest_btn;
    EditText login_passwordInput, login_nameInput;
    private DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        reg_btn = findViewById(R.id.reg_btn);
        login_btn = findViewById(R.id.login_btn);
        guest_btn = findViewById(R.id.guest_btn);
        login_passwordInput = findViewById(R.id.login_passwordInput);
        login_nameInput = findViewById(R.id.login_nameInput);

        final MediaPlayer bgm = MediaPlayer.create(this, R.raw.casinobgm);
        bgm.setVolume(0.2f, 0.2f);
        bgm.setLooping(true);
        bgm.start();

        DB = new DBHelper(this);

        reg_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, RegisterActivity.class));
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = login_nameInput.getText().toString();
                String password = login_passwordInput.getText().toString();

                if (DB.loginCheck(username, password)) {
                    Intent intent = new Intent(MainActivity.this, GameActivity.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "LOGIN Failed!!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        guest_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DB.guestLogin();
                Intent intent = new Intent(MainActivity.this, GameActivity.class);
                intent.putExtra("username", "guest");
                startActivity(intent);
            }
        });
    }
}