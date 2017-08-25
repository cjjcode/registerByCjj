package job.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Test {

	@org.junit.Test
	public void test1() throws Exception {
		String secuTradeType="E,ES,ESC,ESD,U,UP,K,P,PED,RF,HK,HKL";
		List<String> list=new ArrayList<>(Arrays.asList(secuTradeType.split(",")));
	}
	
	private void test2(List<String> list,Integer maxlength) {

		List<String> templist=list.stream().filter(e->e.length()==maxlength).collect(Collectors.toList());
		
		Predicate<String> fourLetterLong = (n) -> n.length() > maxlength;
		templist.forEach(p->{
			Predicate<String> startsWithp = (n) -> n.startsWith(p);  
			
			List<String> deletelist=list.stream().filter(fourLetterLong.and(startsWithp)).collect(Collectors.toList());
		});
		
		//list.removeAll();
	}
}
