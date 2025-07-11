package app;


import java.util.ArrayList;
import exceptions.SemIrmaoException;


public class Pessoa{
    private String nome;
    private String sobrenome;
    private ArrayList<Par<Pessoa, Vinculo.Tipo>> associados;
    private Pessoa conjuge;

    public Pessoa(String nome, String sobrenome) {
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.associados = new ArrayList<Par<Pessoa, Vinculo.Tipo>>();
    }

    public String getNome(){return nome;}
    public String getSobrenome(){return sobrenome;}
    
    public ArrayList<Par<Pessoa, Vinculo.Tipo>> getAssociados(){return associados;}
    
    public void addAssociado(Pessoa irmao, Vinculo.Tipo tipo){
        this.associados.add(new Par<>(irmao, tipo));
    }

    public ArrayList<Pessoa> getIrmaos() throws SemIrmaoException{
        ArrayList<Pessoa> irmaos = new ArrayList<>();
        for (Par<Pessoa, Vinculo.Tipo> par : associados) {
            if (par.getSecond() == Vinculo.Tipo.IRMAO || par.getSecond() == Vinculo.Tipo.IRMA) {
                irmaos.add(par.getFirst());
            }
        }
        if(irmaos.isEmpty())
            throw new SemIrmaoException("Não há irmãos cadastrados.");
        return irmaos;
    }


    public ArrayList<Par<Pessoa, Vinculo.Tipo>> getAssociados(ArrayList<Vinculo.Tipo> tipos){
        ArrayList<Par<Pessoa, Vinculo.Tipo>> associadosBuffer = new ArrayList<>();
        for(Par<Pessoa, Vinculo.Tipo> par : associados)
            if(tipos.contains(par.getSecond()))
                associadosBuffer.add(par);
        return associadosBuffer;
    }

    public void setConjuge(Pessoa conjuge){this.conjuge = conjuge;}
    public Pessoa getConjuge(){return conjuge;}
}
