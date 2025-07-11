package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collection;

import exceptions.SemIrmaoException;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SemIrmaoExceptionTest{
    Pessoa pessoa, descendente;
    String nome, sobrenome,
           nomeDescendente, sobrenomeDescendente;
    Vinculo.Tipo tipoDescendencia;

    public SemIrmaoExceptionTest(String nome, String sobrenome, 
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

            {"pedro", "silva", "joão", "silva", Vinculo.Tipo.PAI},
            {"ana", "silva", "maria", "silva", Vinculo.Tipo.MAE},
        };
        return Arrays.asList(params);
    }
    
    @Test(expected=SemIrmaoException.class)
    @Category(Funcional.class)
    public void testGetIrmaos() throws SemIrmaoException{
        ArrayList<Vinculo.Tipo> tipos = new ArrayList<>(List.of(Vinculo.Tipo.IRMAO, Vinculo.Tipo.IRMA));
        ArrayList<Par<Pessoa, Vinculo.Tipo>> associados = pessoa.getAssociados(tipos);
        if(associados.isEmpty())
            throw new SemIrmaoException("Não há irmãos cadastrados.");
    }
}
