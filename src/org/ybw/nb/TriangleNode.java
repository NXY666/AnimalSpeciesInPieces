package org.ybw.nb;

import java.awt.*;

public class TriangleNode {
	int[][] setP, nowP;
	double[] moveSpeed = {1, 1, 1};
	Color setBg, nowBg;

	public TriangleNode(int[][] setP, String setBg) {
		this.setP = setP;
		this.nowP = setP;
		this.setBg = new Color(Integer.parseInt(setBg.substring(1), 16));
		this.nowBg = this.setBg;
	}

	public void setLocation(int[][] setP, String setBg) {
		this.setP = setP;
		this.setBg = new Color(Integer.parseInt(setBg.substring(1), 16));
	}


	public double[] getVector(int dotIndex, int[][] nowP, int[][] setP) {
		double dx = setP[0][dotIndex] - nowP[0][dotIndex], dy = setP[1][dotIndex] - nowP[1][dotIndex];
		if (dx == 0 && dy == 0) {
			return new double[]{0, 0, 0, 0};
		}
		double dist = Math.sqrt(dx * dx + dy * dy);
		return new double[]{dx / dist, dy / dist, dx, dy};//返回水平 垂直速度 以及水平 垂直高度
	}

	public void movePoint(int dotIndex, int[][] nowP, int[][] setP) {
		double[] vector = getVector(dotIndex, nowP, setP);
		double dx = (vector[0] < 0 ? -1 : 1) * Math.min(Math.abs(vector[0] * moveSpeed[dotIndex]), Math.abs(vector[2]));
		double dy = (vector[1] < 0 ? -1 : 1) * Math.min(Math.abs(vector[1] * moveSpeed[dotIndex]), Math.abs(vector[3]));

		moveSpeed[dotIndex] = dx * dx + dy * dy;
		if (moveSpeed[dotIndex] > 10) {
			moveSpeed[dotIndex] = 10;
		} else if (moveSpeed[dotIndex] < 1) {
			moveSpeed[dotIndex] = 1;
		}

		nowP[0][dotIndex] += (dx > 0 && dx < 1) ? 1 : dx;
		nowP[1][dotIndex] += (dy > 0 && dy < 1) ? 1 : dy;

		if (setBg.getRed()!= nowBg.getRed()) {
			int dis;
			if(setBg.getRed()-nowBg.getRed()>0)
				dis=1;
			else
				dis=-1;
			nowBg=new Color(nowBg.getRed()+dis,nowBg.getGreen(),nowBg.getBlue());
		}
		if (setBg.getBlue()!= nowBg.getBlue()) {
			int dis;
			if(setBg.getBlue()-nowBg.getBlue()>0)
				dis=1;
			else
				dis=-1;
			nowBg=new Color(nowBg.getRed(),nowBg.getGreen(),nowBg.getBlue()+dis);
		}if (setBg.getGreen()!= nowBg.getGreen()) {
			int dis;
			if(setBg.getGreen()-nowBg.getGreen()>0)
				dis=1;
			else
				dis=-1;
			nowBg=new Color(nowBg.getRed(),nowBg.getGreen()+dis,nowBg.getBlue());
		}
	}
	//			int dc = setBg.getRGB()/100 - nowBg.getRGB() / 100;
//			if (Math.abs(dc) < 100) {
//				nowBg = new Color(setBg.getRGB());
//			} else {
//				nowBg = new Color(nowBg.getRGB() + dc);
//			}
	public void render(Graphics2D graphics2D) {
		graphics2D.setColor(nowBg);
		graphics2D.fillPolygon(new Polygon(nowP[0], nowP[1], 3));
	}
}
//170 80  140
//200 150 30