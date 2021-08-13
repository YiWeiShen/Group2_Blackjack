package com.example.group2_blackjack;

import java.io.Serializable;

public class Users implements Serializable {

    private String username;
    private String password;
    private int balance;
    private int score;

    public Users(String username, String password, int balance, int score){
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.score = score;
    }

    public Users(String username, String password){
        this.username = username;
        this.password = password;
        this.balance = 1000;
        this.score = 0;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

}
