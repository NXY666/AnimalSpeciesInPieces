package org.ybw.nb.animations;

public class Transformer {
	int waitFrame = 0;
	int totalFrame = 0;

	int repeatFrame = -1;
	String editColor = null;
	double[][] moveTo = null;

	public int getRepeatFrame() {
		return repeatFrame;
	}

	public void setRepeatFrame(int repeatFrame) {
		this.repeatFrame = repeatFrame;
	}

	public String getEditColor() {
		return editColor;
	}

	public void setEditColor(String editColor) {
		this.editColor = editColor;
	}

	public int getWaitFrame() {
		return waitFrame;
	}

	public void setWaitFrame(int waitFrame) {
		this.waitFrame = waitFrame;
	}

	public int getTotalFrame() {
		return totalFrame;
	}

	public void setTotalFrame(int totalFrame) {
		this.totalFrame = totalFrame;
	}

	public double[][] getMoveTo() {
		return moveTo;
	}

	public void setMoveTo(double[][] moveTo) {
		this.moveTo = moveTo;
	}
}
