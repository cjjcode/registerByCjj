package cjj.demo.stream;

import java.util.Random;
import java.util.function.Supplier;

import cjj.demo.Dog;

public class MySupplier implements Supplier<Dog>{

	Random ran=new Random(10);
	@Override
	public Dog get() {
		return new Dog("black", ran.toString(), ran.nextInt());
	}

	
}

