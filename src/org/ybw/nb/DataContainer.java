package org.ybw.nb;

import com.google.gson.Gson;
import org.ybw.nb.animations.*;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class DataContainer {
	static final double SCREEN_X_SCALE = 12.6 / 2, SCREEN_Y_SCALE = 9.0 / 2;
	static final long FRAME_PER_SECOND = 60;
	//第X个图 第X个结点 XorY 第X个XorY
	static int[][][][] NODE_COORDINATE_DATA;
	static String[][] NODE_COLOR_SET;
	static String[] BG_COLOR_SET;
	static Animation[] ANIMATIONS;

	static BufferedImage GRAD_BG;

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
			// 初始化背景图
			GRAD_BG = DataContainer.adaptImageSize(DataContainer.setImageTransparent(Resource.getImage("#resource_pack/images/grad-bg.png"), 0.3f), new Color(0, 0, 0, 0), (int) (SCREEN_X_SCALE * 150), (int) (SCREEN_Y_SCALE * 150), AdaptType.FitScreen);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "资源初始化失败，请检查资源包文件。(" + e + ")", "错误", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			System.exit(1);
		}
	}

	static public long timeToFrame(int millis) {
		return millis * FRAME_PER_SECOND / 1000;
	}

	static public Color ColorDecoder(String colorStr) {
		int colorInt;
		if (colorStr.length() == 7) {
			colorInt = Integer.parseInt(colorStr.substring(1), 16) | 0xff000000;
		} else {
			colorInt = Integer.parseUnsignedInt(colorStr.substring(1), 16);
		}
		return new Color((colorInt >>> 16) & 0xff, (colorInt >>> 8) & 0xff, (colorInt) & 0xff, (colorInt >>> 24) & 0xff);
	}

	static public BufferedImage adaptImageSize(BufferedImage imageBi, Color backcolor, int frameWidth, int frameHeight, AdaptType adaptType) {
		BufferedImage frameBi = new BufferedImage(frameWidth, frameHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics2D frameGraphics = frameBi.createGraphics();
		frameGraphics.setColor(backcolor);
		frameGraphics.fillRect(0, 0, frameWidth, frameHeight);

		int imageWidth = imageBi.getWidth();
		int imageHeight = imageBi.getHeight();

		int bgX, bgY, bgW, bgH;
		switch (adaptType) {
			case FitScreen -> {
				double sW = (double) imageWidth / frameWidth, sH = (double) imageHeight / frameHeight, maxS = Math.max(sW, sH);
				if (sW > 1 || sH > 1) {
					if (sW >= sH) {
						bgW = frameWidth;
						bgH = (int) (imageHeight / maxS);
						bgX = 0;
						bgY = (frameHeight - bgH) / 2;
					} else {
						bgW = (int) (imageWidth / maxS);
						bgH = frameHeight;
						bgX = (frameWidth - bgW) / 2;
						bgY = 0;
					}
				} else if (sW < 1 || sH < 1) {
					if (sW >= sH) {
						bgW = frameWidth;
						bgH = (int) (imageHeight / maxS);
						bgX = 0;
						bgY = (frameHeight - bgH) / 2;
					} else {
						bgW = (int) (imageWidth / maxS);
						bgH = frameHeight;
						bgX = (frameWidth - bgW) / 2;
						bgY = 0;
					}
				} else {
					bgW = frameWidth;
					bgH = frameHeight;
					bgX = 0;
					bgY = 0;
				}
			}
			case FitCenter -> {
				bgX = (frameWidth - imageWidth) / 2;
				bgY = (frameHeight - imageHeight) / 2;
				bgW = imageWidth;
				bgH = imageHeight;
			}
			default -> {
				bgX = 0;
				bgY = 0;
				bgW = imageWidth;
				bgH = imageHeight;
			}
		}
		frameGraphics.setRenderingHint(
			RenderingHints.KEY_INTERPOLATION,
			RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
		frameGraphics.drawImage(imageBi, bgX, bgY, bgW, bgH, null);
		return frameBi;
	}

	static public BufferedImage setImageTransparent(BufferedImage bi, float alpha) {
		int w = bi.getWidth(), h = bi.getHeight();
		BufferedImage bufferedImage = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D graphics2D = bufferedImage.createGraphics();
		graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		graphics2D.drawImage(bi, 0, 0, w, h, null);
		return bufferedImage;
	}

	enum AdaptType {
		FitScreen,
		FitCenter
	}
}