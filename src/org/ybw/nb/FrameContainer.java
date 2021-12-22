package org.ybw.nb;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.net.URL;

public class FrameContainer extends JFrame {
	int mouseAtX, mouseAtY;

	public FrameContainer() {
		// 标题
		this.setTitle("JavaLikeCss[BY: NXY && YBW]");

		// 无边框
		this.setUndecorated(true);
		this.setAlwaysOnTop(true);

		// 图标
		try {
			this.setIconImage(ImageIO.read(new URL("https://www.webhek.com/misc-res/species-in-pieces/img/blood.png")));
		} catch (IOException e) {
			e.printStackTrace();
		}

		// 窗口大小
		this.setResizable(false);
		this.setSize((int) (DataContainer.SCREEN_X_SCALE * 100), (int) (DataContainer.SCREEN_Y_SCALE * 100) + 40);

		// 窗口位置
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((d.width - getWidth()) / 2, (d.height - getHeight()) / 2);

		// 背景颜色（透明）
		this.setBackground(new Color(0, 0, 0, 0));

		// 退出方式
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// 放置画板
		this.add(new MainCanvas(this));

		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				mouseAtX = e.getPoint().x;
				mouseAtY = e.getPoint().y;
			}
		});
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				setLocation((e.getXOnScreen() - mouseAtX), (e.getYOnScreen() - mouseAtY));//设置拖拽后，窗口的位置
			}
		});

		// 启动窗口
		this.setVisible(true);
	}
}