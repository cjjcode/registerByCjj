package cjj.demo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

public class TestJava_1 {

	static List<Dog> list=new ArrayList<Dog>();
	static Map<String,List<Dog>> map=new HashMap<>();
	static{
		
		list.add(new Dog("blue", "dog1", 7));
		list.add(new Dog("black", "dog2", 3));
		list.add(new Dog("black", "dog3", 3));
		list.add(new Dog("blue", "dog4", 9));
		list.add(new Dog("yellow", "dog5", 4));
		
		map.put("张三", list.subList(0, 3));
		map.put("张三", list.subList(3, 5));
		
	}
	public static void main(String[] args) throws Exception {

		test5();
		test6();
	
	}



	/**
	 * list遍历
	 * @throws Exception
	 */
	@Test
	public void test1() throws Exception {
		
	//	list.forEach(e -> System.out.println(e));
		//指明类型也可以
	//	list.forEach((Dog e) -> System.out.println(e));
		
		list.forEach(e -> {
			//处理逻辑
			if(e.getAge()>4){
				System.out.println(e);
			}
			
		});
	}
	@Test
	public void test2(){
		//获取指定条件的第一条记录
		Optional<Dog> op=list.stream().filter(d-> d.getColor().equals("blue")).findFirst();
		System.out.println(op);
		
		//获取指定条件所有记录
		
		List<Dog> templist=list.stream().filter(d -> d.getAge()==3).collect(Collectors.toList());
		templist.forEach(d ->System.out.println("222"+d));
	}
	
	@Test
	public void test3() throws Exception {
		//list元素分组成 map
		Map<String,List<Dog>> tempMap=list.stream().collect(Collectors.groupingBy(Dog::getColor));
		tempMap.forEach((key,value)->System.out.println(key+"=="+value));
		
	}
	//@Test
	public static void test4() throws Exception {
		Long start=System.currentTimeMillis();
		
		List<Dog> templist=list.stream().filter(d -> d.getAge()==3).collect(Collectors.toList());
		//templist.forEach(d ->System.out.println("222"+d));
		System.out.println(templist);
		Long end=System.currentTimeMillis();
		
		System.out.println("filter:"+(end-start));
	}
	//@Test
	public static void test5() throws Exception {
		Long start=System.currentTimeMillis();
		
		List<Dog> templist=new ArrayList<>();
		for (Dog dog : list) {
			if(dog.getAge()==3){
				templist.add(dog);
			}
		}
		System.out.println(templist);
		Long end=System.currentTimeMillis();
		
		System.out.println("bianli:"+(end-start));
	}
	public static void test6() throws Exception {
		Long start=System.currentTimeMillis();
		
		List<Dog> templist=new ArrayList<>();
		list.forEach(d->{
			if(d.getAge()==3){
				templist.add(d);
			}
		});
		System.out.println(templist);
		Long end=System.currentTimeMillis();
		
		System.out.println("Java8bianli:"+(end-start));
	}
	
}
