package org.ybw.nb;

public class TrianglePos {
	int[][] points;

	public TrianglePos(int[] x, int[] y) {
		points = new int[][]{x, y};
	}

	public TrianglePos(int p1X, int p1Y, int p2X, int p2Y, int p3X, int p3Y) {
		points = new int[][]{{p1X, p2X, p3X}, {p1Y, p2Y, p3Y}};
	}
}
