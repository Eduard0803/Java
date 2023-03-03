import java.util.Scanner;

public class Solution{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int count = sc.nextInt();
        while(count > 0){
            String input = sc.next();
            int tam = input.length();

            if(tam > 10){
                System.out.print(input.charAt(0));
                System.out.print(tam-2);
                System.out.println(input.charAt(tam-1));
            }
            else
                System.out.println(input);
            count--;
        }
    }
}
