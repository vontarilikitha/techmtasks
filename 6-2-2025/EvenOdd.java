package Streams;

import java.util.List;
import java.util.stream.Collectors;
public class EvenOdd {
	  public static void main(String [] args) {
   	   List<Integer> list=List.of(1,2,3,4,5);
     int es=list.stream().filter(n->n%2==0).mapToInt(Integer::intValue).sum();
	  int os=list.stream().filter(n->n%2!=0).mapToInt(Integer::intValue).sum();
	 System.out.println(es+" "+os);
	  }
}
