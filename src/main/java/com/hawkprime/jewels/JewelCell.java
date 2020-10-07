package com.hawkprime.jewels;

import lombok.Getter;
import lombok.Setter;

import java.awt.Image;
import java.awt.Point;

@Getter
@Setter
public class JewelCell {
    private Point cell;
    private Point position;
    private Image image;

    public boolean isAtCell(Point c) {
        if (cell == null) {
            return false;
        }
        return cell.x == c.x && cell.y == c.y;
    }

    public void clear() {
        cell = null;
        position = null;
        image = null;
    }

    public boolean isValidMovingPosition(Point target) {
        return  (target.x == cell.x && target.y == cell.y) ||
                (target.x == cell.x - 1 && target.y == cell.y) ||
                (target.x == cell.x + 1 && target.y == cell.y) ||
                (target.x == cell.x && target.y == cell.y - 1) ||
                (target.x == cell.x && target.y == cell.y + 1);
    }
}
