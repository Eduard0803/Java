import java.util.Scanner;

class Solution{
    public static void main(String[] main){
        Scanner sc = new Scanner(System.in);

        int input = sc.nextInt();

        for(int i=1; i<=10; i++)
            System.out.println(input + " x " + i + " = " + input*i);
    }
}
