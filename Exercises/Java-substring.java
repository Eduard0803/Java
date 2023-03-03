import java.util.Scanner;
import java.util.Arrays;

class Solution{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        String input = sc.next();
        char[] vector = input.toCharArray();
        int start = sc.nextInt();
        int end = sc.nextInt();

        for(int i=start; i<end; i++)
            System.out.print(vector[i]);
        System.out.println();
    }
}