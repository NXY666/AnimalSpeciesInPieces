package org.ybw.nb;

import javax.swing.*;
import java.awt.*;

public class FrameContainer extends JFrame {
	protected int realWidth, realHeight;

	public FrameContainer() {
		setTitle("CssPic");
		setSize(1400, 1000);

		getContentPane().setBackground(Color.GRAY);
		// setResizable(false);
		Toolkit tool = Toolkit.getDefaultToolkit();
		Dimension d = tool.getScreenSize();
		setLocation((d.width - getWidth()) / 2, (d.height - getHeight()) / 2);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPanel(new MainCanvas());

		setVisible(true);
	}

	public void setPanel(JPanel panel) {
		Container c = getContentPane();
		c.removeAll();
		c.add(panel);
		c.validate();
	}
}
