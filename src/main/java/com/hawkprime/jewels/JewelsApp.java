package com.hawkprime.jewels;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;

@Slf4j
public class JewelsApp extends JFrame {
    public JewelsApp() {
        initUI();
    }

    private void initUI() {
        BoardPanel boardPanel = new BoardPanel();
        ScorePanel scorePanel = new ScorePanel();

        setTitle("Jewels");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        add(scorePanel, BorderLayout.PAGE_START);
        add(boardPanel, BorderLayout.CENTER);

        pack();
        setResizable(false);
        setVisible(true);
        setLocationRelativeTo(null);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JewelsApp app = new JewelsApp();
            app.setVisible(true);
        });
    }
}
