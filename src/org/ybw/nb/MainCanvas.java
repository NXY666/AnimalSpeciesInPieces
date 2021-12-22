package org.ybw.nb;

import org.ybw.nb.animations.BonesMap;
import org.ybw.nb.animations.TimelineMap;
import org.ybw.nb.animations.Transformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Set;

public class MainCanvas extends Component {
	JFrame frame;
	TriangleNode[] triangleNode;

	int activeAnimalIndex = 0;
	volatile int activeAnimalFrame = 0;
	Color nowBg, setBg;
	int nowBgAlpha = 0;
	boolean showBg = false;

	public MainCanvas(JFrame frame) {
		this.frame = frame;
		setBg = nowBg = Color.decode(DataContainer.BG_COLOR_SET[0]);
		triangleNode = new TriangleNode[33];
		for (int i = 0; i < 33; i++) {
			try {
				triangleNode[i] = new TriangleNode(DataContainer.NODE_COORDINATE_DATA[0][i], DataContainer.NODE_COLOR_SET[0][i]);
			} catch (ArrayIndexOutOfBoundsException e) {
				triangleNode[i] = new TriangleNode();
			}
		}

		new RepaintService().start();
		new ChangeService().start();

		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
					case KeyEvent.VK_LEFT -> toPreAnimal();
					case KeyEvent.VK_RIGHT -> toNextAnimal();
					case KeyEvent.VK_SPACE -> showBg = !showBg;
					case KeyEvent.VK_ESCAPE -> System.exit(0);
				}
			}
		});
	}

	@Override
	public void paint(Graphics graphics) {
		// 背景
		int disRed = 0, disGreen = 0, disBlue = 0;
		if (setBg.getRed() != nowBg.getRed()) {
			disRed = setBg.getRed() - nowBg.getRed() > 0 ? 1 : -1;
		}
		if (setBg.getGreen() != nowBg.getGreen()) {
			disGreen = setBg.getGreen() - nowBg.getGreen() > 0 ? 1 : -1;
		}
		if (setBg.getBlue() != nowBg.getBlue()) {
			disBlue = setBg.getBlue() - nowBg.getBlue() > 0 ? 1 : -1;
		}
		if (showBg) {
			if (nowBgAlpha < 255) {
				nowBgAlpha += 5;
			}
		} else {
			if (nowBgAlpha > 0) {
				nowBgAlpha -= 5;
			}
		}
		nowBg = new Color(nowBg.getRed() + disRed, nowBg.getGreen() + disGreen, nowBg.getBlue() + disBlue, nowBgAlpha);
		int screenX = (int) (DataContainer.SCREEN_X_SCALE * 100), screenY = (int) (DataContainer.SCREEN_Y_SCALE * 100);
		graphics.setColor(nowBg);
		graphics.fillRect(0, 0, screenX, screenY + 40);

		// 动画
		TimelineMap timelineMap = DataContainer.ANIMATIONS[activeAnimalIndex].getTimelineMap();
		BonesMap moveBlocks = timelineMap.getOrDefault(activeAnimalFrame, new BonesMap());
		Set<Integer> blockIndex = moveBlocks.keySet();
		for (int blockId : blockIndex) {
			Transformer transformer = moveBlocks.get(blockId);
			double[][] rawMoveToList = transformer.getMoveTo();
			int[][] moveToPos;
			if (rawMoveToList != null) {
				moveToPos = new int[2][3];
				for (int nodeIndex = 0; nodeIndex < 3; nodeIndex++) {
					moveToPos[0][nodeIndex] = (int) (rawMoveToList[0][nodeIndex] * DataContainer.SCREEN_X_SCALE);
					moveToPos[1][nodeIndex] = (int) (rawMoveToList[1][nodeIndex] * DataContainer.SCREEN_Y_SCALE);
				}
			} else {
				moveToPos = DataContainer.NODE_COORDINATE_DATA[activeAnimalIndex][blockId];
			}

			String rawEditColor = transformer.getEditColor(), editColor;
			if (rawEditColor != null) {
				editColor = rawEditColor;
			} else {
				editColor = DataContainer.NODE_COLOR_SET[activeAnimalIndex][blockId];
			}

			triangleNode[blockId].setLocation(moveToPos, editColor, transformer.getTransFrame(), transformer.getWaitFrame());

			if (transformer.getRepeatFrame() != -1) {
				BonesMap newBonesMap = timelineMap.getOrDefault(activeAnimalFrame + transformer.getRepeatFrame(), new BonesMap());

				newBonesMap.put(blockId, transformer);
				timelineMap.put(activeAnimalFrame + transformer.getRepeatFrame(), newBonesMap);
			}
		}

		// 三角形
		for (int i = 0; i < 3; i++) {
			triangleNode[i + 30].render((Graphics2D) graphics);
		}
		for (int i = 0; i < 30; i++) {
			triangleNode[i].render((Graphics2D) graphics);
		}

		synchronized (this) {
			activeAnimalFrame++;
		}
	}

	void refreshAnimal() {
		activeAnimalFrame = 0;
		setBg = Color.decode(DataContainer.BG_COLOR_SET[activeAnimalIndex]);
		for (int j = 0; j < 33; j++) {
			try {
				triangleNode[j].setLocation(DataContainer.NODE_COORDINATE_DATA[activeAnimalIndex][j], DataContainer.NODE_COLOR_SET[activeAnimalIndex][j], 25 + j * 6, j);
			} catch (ArrayIndexOutOfBoundsException e) {
				triangleNode[j].kill(25 + j * 6, j);
			}
		}
	}

	void toPreAnimal() {
		if (--activeAnimalIndex < 0) {
			activeAnimalIndex = DataContainer.NODE_COORDINATE_DATA.length - 1;
		}
		refreshAnimal();
	}

	void toNextAnimal() {
		if (++activeAnimalIndex >= DataContainer.NODE_COORDINATE_DATA.length) {
			activeAnimalIndex = 0;
		}
		refreshAnimal();
	}

	class RepaintService extends Thread {
		@Override
		public void run() {
			//noinspection InfiniteLoopStatement
			while (true) {
				repaint();
			}
		}
	}

	class ChangeService extends Thread {
		@Override
		public void run() {
			for (activeAnimalIndex = -1; activeAnimalIndex < DataContainer.NODE_COORDINATE_DATA.length; ) {
				toNextAnimal();
				while (activeAnimalFrame < 1500) {
					Thread.onSpinWait();
				}
			}
		}
	}
}