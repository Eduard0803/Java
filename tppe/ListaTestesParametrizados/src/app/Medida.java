package app;

public class Medida {

	private int valor;
	private String unidade;

	public Medida(int valor, String unidade) {
		this.valor = valor; 
		this.unidade = unidade;
	}

	public int getValor() {
		return valor;
	}

	public String getUnidade() {
		return unidade;
	}

}
