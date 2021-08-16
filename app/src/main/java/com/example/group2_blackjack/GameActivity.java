package com.example.group2_blackjack;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;


public class GameActivity extends AppCompatActivity {

    ImageView userCard1, userCard2, userCard3, userCard4, userCard5, aiCard1, aiCard2, aiCard3, aiCard4, aiCard5;
    Button startButton, needButton, stopButton;

    private int player = 0;
    private int bet = 0;
    private int[] num = new int[52];
    private ArrayList<Integer> user = new ArrayList<Integer>();
    private ArrayList<Integer> ai = new ArrayList<Integer>();
    private int currentPoint;
    private int computerPoint = 0;
    private int currentPage = 0;
    int [] cardArray ={
            R.drawable.spade1,
            R.drawable.spade2,
            R.drawable.spade3,
            R.drawable.spade4,
            R.drawable.spade5,
            R.drawable.spade6,
            R.drawable.spade7,
            R.drawable.spade8,
            R.drawable.spade9,
            R.drawable.spade10,
            R.drawable.spadejocker,
            R.drawable.spadequeen,
            R.drawable.spadeking,
            R.drawable.heart1,
            R.drawable.heart2,
            R.drawable.heart3,
            R.drawable.heart4,
            R.drawable.heart5,
            R.drawable.heart6,
            R.drawable.heart7,
            R.drawable.heart8,
            R.drawable.heart9,
            R.drawable.heart10,
            R.drawable.heartjocker,
            R.drawable.heartqueen,
            R.drawable.heartking,
            R.drawable.diamond1,
            R.drawable.diamond2,
            R.drawable.diamond3,
            R.drawable.diamond4,
            R.drawable.diamond5,
            R.drawable.diamond6,
            R.drawable.diamond7,
            R.drawable.diamond8,
            R.drawable.diamond9,
            R.drawable.diamond10,
            R.drawable.diamondjocker,
            R.drawable.diamondqueen,
            R.drawable.diamondking,
            R.drawable.club1,
            R.drawable.club2,
            R.drawable.club3,
            R.drawable.club4,
            R.drawable.club5,
            R.drawable.club6,
            R.drawable.club7,
            R.drawable.club8,
            R.drawable.club9,
            R.drawable.club10,
            R.drawable.clubjocker,
            R.drawable.clubqueen,
            R.drawable.clubking
    };

    private void shuffle() {
        for (int i = 0; i < 52; i++) {
            num[i] = i;
        }
        for (int j = 0; j < 26; j++) {
            Random r = new Random();
            int i1 = r.nextInt(52);
            int i2 = r.nextInt(52);

            int temp = num[i2];
            num[i2] = num[i1];
            num[i1] = temp;
        }
    }

    private void deal() {
        user.add(num[0]);
        userCard1.setImageResource(cardArray[num[0]]);
        ai.add(num[1]);
        aiCard1.setImageResource(cardArray[num[1]]);
        user.add(num[2]);
        userCard2.setImageResource(cardArray[num[2]]);
        ai.add(num[3]);
        aiCard2.setImageResource(cardArray[num[3]]);
        currentPage = 4;
    }

    private int calPoint(ArrayList<Integer> cards) {
        int temp = 0;
        for (Integer i : cards) {
            if (i % 13 + 1 > 10) { //JQK=10
                temp += 10;
            } else {
                temp += (i + 1) % 13;
            }
        }
        return temp;
    }

    private ArrayList<Integer> needCard(ArrayList<Integer> cards) {

        cards.add(num[currentPage]);
        currentPage++;
        if (player == 0) {
            currentPoint = calPoint(cards);
        } else {
            computerPoint = calPoint(cards);
        }
        return cards;
    }

    private void show() {
        if(player==0){
            userCard1.setImageResource(cardArray[user.get(0)]);
            userCard2.setImageResource(cardArray[user.get(1)]);
            userCard3.setImageResource(cardArray[user.get(2)]);
            userCard4.setImageResource(cardArray[user.get(3)]);
            userCard5.setImageResource(cardArray[user.get(4)]);
        }else {
            aiCard1.setImageResource(cardArray[ai.get(0)]);
            aiCard2.setImageResource(cardArray[ai.get(1)]);
            aiCard3.setImageResource(cardArray[ai.get(2)]);
            aiCard4.setImageResource(cardArray[ai.get(3)]);
            aiCard5.setImageResource(cardArray[ai.get(4)]);
        }

    }

    private void result() {

//        String nameTxt = name.getText().toString();
//        String contactTxt = contact.getText().toString();
//        String dobTxt = dob.getText().toString();

//        Boolean checkupdate = DB.updateuserdata(nameTxt,contactTxt,dobTxt);
        if (currentPoint == 21 || user.size()==5) {
            Toast.makeText(GameActivity.this, "YOU WIN", Toast.LENGTH_SHORT).show();
        } else if (computerPoint > currentPoint) {
            if (computerPoint > 21) {
                Toast.makeText(GameActivity.this, "YOU WIN", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(GameActivity.this, "YOU LOST", Toast.LENGTH_SHORT).show();
            }
        } else if (currentPoint > computerPoint) {
            if (currentPoint > 21 || ai.size()==5) {
                Toast.makeText(GameActivity.this, "YOU LOST", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(GameActivity.this, "YOU WIN", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void start() {
        shuffle();
        deal();
    }

    private void userTurn() {
        user = needCard(user);
        show();
        if (currentPoint >= 21) {
            result();
        }
    }

    private void aiTurn() {
        player = 1;
        while (true) {
            if (computerPoint > currentPoint) {
                break;
            } else if (computerPoint > 21) {
                break;
            }
            ai = needCard(ai);
            show();
        }
        result();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        setContentView(R.layout.activity_game);
    }
}