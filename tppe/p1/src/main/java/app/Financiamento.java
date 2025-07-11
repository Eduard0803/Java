package app;

public class Financiamento {
    private double valorFinanciado;
    private double taxaJuros;
    private int numeroParcelas;
    private Tipo tipo;

    public Financiamento(Tipo tipo, double valorFinanciado, double taxaJuros, int numeroParcelas) {
        this.tipo = tipo;
        this.valorFinanciado = valorFinanciado;
        this.taxaJuros = taxaJuros;
        this.numeroParcelas = numeroParcelas;
    }

    public Parcela calcularParcela(int n){
        if(numeroParcelas <= 0 || n <= 0 || n > numeroParcelas)
            return null;

        double juros = 0.0d, amortizacao = 0.0d, saldoDevedor = valorFinanciado, 
                valorParcela = Parcela.calcularValorParcela(tipo, valorFinanciado, taxaJuros, numeroParcelas);
        int count=0;

        while(count < n){
            juros = saldoDevedor * (taxaJuros / 100);
            amortizacao = valorParcela - juros;
            saldoDevedor -= amortizacao;
            count++;
        }
        return new Parcela(Parcela.ajustaPrecisao(juros, 2), Parcela.ajustaPrecisao(amortizacao, 2));
    }

    public double calcularSaldoDevedor(int n){
        if(numeroParcelas <= 0 || n <= 0 || n > numeroParcelas)
            return 0.0d;

        double juros = 0.0d, amortizacao = 0.0d, saldoDevedor = valorFinanciado, 
                valorParcela = Parcela.calcularValorParcela(tipo, valorFinanciado, taxaJuros, numeroParcelas);
        int count=0;

        while(count < n){
            juros = saldoDevedor * (taxaJuros / 100);
            amortizacao = valorParcela - juros;
            saldoDevedor -= amortizacao;
            count++;
        }
        return saldoDevedor;
    }

    public double getValorFinanciado() {return valorFinanciado;}
    public void setValorFinanciado(double valorFinanciado) {this.valorFinanciado = valorFinanciado;}

    public double getTaxaJuros() {return taxaJuros;}
    public void setTaxaJuros(double taxaJuros) {this.taxaJuros = taxaJuros;}

    public int getNumeroParcelas() {return numeroParcelas;}
    public void setNumeroParcelas(int numeroParcelas) {this.numeroParcelas = numeroParcelas;}

    public Tipo getTipoFinanciamento() {return tipo;}
    public void setTipoFinanciamento(Tipo tipo) {this.tipo = tipo;}

    public enum Tipo {
        TABELA_PRICE,
        SAC
    }
}
