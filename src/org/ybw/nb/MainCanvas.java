package org.ybw.nb;

import javax.swing.*;
import java.awt.*;

public class MainCanvas extends JPanel {
	TriangleNode[] triangleNode;

	int nowAnimalIndex;

	public MainCanvas() {
		triangleNode = new TriangleNode[33];
		for (int i = 0; i < 33; i++) {
			try {
				triangleNode[i] = new TriangleNode(NodeDataSet.NODE_COORDINATE_DATA[0][i], NodeDataSet.NODE_COLOR_SET[0][i]);
			} catch (ArrayIndexOutOfBoundsException e) {
				triangleNode[i] = new TriangleNode();
			}
		}

		new ChangeService().start();
		new Thread(() -> {
			try {
				for (int i = 0; i < NodeDataSet.NODE_COORDINATE_DATA.length; ) {
					Thread.sleep(1000);
					for (int j = 0; j < 33; j++) {
						try {
							triangleNode[j].setLocation(NodeDataSet.NODE_COORDINATE_DATA[i][j], NodeDataSet.NODE_COLOR_SET[i][j], 50 + j * 8, j * 2);
						} catch (ArrayIndexOutOfBoundsException e) {
							triangleNode[j].kill(140, 30);
						}
					}
					Thread.sleep(1000);
					if (++i == NodeDataSet.NODE_COORDINATE_DATA.length) {
						i = 0;
					}
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
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