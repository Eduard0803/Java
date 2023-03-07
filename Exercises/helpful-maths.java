import java.util.*;

public class Solution{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        String input = sc.next();
        char[] array = input.toCharArray();

        Arrays.sort(array);

        for(int i=array.length/2; i<array.length-1; i++){
            System.out.print(array[i]);
            System.out.print("+");
        }
        System.out.println(array[array.length-1]);
    }
}
