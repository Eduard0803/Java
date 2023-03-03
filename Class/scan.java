import java.util.Scanner;

class Scan{
    public static int scan_int(){
        Scanner sc = new Scanner(System.in);
        int number = sc.nextInt();
        return number;
    }
    public static float scan_float(){
        Scanner sc = new Scanner(System.in);
        float number = sc.nextFloat();
        return number;
    }
    public static double scan_double(){
        Scanner sc = new Scanner(System.in);
        double number = sc.nextDouble();
        return number;
    }
    public static char scan_char(){
        Scanner sc = new Scanner(System.in);
        char input = sc.next().charAt(0);
        return input;
    }
    public static String scan_string(int choose){
        Scanner sc = new Scanner(System.in);
        String input;
        if(choose == 0)
            input = sc.next();
        else
            input = sc.nextLine();
        return input;
    }
}
