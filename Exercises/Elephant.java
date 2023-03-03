import java.util.Scanner;

public class Solution{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        int input = sc.nextInt();
        int passos = input / 5;
        
        if(input % 5 != 0)
            passos++;
        System.out.println(passos);
    }
}
