package com.example.group2_blackjack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class GameActivity extends AppCompatActivity {

    private int player = 0;
    private int[] num = new int[52];
    private String[] rank = {"A","2","3","4","5","6","7","8","9","10","J","Q","K"};
    private String[] suit= {"Spade","Heart","Diamond","Club"};
    private ArrayList<Integer> user = new ArrayList<Integer>();
    private ArrayList<Integer> ai = new ArrayList<Integer>();
    private int currentPoint;
    private int computerPoint = 0;
    private int currentPage=0;

    private void shuffle() {
        for(int i = 0; i < 52 ;i++ ){
            num[i]=i;
        }
        for(int j = 0;j<26;j++){
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
        ai.add(num[1]);
        user.add(num[2]);
        ai.add(num[3]);
        currentPage=4;
    }

    private int calPoint(ArrayList<Integer> cards) {
        int temp = 0;
        for (Integer i : cards) {
            if(i%13+1>10){ //JQK=10
                temp += 10;
            }
            else{
                temp += (i+1)%13;
            }
        }
        return temp;
    }

    private ArrayList<Integer> needCard(ArrayList<Integer> cards) {

        cards.add(num [currentPage]);
        currentPage++;
        if(player == 0){
            currentPoint = calPoint(cards);
        }
        else {
            computerPoint = calPoint(cards);
        }
        return cards;
    }

    private void show() {
        for(Integer in:user){
            //textView
            //System.out.print(suit[in/13]+" "+rank[in%13]+"\t");
        }
        //textView

    }

    private void result() {
        if(currentPoint==21){
            Toast.makeText(GameActivity.this, "YOU WIN", Toast.LENGTH_SHORT).show();
        }else if(computerPoint>currentPoint){
            if(computerPoint>21) {
                Toast.makeText(GameActivity.this, "YOU LOST", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(GameActivity.this, "YOU WIN", Toast.LENGTH_SHORT).show();
            }
        }else if(currentPoint>computerPoint){
            if(currentPoint>21) {
                Toast.makeText(GameActivity.this, "YOU WIN", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(GameActivity.this, "YOU LOST", Toast.LENGTH_SHORT).show();
            }
        }

    }

    private void start(){
        shuffle();
        deal();
    }

    private void userTurn(){
        user = needCard(user);
        if(currentPoint >= 21){
            result();
        }
    }

    private void aiTurn(){
        player = 1;
        while(true) {
            if(computerPoint>currentPoint){
                break;
            }
            else if(computerPoint>21){
                break;
            }
            ai = needCard(ai);
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