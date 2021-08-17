package com.example.group2_blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class RankingActivity extends AppCompatActivity {

    ArrayList<Users> userList;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking2);

        listView = findViewById(R.id.listview);

        Intent intent = getIntent();
        userList = (ArrayList<Users>) intent.getSerializableExtra("userlist");
        UserAdapter userAdapter = new UserAdapter(this, R.layout.activity_ranking, userList);
        listView.setAdapter(userAdapter);
    }
}