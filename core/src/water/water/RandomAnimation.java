package water.water;

import java.util.Random;

public class RandomAnimation {

	public static RandomAnimation flyingPoop = new RandomAnimation(new Animation[] {
		new Animation("flying-poop-1.png", 200, 200, 0.75f),
		new Animation("flying-poop-2.png", 200, 200, 0.75f),
		new Animation("flying-poop-3.png", 200, 200, 0.75f),
	});
	
	private static Random random = new Random();
	
	private Animation[] anims;
	
	public RandomAnimation(Animation[] anims) {
		this.anims = anims;
	}
	
	public Animation getAnim() {
		return anims[random.nextInt(anims.length)];
	}
}
