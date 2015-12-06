package water.water;

import java.util.ArrayList;

public class Pool<T> {
	
	public static Pool<Water> water = new Pool<Water>(1000, new Water());
	public static Pool<Goose> goose = new Pool<Goose>(20, new Goose());
	public static Pool<Platform> platform = new Pool<Platform>(20, new Platform());
	public static Pool<Cloud> cloud = new Pool<Cloud>(20, new Cloud());
	public static Pool<WaterItem> waterItem = new Pool<WaterItem>(20, new WaterItem());
	public static Pool<FlyingGoose> flyingGoose = new Pool<FlyingGoose>(20, new FlyingGoose());
	public static Pool<Poop> poop = new Pool<Poop>(20, new Poop());
	
	private ArrayList<T> objects;
	private T t;
	
	public Pool(int numObjects, T t) {
		objects = new ArrayList<T>(numObjects);
		this.t = t;
	}
	
	@SuppressWarnings("unchecked")
	public T get() {
		if(objects.isEmpty()) {
			try {
				return (T)(t.getClass().getConstructor().newInstance());
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		} else {
			return objects.remove(objects.size() - 1);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void put(Object t) {
		objects.add((T)t);
	}
	
}
