package app;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import exceptions.PVNuloOuNegativoException;
import exceptions.QtdePrestacaoesInvalidaException;
import exceptions.TxJurosNulaException;


@RunWith(Parameterized.class)
public class ExcecaoTest {
    double pv, i;
    int n;

    public ExcecaoTest(double pv, double i, int n) {
        this.pv = pv;
        this.i = i;
        this.n = n;
    }

    @Parameters
    public static Collection<Object[]> getParameters() {
        return Arrays.asList(new Object[][] {
            { 0.0d, 0.0d, 1 },
            { -1.0d, 0.0d, 0 },
            { -2.0d, 0.0d, -1 },
        });
    }

    @Test(expected=PVNuloOuNegativoException.class)
    @Category(Excecao.class)
    public void PVNuloOuNegativoTest() throws PVNuloOuNegativoException{
        if(pv <= 0.0d)
            throw new PVNuloOuNegativoException("pv nulo ou negativo");
    }

    @Test(expected=QtdePrestacaoesInvalidaException.class)
    @Category(Excecao.class)
    public void QtdePrestacaoesInvalidaTest() throws QtdePrestacaoesInvalidaException{
        if(n <= 1)
            throw new QtdePrestacaoesInvalidaException("quantidade de parcelas nula ou negativa");
    }

    @Test(expected=TxJurosNulaException.class)
    @Category(Excecao.class)
    public void TxJurosNulaTest() throws TxJurosNulaException{
        if(i == 0.0d)
            throw new TxJurosNulaException("taxa de juros nula");
    }
}
