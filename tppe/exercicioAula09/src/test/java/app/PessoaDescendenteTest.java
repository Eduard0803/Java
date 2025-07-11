package app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collection;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class PessoaDescendenteTest{
    Pessoa pessoa, descendente;
    String nome, sobrenome,
           nomeDescendente, sobrenomeDescendente;
    Vinculo.Tipo tipoDescendencia;

    public PessoaDescendenteTest(String nome, String sobrenome, 
                                String nomeDescendente, String sobrenomeDescendente, 
                                Vinculo.Tipo tipoDescendencia
    ){    
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.tipoDescendencia = tipoDescendencia;
    }

    @Before
    public void setup() {
        pessoa = new Pessoa(nome, sobrenome);
        descendente = new Pessoa(nomeDescendente, sobrenomeDescendente);
        pessoa.addAssociado(descendente, tipoDescendencia);
    }
    
    @Parameters
    public static Collection<Object[]> getParameters(){
        Object[][] params = new Object[][] {
            {"joão", "silva", "pedro", "silva", Vinculo.Tipo.FILHO},
            {"maria", "silva", "ana", "silva", Vinculo.Tipo.FILHA},

            {"maria", "silva", "leticia", "silva", Vinculo.Tipo.IRMA},
            {"maria", "silva", "carlos", "silva", Vinculo.Tipo.IRMAO},
            {"maria", "silva", "joão", "silva", Vinculo.Tipo.IRMAO},

            {"pedro", "silva", "leticia", "silva", Vinculo.Tipo.TIA},
            {"pedro", "silva", "carlos", "silva", Vinculo.Tipo.TIO},
            {"pedro", "silva", "maria", "silva", Vinculo.Tipo.TIA},

            {"ana", "silva", "leticia", "silva", Vinculo.Tipo.TIA},
            {"ana", "silva", "carlos", "silva", Vinculo.Tipo.TIO},
            {"ana", "silva", "joão", "silva", Vinculo.Tipo.TIO},
        };
        return Arrays.asList(params);
    }

    @Test
    @Category(Funcional.class)
    public void testGetAscendente(){
        assertEquals(nome, pessoa.getNome());
        assertEquals(sobrenome, pessoa.getSobrenome());
    }

    @Test
    @Category(Funcional.class)
    public void testGetDescendentes(){
        Pessoa descendenteBuffer = pessoa.getAssociados().get(0).getFirst();
        Vinculo.Tipo tipoDescendenciaBuffer = pessoa.getAssociados().get(0).getSecond();
        assertEquals(descendenteBuffer.getNome(), descendente.getNome());
        assertEquals(descendenteBuffer.getSobrenome(), descendente.getSobrenome());
        assertEquals(tipoDescendenciaBuffer, tipoDescendencia);
    }

    @Test
    @Category(Funcional.class)
    public void testGetIrmaos(){
        ArrayList<Vinculo.Tipo> tipos = new ArrayList<>(List.of(Vinculo.Tipo.IRMAO, Vinculo.Tipo.IRMA));
        ArrayList<Par<Pessoa, Vinculo.Tipo>> associados = pessoa.getAssociados(tipos);
        for(Par<Pessoa, Vinculo.Tipo> associado : associados)
            assertTrue(tipos.contains(associado.getSecond()));
    }

    @Test
    @Category(Funcional.class)
    public void testGetTios(){
        ArrayList<Vinculo.Tipo> tipos = new ArrayList<>(List.of(Vinculo.Tipo.TIO, Vinculo.Tipo.TIA));
        ArrayList<Par<Pessoa, Vinculo.Tipo>> associados = pessoa.getAssociados(tipos);
        for(Par<Pessoa, Vinculo.Tipo> associado : associados)
            assertTrue(tipos.contains(associado.getSecond()));
    }
}
