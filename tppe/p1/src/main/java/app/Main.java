package app;

public class Main {
    public static void main(String[] args) {
        Financiamento f = new Financiamento(Financiamento.Tipo.TABELA_PRICE, 100_000.00d, 1.5d, 5);
        for(int i=1; i <= f.getNumeroParcelas(); i++){
            if(i%2 != 0){
                Parcela p = f.calcularParcela(i);
                System.out.println(i + ": " + p.getValorJuros() + " - " + p.getValorAmortizacao());
            }
        }
    }
}
