package com.hawkprime.jewels;

import sun.java2d.pipe.DrawImage;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class ScorePanel extends JPanel implements ActionListener{
    private static final int HEIGHT = 70;
    private static final int WIDTH = 8 * 120;
    private Font scoreFont;

    private static String saveDataPath;
    private static String fileName = "BestScore";
    private static int highScore = 0;

    public ScorePanel(){
        scoreFont = new Font("IMMORTAL",Font.BOLD,30);

        try{
            saveDataPath = ScorePanel.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath();
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

    private void createSaveData(){
        try{
            File file = new File(saveDataPath, fileName);

            FileWriter output = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(output);
            writer.write(""+0);
            writer.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void loadHighScore(){
        try{
            File file = new File(saveDataPath, fileName);
            if(!file.isFile()){
                createSaveData();
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            highScore = Integer.parseInt(reader.readLine());
            reader.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void setHighScore(){
        FileWriter output = null;

        try{
            File file = new File(saveDataPath,fileName);
            output = new FileWriter(file);
            BufferedWriter writer = new BufferedWriter(output);

            if(Player.getScore() >= highScore){
                writer.write(""+Player.getScore());
            }

            writer.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}


