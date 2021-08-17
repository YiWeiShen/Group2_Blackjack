package com.example.group2_blackjack;


import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameActivity extends Activity {

    ImageView userCard1, userCard2, userCard3, userCard4, userCard5, aiCard1, aiCard2, aiCard3, aiCard4, aiCard5;
    Button startButton, needButton, stopButton, coin10, coin20, coin50, coin100, clearButton, doubleButton, rankingButton;
    TextView bet_txt, username_txt, balance_txt, score_txt, dealerPoint, userPoint;

    ArrayList<Users> userList;
    private DBHelper DB;
    private boolean endflag = false;
    private int player = 0;
    private int bet = 0;
    private int[] num = new int[52];
    private ArrayList<Integer> userCards = new ArrayList<Integer>();
    private ArrayList<Integer> dealerCards = new ArrayList<Integer>();
    private int currentPoint;
    private int computerPoint = 0;
    private int currentPage = 0;
    int cardBack = R.drawable.cardback;
    int[] cardArray = {
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
        for (int j = 0; j < 100; j++) {
            Random r = new Random();
            int i1 = r.nextInt(52);
            int i2 = r.nextInt(52);

            int temp = num[i2];
            num[i2] = num[i1];
            num[i1] = temp;
        }
    }

    private void deal() {
        userCards.add(num[0]);
        userCard1.setImageResource(cardArray[num[0]]);
        dealerCards.add(num[1]);
        aiCard1.setImageResource(cardArray[num[1]]);
        userCards.add(num[2]);
        userCard2.setImageResource(cardArray[num[2]]);
        dealerCards.add(num[3]);
        aiCard2.setImageResource(cardBack);
        currentPoint = calPoint(userCards);
        String b = "User Point: ";
        String a = b + String.valueOf(currentPoint);
        userPoint.setText(a);
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
            String b = "User Point: ";
            String a = b + String.valueOf(currentPoint);
            userPoint.setText(a);
        } else {
            computerPoint = calPoint(cards);
            String b = "Dealer Point: ";
            String a = b + String.valueOf(computerPoint);
            dealerPoint.setText(a);
        }
        return cards;
    }

    private void show() {
        if (player == 0) {
            for (int i = 0; i < userCards.size(); i++) {
                switch (i) {
                    case 0:
                        userCard1.setImageResource(cardArray[userCards.get(0)]);
                        break;
                    case 1:
                        userCard2.setImageResource(cardArray[userCards.get(1)]);
                    case 2:
                        userCard3.setImageResource(cardArray[userCards.get(2)]);
                        break;
                    case 3:
                        userCard4.setImageResource(cardArray[userCards.get(3)]);
                        break;
                    case 4:
                        userCard5.setImageResource(cardArray[userCards.get(4)]);
                        break;
                }
            }
        } else {
            for (int i = 0; i < dealerCards.size(); i++) {
                switch (i) {
                    case 0:
                        aiCard1.setImageResource(cardArray[dealerCards.get(0)]);
                        break;
                    case 1:
                        aiCard2.setImageResource(cardArray[dealerCards.get(1)]);
                    case 2:
                        aiCard3.setImageResource(cardArray[dealerCards.get(2)]);
                        break;
                    case 3:
                        aiCard4.setImageResource(cardArray[dealerCards.get(3)]);
                        break;
                    case 4:
                        aiCard5.setImageResource(cardArray[dealerCards.get(4)]);
                        break;
                }
            }
        }

    }

    private boolean result() {
        boolean flag = true;
        userCards.clear();
        dealerCards.clear();
        currentPage = 0;

        if (currentPoint == 21) {
            Toast.makeText(GameActivity.this, "YOU WIN", Toast.LENGTH_SHORT).show();
            flag = true;
        } else if (currentPoint < 21 && userCards.size() == 5) {
            Toast.makeText(GameActivity.this, "YOU WIN", Toast.LENGTH_SHORT).show();
            flag = true;
        } else if (computerPoint > currentPoint) {
            if (computerPoint > 21) {
                Toast.makeText(GameActivity.this, "YOU WIN", Toast.LENGTH_SHORT).show();
                flag = true;
            } else {
                Toast.makeText(GameActivity.this, "YOU LOST", Toast.LENGTH_SHORT).show();
                flag = false;
            }
        } else if (currentPoint > computerPoint) {
            if (currentPoint > 21 || dealerCards.size() == 5) {
                Toast.makeText(GameActivity.this, "YOU LOST", Toast.LENGTH_SHORT).show();
                flag = false;
            } else {
                Toast.makeText(GameActivity.this, "YOU WIN", Toast.LENGTH_SHORT).show();
                flag = true;
            }
        }
        startButton.setClickable(true);
        startButton.setAlpha(1.00f);
        coin10.setClickable(true);
        coin10.setAlpha(1.00f);
        coin20.setClickable(true);
        coin20.setAlpha(1.00f);
        coin50.setClickable(true);
        coin50.setAlpha(1.00f);
        coin100.setClickable(true);
        coin100.setAlpha(1.00f);
        clearButton.setClickable(true);
        clearButton.setAlpha(1.00f);
        rankingButton.setClickable(true);
        rankingButton.setAlpha(1.00f);
        endflag = true;
        return flag;

    }

    private void userTurn() {
        userCards = needCard(userCards);
        show();
        if (currentPoint >= 21 || userCards.size() == 5) {
            result();
        }
    }

    private void aiTurn() {
        player = 1;
        while (true) {
            if (computerPoint > currentPoint || dealerCards.size() == 5) {
                break;
            } else if (computerPoint > 21) {
                break;
            }
            dealerCards = needCard(dealerCards);
            show();
        }
        result();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        DB = new DBHelper(this);
        Users user = DB.getUserByName(getIntent().getExtras().getString("username"));
        startButton = findViewById(R.id.btn_deal);
        needButton = findViewById(R.id.btn_hit);
        stopButton = findViewById(R.id.btn_stand);
        coin10 = findViewById(R.id.coin_10);
        coin20 = findViewById(R.id.coin_20);
        coin50 = findViewById(R.id.coin_50);
        coin100 = findViewById(R.id.coin_100);
        clearButton = findViewById(R.id.btn_clear);
        doubleButton = findViewById(R.id.btn_double);
        rankingButton = findViewById(R.id.btn_ranking);

        startButton.setEnabled(false);
        needButton.setEnabled(false);
        stopButton.setEnabled(false);

        bet_txt = findViewById((R.id.bet));
        username_txt = findViewById(R.id.username_ingame);
        balance_txt = findViewById(R.id.balance_ingame);
        score_txt = findViewById(R.id.score);
        dealerPoint = findViewById(R.id.dealer_point);
        userPoint = findViewById(R.id.user_point);

        username_txt.setText("User: " + user.getUsername());
        balance_txt.setText("Balance: " + user.getBalance());
        score_txt.setText("Score: " + user.getScore());

        userCard1 = findViewById(R.id.player_card1);
        userCard2 = findViewById(R.id.player_card2);
        userCard3 = findViewById(R.id.player_card3);
        userCard4 = findViewById(R.id.player_card4);
        userCard5 = findViewById(R.id.player_card5);
        aiCard1 = findViewById(R.id.dealer_card1);
        aiCard2 = findViewById(R.id.dealer_card2);
        aiCard3 = findViewById(R.id.dealer_card3);
        aiCard4 = findViewById(R.id.dealer_card4);
        aiCard5 = findViewById(R.id.dealer_card5);

        final MediaPlayer coin = MediaPlayer.create(this, R.raw.coin);
        final MediaPlayer yea = MediaPlayer.create(this, R.raw.yea);
        final MediaPlayer ohh = MediaPlayer.create(this, R.raw.ohh);

        startButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                onCreate(null);
                if (!startButton.isEnabled()) {
                    startButton.setEnabled(true);
                    needButton.setEnabled(true);
                    stopButton.setEnabled(true);
                }
                String b = "Bet: ";
                String a = b + String.valueOf(bet);
                bet_txt.setText(a);
                shuffle();
                deal();
                startButton.setClickable(false);
                startButton.setAlpha(0.25f);
                coin10.setClickable(false);
                coin10.setAlpha(0.25f);
                coin20.setClickable(false);
                coin20.setAlpha(0.25f);
                coin50.setClickable(false);
                coin50.setAlpha(0.25f);
                coin100.setClickable(false);
                coin100.setAlpha(0.25f);
                clearButton.setClickable(false);
                clearButton.setAlpha(0.25f);
                rankingButton.setClickable(false);
                rankingButton.setAlpha(0.25f);
            }
        });

        needButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                userTurn();
                if (endflag) {
                    int balance, score;
                    String name = user.getUsername();
                    String password = user.getPassword();

                    if (result()) {
                        // player win
                        yea.setVolume(100, 100);
                        yea.start();
                        balance = user.getBalance() + bet;
                        score = user.getScore() + bet;
                    } else {
                        // player lose
                        ohh.setVolume(100, 100);
                        ohh.start();
                        balance = user.getBalance() - bet;
                        score = user.getScore();
                    }

                    Boolean checkUpdate = DB.updateUserData(name, password, balance, score);
                    Log.d("Update score(Endgame): ", String.valueOf(checkUpdate));
                    endflag = false;
                    bet = 0;
                }
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                aiTurn();
                if (endflag) {
                    if (result()) {
                        yea.setVolume(100, 100);
                        yea.start();
                        String name = user.getUsername();
                        String password = user.getPassword();
                        int balance = user.getBalance() + bet;
                        int score = user.getScore() + bet;
                        Boolean checkupdate = DB.updateUserData(name, password, balance, score);


//                        if(checkupdate){
//                            Toast.makeText(GameActivity.this, "Entry Updated", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            Toast.makeText(GameActivity.this, "Entry not Updated", Toast.LENGTH_SHORT).show();
//                        }
                    } else {
                        ohh.setVolume(100, 100);
                        ohh.start();
                        String name = user.getUsername();
                        String password = user.getPassword();
                        int balance = user.getBalance() - bet;
                        int score = user.getScore();
                        Boolean checkupdate = DB.updateUserData(name, password, balance, score);
//                        if(checkupdate){
//                            Toast.makeText(GameActivity.this, "Entry Updated", Toast.LENGTH_SHORT).show();
//                        }
//                        else {
//                            Toast.makeText(GameActivity.this, "Entry not Updated", Toast.LENGTH_SHORT).show();
//                        }
                    }
                    endflag = false;
                    bet = 0;

                    needButton.setEnabled(false);
                    stopButton.setEnabled(false);
                }
            }
        });

        coin10.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!startButton.isEnabled()) {
                    startButton.setEnabled(true);
                }
                if (user.getBalance() >= 10) {
                    bet = 10;
                    String b = "Bet: ";
                    String a = String.valueOf(b) + String.valueOf(bet);
                    bet_txt.setText(a);
                } else {
                    Toast.makeText(GameActivity.this, "Not enough balance!", Toast.LENGTH_SHORT).show();
                }

                coin.start();
            }
        });

        coin20.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!startButton.isEnabled()) {
                    startButton.setEnabled(true);
                }
                if (user.getBalance() >= 20) {
                    bet = 20;
                    String b = "Bet: ";
                    String a = String.valueOf(b) + String.valueOf(bet);
                    bet_txt.setText(a);
                } else {
                    Toast.makeText(GameActivity.this, "Not enough balance!", Toast.LENGTH_SHORT).show();
                }

                coin.start();
            }
        });

        coin50.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!startButton.isEnabled()) {
                    startButton.setEnabled(true);
                }
                if (user.getBalance() >= 50) {
                    bet = 50;
                    String b = "Bet: ";
                    String a = String.valueOf(b) + String.valueOf(bet);
                    bet_txt.setText(a);
                } else {
                    Toast.makeText(GameActivity.this, "Not enough balance!", Toast.LENGTH_SHORT).show();
                }

                coin.start();
            }
        });

        coin100.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (!startButton.isEnabled()) {
                    startButton.setEnabled(true);
                }
                if (user.getBalance() >= 100) {
                    bet = 100;
                    String b = "Bet: ";
                    String a = String.valueOf(b) + String.valueOf(bet);
                    bet_txt.setText(a);
                } else {
                    Toast.makeText(GameActivity.this, "Not enough balance!", Toast.LENGTH_SHORT).show();
                }

                coin.start();
            }
        });

        doubleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if ((bet * 2) < user.getBalance()) {
                    bet = bet * 2;
                    String b = "Bet: ";
                    String a = String.valueOf(b) + String.valueOf(bet);
                    bet_txt.setText(a);

                    coin.start();
                } else {
                    Toast.makeText(GameActivity.this, "Not enough balance!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                bet = 0;
                String b = "Bet: ";
                String a = String.valueOf(b) + String.valueOf(bet);
                bet_txt.setText(a);
            }
        });

        rankingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor res = DB.getdata();
                if (res.getCount() == 0) {
                    Toast.makeText(GameActivity.this, "Nothing existed!", Toast.LENGTH_SHORT).show();

                } else {
                    userList = new ArrayList<>();
                    Users user;
                    while (res.moveToNext()) {
                        user = new Users(res.getString(0), "hide", res.getInt(1), res.getInt(2));
                        userList.add(user);

                    }
                    Intent intent = new Intent(GameActivity.this, RankingActivity.class);
                    intent.putExtra("userlist", userList);
                    startActivity(intent);

                }
            }
        });

    }
}