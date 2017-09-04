package cjj.demo.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

public class TestDepart {

	static List<Depart> list=new ArrayList<Depart>();
	static List<String> prtlist=new ArrayList<String>();
	static List<String> sonlist=new ArrayList<String>();
	static Map<Integer,List<Depart>> result=new HashMap<Integer,List<Depart>>();
	static{
		list.add(new Depart("prt", "son"));
		list.add(new Depart("A", "X"));
		list.add(new Depart("B", "Y"));
		list.add(new Depart("C", "B"));
		list.add(new Depart("D", "C"));
		list.add(new Depart("E", "F"));
		list.add(new Depart("F", "G"));
	}
	
	
	@Test
	public void test(){
		List<Depart> templist=null;
		for(Depart e:list){
			
			Integer level=recheck(1,e);
			if(result.get(level)==null){
				templist=new ArrayList<Depart>();
				templist.add(e);
				result.put(level, templist);
			}else{
				templist=result.get(level);
				templist.add(e);
			}
			
		}
		
		result.forEach((e,v)->{
			System.out.println(e+"=="+v);
		});
	}
	
	
	public Integer recheck(Integer num,Depart d){

		Depart hasprt=list.stream().filter(e->e.getID().equals(d.getPrtID())).findAny().orElse(new Depart());
		if(null!=hasprt.getID()){
			num+=1;
			return recheck(num,hasprt);
		}else{
			return num;
		}


	}
	

	
	
}
