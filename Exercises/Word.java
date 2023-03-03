import java.util.Scanner;

public class Solution{
    private static int eduardo(String str){
        int minusculas=0, maiusculas=0;
        char[] array = str.toCharArray();

        for(int i=0; i<str.length(); i++)
            if(array[i] < 97)
                maiusculas++;
            else
                minusculas++;
        if(minusculas >= maiusculas)
            return 0;
        return 1;
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String input = sc.next();

        if(Solution.eduardo(input) == 0)
            System.out.println(input.toLowerCase());
        else
            System.out.println(input.toUpperCase());
    }
}