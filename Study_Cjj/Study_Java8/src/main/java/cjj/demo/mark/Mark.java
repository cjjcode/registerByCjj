package cjj.demo.mark;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class Mark {

	//传递 lambda 表达式?
	
	@Test
	public void test1() throws Exception {
		List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
		numbers.stream()
		.filter(e -> e % 2 == 0)//这个lambda表达式执行一些逻辑
		.forEach(e -> System.out.println(e));//这个表达式无实际意义
		//传递给 forEach 方法的 lambda 表达式就是我们所称的传递 lambda 表达式。表达式 e -> System.out.println(e)将它的形参作为实参传递给 PrintStream 类的 println 方法，该方法是 System.out 实例。
		
		//优化
		numbers.stream()
		.filter(e -> e % 2 == 0)
		.forEach(System.out::println);
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
