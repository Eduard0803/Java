package tst;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

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
import exceptions.UnidadesMedidasDiferentesException;

@RunWith(Parameterized.class)
public class AreaPerimetro {

	//Parametros (ou atributos) do objeto de teste
	private int a, 
	            b, 
	            c, 
	            areaEsperada, 
	            perimetroEsperado; 
	private boolean ehTriangulo;
	
	private Triangulo t;
	private String unA;
	private String unB;
	private String unC;
	private String unArea;
	private String unPer;
	private Medida aMed;
	private Medida bMed;
	private Medida cMed;
	private Medida areaEspMed;
	private Medida perEspMed;

	
	@Before
	public void setup() {
		t = new Triangulo(aMed, bMed, cMed);
	}
	
	@Parameters
	public static Collection<Object[]> getParameters(){
		Medida a1, b1, c1, area1, per1,
		       a2, b2, c2, area2, per2, 
		       a3, b3, c3, area3, per3;
		
		//Triangulo 1
		a1 = new Medida(3, "cm"); 
		b1 = new Medida(4, "cm");
		c1 = new Medida(5, "cm");
		area1 = new Medida(6, "cm2");
		per1 = new Medida(12, "cm");
		
		//Triangulo 2
		a2 = new Medida(2, "dm"); 
		b2 = new Medida(3, "dm");
		c2 = new Medida(4, "dm");
		area2 = new Medida(3, "dm2");
		per2 = new Medida(9, "dm");
		
		//Triangulo 2
		a3 = new Medida(5, "m"); 
		b3 = new Medida(6, "m");
		c3 = new Medida(7, "m");
		area3 = new Medida(15, "m2");
		per3 = new Medida(18, "m");
		
		Object[][] parametros = new Object[][] {
			{a1, b1, c1, area1, per1, true},
			{a2, b2, c2, area2, per2, true},
			{a3, b3, c3, area3, per3, true}
		};
		
		return Arrays.asList(parametros);
	}
	
	public AreaPerimetro(Medida a, Medida b, Medida c, Medida areaEsperada, Medida perEsperado, boolean ehTriangulo) {
		this.aMed = a;
		this.bMed = b;
		this.cMed = c; 
		this.areaEspMed = areaEsperada;
		this.perEspMed = perEsperado;
		this.ehTriangulo = ehTriangulo;
	}
	
	
	@Test
	@Category(Funcional.class)
	public void testArea() throws UnidadesMedidasDiferentesException {
		assertEquals(areaEspMed.getValor(), t.calcularArea());
		assertTrue(t.getUnidadeArea().equalsIgnoreCase(areaEspMed.getUnidade()));
	}

	
	@Test
	@Category(Funcional.class)
	public void testPerimetro() throws UnidadesMedidasDiferentesException {
		assertEquals(perEspMed.getValor(), t.calcularPerimetro());
		assertTrue(t.getUnidadePerimetro().equalsIgnoreCase(perEspMed.getUnidade()));
	}

	@Test
	@Category(Funcional.class)
	public void testEhTriangulo() throws NaoEhTrianguloException {
		assertEquals(ehTriangulo, t.ehTriangulo());
	}
}
