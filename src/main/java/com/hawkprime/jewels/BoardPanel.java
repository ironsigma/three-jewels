package com.hawkprime.jewels;

import lombok.extern.slf4j.Slf4j;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

@Slf4j
public class BoardPanel extends JPanel implements MouseListener, MouseMotionListener, ActionListener {
    private static final int GRID_WIDTH = 8;
    private static final int GRID_HEIGHT = 8;
    private static final int JEWEL_COUNT = 10;
    private static final int JEWEL_WIDTH = 120;
    private static final int JEWEL_HEIGHT = 120;

    private Image[] jewelsImage;
    private GridModel model;
    private JewelCell movingJewel;
    private JewelCell targetJewel;
    private Point lastMousePos;

    public BoardPanel() {
        initComponents();
    }

    public void initComponents() {
        // init vars
        model = new GridModel(GRID_WIDTH, GRID_HEIGHT, JEWEL_COUNT);
        movingJewel = new JewelCell();
        targetJewel = new JewelCell();

        // load images
        jewelsImage = splitImage(loadImage("jewels.png"), JEWEL_WIDTH, JEWEL_HEIGHT);

        // set panel size
        setPreferredSize(new Dimension(GRID_WIDTH * JEWEL_WIDTH, GRID_HEIGHT * JEWEL_HEIGHT));

        // add mouse listeners
        addMouseListener(this);
        addMouseMotionListener(this);

        // image refresh timer
        new Timer(10, this).start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the jewels
        Point pos = new Point(0, 0);
        for (char cell : model.getCells()) {

            // If the current x, y is the same as the moving or target jewel, skip it
            if (!movingJewel.isAtCell(pos) && !targetJewel.isAtCell(pos)) {
                g.drawImage(modelCharToImage(cell), pos.x * JEWEL_WIDTH, pos.y * JEWEL_HEIGHT, this);
            }

            // Increase the x position, if we're at the edge, move to the next row
            pos.x += 1;
            if (pos.x >= GRID_WIDTH) {
                pos.x = 0;
                pos.y += 1;
            }
        }

        // If we have a moving jewel, draw it
        if (movingJewel.getImage() != null) {
            pos = movingJewel.getPosition();
            g.drawImage(movingJewel.getImage(), pos.x, pos.y, this);
        }

        // If we're hovering over a target jewel
        // draw the target jewel at the moving jewel's original location.
        // so it looks like they swapped places.
        if (targetJewel.getImage() != null) {
            pos = movingJewel.getCell();
            g.drawImage(targetJewel.getImage(), pos.x * JEWEL_WIDTH, pos.y * JEWEL_HEIGHT, this);
        }

        // make sure the display get updated and not buffered.
        Toolkit.getDefaultToolkit().sync();
    }

    private Image modelCharToImage(char index) {
        return jewelsImage[index];
    }

    private Point mousePointToGridCell(Point click) {
        return new Point(click.x / JEWEL_WIDTH, click.y / JEWEL_HEIGHT);
    }

    @Override
    public void mousePressed(MouseEvent mouseEvent) {
        // Save this mouse position as last mouse position for smooth relative movement.
        lastMousePos = mouseEvent.getPoint();

        // Convert the mouse position to a cell in the grid.
        Point cell = mousePointToGridCell(mouseEvent.getPoint());

        // Save the original jewel location (grid) and position (screen) and grab the image.
        movingJewel.setCell(cell);
        movingJewel.setPosition(new Point(cell.x * JEWEL_WIDTH, cell.y * JEWEL_HEIGHT));
        movingJewel.setImage(modelCharToImage(model.getCell(cell.x, cell.y)));
    }

    @Override
    public void mouseDragged(MouseEvent mouseEvent) {
        // Convert the mouse position to a cell in the grid.
        Point cell = mousePointToGridCell(mouseEvent.getPoint());

        // If the cell is not a valid moving position, just return
        if (!movingJewel.isValidMovingPosition(cell)) {
            return;
        }

        // get the current mouse position and calculate the delta.
        // use the delta to move the jewel in the same way
        Point mousePos = mouseEvent.getPoint();
        movingJewel.getPosition().translate(mousePos.x - lastMousePos.x, mousePos.y - lastMousePos.y);

        // save current mouse potion
        lastMousePos = mousePos;

        // if the moving jewel is hovering over its original position
        // clear out the target jewel so it goes back to its position
        if (cell.equals(movingJewel.getCell())) {
            targetJewel.clear();

        } else {
            // moving jewel is hovering over another jewel, save the
            // target jewel's original position, and get the image so
            // we can swap it with the moving jewel's position
            targetJewel.setCell(cell);
            targetJewel.setImage(modelCharToImage(model.getCell(cell.x, cell.y)));
        }
    }

    @Override
    public void mouseReleased(MouseEvent mouseEvent) {
        // Convert the mouse position to a cell in the grid.
        Point cell = mousePointToGridCell(mouseEvent.getPoint());

        // if the moving jewel is at a new grid position, tell the model to swap it.
        if (!movingJewel.isAtCell(cell)) {
            Point source = movingJewel.getCell();
            Point target = targetJewel.getCell();
            model.swapCell(source.x, source.y, target.x, target.y);

            // Check adjacent cells on the target and source,
            // then remove the list of matching cells from the grid
            model.removeCells(model.checkAdjacentCells(target.x, target.y));
            model.removeCells(model.checkAdjacentCells(source.x, source.y));
        }

        // clear the moving and target jewels
        movingJewel.clear();
        targetJewel.clear();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        // This gets called by the timer, we just request a repaint.
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

    public static BufferedImage loadImage(String filename) {
        // make a URL from the file name
        URL url = BoardPanel.class.getResource("/" + filename);
        if (url == null) {
            throw new IllegalArgumentException("Image \"" + filename + "\" not found.");
        }

        // Load the image using the URL
        try {
            return ImageIO.read(url);
        } catch (IOException e) {
            throw new IllegalArgumentException("Cannot load \"" + filename + "\".");
        }
    }

    private Image[] splitImage(BufferedImage image, int cellWidth, int cellHeight) {
        // figure out how many rows and columns are in the image
        int rows = image.getHeight() / cellHeight;
        int cols = image.getWidth() / cellWidth;

        // create an array to hold the images
        Image[] imageArray = new Image[rows * cols];

        // extract each image into the array
        for (int x = 0; x < cols; ++x) {
            for (int y = 0; y < rows; ++y) {
                imageArray[x + y * cols] = image.getSubimage(
                        x * cellWidth, y * cellHeight,
                        cellWidth, cellHeight);
            }
        }
        return imageArray;
    }
}
