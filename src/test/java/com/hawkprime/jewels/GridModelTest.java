package com.hawkprime.jewels;

import org.junit.Test;

import java.awt.Point;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertThrows;

public class GridModelTest {

    @Test
    public void setCellsTest() {
        GridModel model = new GridModel(3, 3, 3);
        final Exception e = assertThrows(IllegalArgumentException.class, () -> model.setCells(new char[] { 'X' }));

        assertThat(e.getMessage(), is("Cell values does not match grid dimensions."));
    }

    @Test
    public void removeCellsTest() {
        GridModel model = new GridModel(4, 5, 3);
        model.setCells(new char[] {
                '0', '1', '0', 'X',
                '1', 'X', '1', '0',
                'X', 'X', 'X', '0',
                '2', 'X', '2', '0',
                '0', 'X', '0', 'X',
        });

        List<Point> cellsToRemove = model.checkAdjacentCells(1, 2);
        char[] newCells = model.removeCells(cellsToRemove);

        assertThat(newCells[0], is(not('0')));
        assertThat(newCells[1], is(not('0')));
        assertThat(newCells[2], is(not('0')));
        assertThat(newCells[3], is('X'));

        assertThat(newCells[4], is('0'));
        assertThat(newCells[5], is(not('0')));
        assertThat(newCells[6], is('0'));
        assertThat(newCells[7], is('0'));

        assertThat(newCells[8], is('1'));
        assertThat(newCells[9], is(not('0')));
        assertThat(newCells[10], is('1'));
        assertThat(newCells[11], is('0'));

        assertThat(newCells[12], is('2'));
        assertThat(newCells[13], is(not('0')));
        assertThat(newCells[14], is('2'));
        assertThat(newCells[15], is('0'));

        assertThat(newCells[16], is('0'));
        assertThat(newCells[17], is('1'));
        assertThat(newCells[18], is('0'));
        assertThat(newCells[19], is('X'));
    }

    @Test
    public void swapCellTest() {
        GridModel model = new GridModel(3, 3, 3);
        model.setCells(new char[] {
                '0', '0', '0',
                '0', 'X', '0',
                '0', '0', '0',
        });

        char[] cells = model.getCells();
        assertThat(cells[0], is('0'));
        assertThat(cells[1], is('0'));
        assertThat(cells[2], is('0'));
        assertThat(cells[3], is('0'));
        assertThat(cells[4], is('X'));
        assertThat(cells[5], is('0'));
        assertThat(cells[6], is('0'));
        assertThat(cells[7], is('0'));
        assertThat(cells[8], is('0'));

        model.swapCell(1, 1, 2, 1);
        cells = model.getCells();

        assertThat(cells[0], is('0'));
        assertThat(cells[1], is('0'));
        assertThat(cells[2], is('0'));
        assertThat(cells[3], is('0'));
        assertThat(cells[4], is('0'));
        assertThat(cells[5], is('X'));
        assertThat(cells[6], is('0'));
        assertThat(cells[7], is('0'));
        assertThat(cells[8], is('0'));
    }

    @Test
    public void checkAdjacentCellsTest() {
        GridModel model = new GridModel(4, 5, 3);
        model.setCells(new char[] {
                '0', '0', '0', 'X',
                '0', 'X', '0', '0',
                'X', 'X', 'X', '0',
                '0', 'X', 'X', '0',
                '0', 'X', 'X', 'X',
        });

        List<Point> matching = model.checkAdjacentCells(3, 0);
        assertThat(matching.isEmpty(), is(true));

        matching = model.checkAdjacentCells(1, 1);
        assertThat(matching.size(), is(4));
        assertThat(matching.contains(new Point(1, 1)), is(true));
        assertThat(matching.contains(new Point(1, 2)), is(true));
        assertThat(matching.contains(new Point(1, 3)), is(true));
        assertThat(matching.contains(new Point(1, 4)), is(true));

        matching = model.checkAdjacentCells(0, 2);
        assertThat(matching.size(), is(3));
        assertThat(matching.contains(new Point(0, 2)), is(true));
        assertThat(matching.contains(new Point(1, 2)), is(true));
        assertThat(matching.contains(new Point(2, 2)), is(true));

        matching = model.checkAdjacentCells(1, 2);
        assertThat(matching.size(), is(6));
        assertThat(matching.contains(new Point(0, 2)), is(true));
        assertThat(matching.contains(new Point(1, 2)), is(true));
        assertThat(matching.contains(new Point(2, 2)), is(true));
        assertThat(matching.contains(new Point(1, 1)), is(true));
        assertThat(matching.contains(new Point(1, 3)), is(true));
        assertThat(matching.contains(new Point(1, 4)), is(true));

        matching = model.checkAdjacentCells(2, 2);
        assertThat(matching.size(), is(5));
        assertThat(matching.contains(new Point(0, 2)), is(true));
        assertThat(matching.contains(new Point(1, 2)), is(true));
        assertThat(matching.contains(new Point(2, 2)), is(true));
        assertThat(matching.contains(new Point(2, 3)), is(true));
        assertThat(matching.contains(new Point(2, 4)), is(true));

        matching = model.checkAdjacentCells(1, 3);
        assertThat(matching.size(), is(4));
        assertThat(matching.contains(new Point(1, 1)), is(true));
        assertThat(matching.contains(new Point(1, 2)), is(true));
        assertThat(matching.contains(new Point(1, 3)), is(true));
        assertThat(matching.contains(new Point(1, 4)), is(true));

        matching = model.checkAdjacentCells(2, 3);
        assertThat(matching.size(), is(3));
        assertThat(matching.contains(new Point(2, 2)), is(true));
        assertThat(matching.contains(new Point(2, 3)), is(true));
        assertThat(matching.contains(new Point(2, 4)), is(true));

        matching = model.checkAdjacentCells(1, 4);
        assertThat(matching.size(), is(6));
        assertThat(matching.contains(new Point(1, 1)), is(true));
        assertThat(matching.contains(new Point(1, 2)), is(true));
        assertThat(matching.contains(new Point(1, 3)), is(true));
        assertThat(matching.contains(new Point(1, 4)), is(true));
        assertThat(matching.contains(new Point(2, 4)), is(true));
        assertThat(matching.contains(new Point(3, 4)), is(true));

        matching = model.checkAdjacentCells(2, 4);
        assertThat(matching.size(), is(5));
        assertThat(matching.contains(new Point(2, 2)), is(true));
        assertThat(matching.contains(new Point(2, 3)), is(true));
        assertThat(matching.contains(new Point(2, 4)), is(true));
        assertThat(matching.contains(new Point(2, 4)), is(true));
        assertThat(matching.contains(new Point(3, 4)), is(true));

        matching = model.checkAdjacentCells(3, 4);
        assertThat(matching.size(), is(3));
        assertThat(matching.contains(new Point(1, 4)), is(true));
        assertThat(matching.contains(new Point(2, 4)), is(true));
        assertThat(matching.contains(new Point(3, 4)), is(true));
    }
}