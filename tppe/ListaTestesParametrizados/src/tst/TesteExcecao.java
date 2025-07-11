package tst;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import app.Medida;
import app.Triangulo;
import exceptions.NaoEhTrianguloException;

@RunWith(Parameterized.class)
public class TesteExcecao {

	Medida a;
	Medida b;
	//Criar os atributos do objeto de teste
	Medida c;
	Triangulo t;
	private String unA;
	private String unB;
	private String unC;
	
	@Parameters
	public static Collection<Object[]> getParameters() {
		Medida a1, b1, c1,
		       a2, b2, c2,
		       a3, b3, c3;
		
		a1 = new Medida(4, "cm");
		b1 = new Medida(5, "cm");
		c1 = new Medida(12, "cm");
		
		a2 = new Medida(2, "cm");
		b2 = new Medida(3, "cm");
		c2 = new Medida(6, "cm");
		
		a3 = new Medida(1, "cm");
		b3 = new Medida(1, "cm");
		c3 = new Medida(3, "cm");
		
		Object[][] parametros = new Object[][] {
			{a1, b1, c1},
			{a2, b2, c2},
			{a3, b3, c3}
		};
		return Arrays.asList(parametros);
	}
	

	public TesteExcecao(Medida a, Medida b, Medida c) {
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	@Before
	public void setup() {
		t = new Triangulo(a, b, c);
	}
	
	@Test(expected=NaoEhTrianguloException.class)
	@Category(Excecao.class)
	public void test() throws NaoEhTrianguloException {
		t.ehTriangulo();
	}

}
