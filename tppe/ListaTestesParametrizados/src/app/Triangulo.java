package app;

import exceptions.NaoEhTrianguloException;
import exceptions.UnidadesMedidasDiferentesException;

public class Triangulo {

	private int a;
	private int b;
	private int c;
	private String unA;
	private String unB;
	private String unC;
	private String unArea;
	private String unPer;

	public Triangulo(Medida a, Medida b, Medida c) {
		this.a = a.getValor(); 
		this.unA = a.getUnidade();
		this.b = b.getValor(); 
		this.unB = b.getUnidade();
		this.c = c.getValor();
		this.unC = c.getUnidade();
	}
	
	

	public int calcularArea() throws UnidadesMedidasDiferentesException {
		if (!unA.equalsIgnoreCase(unB) || !unA.equalsIgnoreCase(unC)) {
			throw new UnidadesMedidasDiferentesException();
		}
		unArea = unA + "2";
		return a * b / 2 ;
	}

	public int calcularPerimetro() throws UnidadesMedidasDiferentesException {
		if (!unA.equalsIgnoreCase(unB) || !unA.equalsIgnoreCase(unC)) {
			throw new UnidadesMedidasDiferentesException();
		}
		unPer = unA;
		return a + b + c;
	}

	public boolean ehTriangulo() throws NaoEhTrianguloException {
		boolean ehTriangulo = (a >= Math.abs(b-c) && a <= Math.abs(b+c)) &&
						   (b >= Math.abs(a-c) && b <= Math.abs(a+c)) &&
						   (c >= Math.abs(a-b) && c <= Math.abs(a+b));
		if (!ehTriangulo)
			throw new NaoEhTrianguloException();
		return ehTriangulo;
	}

	public String getUnidadeArea() {
		return unArea;
	}

	public String getUnidadePerimetro() {
		return unPer;
	}

}
