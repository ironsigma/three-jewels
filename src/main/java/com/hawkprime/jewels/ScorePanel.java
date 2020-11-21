package com.hawkprime.jewels;




import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.prefs.Preferences;


public class ScorePanel extends JPanel implements ActionListener{
    private static final int HEIGHT = 70;
    private static final int WIDTH = 8 * 120;
    private Font scoreFont;

    private static String fileName = "BestScore";
    private static int highScore = 0;

    private static Preferences preferences;


    public ScorePanel(){
        scoreFont = new Font("IMMORTAL",Font.BOLD,30);

        try{
            preferences = Preferences.userNodeForPackage(com.hawkprime.jewels.ScorePanel.class);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        createPanel();

    }

    public void createPanel(){
        loadHighScore();
        setPreferredSize(new Dimension(WIDTH,HEIGHT));
        setBackground(Color.DARK_GRAY);

        new Timer(10, this).start();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.setFont(scoreFont);
        g.drawString("Score: "+Player.getScore(), 30,35);
        g.drawString("Moves: "+Player.getMoves(),30 ,70);
        g.drawString("High Score: " +highScore, WIDTH - 300, 40);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    private void loadHighScore(){
        try{
            highScore = preferences.getInt("BestScore" , 0);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void setHighScore(){

        try{
            if(Player.getScore() >= highScore){
                preferences.putInt("BestScore", Player.getScore());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
}


