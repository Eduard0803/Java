package app;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;


@RunWith(Parameterized.class)
public class ParcelaCalculoTest {
    Financiamento f;
    double juros, amortizacao, saldoDevedor;
    int n;

    public ParcelaCalculoTest(Financiamento f, int n, double juros, double amortizacao, double saldoDevedor) {
        this.f = f;
        this.n = n;
        this.juros = juros;
        this.amortizacao = amortizacao;
        this.saldoDevedor = saldoDevedor;
    }
    
    @Parameters
    public static Collection<Object[]> getParameters() {
        Financiamento f = new Financiamento(Financiamento.Tipo.TABELA_PRICE, 100_000.00d, 1.5d, 5);
        return Arrays.asList(new Object[][] {
            { f, 1, 1_500.00d, 19_408.93d, 80_591.07d },
            { f, 3, 913.37d, 19_995.56d, 40_895.44d },
            { f, 5, 309.00d, 20_599.93d, 0.01d },
        });
    }

    @Test
    @Category(Funcional.class)
    public void TesteCalculoParcelas() {
        Parcela p = f.calcularParcela(n);
        assertEquals(p.getValorJuros(), juros, 0.01);
        assertEquals(p.getValorAmortizacao(), amortizacao, 0.01);
        // assertEquals(f.calcularSaldoDevedor(n), saldoDevedor, 0.01);
    }
}
