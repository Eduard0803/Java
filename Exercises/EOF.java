import java.util.Scanner;

class Solution{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int count=1;

        while(sc.hasNext())
        {
            String input = sc.nextLine();
            System.out.println(count++ + " " + input);
        }
    }
}
