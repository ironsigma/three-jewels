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

        add(boardPanel);

        pack();
        setResizable(false);

        setTitle("Jewels");
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            JewelsApp app = new JewelsApp();
            app.setVisible(true);
        });
    }
}
