package org.ybw.nb;

import javax.swing.*;
import java.awt.*;

public class MainCanvas extends JPanel {
    TriangleNode[] triangleNode;

    public MainCanvas() {
        triangleNode = new TriangleNode[33];
        for (int i = 0; i < 33; i++) {
            triangleNode[i] = new TriangleNode(NodeDataSet.NODE_COORDINATE_DATA[0][i], NodeDataSet.NODE_COLOR_SET[0][i]);
        }


        new ChangeService().start();
        new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            for (int i = 0; i < 33; i++) {
                try {
                    triangleNode[i].setLocation(NodeDataSet.NODE_COORDINATE_DATA[1][i], NodeDataSet.NODE_COLOR_SET[1][i], 50 + i * 8, i * 2);
                } catch (ArrayIndexOutOfBoundsException e) {
                    triangleNode[i].kill(140, 30);
                }
            }
        }).start();
    }


    @Override
    public void paint(Graphics graphics) {
        graphics.setColor(Color.GRAY);
        graphics.fillRect(0, 0, 1400, 1000);
        for (int i = 0; i < 3; i++) {
            triangleNode[i + 30].render((Graphics2D) graphics);
        }

        for (int i = 0; i < 30; i++) {
            triangleNode[i].render((Graphics2D) graphics);
        }
    }

    class ChangeService extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    repaint();
                    sleep(10);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}