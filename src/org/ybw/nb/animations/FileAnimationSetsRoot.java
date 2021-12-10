package org.ybw.nb.animations;

import java.util.HashMap;

public class FileAnimationSetsRoot {
	HashMap<String, FileAnimationSet> animation_sets;

	public HashMap<String, FileAnimationSet> getAnimationSets() {
		return animation_sets;
	}

	public void setAnimationSets(HashMap<String, FileAnimationSet> animationSets) {
		this.animation_sets = animationSets;
	}
}