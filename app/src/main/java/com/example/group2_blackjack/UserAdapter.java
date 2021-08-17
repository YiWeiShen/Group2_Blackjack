package com.example.group2_blackjack;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class UserAdapter extends ArrayAdapter {
    private ArrayList<Users> users;
    private final LayoutInflater layoutInflater;
    private final int layoutResource;

    public UserAdapter(@NonNull Context context, int resource, ArrayList<Users> users) {
        super(context, resource);
        this.users = users;
        this.layoutInflater = LayoutInflater.from(context);
        this.layoutResource = resource;
    }

    @Override
    public int getCount() {
        return users.size();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View v = layoutInflater.inflate(layoutResource, parent, false);
        TextView nameText = v.findViewById(R.id.username);
        TextView balanceText = v.findViewById(R.id.balance);
        TextView scoreText = v.findViewById(R.id.Score);

        nameText.setText(users.get(position).getUsername());
        balanceText.setText("Balance: " + String.valueOf(users.get(position).getBalance()));
        scoreText.setText("Score: " + String.valueOf(users.get(position).getScore()));

        return v;
    }

}
