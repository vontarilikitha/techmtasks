package coreproject1;
import java.util.Scanner;
public class SmallestVowel {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		char z='\0';
		String a=sc.next();
		for(int i=0;i<a.length();i++)
		{
			if(a.charAt(i)=='a' ||a.charAt(i)=='e'||a.charAt(i)=='i'||a.charAt(i)=='o'||a.charAt(i)=='u'||a.charAt(i)=='A'||a.charAt(i)=='E'||a.charAt(i)=='I'||a.charAt(i)=='O'||a.charAt(i)=='U')
			{
				if(z=='\0' || i<z)
				{
					z=a.charAt(i);
				}
			}
				
		}
		System.out.println(z);
		
		
		
		
	}

}
