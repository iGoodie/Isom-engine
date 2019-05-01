package net.programmer.igoodie.engine.registry;

import java.util.ArrayList;
import java.util.List;

public abstract class Registry<E> {

	protected List<E> registry;

	public Registry() {
		this.registry = new ArrayList<E>();
	}

	public int size() {
		return registry.size();
	}

	public int register(E element) {
		registry.add(element);
		return registry.size() - 1;
	}

	public E get(int id) {
		if (id < 0 || id >= registry.size())
			return null;

		return registry.get(id);
	}

}
