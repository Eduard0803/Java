import java.util.*;

public class Solution{
    public static void main(String[] ags){
        Scanner sc = new Scanner(System.in);
        Set<Character> data = new HashSet<Character>();

        String input = sc.next();
        char[] array = input.toCharArray();

        for(int i=0; i<array.length; i++)
            data.add(array[i]);
        if(data.size() % 2 == 0)
            System.out.println("CHAT WITH HER!");
        else
            System.out.println("IGNORE HIM!");
    }
}
