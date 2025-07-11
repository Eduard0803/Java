package com.aula5;

import java.util.ArrayList;
import java.util.Collections;

public class Calculadora {
    public static int soma(int a, int b){
        return (int) a+b;
    }

    public static int areaTriangulo(int a, int b, int c){
        ArrayList<Integer> lados = new ArrayList<>();
        lados.add(a);
        lados.add(b);
        lados.add(c);
        Collections.sort(lados);
        return (int) ((lados.get(0)*lados.get(1)) / 2);
    }

    public static int perimetroTriangulo(int a, int b, int c){
        return (int) (a+b+c);
    }

    public static boolean ehTriangulo(int a, int b, int c){
        return (a+b>c && a+c>b && b+c>a);
    }
}
