package water.water;

import java.lang.reflect.Constructor;
import java.util.ArrayList;

public class Pool<T> {
	
	private static ArrayList<Pool<?>> pools = new ArrayList<Pool<?>>(20);
	
	public static void put(Object t) {
		Class<?> tClass = t.getClass();
		Pool<?> p = getPool(tClass, t);
		
		if(p != null) {
			p.insert(t);
		}
	}
	
	public static <E> E get(Class<E> tClass) {
		return (E)getPool(tClass, null).acquire();
	}
	
	@SuppressWarnings("unchecked")
	private static <E> Pool<E> getPool(Class<E> tClass, Object elem) {
		for(int i = 0; i < pools.size(); i++) {
			Pool<?> p = pools.get(i);
			Class<?> c =  (Class<?>) p.t.getClass();
			
			if(c.equals(tClass)) {
				return (Pool<E>)p;
			}
		}
		
		try {
			if(elem == null) {
				return new Pool<E>((E)tClass.getConstructor().newInstance());
			} else {
				new Pool<E>((E)elem);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
		return null;
	}
	
	private ArrayList<T> objects;
	private T t;
	private Constructor<T> tConstructor;
	
	@SuppressWarnings("unchecked")
	public Pool(T t) {
		objects = new ArrayList<T>(100);
		this.t = t;
		
		objects.add(t);
		try {
			this.tConstructor = (Constructor<T>) t.getClass().getConstructor();
		} catch (Exception e) {
			e.printStackTrace();
		}
		pools.add(this);
	}
	
	public T acquire() {
		if(objects.isEmpty()) {
			try {
				return (T)(tConstructor.newInstance());
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
