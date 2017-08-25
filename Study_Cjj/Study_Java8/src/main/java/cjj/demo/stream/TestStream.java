package cjj.demo.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.Test;

import cjj.demo.Dog;

public class TestStream {
	/*
	 * Stream 的操作：
	 * 
	 * --| Intermediate：
	 * 
	 * map (mapToInt, flatMap 等)、 filter、 distinct、 sorted、 peek、 limit、 skip、
	 * parallel、 sequential、 unordered
	 * 
	 * --|Terminal
	 * 
	 * forEach、 forEachOrdered、 toArray、 reduce、 collect、 min、 max、 count、
	 * anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、 iterator
	 * 
	 * --|Short-circuiting anyMatch、 allMatch、 noneMatch、 findFirst、 findAny、
	 * limit
	 */
	static List<Integer> intList = new ArrayList<>();
	static List<String> strList = new ArrayList<>();
	static {
		intList.add(5);
		intList.add(7);
		intList.add(3);
		intList.add(4);

		strList.add("aaaaaa");
		strList.add("bbba");
		strList.add("dddaaa");
		strList.add("ccc");
	}

	// map/flatMap
	@Test
	public void test1() {// map 生成的是个 1:1 映射，每个输入元素，都按照规则转换成为另外一个元素

		// 将所有元素转换大写
		List<String> templist = strList.stream().map(String::toUpperCase).collect(Collectors.toList());
		System.out.println(templist);

		// 元素平方
		List<Integer> templist2 = intList.stream().map(n -> n * n).collect(Collectors.toList());
		System.out.println(templist2);
	}

	@Test
	public void test2() {// 一对多映射关系的，这时需要 flatMap
		/*
		 * flatMap 把 input Stream 中的层级结构扁平化，就是将最底层元素抽出来放到一起，最终 output 的新 Stream
		 * 里 面已经没有 List 了，都是直接的数字
		 */

		Stream<List<Integer>> inputStream = Stream.of(Arrays.asList(1), Arrays.asList(2, 3), Arrays.asList(4, 5, 6));

		List<Integer> templist = inputStream.flatMap(e -> e.stream()).collect(Collectors.toList());
		System.out.println(templist);
	}

	@Test
	public void test3() {// filter 对原始 Stream 进行某项测试，通过测试的元素被留下来生成一个新 Stream

		Integer[] intarr = intList.stream().filter(e -> e > 3).toArray(Integer[]::new);
		System.out.println(Arrays.toString(intarr));
	}

	@Test
	public void test4() {// forEach 方法接收一个 Lambda 表达式，然后在 Stream 的每一个元素上执行该表达式。
		// forEach 不能修改自己包含的本地变量值，也不能用 break/return 之类的关键字提前结束循环。

		intList.forEach(e -> {
			System.out.println(e);
		});

		intList.stream().filter(e -> e > 3).forEach(p -> System.out.println(p));

	}

	@Test
	public void test5() {// peek 对每个元素执行操作并返回一个新的 Stream

		List<String> templist = strList.stream().filter(e -> e.length() > 3).peek(p -> System.out.println(p))
				.map(String::toUpperCase).peek(a -> System.out.println(a)).collect(Collectors.toList());

		System.out.println(templist);
	}

	// Optional 作为一个容器，它可能含有某值，或者不包含。使用它的目的是尽可能避免 NullPointerException
	// Stream 中的 findAny、max/min、reduce 等方法等返回 Optional 值。还有例如
	// IntStream.average() 返回 OptionalDouble 等等。
	@Test
	public void test6() {// findFirst是一个 termimal 兼 short-circuiting 操作，它总是返回
							// Stream 的第一个元素，或者空

		Optional<String> tempstr = strList.stream().filter(e -> e.length() < 3).findFirst();

		// Optional.ofNullable(tempstr).ifPresent(System.out::print);
		Integer length = tempstr.map(String::length).orElse(-1);
		System.out.println(length);
	}

	@Test
	public void test7() {// reduce是把 Stream
							// 元素组合起来。它提供一个起始值（种子），然后依照运算规则（BinaryOperator），和前面
							// Stream 的第一个、第二个、第 n 个元素组合。从这个意义上说，字符串拼接、数值的
							// sum、min、max、average 都是特殊的 reduce

		// 拼接字符串，无初始值，无具体对象 返回optional
		Integer tempint = intList.stream().reduce(Integer::sum).get();
		System.out.println(tempint);

		// 拼接字符串，第一个参数开始 有具体对象
		String tempstr = strList.stream().reduce("start：", String::concat);

		// 上面代码例如第一个示例的 reduce()，第一个参数（空白字符）即为起始值，第二个参数（String::concat）为
		// BinaryOperator。
		// 这类有起始值的 reduce() 都返回具体的对象。而对于第四个示例没有起始值的 reduce()，由于可能没有足够的元素，返回的是
		// Optional，请留意这个区别。
		System.out.println(tempstr);
	}

	@Test
	public void test8() {// limit/skip
		// limit 返回 Stream 的前面 n 个元素；skip 则是扔掉前 n 个元素（它是由一个叫 subStream 的方法改名而来）。

		// 执行次数是limit次数
		List<String> templist = strList.stream().filter(e -> e.contains("a")).limit(2).collect(Collectors.toList());
		System.out.println(templist);

		List<String> skiplist = strList.stream().filter(e -> e.contains("a")).skip(2).collect(Collectors.toList());
		System.out.println(skiplist);

		/*
		 * 有一种情况是 limit/skip 无法达到 short-circuiting 目的的，就是把它们放在 Stream 的排序操作后，原因跟
		 * sorted 这个 intermediate 操作有关：此时系统并不知道 Stream 排序后的次序如何，所以 sorted
		 * 中的操作看上去就像完全没有被 limit 或者 skip 一样。 结果是limit次数，但执行次数并不是limit的次数，先排序后取值
		 * 
		 */
		List<String> sortlist = strList.stream().sorted((e1, e2) -> e1.length() - e2.length()).limit(2)
				.collect(Collectors.toList());
		System.out.println(sortlist);

	}

	@Test
	public void test9() {
		// sorted
		/*
		 * 对 Stream 的排序通过 sorted 进行，它比数组的排序更强之处在于你可以首先对 Stream 进行各类
		 * map、filter、limit、skip 甚至 distinct 来减少元素数量后，再排序，这能帮助程序明显缩短执行时间
		 */ List<String> sortlist = strList.stream().limit(3).sorted((e1, e2) -> e1.length() - e2.length())
				.collect(Collectors.toList());
		System.out.println(sortlist);

	}

	@Test
	public void test10() {
		// min/max/distinct
		/*
		 * min 和 max 的功能也可以通过对 Stream 元素先排序，再 findFirst 来实现，但前者的性能会更好，为 O(n)，而
		 * sorted 的成本 是 O(n log n)。同时它们作为特殊的 reduce 方法被独立出来也是因为求最大最小值是很常见的操作。
		 */

		// Long s2 = System.currentTimeMillis();
		// String sortstr = strList.stream().sorted((e1, e2) -> e2.length() -
		// e1.length()).findFirst().orElse("null");
		// System.out.println(sortstr);
		//
		// Long end2 = System.currentTimeMillis();
		// System.out.println("sort:" + (end2 - s2));

		Long s1 = System.currentTimeMillis();

		String maxstr = strList.stream().max((e1, e2) -> e1.length() - e2.length()).orElse("null");
		System.out.println(maxstr);

		Long end1 = System.currentTimeMillis();
		System.out.println("max:" + (end1 - s1));

	}

	// 通过实现 Supplier 接口，你可以自己来控制流的生成.通常用于随机数、常量的 Stream，或者需要前后元素间维持着某种状态信息的
	// Stream
	@Test
	public void test11() {
		Random rd = new Random();
		Supplier<Integer> rdint = rd::nextInt;

		Stream.generate(rdint).limit(10).forEach(System.out::println);

	}

	// Stream.iterate iterate 跟 reduce 操作很像，接受一个种子值，和一个 UnaryOperator（例如f）。
	// 然后种子值成为 Stream 的第一个元素，f(seed) 为第二个，f(f(seed)) 第三个，以此类推
	@Test
	public void test12() {
		Stream.iterate(0, n -> n + 3).limit(10).forEach(x -> System.out.print(x + " "));

		// 与 Stream.generate 相仿，在 iterate 时候管道必须有 limit 这样的操作来限制 Stream 大小
	}

	
	static List<Dog> list=new ArrayList<Dog>();
	static{
		
		list.add(new Dog("blue", "dog1", 7));
		list.add(new Dog("black", "dog2", 3));
		list.add(new Dog("black", "dog3", 3));
		list.add(new Dog("blue", "dog4", 9));
		list.add(new Dog("yellow", "dog5", 4));
		
	}
	// Collectors java.util.stream.Collectors 类的主要作用就是辅助进行各类有用的 reduction
	// 操作，例如转变输出为 Collection，把 Stream 元素进行归组
	@Test
	public void test13() {
		Map<Integer,List<Dog>> tempmap=list.stream().collect(Collectors.groupingBy(Dog::getAge));
		System.out.println(tempmap);
	}
	
	@Test
	public void test14() {
		//partitioningBy特殊的groupingBy，將結果分成兩種情況true false包含全部結果
		Map<Boolean, List<Dog>> tempmap=list.stream().collect(Collectors.partitioningBy(d->d.getAge()>4));
		System.out.println("true:"+tempmap.get(true));
		System.out.println("false:"+tempmap.get(false));
	}

	/*
	 
	 Stream 的特性可以归纳为：
	 
	 1.不是数据结构
	 2.它没有内部存储，它只是用操作管道从 source（数据结构、数组、generator function、IO channel）抓取数据。
	 3.它也绝不修改自己所封装的底层数据结构的数据。例如 Stream 的 filter 操作会产生一个不包含被过滤元素的新 Stream，而不是从 source 删除那些元素。
	 4.所有 Stream 的操作必须以 lambda 表达式为参数
	 5.你可以请求第一个元素，但无法请求第二个，第三个，或最后一个
	 6.不支持索引访问
	 7.很容易生成数组或者 List
	 8.并行能力 当一个 Stream 是并行化的，就不需要再写多线程代码，所有对它的操作会自动并行进行的。
	 9.可以是无限的
集合有固定大小，Stream 则不必。limit(n) 和 findFirst() 这类的 short-circuiting 操作可以对无限的 Stream 进行运算并很快完成
	 10.Intermediate 操作永远是惰性化的。
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	*/
	
	
	
}
