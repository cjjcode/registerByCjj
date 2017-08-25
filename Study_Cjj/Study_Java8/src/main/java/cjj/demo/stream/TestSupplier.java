package cjj.demo.stream;

import java.util.stream.Stream;

import org.junit.Test;

public class TestSupplier {

	
	@Test
	public void test1(){
		Stream.generate(new MySupplier()).limit(10).forEach(System.out::println);
	}

}
