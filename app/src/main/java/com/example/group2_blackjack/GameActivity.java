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
    Button dealButton, hitButton, standButton, coin10, coin20, coin50, coin100, clearButton, doubleButton, rankingButton;
    TextView bet_txt, username_txt, balance_txt, score_txt, dealer_point_txt, user_point_txt;

    Users user;
    ArrayList<Users> userList;
    private DBHelper DB;
    private boolean endFlag = false;
    private int player = 0;
    private int bet = 0;
    private int[] nums = new int[52];
    private ArrayList<Integer> userCards = new ArrayList<Integer>();
    private ArrayList<Integer> dealerCards = new ArrayList<Integer>();
    private int userPoint;
    private int aiPoint = 0;
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
            nums[i] = i;
        }
        for (int j = 0; j < 300; j++) {
            Random r = new Random();
            int i1 = r.nextInt(52);
            int i2 = r.nextInt(52);

            int temp = nums[i2];
            nums[i2] = nums[i1];
            nums[i1] = temp;
        }
    }

    private void deal() {
        userCards.add(nums[0]);
        userCard1.setImageResource(cardArray[nums[0]]);
        dealerCards.add(nums[1]);
        aiCard1.setImageResource(cardArray[nums[1]]);
        userCards.add(nums[2]);
        userCard2.setImageResource(cardArray[nums[2]]);
        dealerCards.add(nums[3]);
        aiCard2.setImageResource(cardBack);
        userPoint = calPoint(userCards);
        user_point_txt.setText("User Point: " + String.valueOf(userPoint));
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

    private ArrayList<Integer> hitCard(ArrayList<Integer> cards) {

        cards.add(nums[currentPage]);
        currentPage++;
        if (player == 0) {
            userPoint = calPoint(cards);
            user_point_txt.setText("User Point: " + String.valueOf(userPoint));
        } else {
            aiPoint = calPoint(cards);
            dealer_point_txt.setText("Dealer Point: " + String.valueOf(aiPoint));
        }
        return cards;
    }

    private void show() {
        if (player == 0) {
            switch (userCards.size()) {
                case 5:
                    userCard5.setImageResource(cardArray[userCards.get(4)]);
                case 4:
                    userCard4.setImageResource(cardArray[userCards.get(3)]);
                case 3:
                    userCard3.setImageResource(cardArray[userCards.get(2)]);
                case 2:
                    userCard2.setImageResource(cardArray[userCards.get(1)]);
                case 1:
                    userCard1.setImageResource(cardArray[userCards.get(0)]);
            }
        } else {
            switch (dealerCards.size()) {
                case 5:
                    aiCard5.setImageResource(cardArray[dealerCards.get(4)]);
                case 4:
                    aiCard4.setImageResource(cardArray[dealerCards.get(3)]);
                case 3:
                    aiCard3.setImageResource(cardArray[dealerCards.get(2)]);
                case 2:
                    aiCard2.setImageResource(cardArray[dealerCards.get(1)]);
                case 1:
                    aiCard1.setImageResource(cardArray[dealerCards.get(0)]);
            }
        }
    }

    private boolean result() {
        boolean flag = false;
        userCards.clear();
        dealerCards.clear();
        currentPage = 0;

        if (userPoint == 21) {
            Toast.makeText(GameActivity.this, "YOU WIN", Toast.LENGTH_SHORT).show();
            flag = true;
        } else if (userPoint < 21 && userCards.size() == 5) {
            Toast.makeText(GameActivity.this, "YOU WIN", Toast.LENGTH_SHORT).show();
            flag = true;
        } else if (aiPoint > userPoint) {
            if (aiPoint > 21) {
                Toast.makeText(GameActivity.this, "YOU WIN", Toast.LENGTH_SHORT).show();
                flag = true;
            } else {
                Toast.makeText(GameActivity.this, "YOU LOST", Toast.LENGTH_SHORT).show();
                flag = false;
            }
        } else if (userPoint > aiPoint) {
            if (userPoint > 21 || dealerCards.size() == 5) {
                Toast.makeText(GameActivity.this, "YOU LOST", Toast.LENGTH_SHORT).show();
                flag = false;
            } else {
                Toast.makeText(GameActivity.this, "YOU WIN", Toast.LENGTH_SHORT).show();
                flag = true;
            }
        }

        clearButton.setEnabled(true);
        clearButton.setAlpha(1.00f);

        hitButton.setEnabled(false);
        hitButton.setAlpha(0.25f);

        standButton.setEnabled(false);
        standButton.setAlpha(0.25f);

        doubleButton.setEnabled(false);
        doubleButton.setAlpha(0.25f);

        endFlag = true;
        return flag;

    }

    private void userTurn() {
        player = 0;
        userCards = hitCard(userCards);
        show();
        if (userPoint >= 21 || userCards.size() == 5) {
            result();
        }
    }

    private void aiTurn() {
        player = 1;
        while (true) {
            if (aiPoint > userPoint || dealerCards.size() == 5) {
                break;
            } else if (aiPoint > 21) {
                break;
            }
            dealerCards = hitCard(dealerCards);
        }
        show();
        result();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        DB = new DBHelper(this);
        user = DB.getUserByName(getIntent().getExtras().getString("username"));

        dealButton = findViewById(R.id.btn_deal);
        hitButton = findViewById(R.id.btn_hit);
        standButton = findViewById(R.id.btn_stand);
        coin10 = findViewById(R.id.coin_10);
        coin20 = findViewById(R.id.coin_20);
        coin50 = findViewById(R.id.coin_50);
        coin100 = findViewById(R.id.coin_100);
        clearButton = findViewById(R.id.btn_clear);
        doubleButton = findViewById(R.id.btn_double);
        rankingButton = findViewById(R.id.btn_ranking);

        bet_txt = findViewById((R.id.bet));
        username_txt = findViewById(R.id.username_ingame);
        balance_txt = findViewById(R.id.balance_ingame);
        score_txt = findViewById(R.id.score);
        dealer_point_txt = findViewById(R.id.dealer_point);
        user_point_txt = findViewById(R.id.user_point);

        username_txt.setText("User: " + user.getUsername());
        balance_txt.setText("Balance: " + user.getBalance());
        score_txt.setText("Score: " + user.getScore());

        dealer_point_txt.setAlpha(0.0f);
        user_point_txt.setAlpha(0.0f);

        dealButton.setEnabled(false);
        dealButton.setAlpha(0.25f);

        hitButton.setEnabled(false);
        hitButton.setAlpha(0.25f);

        standButton.setEnabled(false);
        standButton.setAlpha(0.25f);

        doubleButton.setEnabled(false);
        doubleButton.setAlpha(0.25f);

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

        dealButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                // show points txt
                dealer_point_txt.setAlpha(1.0f);
                user_point_txt.setAlpha(1.0f);
                shuffle();
                deal();

                // disable buttons
                dealButton.setEnabled(false);
                dealButton.setAlpha(0.25f);
                coin10.setEnabled(false);
                coin10.setAlpha(0.25f);
                coin20.setEnabled(false);
                coin20.setAlpha(0.25f);
                coin50.setEnabled(false);
                coin50.setAlpha(0.25f);
                coin100.setEnabled(false);
                coin100.setAlpha(0.25f);
                clearButton.setEnabled(false);
                clearButton.setAlpha(0.25f);
                rankingButton.setEnabled(false);
                rankingButton.setAlpha(0.25f);

                // enable buttons
                hitButton.setAlpha(1.0f);
                hitButton.setEnabled(true);
                standButton.setAlpha(1.0f);
                standButton.setEnabled(true);
                doubleButton.setAlpha(1.0f);
                doubleButton.setEnabled(true);

            }
        });

        hitButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                userTurn();
                if (endFlag) {
                    // game ends and set player to 1 for showing the cards
                    player = 1;
                    show();
                    if (result()) {
                        // player wins
                        yea.setVolume(100, 100);
                        yea.start();
                        user.setBalance(user.getBalance() + bet * 2);
                        user.setScore(user.getScore() + bet);
                    } else {
                        // player loses
                        ohh.setVolume(100, 100);
                        ohh.start();
                    }

                    Boolean checkUpdate = DB.updateUserData(user.getUsername(), user.getPassword(), user.getBalance(), user.getScore());
                    Log.d("Update/End/UserTurn: ", String.valueOf(checkUpdate));
                }
            }
        });

        standButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                aiTurn();
                if (endFlag) {
                    // game ends and set player to 1 for showing the cards
                    player = 1;
                    show();
                    if (result()) {
                        // player wins
                        yea.setVolume(100, 100);
                        yea.start();
                        user.setBalance(user.getBalance() + bet * 2);
                        user.setScore(user.getScore() + bet);
                    } else {
                        // player loses
                        ohh.setVolume(100, 100);
                        ohh.start();
                    }
                    Boolean checkUpdate = DB.updateUserData(user.getUsername(), user.getPassword(), user.getBalance(), user.getScore());
                    Log.d("Update/End/aiTurn: ", String.valueOf(checkUpdate));
                }
            }
        });

        coin10.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (user.getBalance() >= 10) {
                    if (!dealButton.isEnabled()) {
                        dealButton.setEnabled(true);
                        dealButton.setAlpha(1.0f);
                    }
                    coin.start();
                    bet += 10;
                    user.setBalance(user.getBalance() - 10);
                    bet_txt.setText("Bet: " + String.valueOf(bet));
                    balance_txt.setText(("Balance: " + user.getBalance()));
                } else {
                    Toast.makeText(GameActivity.this, "Not enough balance!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        coin20.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (user.getBalance() >= 20) {
                    if (!dealButton.isEnabled()) {
                        dealButton.setEnabled(true);
                        dealButton.setAlpha(1.0f);
                    }
                    coin.start();
                    bet += 20;
                    user.setBalance(user.getBalance() - 20);
                    bet_txt.setText("Bet: " + String.valueOf(bet));
                    balance_txt.setText(("Balance: " + user.getBalance()));
                } else {
                    Toast.makeText(GameActivity.this, "Not enough balance!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        coin50.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (user.getBalance() >= 50) {
                    if (!dealButton.isEnabled()) {
                        dealButton.setEnabled(true);
                        dealButton.setAlpha(1.0f);
                    }
                    coin.start();
                    bet += 50;
                    user.setBalance(user.getBalance() - 50);
                    bet_txt.setText("Bet: " + String.valueOf(bet));
                    balance_txt.setText(("Balance: " + user.getBalance()));
                } else {
                    Toast.makeText(GameActivity.this, "Not enough balance!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        coin100.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (user.getBalance() >= 100) {
                    if (!dealButton.isEnabled()) {
                        dealButton.setEnabled(true);
                        dealButton.setAlpha(1.0f);
                    }
                    coin.start();
                    bet += 100;
                    user.setBalance(user.getBalance() - 100);
                    bet_txt.setText("Bet: " + String.valueOf(bet));
                    balance_txt.setText(("Balance: " + user.getBalance()));
                } else {
                    Toast.makeText(GameActivity.this, "Not enough balance!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        doubleButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (bet < user.getBalance()) {
                    coin.start();
                    user.setBalance(user.getBalance() - bet);
                    bet *= 2;
                    bet_txt.setText("Bet: " + String.valueOf(bet));
                    balance_txt.setText("Balance: " + user.getBalance());
                    userTurn();
                    if (endFlag) {
                        // game ends and set player to 1 for showing the cards
                        player = 1;
                        show();
                        if (result()) {
                            // player wins
                            yea.setVolume(100, 100);
                            yea.start();
                            user.setBalance(user.getBalance() + bet * 2);
                            user.setScore(user.getScore() + bet);
                            balance_txt.setText("Balance: " + user.getBalance());
                            score_txt.setText("Score: " + user.getScore());
                        } else {
                            // player loses
                            ohh.setVolume(100, 100);
                            ohh.start();
                        }
                        Boolean checkUpdate = DB.updateUserData(user.getUsername(), user.getPassword(), user.getBalance(), user.getScore());
                        Log.d("Update/End/UserTurn: ", String.valueOf(checkUpdate));
                    }
                } else {
                    Toast.makeText(GameActivity.this, "Not enough balance!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        clearButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                user.setBalance(user.getBalance() + bet);
                bet = 0;
                bet_txt.setText("Bet: " + String.valueOf(bet));
                balance_txt.setText(("Balance: " + user.getBalance()));

                dealButton.setEnabled(false);
                dealButton.setAlpha(0.25f);

                dealer_point_txt.setText("Dealer Point: ");
                dealer_point_txt.setAlpha(0.0f);
                user_point_txt.setAlpha(0.0f);

                score_txt.setText("Score: " + user.getScore());


                aiCard1.setImageResource(android.R.color.transparent);
                aiCard2.setImageResource(android.R.color.transparent);
                aiCard3.setImageResource(android.R.color.transparent);
                aiCard4.setImageResource(android.R.color.transparent);
                aiCard5.setImageResource(android.R.color.transparent);

                userCard1.setImageResource(android.R.color.transparent);
                userCard2.setImageResource(android.R.color.transparent);
                userCard3.setImageResource(android.R.color.transparent);
                userCard4.setImageResource(android.R.color.transparent);
                userCard5.setImageResource(android.R.color.transparent);

                coin10.setEnabled(true);
                coin10.setAlpha(1.00f);
                coin20.setEnabled(true);
                coin20.setAlpha(1.00f);
                coin50.setEnabled(true);
                coin50.setAlpha(1.00f);
                coin100.setEnabled(true);
                coin100.setAlpha(1.00f);

                rankingButton.setEnabled(true);
                rankingButton.setAlpha(1.00f);

                // reset game status
                player = 0;
                currentPage = 0;
                endFlag = false;
                bet = 0;
                aiPoint = 0;
                userCards = new ArrayList<Integer>();
                dealerCards = new ArrayList<Integer>();
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
                    while (res.moveToNext()) {
                        Users u = new Users(res.getString(0), "hide", res.getInt(1), res.getInt(2));
                        userList.add(u);
                    }
                    Intent intent = new Intent(GameActivity.this, RankingActivity.class);
                    intent.putExtra("userlist", userList);
                    startActivity(intent);
                }
            }
        });

    }
}