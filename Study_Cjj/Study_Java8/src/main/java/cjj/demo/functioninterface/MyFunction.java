package cjj.demo.functioninterface;

@FunctionalInterface
public interface MyFunction {

	void method();
	
	default void defaultmethod(){
		System.out.println("我是defaultmethod");
	}
	
	default void defaultmethod2(){
		System.out.println("我是defaultmethod");
	}
	
	static void defaultmethod3(){
		System.out.println("我是defaultmethod");
	}
}
