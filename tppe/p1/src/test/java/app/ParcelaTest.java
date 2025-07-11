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
public class ParcelaTest {
    double pv, i, p;
    int n;

    public ParcelaTest(double pv, double i, int n, double p) {
        this.pv = pv;
        this.i = i;
        this.n = n;
        this.p = p;
    }

    @Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
            { 10_000.00d, 2.0d, 5, 2_121.58 },
            { 20_000.00d, 1.8d, 10, 2_203.30 },
            { 50_000.00d, 1.3d, 24, 2_438.61 },
        });
    }

    @Test
    @Category(Funcional.class)
    public void TesteCalculoValorParcela() {
        double resultado = Parcela.calcularTabelaPrice(pv, i, n);
        assertEquals(p, resultado, 0.01);
    }
}
