package Streams;
import java.util.*;
public class Avearage {
       public static void main(String [] args) {
    	   List<Integer> list=List.of(1,2,3,4,5);
           OptionalDouble avg=list.stream().mapToDouble(Integer:: doubleValue).average();
      System.out.println(avg);
       }
}
