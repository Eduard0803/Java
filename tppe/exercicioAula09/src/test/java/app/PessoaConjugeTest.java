package app;

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

@RunWith(Parameterized.class)
public class PessoaConjugeTest{

    Pessoa pessoa, conjuge;
    String nome, sobrenome,
           nomeConjuge, sobrenomeConjuge;

    public PessoaConjugeTest(String nome, String sobrenome, 
                            String nomeConjuge, String sobrenomeConjuge
    ){    
        this.nome = nome;
        this.sobrenome = sobrenome;
    }

    @Before
    public void setup() {
        pessoa = new Pessoa(nome, sobrenome);
        conjuge = new Pessoa(nomeConjuge, sobrenomeConjuge);
        pessoa.setConjuge(conjuge);
    }

    @Parameters
    public static Collection<Object[]> getParameters(){
        String nome1 = "João", sobrenome1 = "Silva", nomeConjuge1 = "Maria", sobrenomeConjuge1 = "Silva",
                nome2 = "Pedro", sobrenome2 = "Silva", nomeConjuge2 = "Ana", sobrenomeConjuge2 = "Silva";

        Object[][] params = new Object[][] {
            {nome1, sobrenome1, nomeConjuge1, sobrenomeConjuge1},
            {nome2, sobrenome2, nomeConjuge2, sobrenomeConjuge2},
        };
        return Arrays.asList(params);
    }

    @Test
    @Category(Funcional.class)
    public void testGetConjuge(){
        Pessoa conjugeBuffer = pessoa.getConjuge();
        assertTrue(conjugeBuffer instanceof Pessoa);

        assertEquals(nomeConjuge, conjugeBuffer.getNome());
        assertTrue(conjugeBuffer.getNome() instanceof String);

        assertEquals(sobrenomeConjuge, conjugeBuffer.getSobrenome());
        assertTrue(conjugeBuffer.getSobrenome() instanceof String);
    }
}
