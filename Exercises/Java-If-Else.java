import java.util.Scanner;

class Solution{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);

        int input = sc.nextInt();

        if(input % 2 != 0)
            System.out.println("Weird");
        else if(input >= 2 && input <= 5)
            System.out.println("Not Weird");
        else if(input >= 6 && input <= 20)
            System.out.println("Weird");
        else
            System.out.println("Not Weird");
    }
}
