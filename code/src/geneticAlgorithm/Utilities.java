package geneticAlgorithm;
import java.util.*;
public class Utilities {
	
	public static boolean print = false;
	
	public static LinkedList<Integer> stringToList(String ps){
		LinkedList<Integer> l = new LinkedList<Integer>();
		if (print) System.out.println("ps: /" + ps + "/");
		String[]psA = ps.split(" ");
		for ( int i = 0; i < psA.length;i++){
			if (psA[i] != "") l.addLast(Integer.valueOf(psA[i]));
		}
		if (print) System.out.println("after:");
		if (print) for (int i = 0; i < l.size(); i ++) System.out.print("/" + l.get(i)+"/, ");
		if (print) System.out.println();
		return l;
	}
	
	public static void printList(LinkedList<Integer> l){
		System.out.print("List: {");
		for (int i = 0; i < l.size(); i++){
			if (i < l.size()-1) System.out.print(l.get(i)+", ");
			else System.out.print(l.get(i));
		}
		System.out.print("}");
		System.out.println();
	}
}
