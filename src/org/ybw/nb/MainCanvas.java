package org.ybw.nb;

import org.ybw.nb.animations.BonesMap;
import org.ybw.nb.animations.TimelineMap;
import org.ybw.nb.animations.Transformer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Set;

public class MainCanvas extends Component {
	JFrame frame;
	TriangleNode[] triangleNode;

	long frameStartTime = System.currentTimeMillis();
	int activeAnimalIndex = 0, activeAnimalFrame = 0, preAnimalFrame = -1;
	Color nowBg, setBg;
	int nowBgAlpha = 0;
	volatile boolean showBg = true, painting = false;

	public MainCanvas(JFrame frame) {
		this.frame = frame;
		setBg = nowBg = DataContainer.ColorDecoder(DataContainer.BG_COLOR_SET[0]);
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
					case KeyEvent.VK_SPACE -> //noinspection NonAtomicOperationOnVolatileField
						showBg = !showBg;
					case KeyEvent.VK_ESCAPE -> System.exit(0);
				}
			}
		});

		frame.addWindowListener(new WindowAdapter() {
			@Override
			public void windowDeiconified(WindowEvent e) {
				System.out.println(e);
				painting = false;
				refreshAnimal();
			}
		});
	}

	@Override
	public void paint(Graphics screenGraphics) {
		Graphics2D screenGraphics2D = (Graphics2D) screenGraphics;
		screenGraphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		synchronized (this) {
			preAnimalFrame = activeAnimalFrame;
			activeAnimalFrame = (int) ((System.currentTimeMillis() - frameStartTime) * DataContainer.FRAME_PER_SECOND / 1000);
			int deltaFrame = activeAnimalFrame - preAnimalFrame;
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
			screenGraphics2D.setColor(nowBg);
			screenGraphics2D.fillRect(0, 0, screenX, screenY);
			if (showBg) {
				screenGraphics2D.drawImage(DataContainer.GRAD_BG, screenX / 2 - DataContainer.GRAD_BG.getWidth() / 2, screenY / 2 - DataContainer.GRAD_BG.getHeight() / 2, null);
			}

			// 动画（需补齐所有帧）
			TimelineMap timelineMap = DataContainer.ANIMATIONS[activeAnimalIndex].getTimelineMap();
			for (int frame = preAnimalFrame + 1; frame <= activeAnimalFrame; frame++) {
				BonesMap moveBones = timelineMap.getOrDefault(frame, new BonesMap());
				// if (timelineMap.containsValue(moveBones)) {
				// 	System.out.println(frame);
				// } else {
				// 	System.err.println(frame);
				// }
				Set<Integer> boneIndex = moveBones.keySet();
				for (int boneId : boneIndex) {
					Transformer transformer = moveBones.get(boneId);
					double[][] rawMoveToList = transformer.getMoveTo();
					int[][] moveToPos;
					if (rawMoveToList != null) {
						moveToPos = new int[2][3];
						for (int nodeIndex = 0; nodeIndex < 3; nodeIndex++) {
							moveToPos[0][nodeIndex] = (int) (rawMoveToList[0][nodeIndex] * DataContainer.SCREEN_X_SCALE);
							moveToPos[1][nodeIndex] = (int) (rawMoveToList[1][nodeIndex] * DataContainer.SCREEN_Y_SCALE);
						}
					} else {
						moveToPos = DataContainer.NODE_COORDINATE_DATA[activeAnimalIndex][boneId];
					}

					String rawEditColor = transformer.getEditColor(), editColor;
					if (rawEditColor != null) {
						editColor = rawEditColor;
					} else {
						editColor = DataContainer.NODE_COLOR_SET[activeAnimalIndex][boneId];
					}

					triangleNode[boneId].setLocation(moveToPos, editColor, transformer.getTransFrame(), transformer.getWaitFrame());

					if (transformer.getRepeatFrame() != -1) {
						BonesMap bonesMap = timelineMap.getOrDefault(activeAnimalFrame + transformer.getRepeatFrame(), new BonesMap());
						bonesMap.putIfAbsent(boneId, transformer);
						timelineMap.putIfAbsent(activeAnimalFrame + transformer.getRepeatFrame(), bonesMap);
					}
				}

				for (int j = 0; j < 33 && frame != activeAnimalFrame; j++) {
					triangleNode[j].runFrame();
				}
			}

			// 三角形
			for (int i = 0; i < 3; i++) {
				triangleNode[i + 30].render(screenGraphics2D, deltaFrame != 0);
			}
			for (int i = 0; i < 30; i++) {
				triangleNode[i].render(screenGraphics2D, deltaFrame != 0);
			}
			painting = false;
		}
	}

	void refreshAnimal() {
		activeAnimalFrame = 0;
		frameStartTime = System.currentTimeMillis();
		setBg = DataContainer.ColorDecoder(DataContainer.BG_COLOR_SET[activeAnimalIndex]);
		for (int j = 0; j < 33; j++) {
			try {
				triangleNode[j].setLocation(DataContainer.NODE_COORDINATE_DATA[activeAnimalIndex][j], DataContainer.NODE_COLOR_SET[activeAnimalIndex][j], 6 + j * 3, j);
			} catch (ArrayIndexOutOfBoundsException e) {
				triangleNode[j].kill(25 + j * 3, j);
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
			try {
				//noinspection InfiniteLoopStatement
				while (true) {
					long st = System.currentTimeMillis();
					painting = true;
					repaint();
					while (painting) {
						Thread.onSpinWait();
					}
					long sleepTime = 1000 / DataContainer.FRAME_PER_SECOND - (System.currentTimeMillis() - st);
					if (sleepTime < 0) {
						System.err.println("检测到掉帧，请修改每秒帧数或缩小界面大小。(错过 " + (-sleepTime) + " 帧)");
						sleepTime = 0;
					}
					//noinspection BusyWait
					sleep(sleepTime);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	class ChangeService extends Thread {
		@Override
		public void run() {
			for (activeAnimalIndex = -1; activeAnimalIndex < DataContainer.NODE_COORDINATE_DATA.length; ) {
				toNextAnimal();
				long waitFrame = DataContainer.timeToFrame(1000000);
				while (activeAnimalFrame < waitFrame) {
					Thread.onSpinWait();
				}
			}
		}
	}
}