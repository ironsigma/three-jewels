package com.hawkprime.jewels;

public class Player {
    public static int score;
    public static int moves;

    public Player(){
        score = 0;
        moves = 0;
    }

    public Player(int playerScore){
        this.score = playerScore;
    }

    public static void setScore(int addScore){
        score = score + addScore;
    }

    public static int getScore(){
        return score;
    }

    public static void addMove(int move){
        moves = moves + move;
    }
    public static int getMoves(){
        return moves;
    }
}