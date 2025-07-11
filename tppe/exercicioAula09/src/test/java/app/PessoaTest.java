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
public class PessoaTest{
    Pessoa pessoa;
    String nome, sobrenome;

    public PessoaTest(String nome, String sobrenome){
        this.nome = nome;
        this.sobrenome = sobrenome;
    }

    @Before
    public void setup(){
        pessoa = new Pessoa(nome, sobrenome);
    }
    
    @Parameters
    public static Collection<Object[]> getParameters(){
        Object[][] params = new Object[][] {
            {"joão", "silva"},
            {"maria", "silva"},
        };
        return Arrays.asList(params);
    }

    @Test
    @Category(Funcional.class)
    public void testGetNome(){
        assertEquals(nome, pessoa.getNome());
    }

    @Test
    @Category(Funcional.class)
    public void testTypeNome(){
        assertTrue(pessoa.getNome() instanceof String);
    }

    @Test
    @Category(Funcional.class)
    public void testGetSobrenome(){
        assertEquals(sobrenome, pessoa.getSobrenome());
    }

    @Test
    @Category(Funcional.class)
    public void testTypeSobrenome(){
        assertTrue(pessoa.getSobrenome() instanceof String);
    }
}
