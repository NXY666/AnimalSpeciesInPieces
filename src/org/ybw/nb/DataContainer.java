package org.ybw.nb;

import com.google.gson.Gson;
import org.ybw.nb.animations.*;

import javax.swing.*;
import java.util.HashMap;

public class DataContainer {
	static final double SCREEN_X_SCALE = 12.6 / 1.3, SCREEN_Y_SCALE = 9 / 1.3;
	//第X个图 第X个结点 XorY 第X个XorY
	static int[][][][] NODE_COORDINATE_DATA;
	static String[][] NODE_COLOR_SET;
	static String[] BG_COLOR_SET;
	static Animation[] ANIMATIONS;

	static public void init() {
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
			// 初始化ANIMATIONS
			FileAnimationSetsRoot fileAnimationSetsRoot = Resource.getJson("#resource_pack/models/animation_sets.json", FileAnimationSetsRoot.class);
			FileAnimationsRoot fileAnimationsRoot = Resource.getJson("#resource_pack/models/animations.json", FileAnimationsRoot.class);
			FileAnimation[] fileAnimations = fileAnimationsRoot.getAnimations();
			HashMap<String, FileAnimationSet> fileAnimationSets = fileAnimationSetsRoot.getAnimationSets();
			ANIMATIONS = new Animation[fileAnimations.length];
			for (int i = 0; i < ANIMATIONS.length; i++) {
				ANIMATIONS[i] = new Animation();
				TimelineMap timelineMap = ANIMATIONS[i].getTimelineMap();
				FileTimelineMap rawTimeLine = fileAnimations[i].getTimeline();
				for (int startFrame : rawTimeLine.keySet()) {
					for (String aniSetName : rawTimeLine.get(startFrame)) {
						FileAnimationSet fileAnimationSet = fileAnimationSets.get(aniSetName);
						if (fileAnimationSet == null) {
							throw new Exception("动画组 " + aniSetName + " 未定义。");
						}
						TimelineMap aniSetTimelineMap = fileAnimationSet.getTimelineMap();
						for (int deltaFrame : aniSetTimelineMap.keySet()) {
							BonesMap aniSetBonesMap = aniSetTimelineMap.get(deltaFrame);
							BonesMap bonesMap = timelineMap.getOrDefault(startFrame + deltaFrame, new BonesMap());
							for (int boneId : aniSetBonesMap.keySet()) {
								Transformer aniSetTransformer = aniSetBonesMap.get(boneId);
								if (fileAnimationSet.isLoop()) {
									aniSetTransformer.setRepeatFrame(fileAnimationSet.getTotalFrame());
								}
								if (bonesMap.containsKey(boneId)) {
									throw new Exception(
										"动物 " + i + " 中骨头 " + boneId + " 在第 " + (startFrame + deltaFrame) + " 帧发生冲突。\n" +
											"已存在：" + new Gson().toJson(bonesMap.get(boneId)) + "\n" +
											"将添加：" + new Gson().toJson(aniSetTransformer)
									);
								}
								bonesMap.put(boneId, aniSetTransformer);
							}
							timelineMap.put(startFrame + deltaFrame, bonesMap);
						}
					}
				}
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "资源初始化失败，请检查资源包文件。(" + e + ")", "错误", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(1);
		}
	}
}