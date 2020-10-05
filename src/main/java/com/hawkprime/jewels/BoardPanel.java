package com.hawkprime.jewels;

import lombok.extern.slf4j.Slf4j;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

@Slf4j
public class BoardPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    private transient Image jewelsImage;

    public BoardPanel() {
        initComponents();
    }

    public void initComponents() {
        // load images
        jewelsImage = getImage("gems.png");

        // set panel size
        setPreferredSize(new Dimension(jewelsImage.getWidth(null), jewelsImage.getHeight(null)));

        // add listeners
        addMouseListener(this);
        addMouseMotionListener(this);

        // image refresh timer
        new Timer(10, this).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawImage(jewelsImage, 0, 0, this);

        Toolkit.getDefaultToolkit().sync();
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        /* empty */
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        /* empty */
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        /* empty */
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        repaint();
    }

    @Override
    public void mouseClicked(MouseEvent mouseEvent) {
        /* No need to handle clicks */
    }

    @Override
    public void mouseMoved(MouseEvent mouseEvent) {
        /* No need to handle moves */
    }

    @Override
    public void mouseEntered(MouseEvent mouseEvent) {
        /* No need to test for enter */
    }

    @Override
    public void mouseExited(MouseEvent mouseEvent) {
        /* No need to test for exit */
    }

    public static Image getImage(String filename) {
        URL url = BoardPanel.class.getResource("/" + filename);
        if (url == null) {
            throw new IllegalArgumentException("Image \"" + filename + "\" not found.");
        }
        return new ImageIcon(url).getImage();
    }
}
