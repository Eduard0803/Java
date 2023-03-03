import java.util.Scanner;
import java.util.Arrays;

public class Solution{
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        int n_querys = sc.nextInt();
        int count=0, i=0;
        int[] v = new int[3];

        while(n_querys > 0){
            n_querys--;
            v[i++] = sc.nextInt();
            v[i++] = sc.nextInt();
            v[i++] = sc.nextInt();
            Arrays.sort(v);
            if(v[1] == 1)
                count++;
            if(i == 3)
                i=0;
        }
        System.out.println(count);
    }
}