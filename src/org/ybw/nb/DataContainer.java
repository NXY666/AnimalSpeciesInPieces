package org.ybw.nb;

import javax.swing.*;
import java.io.IOException;

public class DataContainer {
	static final double SCREEN_X_SCALE = 8.4, SCREEN_Y_SCALE = 6;
	//第X个图 第X个结点 XorY 第X个XorY
	static int[][][][] NODE_COORDINATE_DATA;
	static String[][] NODE_COLOR_SET;
	static String[] BG_COLOR_SET;

	static public void init() throws Exception {
		try {
			// 初始化NODE_COORDINATE_DATA
			double[][][][] tmpNCData = Resource.getJson("#resource_pack/models/models.json", double[][][][].class);
			int tmpNCDataLength = tmpNCData.length;
			NODE_COORDINATE_DATA = new int[tmpNCDataLength][][][];
			for (int i = 0; i < tmpNCDataLength; i++) {
				double[][][] animal = tmpNCData[i];
				int animalLength = animal.length;
				NODE_COORDINATE_DATA[i] = new int[animalLength][][];
				for (int j = 0; j < animalLength; j++) {
					double[][] triangle = animal[j];
					NODE_COORDINATE_DATA[i][j] = new int[2][3];
					for (int nodeIndex = 0; nodeIndex < 3; nodeIndex++) {
						NODE_COORDINATE_DATA[i][j][0][nodeIndex] = (int) (triangle[0][nodeIndex] * SCREEN_X_SCALE);
						NODE_COORDINATE_DATA[i][j][1][nodeIndex] = (int) (triangle[1][nodeIndex] * SCREEN_Y_SCALE);
					}
				}
			}
			// 初始化BG_COLOR_SET
			BG_COLOR_SET = Resource.getJson("#resource_pack/colors/background_colors.json", String[].class);
			// 初始化NODE_COLOR_SET
			NODE_COLOR_SET = Resource.getJson("#resource_pack/colors/model_colors.json", String[][].class);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "资源初始化失败，请检查资源包文件。(" + e + ")", null, JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			throw new Exception("资源初始化失败，请检查资源包文件。");
		}
	}
}