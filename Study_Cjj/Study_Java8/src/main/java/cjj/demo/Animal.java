package cjj.demo;

public interface Animal {

	public void eat(String name,String food);
	
	default void sleep(){
		System.out.println("接口有默认实现方法sleep");
	}
	
}
