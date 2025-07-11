package app;

public class Parcela {
    private double valorJuros;
    private double valorAmortizacao;

    private Parcela(){}

    public Parcela(double valorJuros, double valorAmortizacao) {
        this.valorJuros = valorJuros;
        this.valorAmortizacao = valorAmortizacao;
    }

    public double getValorJuros() {return valorJuros;}
    public void setValorJuros(double valorJuros) {this.valorJuros = valorJuros;}

    public double getValorAmortizacao() {return valorAmortizacao;}
    public void setValorAmortizacao(double valorAmortizacao) {this.valorAmortizacao = valorAmortizacao;}

    public static double calcularValorParcela(Financiamento.Tipo tipo, double pv, double i, int n) {
        if(tipo == Financiamento.Tipo.TABELA_PRICE)
            return calcularTabelaPrice(pv, i, n);
        else if(tipo == Financiamento.Tipo.SAC)
            return calcularSAC(pv, i, n);
        return 0.0d;
    }

    public static double calcularTabelaPrice(double pv, double taxaJuros, int n) {
        double i = taxaJuros/100;
        double numerador = Math.pow((1+i), n)*i;
        double denominador = Math.pow((1+i), n)-1;
        
        return ajustaPrecisao(pv*numerador/denominador, 2);
    }

    public static double calcularSAC(double valorFinanciado, double taxaJuros, int numeroParcelas) {
        return 1.0d;
    }

    public static double ajustaPrecisao(double valor, int precisao) {
        double fator = Math.pow(10, precisao);
        return Math.round(valor * fator) / fator;
    }
}
