package Streams;

import java.util.List;
import java.util.OptionalInt;

public class maxmin {
	public static void main(String [] args) {
 	   List<Integer> list=List.of(1,2,3,4,5);
       OptionalInt max=list.stream().mapToInt(Integer::intValue).max();
       OptionalInt min=list.stream().mapToInt(Integer::intValue).min();
	System.out.println(max+" "+min);
	}
}
