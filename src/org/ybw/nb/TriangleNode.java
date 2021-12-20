package org.ybw.nb;

import java.awt.*;

public class TriangleNode {
	//      过去   现在   未来
	int[][] oldP, nowP, setP;
	Color setColor, nowColor;

	//移动参数
	int totalFrame = getRandInt(50, 100), usedFrame = 0, waitFrame = getRandInt(0, 50);

	public TriangleNode(int[][] setP, String setColor) {
		this.setP = setP;
		int screenX = (int) (DataContainer.SCREEN_X_SCALE * 100), screenY = (int) (DataContainer.SCREEN_Y_SCALE * 100);
		int screenSX = (int) (screenX * 0.1), screenSY = (int) (screenY * 0.1);
		int[] triPos = {getRandInt(screenSX, screenX - screenSX), getRandInt(screenSY, screenY - screenSY)};
		this.oldP = this.nowP = new int[][]{
			{triPos[0] + getRandInt(-25, 25), triPos[0] + getRandInt(-25, 25), triPos[0] + getRandInt(-25, 25)},//在画布范围内随机取x点
			{triPos[1] + getRandInt(-25, 25), triPos[1] + getRandInt(-25, 25), triPos[1] + getRandInt(-25, 25)}//在画布范围内随机取y点
		};
		this.setColor = this.nowColor = Color.decode(setColor);
	}

	public TriangleNode() {
		int screenX = (int) (DataContainer.SCREEN_X_SCALE * 50), screenY = (int) (DataContainer.SCREEN_Y_SCALE * 50);
		this.oldP = this.setP = this.nowP = new int[][]{{screenX, screenX, screenX}, {screenY, screenY, screenY}};
		this.setColor = this.nowColor = Color.WHITE;
	}

	private int getRandInt(int min, int max) {
		return (int) (min + Math.random() * (max - min + 1));
	}

	/**
	 * 设置三角形新的目的地
	 *
	 * @param setP        目的地坐标。 {{x1, x2, x3}, {y1, y2, y3}}
	 * @param setColorStr 新的三角形颜色，若为null则不作修改。
	 * @param totalFrame  动画持续时间。
	 * @param waitFrame   动画启动延时。
	 */
	public void setLocation(int[][] setP, String setColorStr, int totalFrame, int waitFrame) {
		this.oldP = this.nowP;
		this.setP = setP;
		if (setColorStr != null) {
			this.setColor = Color.decode(setColorStr);
		}

		this.waitFrame = waitFrame;
		this.totalFrame = totalFrame;
		this.usedFrame = 0;
	}

	public void kill(int totalFrame, int waitFrame) {
		this.oldP = this.nowP;
		int screenX = (int) (DataContainer.SCREEN_X_SCALE * 50), screenY = (int) (DataContainer.SCREEN_Y_SCALE * 50);
		this.setP = new int[][]{{screenX, screenX, screenX}, {screenY, screenY, screenY}};

		this.waitFrame = waitFrame;
		this.totalFrame = totalFrame;
		this.usedFrame = 0;
	}

	/**
	 * 获取移动向量
	 *
	 * @return double[] { dx//水平距离, dy//垂直距离 }
	 */
	public double[] getVector(int dotIndex) {
		double dx = setP[0][dotIndex] - oldP[0][dotIndex], dy = setP[1][dotIndex] - oldP[1][dotIndex];
		if (dx == 0 && dy == 0) {
			return new double[]{0, 0, 0, 0};
		}
		return new double[]{dx, dy};
	}

	public double getDistPercent(double currentTime, double duration) {
		currentTime /= duration / 2;
		if (currentTime < 1) {
			return 0.5 * currentTime * currentTime;
		}
		currentTime--;
		return -0.5 * (currentTime * (currentTime - 2) - 1);
	}

	public void movePoint(int dotIndex) {
		double[] vector = getVector(dotIndex);
		double distPercent = getDistPercent(usedFrame, totalFrame); // 百度的缓动函数（CSS用的这个）
		// nowP [0] x 点集合 nowP[1] y 点集合
		nowP[0][dotIndex] = (int) (oldP[0][dotIndex] + vector[0] * distPercent); // disPercent趋于1 水平距离趋于setP
		nowP[1][dotIndex] = (int) (oldP[1][dotIndex] + vector[1] * distPercent); // disPercent趋于1 垂直距离趋于setP

		if (setColor.getRed() != nowColor.getRed()) {
			int dis = setColor.getRed() - nowColor.getRed() > 0 ? 1 : -1;
			nowColor = new Color(nowColor.getRed() + dis, nowColor.getGreen(), nowColor.getBlue());
		}
		if (setColor.getBlue() != nowColor.getBlue()) {
			int dis = setColor.getBlue() - nowColor.getBlue() > 0 ? 1 : -1;
			nowColor = new Color(nowColor.getRed(), nowColor.getGreen(), nowColor.getBlue() + dis);
		}
		if (setColor.getGreen() != nowColor.getGreen()) {
			int dis = setColor.getGreen() - nowColor.getGreen() > 0 ? 1 : -1;
			nowColor = new Color(nowColor.getRed(), nowColor.getGreen() + dis, nowColor.getBlue());
		}
	}

	public void render(Graphics2D graphics2D) {
		if (waitFrame > 0) {
			waitFrame--;
		} else if (usedFrame <= totalFrame) {
			for (int i = 0; i < 3; i++) {
				movePoint(i);
			}
			usedFrame++;
		}
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		graphics2D.setColor(nowColor);
		graphics2D.fillPolygon(new Polygon(nowP[0], nowP[1], 3));
	}
}