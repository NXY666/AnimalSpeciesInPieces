package org.ybw.nb;

import org.ybw.nb.animations.BonesMap;
import org.ybw.nb.animations.TimelineMap;
import org.ybw.nb.animations.Transformer;

import java.awt.*;
import java.util.Set;

public class MainCanvas extends Component {
	TriangleNode[] triangleNode;

	int nowAnimalIndex = 0;
	volatile int nowAnimalFrame = 0;
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
			for (int i = 0; i < DataContainer.NODE_COORDINATE_DATA.length; ) {
				nowAnimalIndex = i;
				nowAnimalFrame = 0;
				setBg = Color.decode(DataContainer.BG_COLOR_SET[i]);
				for (int j = 0; j < 33; j++) {
					try {
						triangleNode[j].setLocation(DataContainer.NODE_COORDINATE_DATA[i][j], DataContainer.NODE_COLOR_SET[i][j], 25 + j * 4, j);
					} catch (ArrayIndexOutOfBoundsException e) {
						triangleNode[j].kill(140, 30);
					}
				}
				while (nowAnimalFrame < 1500) {
					Thread.onSpinWait();
				}
				if (++i == DataContainer.NODE_COORDINATE_DATA.length) {
					i = 0;
				}
			}
		}).start();
	}


	@Override
	public void paint(Graphics graphics) {
		// 背景
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

		// 动画
		TimelineMap timelineMap = DataContainer.ANIMATIONS[nowAnimalIndex].getTimelineMap();
		BonesMap moveBlocks = timelineMap.getOrDefault(nowAnimalFrame, new BonesMap());
		Set<Integer> blockIndex = moveBlocks.keySet();
		for (int blockId : blockIndex) {
			Transformer transformer = moveBlocks.get(blockId);
			double[][] rawMoveToList = transformer.getMoveTo();
			if (rawMoveToList != null) {
				int[][] moveToPos = new int[2][3];
				for (int nodeIndex = 0; nodeIndex < 3; nodeIndex++) {
					moveToPos[0][nodeIndex] = (int) (rawMoveToList[0][nodeIndex] * DataContainer.SCREEN_X_SCALE);
					moveToPos[1][nodeIndex] = (int) (rawMoveToList[1][nodeIndex] * DataContainer.SCREEN_Y_SCALE);
				}
				triangleNode[blockId].setLocation(moveToPos, transformer.getEditColor(), transformer.getTransFrame(), transformer.getWaitFrame());
			} else {
				triangleNode[blockId].setLocation(DataContainer.NODE_COORDINATE_DATA[nowAnimalIndex][blockId], transformer.getEditColor(), transformer.getTransFrame(), transformer.getWaitFrame());
			}
			if (transformer.getRepeatFrame() != -1) {
				BonesMap newBonesMap = timelineMap.getOrDefault(nowAnimalFrame + transformer.getRepeatFrame(), new BonesMap());

				newBonesMap.put(blockId, transformer);
				timelineMap.put(nowAnimalFrame + transformer.getRepeatFrame(), newBonesMap);
			}
		}

		// 三角形
		for (int i = 0; i < 3; i++) {
			triangleNode[i + 30].render((Graphics2D) graphics, nowAnimalFrame);
		}
		for (int i = 0; i < 30; i++) {
			triangleNode[i].render((Graphics2D) graphics, nowAnimalFrame);
		}

		synchronized (this) {
			nowAnimalFrame++;
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
					sleep(1);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}