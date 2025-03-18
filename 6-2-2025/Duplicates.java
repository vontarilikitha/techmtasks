package Streams;

import java.util.List;
import java.util.stream.Collectors;

public class Duplicates {
	public static void main(String [] args) {
 	   List<Integer> list=List.of(1,2,3,3,4,5,4,5);
       List<Integer> res=list.stream().distinct().collect(Collectors.toList());
	System.out.println(res);
	}
}
