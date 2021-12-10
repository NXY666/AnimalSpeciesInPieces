package org.ybw.nb.animations;

public class FileAnimationSet {
	boolean loop;
	TimelineMap timeline;
	int totalFrame;

	public boolean isLoop() {
		return loop;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	public TimelineMap getTimelineMap() {
		return timeline;
	}

	public void setTimelineMap(TimelineMap timelineMap) {
		this.timeline = timelineMap;
	}

	public int getTotalFrame() {
		return totalFrame;
	}

	public void setTotalFrame(int totalFrame) {
		this.totalFrame = totalFrame;
	}
}