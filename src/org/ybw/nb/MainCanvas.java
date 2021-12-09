package org.ybw.nb;

import java.awt.*;

public class MainCanvas extends Component {
	TriangleNode[] triangleNode;

	int nowAnimalIndex = 0;
	Color nowBg, setBg;

	public MainCanvas() {
		setBg = nowBg = Color.decode(DataContainer.BG_COLOR_SET[0]);
		triangleNode = new TriangleNode[33];
		for (int i = 0; i < 33; i++) {
			try {
				triangleNode[i] = new TriangleNode(DataContainer.NODE_COORDINATE_DATA[0][i], DataContainer.NODE_COLOR_SET[0][i]);
			} catch (ArrayIndexOutOfBoundsException e) {
				triangleNode[i] = new TriangleNode();
			}
		}

		new ChangeService().start();
		new Thread(() -> {
			try {
				for (int i = 0; i < DataContainer.NODE_COORDINATE_DATA.length; ) {
					//noinspection BusyWait
					Thread.sleep(1500);
					nowAnimalIndex = i;
					setBg = Color.decode(DataContainer.BG_COLOR_SET[i]);
					for (int j = 0; j < 33; j++) {
						try {
							triangleNode[j].setLocation(DataContainer.NODE_COORDINATE_DATA[i][j], DataContainer.NODE_COLOR_SET[i][j], 50 + j * 8, j * 2);
						} catch (ArrayIndexOutOfBoundsException e) {
							triangleNode[j].kill(140, 30);
						}
					}
					//noinspection BusyWait
					Thread.sleep(1500);
					if (++i == DataContainer.NODE_COORDINATE_DATA.length) {
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
		if (setBg.getRed() != nowBg.getRed()) {
			int dis = setBg.getRed() - nowBg.getRed() > 0 ? 1 : -1;
			nowBg = new Color(nowBg.getRed() + dis, nowBg.getGreen(), nowBg.getBlue());
		}
		if (setBg.getBlue() != nowBg.getBlue()) {
			int dis = setBg.getBlue() - nowBg.getBlue() > 0 ? 1 : -1;
			nowBg = new Color(nowBg.getRed(), nowBg.getGreen(), nowBg.getBlue() + dis);
		}
		if (setBg.getGreen() != nowBg.getGreen()) {
			int dis = setBg.getGreen() - nowBg.getGreen() > 0 ? 1 : -1;
			nowBg = new Color(nowBg.getRed(), nowBg.getGreen() + dis, nowBg.getBlue());
		}
		graphics.setColor(nowBg);
		int screenX = (int) (DataContainer.SCREEN_X_SCALE * 100), screenY = (int) (DataContainer.SCREEN_Y_SCALE * 100);
		graphics.fillRect(0, 0, screenX, screenY + 40);
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
				//noinspection InfiniteLoopStatement
				while (true) {
					repaint();
					//noinspection BusyWait
					sleep(5);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}