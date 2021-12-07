package org.ybw.nb;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Arrays;

public class MainCanvas extends JPanel {
	TriangleNode[] triangleNode;

	public MainCanvas(FrameContainer frame) {
		// super(frame);
		BufferedImage bi = new BufferedImage(1000, 1000, BufferedImage.TYPE_INT_RGB);
		Graphics2D graphics = bi.createGraphics();
		graphics.setColor(Color.GRAY);
		graphics.fillRect(0, 0, 1000, 1000);

		triangleNode = new TriangleNode[33];
		// for (int i = 0; i < 30; i++) {
		// 	triangleNode[i] = new TriangleNode(
		// 		new int[][]{{getRandInt(0, 1000), getRandInt(0, 1000), getRandInt(0, 1000)}, {getRandInt(0, 1000), getRandInt(0, 1000), getRandInt(0, 1000)}},
		// 		Color.getHSBColor((float) Math.random(), 1, 1)
		// 	);
		// }

		triangleNode[0] = new TriangleNode(new int[][]{{(int)(12.6*25.4),(int)(12.6*33.5),(int)(12.6*25.92)},{(int)(9*30.6),(int)(9*32.2),(int)(9*36.857)}},"#FEDD47");
		triangleNode[1] = new TriangleNode(new int[][]{{(int)(12.6*25.85),(int)(12.6*35.25),(int)(12.6*36.15)},{(int)(9*36.9),(int)(9*28.857),(int)(9*47.714)}},"#F1C838");
		triangleNode[2] = new TriangleNode(new int[][]{{(int)(12.6*25.45),(int)(12.6*30.35),(int)(12.6*32.1)},{(int)(9*30.714),(int)(9*28.286),(int)(9*32.143)}},"#FDD24B");
		triangleNode[3] = new TriangleNode(new int[][]{{(int)(12.6*30.3),(int)(12.6*35.35),(int)(12.6*31.4)},{(int)(9*28.2),(int)(9*28.857),(int)(9*32.2)}},"#F3E567");
		triangleNode[4] = new TriangleNode(new int[][]{{(int)(12.6*25.95),(int)(12.6*36.25),(int)(12.6*27.85)},{(int)(9*36.857),(int)(9*47.714),(int)(9*41.714)}},"#CDAA25");
		triangleNode[5] = new TriangleNode(new int[][]{{(int)(12.6*30.25),(int)(12.6*35.85),(int)(12.6*39.99)},{(int)(9*28.4),(int)(9*26),(int)(9*29.686)}},"#FFF88B");
		triangleNode[6] = new TriangleNode(new int[][]{{(int)(12.6*35),(int)(12.6*39.85),(int)(12.6*43.5)},{(int)(9*29),(int)(9*29.429),(int)(9*39.4)}},"#FDD24B");
		triangleNode[7] = new TriangleNode(new int[][]{{(int)(12.6*35.15),(int)(12.6*43.45),(int)(12.6*36.04)},{(int)(9*29),(int)(9*39.2),(int)(9*47.99)}},"#E4BA2D");
		triangleNode[8] = new TriangleNode(new int[][]{{(int)(12.6*31.25),(int)(12.6*35.15),(int)(12.6*37.45)},{(int)(9*35.429),(int)(9*30),(int)(9*38)}},"#F4E281");
		triangleNode[9] = new TriangleNode(new int[][]{{(int)(12.6*31.15),(int)(12.6*34.55),(int)(12.6*37.35)},{(int)(9*35.571),(int)(9*33.714),(int)(9*38.2)}},"#3A393B");
		triangleNode[10] = new TriangleNode(new int[][]{{(int)(12.6*31.15),(int)(12.6*37.45),(int)(12.6*33.15)},{(int)(9*35.429),(int)(9*38.143),(int)(9*39.714)}},"#2B2424");
		triangleNode[11] = new TriangleNode(new int[][]{{(int)(12.6*32.65),(int)(12.6*33.55),(int)(12.6*33.15)},{(int)(9*35.286),(int)(9*34.857),(int)(9*36)}},"#fff");
		triangleNode[12] = new TriangleNode(new int[][]{{(int)(12.6*39.75),(int)(12.6*56.15),(int)(12.6*43.35)},{(int)(9*29.4),(int)(9*41.857),(int)(9*39.243)}},"#F0EA75");
		triangleNode[13] = new TriangleNode(new int[][]{{(int)(12.6*43.35),(int)(12.6*43.65),(int)(12.6*36.15)},{(int)(9*39),(int)(9*51.571),(int)(9*47.857)}},"#FAC932");
		triangleNode[14] = new TriangleNode(new int[][]{{(int)(12.6*43.3),(int)(12.6*52.55),(int)(12.6*43.45)},{(int)(9*38.7),(int)(9*56.857),(int)(9*58)}},"#FED63C");
		triangleNode[15] = new TriangleNode(new int[][]{{(int)(12.6*43.25),(int)(12.6*56.15),(int)(12.6*52.45)},{(int)(9*38.857),(int)(9*41.857),(int)(9*57)}},"#F9E049");
		triangleNode[16] = new TriangleNode(new int[][]{{(int)(12.6*43.45),(int)(12.6*52.55),(int)(12.6*53.25)},{(int)(9*57.857),(int)(9*56.7),(int)(9*70.286)}},"#E6BF2B");
		triangleNode[17] = new TriangleNode(new int[][]{{(int)(12.6*37.45),(int)(12.6*53.35),(int)(12.6*43.95)},{(int)(9*50.429),(int)(9*70.286),(int)(9*65.143)}},"#70540E");
		triangleNode[18] = new TriangleNode(new int[][]{{(int)(12.6*36),(int)(12.6*43.75),(int)(12.6*38.45)},{(int)(9*47.6657),(int)(9*51.429),(int)(9*55)}},"#FBD942");
		triangleNode[19] = new TriangleNode(new int[][]{{(int)(12.6*43.65),(int)(12.6*49.35),(int)(12.6*38.35)},{(int)(9*51.429),(int)(9*63.286),(int)(9*55)}},"#F0EA75");
		triangleNode[20] = new TriangleNode(new int[][]{{(int)(12.6*34.35),(int)(12.6*49.35),(int)(12.6*41.55)},{(int)(9*71.571),(int)(9*73.429),(int)(9*75.143)}},"#2C2924");
		triangleNode[21] = new TriangleNode(new int[][]{{(int)(12.6*45.35),(int)(12.6*49.25),(int)(12.6*37.85)},{(int)(9*60.286),(int)(9*63.143),(int)(9*73.286)}},"#D8C533");
		triangleNode[22] = new TriangleNode(new int[][]{{(int)(12.6*37.65),(int)(12.6*49.1),(int)(12.6*41.75)},{(int)(9*73.3),(int)(9*63.143),(int)(9*75.43)}},"#BDA126");
		triangleNode[23] = new TriangleNode(new int[][]{{(int)(12.6*52.45),(int)(12.6*56.05),(int)(12.6*67.25)},{(int)(9*56.9),(int)(9*41.857),(int)(9*63.4)}},"#FEEC63");
		triangleNode[24] = new TriangleNode(new int[][]{{(int)(12.6*52.45),(int)(12.6*67.15),(int)(12.6*53.2)},{(int)(9*56.714),(int)(9*63.286),(int)(9*70.3)}},"#F3CD37");
		triangleNode[25] = new TriangleNode(new int[][]{{(int)(12.6*61.45),(int)(12.6*72.85),(int)(12.6*61.65)},{(int)(9*66.429),(int)(9*68.143),(int)(9*75.857)}},"#986E19");
		triangleNode[26] = new TriangleNode(new int[][]{{(int)(12.6*53.2),(int)(12.6*67.25),(int)(12.6*55.25)},{(int)(9*70.1),(int)(9*63.1),(int)(9*81.714)}},"#F9DB35");
		triangleNode[27] = new TriangleNode(new int[][]{{(int)(12.6*62.35),(int)(12.6*75.15),(int)(12.6*55.15)},{(int)(9*70.571),(int)(9*72.429),(int)(9*81.714)}},"#FAC62D");
		triangleNode[28] = new TriangleNode(new int[][]{{(int)(12.6*75.15),(int)(12.6*65.95),(int)(12.6*57.85)},{(int)(9*72.429),(int)(9*84.286),(int)(9*83.571)}},"#FDDA54");
		triangleNode[29] = new TriangleNode(new int[][]{{(int)(12.6*58.65),(int)(12.6*65.85),(int)(12.6*52.65)},{(int)(9*83),(int)(9*84.429),(int)(9*84)}},"#180D13");
		triangleNode[30] = new TriangleNode(new int[][]{{(int)(12.6*24.35),(int)(12.6*56.65),(int)(12.6*79.35)},{(int)(9*68.143),(int)(9*67.143),(int)(9*85.714)}},"#3B6F4B");
		triangleNode[31] = new TriangleNode(new int[][]{{(int)(12.6*24.25),(int)(12.6*79.35),(int)(12.6*50.35)},{(int)(9*68.143),(int)(9*85.714),(int)(9*87.286)}},"#4E9F73");
		triangleNode[32] = new TriangleNode(new int[][]{{(int)(12.6*24.35),(int)(12.6*29.25),(int)(12.6*27.25)},{(int)(9*68.143),(int)(9*71.714),(int)(9*79)}},"#1D3246");

		triangleNode[0].setLocation(new int[][]{{(int)(12.6*23.25),(int)(12.6*31.25),(int)(12.6*46.85)},{(int)(9*43.286),(int)(9*33.571),(int)(9*36.286)}},"#EFDCCD");
		triangleNode[1].setLocation(new int[][]{{(int)(12.6*32.5),(int)(12.6*36.05),(int)(12.6*41.75)},{(int)(9*60),(int)(9*55),(int)(9*66.857)}},"#7B7362");
		triangleNode[2].setLocation(new int[][]{{(int)(12.6*23.35),(int)(12.6*34.8),(int)(12.6*23.45)},{(int)(9*43.286),(int)(9*57.5),(int)(9*56.143)}},"#AE886D");
		triangleNode[3].setLocation(new int[][]{{(int)(12.6*23.45),(int)(12.6*34.66),(int)(12.6*22.45)},{(int)(9*56.143),(int)(9*57.3),(int)(9*71.714)}},"#D7C5A4");
		triangleNode[4].setLocation(new int[][]{{(int)(12.6*27.85),(int)(12.6*33.05),(int)(12.6*29.75)},{(int)(9*65.429),(int)(9*59.2),(int)(9*85.857)}},"#7F6B5F");
		triangleNode[5].setLocation(new int[][]{{(int)(12.6*23.25),(int)(12.6*46.85),(int)(12.6*35.05)},{(int)(9*43.286),(int)(9*36.286),(int)(9*57.714)}},"#DACDBC");
		triangleNode[6].setLocation(new int[][]{{(int)(12.6*22.45),(int)(12.6*28.3),(int)(12.6*24.65)},{(int)(9*71.714),(int)(9*64.714),(int)(9*85.857)}},"#F2E1C5");
		triangleNode[7].setLocation(new int[][]{{(int)(12.6*36.55),(int)(12.6*46.85),(int)(12.6*50.65)},{(int)(9*54.714),(int)(9*36.286),(int)(9*52.286)}},"#E9DCC4");
		triangleNode[8].setLocation(new int[][]{{(int)(12.6*36),(int)(12.6*50.65),(int)(12.6*41.85)},{(int)(9*54.857),(int)(9*52.286),(int)(9*66.857)}},"#CFBA91");
		triangleNode[9].setLocation(new int[][]{{(int)(12.6*41.75),(int)(12.6*50.65),(int)(12.6*56.05)},{(int)(9*66.857),(int)(9*52.143),(int)(9*64.714)}},"#BAAE96");
		triangleNode[10].setLocation(new int[][]{{(int)(12.6*46.75),(int)(12.6*54.35),(int)(12.6*61.25)},{(int)(9*36.429),(int)(9*33),(int)(9*36.429)}},"#F3EADF");
		triangleNode[11].setLocation(new int[][]{{(int)(12.6*46.75),(int)(12.6*61.15),(int)(12.6*50.65)},{(int)(9*36.429),(int)(9*36.286),(int)(9*52.143)}},"#EBE1CB");
		triangleNode[12].setLocation(new int[][]{{(int)(12.6*50.4),(int)(12.6*61.25),(int)(12.6*60.96)},{(int)(9*52.286),(int)(9*36.1),(int)(9*56.143)}},"#DDCFB7");
		triangleNode[13].setLocation(new int[][]{{(int)(12.6*59.65),(int)(12.6*63.65),(int)(12.6*61.05)},{(int)(9*63.286),(int)(9*61.714),(int)(9*86)}},"#8A7B63");
		triangleNode[14].setLocation(new int[][]{{(int)(12.6*50.55),(int)(12.6*60.85),(int)(12.6*60.55)},{(int)(9*52.286),(int)(9*56.143),(int)(9*75.143)}},"#EBE1CB");
		triangleNode[15].setLocation(new int[][]{{(int)(12.6*58.15),(int)(12.6*60.45),(int)(12.6*58.95)},{(int)(9*69.714),(int)(9*75),(int)(9*85.714)}},"#A89F92");
		triangleNode[16].setLocation(new int[][]{{(int)(12.6*61.25),(int)(12.6*66.25),(int)(12.6*60.73)},{(int)(9*36.429),(int)(9*60.5),(int)(9*63)}},"#78523B");
		triangleNode[17].setLocation(new int[][]{{(int)(12.6*61.15),(int)(12.6*69.75),(int)(12.6*73.75)},{(int)(9*36.286),(int)(9*23.429),(int)(9*35.286)}},"#A2785C");
		triangleNode[18].setLocation(new int[][]{{(int)(12.6*61.15),(int)(12.6*73.7),(int)(12.6*66.14)},{(int)(9*36.2),(int)(9*35.23),(int)(9*60.7)}},"#825D48");
		triangleNode[19].setLocation(new int[][]{{(int)(12.6*73.55),(int)(12.6*76.25),(int)(12.6*66.2)},{(int)(9*35.286),(int)(9*36),(int)(9*60.429)}},"#634C39");
		triangleNode[20].setLocation(new int[][]{{(int)(12.6*69.65),(int)(12.6*74.15),(int)(12.6*73.75)},{(int)(9*23.429),(int)(9*22.429),(int)(9*35.286)}},"#B98D5D");
		triangleNode[21].setLocation(new int[][]{{(int)(12.6*73.75),(int)(12.6*74.15),(int)(12.6*79.55)},{(int)(9*35.286),(int)(9*20),(int)(9*28.143)}},"#ECE1D5");
		triangleNode[22].setLocation(new int[][]{{(int)(12.6*79.45),(int)(12.6*84.55),(int)(12.6*75.95)},{(int)(9*28),(int)(9*34.286),(int)(9*32.571)}},"#D2C9C1");
		triangleNode[23].setLocation(new int[][]{{(int)(12.6*75.95),(int)(12.6*84.45),(int)(12.6*84.15)},{(int)(9*32.571),(int)(9*34.143),(int)(9*38.429)}},"#B4ABA0");
		triangleNode[24].setLocation(new int[][]{{(int)(12.6*75.95),(int)(12.6*83.45),(int)(12.6*73.75)},{(int)(9*32.429),(int)(9*38),(int)(9*35.286)}},"#AFA491");
		triangleNode[25].setLocation(new int[][]{{(int)(12.6*70.65),(int)(12.6*69.95),(int)(12.6*73.05)},{(int)(9*23.286),(int)(9*17.714),(int)(9*22.857)}},"#776244");
		triangleNode[26].setLocation(new int[][]{{(int)(12.6*73.05),(int)(12.6*67.95),(int)(12.6*75.35)},{(int)(9*22.857),(int)(9*7.714),(int)(9*21.714)}},"#514C49");
		triangleNode[27].setLocation(new int[][]{{(int)(12.6*50.55),(int)(12.6*67.99),(int)(12.6*68.98)},{(int)(9*1.714),(int)(9*7.857),(int)(9*10.571)}},"#312F34");
		triangleNode[28].setLocation(new int[][]{{(int)(12.6*39.15),(int)(12.6*50.65),(int)(12.6*52.95)},{(int)(9*7.714),(int)(9*1.714),(int)(9*2.857)}},"#272220");
		triangleNode[29].setLocation(new int[][]{{(int)(12.6*72.65),(int)(12.6*77.95),(int)(12.6*75.35)},{(int)(9*22.857),(int)(9*16.286),(int)(9*22.714)}},"#C8C0B9");

		new ChangeService().start();
	}

	private int getRandInt(int min, int max) {
		return (int) (min + Math.random() * (max - min + 1));
	}

	@Override
	public void paint(Graphics graphics) {
		graphics.setColor(Color.GRAY);
		graphics.fillRect(0, 0, 1400, 1000);

		for (int i = 0; i < 33; i++) {
			triangleNode[i].render((Graphics2D) graphics);
		}
	}

	class ChangeService extends Thread {
		@Override
		public void run() {
			try {
				while (true) {
					// for (int i = 0; i < 33; i++) {
					// 	if (Arrays.deepEquals(triangleNode[i].nowP, triangleNode[i].setP)) {
					// 		int[][] newPos = new int[][]{{getRandInt(0, 1000), getRandInt(0, 1000), getRandInt(0, 1000)}, {getRandInt(0, 1000), getRandInt(0, 1000), getRandInt(0, 1000)}};
					// 		triangleNode[i].setLocation(newPos, triangleNode[i].setBg);
					// 	}
					// }
					repaint();
					sleep(10);
				}
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}