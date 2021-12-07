package org.ybw.nb;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class MainCanvas extends JPanel {
	TriangleNode[] triangleNode;

	public MainCanvas(FrameContainer frame) {
		// super(frame);
		BufferedImage bi = new BufferedImage(700, 700, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = bi.createGraphics();
		graphics.setColor(Color.GRAY);
		graphics.fillRect(0, 0, 700, 700);

		triangleNode = new TriangleNode[30];
		for (int i = 0; i < 30; i++) {
			triangleNode[i] = new TriangleNode(
				new int[][]{{getRandInt(0, 700), getRandInt(0, 700), getRandInt(0, 700)}, {getRandInt(0, 700), getRandInt(0, 700), getRandInt(0, 700)}},
				Color.getHSBColor((float) Math.random(), 1, 1)
			);
		}

		new ChangeService().start();
	}

	private int getRandInt(int min, int max) {
		return (int) (min + Math.random() * (max - min + 1));
	}

	@Override
	public void paint(Graphics graphics) {
		graphics.setColor(Color.GRAY);
		graphics.fillRect(0, 0, 700, 720);

		for (int i = 0; i < 1; i++) {
			triangleNode[i].render((Graphics2D) graphics);
		}
	}

	class ChangeService extends Thread {
		@Override
		public void run() {
			try {
				while (true) {
					for (int i = 0; i < 1; i++) {
						if (Arrays.deepEquals(triangleNode[i].nowP, triangleNode[i].setP)) {
							int[][] newPos = new int[][]{{getRandInt(0, 700), getRandInt(0, 700), getRandInt(0, 700)}, {getRandInt(0, 700), getRandInt(0, 700), getRandInt(0, 700)}};
							triangleNode[i].setLocation(newPos, triangleNode[i].bg);
						}
					}
					repaint();
					sleep(10);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}