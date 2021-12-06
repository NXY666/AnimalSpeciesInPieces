package org.ybw.nb;

import javax.swing.*;
import java.awt.*;

abstract public class Frame extends JPanel {
	protected final FrameContainer frame;

	public Frame(FrameContainer frame) {
		this.frame = frame;
		this.setOpaque(false);
	}

	public abstract void render(Graphics2D screenGraphics);

	@Override
	public void paint(Graphics graphics) {
		render((Graphics2D) graphics);
	}

	protected void onFinish() {
	}
}

