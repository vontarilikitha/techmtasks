package Streams;

import java.util.List;
import java.util.stream.Collectors;

public class Sort {
	public static void main(String [] args) {
	  	   List<String> list=List.of("abc","asd","ASDF","ERG","ADads");
           List<String> res=list.stream().sorted().collect(Collectors.toList());
	 System.out.println(res);
	}
}
