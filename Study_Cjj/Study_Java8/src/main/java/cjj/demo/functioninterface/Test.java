package cjj.demo.functioninterface;

public class Test implements MyFunction{

	@Override
	public void method() {
		// TODO 自动生成的方法存根
		
	}
	
	//可重写默认方法
	@Override
	public void defaultmethod() {
		// TODO 自动生成的方法存根
		MyFunction.super.defaultmethod();
	}
}
