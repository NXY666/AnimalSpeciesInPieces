package org.ybw.nb;

import javax.swing.*;
import java.awt.*;

public class FrameContainer extends JFrame {
	public FrameContainer() {
		setTitle("JavaLikeCss(BY: NXY && YBW)");
		setSize((int) (DataContainer.SCREEN_X_SCALE * 100), (int) (DataContainer.SCREEN_Y_SCALE * 100) + 40);

		getContentPane().setBackground(Color.GRAY);
		this.setResizable(false);
		Toolkit tool = Toolkit.getDefaultToolkit();
		Dimension d = tool.getScreenSize();
		this.setUndecorated(true);
		this.setLocation((d.width - getWidth()) / 2, (d.height - getHeight()) / 2);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.add(new MainCanvas(this));

		this.setVisible(true);
	}
}