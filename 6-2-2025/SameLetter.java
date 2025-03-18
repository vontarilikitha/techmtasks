package Streams;

import java.util.List;
import java.util.stream.Stream;

public class SameLetter {
	public static void main(String [] args) {
	  	   List<String> list=List.of("abc","asd","ASDF","ERG","ADads");
	  	 char start = 'a';
	  	long count=list.stream().filter(s->s.toLowerCase().startsWith(String.valueOf(start).toLowerCase())).count();
	System.out.println(count);
	}
}
