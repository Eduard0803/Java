import java.util.Scanner;
import java.util.Arrays;

class string{
    public static boolean palindromo(String str){ // metodo para verificar uma string é um palindromo 
        String reverse = "";

        for(int i=str.length()-1; i>=0; i--) // inverte a string `str` e armazena na string `reverse`
            reverse += str.charAt(i);

        if(str.equals(reverse)) // se a string `str` for igual a string `reverse` retorna `true`, senão retorna `false`
            return true;
        else
            return false;
    }

    public static void scannerEOF(String[] args){ // exemplo de como ler strings até o EOF - End Of File
        Scanner sc = new Scanner(System.in);

        while(sc.hasNext()) // metodo para ler strings até o EOF
        {
            String input = sc.nextLine();
            System.out.println(input);
        }
    }
    
    public static boolean anagramas(String str1, String str2){ // retorna verdadeiro se `str1` e `str2` forem anagramas, senão retorna falso
        int tam1 = str1.length();
        int tam2 = str2.length();
        if(tam1 != tam2) // se os tamanhos forem diferentes ja retorna falso
            return false;

        // transforma as strings em arrays de caracteres
        char[] array1 = str1.toCharArray();
        char[] array2 = str2.toCharArray();
        
        // transforma todos os caracteres maiusculos da string em minusculos
        for(int i=0; i<tam1; i++){
            if(array1[i] < 97)
                array1[i] += 32;
            if(array2[i] < 97)
                array2[i] += 32;
        }

        // ordena os dois arrays
        Arrays.sort(array1);
        Arrays.sort(array2);

        // volta os arrays para strings
        String sortedStr1 = new String(array1);
        String sortedStr2 = new String(array2);

        if(sortedStr1.equals(sortedStr2)) // compara as duas strings
            return true;
        else
            return false;
    }
}
