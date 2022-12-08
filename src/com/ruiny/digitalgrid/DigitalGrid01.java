package com.ruiny.digitalgrid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DigitalGrid01 {

    record GenGridResult(String gridText, List<Integer> losts) {}
    
    static GenGridResult generateText(int cols, int rows, int param1) {

        var rnd = new Random();

        StringBuilder grid = new StringBuilder();
        int n = 0;
        ArrayList<Integer> losts = new ArrayList<>();

        for(int y = 0; y < rows; ++y) {
            for(int x = 0; x < cols; ++x) {
                if(rnd.nextInt(param1)==0) {
                    losts.add(n);
                    n = ++n < param1 ? n : 0;
                }
                grid.append(n);
                n = ++n < param1 ? n : 0;
            }
            grid.append("\n");
        }

        return new GenGridResult(grid.toString(), losts);
    }
    public static void main(String[] args) {
        System.out.println("# DigitalGrid01");

        final int COLS = 13;
        final int ROWS = 9;
        final int DIGITS = 10;

        GenGridResult grid = generateText(COLS, ROWS, DIGITS);
        System.out.println("# grid.losts: " + grid.losts);

        var pane = new JTextPane();
        pane.setEditable(false);
        pane.setFont(new Font("Courier New", Font.BOLD, 48));
        pane.setText(grid.gridText);


        pane.addKeyListener(new KeyAdapter() {
            int lostsIndex = 0;
            @Override
            public void keyTyped(KeyEvent e) {

                int n = Integer.parseInt( "" + e.getKeyChar() );
                int lost = grid.losts.get(lostsIndex);
                if(lost == n) {
                    System.out.println("# OK " + n);
                    pane.setText(pane.getText() + n);
                    ++lostsIndex;
                } else {
                    pane.setText(pane.getText() + '!');
                    System.out.println("# WRONG " + n + " (" + lost + ")");
                }

                if(lostsIndex == grid.losts.size()) {
                    System.out.println("# DONE.");
                    pane.setText( pane.getText() + '.');
                    pane.removeKeyListener(this);
                }

            }
        });

        var frame = new JFrame("DigitalGrid01");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setSize(700,700);
        frame.setContentPane(pane);
        frame.setVisible(true);
    }
}
