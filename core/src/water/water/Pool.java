package water.water;

import java.util.ArrayList;

public class Pool<T> {
	
	private static ArrayList<Pool<?>> pools = new ArrayList<Pool<?>>(20);
	
	public static void put(Object t) {
		Class<?> tClass = t.getClass();
		getPool(tClass).insert(t);
	}
	
	@SuppressWarnings("unchecked")
	public static <E> E get(Class<E> tClass) {
		return (E)getPool(tClass).acquire();
	}
	
	@SuppressWarnings("unchecked")
	private static <E> Pool<E> getPool(Class<?> tClass) {
		for(int i = 0; i < pools.size(); i++) {
			Pool<?> p = pools.get(i);
			Class<?> c =  (Class<?>) p.t.getClass();
			
			if(c.equals(tClass)) {
				return (Pool<E>)p;
			}
		}
		
		try {
			return new Pool<E>(100, (E)tClass.getConstructor().newInstance());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	private ArrayList<T> objects;
	private T t;
	
	public Pool(int numObjects, T t) {
		objects = new ArrayList<T>(numObjects);
		this.t = t;
		pools.add(this);
	}
	
	@SuppressWarnings("unchecked")
	public T acquire() {
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
	public void insert(Object t) {
		objects.add((T)t);
	}
	
}
