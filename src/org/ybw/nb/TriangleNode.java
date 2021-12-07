package org.ybw.nb;

import java.awt.*;

public class TriangleNode {
	//      过去   现在   未来
	int[][] oldP, nowP, setP;
	Color setBg, nowBg;

	//移动参数
	int totalFrame = 50, usedFrame = 0, waitFrame = 0;

	public TriangleNode(int[][] setP, String setBg) {
		this.setP = setP;
		int[] triPos = {getRandInt(200, 1200), getRandInt(200, 800)};
		this.nowP = new int[][]{
			{triPos[0] + getRandInt(-25, 25), triPos[0] + getRandInt(-25, 25), triPos[0] + getRandInt(-25, 25)},
			{triPos[1] + getRandInt(-25, 25), triPos[1] + getRandInt(-25, 25), triPos[1] + getRandInt(-25, 25)}
		};
		this.oldP = this.nowP;
		this.setBg = new Color(Integer.parseInt(setBg.substring(1), 16));
		this.nowBg = this.setBg;
	}

	private int getRandInt(int min, int max) {
		return (int) (min + Math.random() * (max - min + 1));
	}

	public void setLocation(int[][] setP, String setBg, int totalFrame, int waitFrame) {
		this.oldP = this.nowP;
		this.setP = setP;
		this.setBg = new Color(Integer.parseInt(setBg.substring(1), 16));

		this.waitFrame = waitFrame;
		this.totalFrame = totalFrame;
		this.usedFrame = 0;
	}

	public void kill(int totalFrame, int waitFrame) {
		this.oldP = this.nowP;
		this.setP = new int[][]{{700, 700, 700}, {500, 500, 500}};

		this.waitFrame = waitFrame;
		this.totalFrame = totalFrame;
		this.usedFrame = 0;
	}

	/**
	 * 获取移动向量
	 *
	 * @return double[] { x/距离, y/距离, dx, dy }
	 */
	public double[] getVector(int dotIndex) {
		double dx = setP[0][dotIndex] - oldP[0][dotIndex], dy = setP[1][dotIndex] - oldP[1][dotIndex];
		if (dx == 0 && dy == 0) {
			return new double[]{0, 0, 0, 0};
		}
		double dist = Math.sqrt(dx * dx + dy * dy);
		return new double[]{dx / dist, dy / dist, dx, dy};
	}

	public double getDistPercent(double timePercent) {
		double twoPI = 2 * Math.PI;
		return (twoPI * timePercent - Math.sin(twoPI * timePercent)) / twoPI;
	}

	public void movePoint(int dotIndex) {
		double[] vector = getVector(dotIndex);
		double distPercent = getDistPercent((double) usedFrame / totalFrame);

		nowP[0][dotIndex] = (int) (oldP[0][dotIndex] + vector[2] * distPercent);
		nowP[1][dotIndex] = (int) (oldP[1][dotIndex] + vector[3] * distPercent);

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
	}

	public void render(Graphics2D graphics2D) {
		if (waitFrame != 0) {
			waitFrame--;
		} else if (usedFrame <= totalFrame) {
			for (int i = 0; i < 3; i++) {
				movePoint(i);
			}
		}
		graphics2D.setColor(nowBg);
		graphics2D.fillPolygon(new Polygon(nowP[0], nowP[1], 3));
		usedFrame++;
	}
}
//170 80  140
//200 150 30