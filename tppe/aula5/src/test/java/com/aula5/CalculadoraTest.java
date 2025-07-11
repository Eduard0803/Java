package com.aula5;

import static org.junit.Assert.assertEquals;

import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class CalculadoraTest {
    Calculadora calc = new Calculadora();
    int a, b, c, area, perimetro;
    boolean existe;

    @Before
    public void setup(int a, int b, int c, int area, int perimetro, boolean existe){
        this.a = a;
        this.b = b;
        this.c = c;
        this.area = area;
        this.perimetro = perimetro;
        this.existe = existe;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return java.util.Arrays.asList(new Object[][] {
            { 3, 4, 5, 6, 12, true },
            { 2, 3, 4, 3, 9, true },
            { 5, 6, 7, 15, 18, true },
            { 4, 6, 12, 12, 22, false },
            { 2, 3, 6, 3, 11, false },
            { 1, 1, 3, 0, 5, false }
        });
    }

    @Test
    public void testeArea(){
        assertEquals(area, calc.areaTriangulo(a, b, c));
    }

    @Test
    public void testePerimetro(){
        assertEquals(perimetro, calc.perimetroTriangulo(a, b, c));
    }

    @Test
    @Category(Funcional.class)
    public void testeExistencia(){
        assertEquals(existe, calc.ehTriangulo(a, b, c));
    }

    @Test(expected=NaoEhTrianguloException.class)
    @Category(Excecao.class)
    public void testeExcecao() throws NaoEhTrianguloException {
        if(!calc.ehTriangulo(a, b, c))
            throw new NaoEhTrianguloException("Nao eh triangulo");
    }
}
