public abstract class Parking{
    /*
        Falta Fazer
            - Colocar o horario de funcionamento
            - Receber os horarios de funcionamento no metodo construtor
    */
    // Atributos
    private String name;
    private int capacidade, vagaOcupadas, retornoContratante, nAcessos;
    private float valorFracao, valorHora, valorDiurno, 
            valorNoturno, valorMensal, valorEvento, valorArrecadado;

    // Metodo Construtor
    private Parking(){}
    public Parking(String name, int capacidade, float valorFracao, 
                    float valorHora, float valorDiurno, float valorNoturno, 
                    float valorMensal, float valorEvento, int retornoContratante)
    {
        this.name = name;
        this.capacidade = capacidade;
        this.valorFracao = valorFracao;
        this.valorHora = valorHora;
        this.valorDiurno = valorDiurno;
        this.valorNoturno = valorNoturno;
        this.valorMensal = valorMensal;
        this.valorEvento = valorEvento;
        this.retornoContratante = retornoContratante;
        this.valorArrecadado = 0;
        this.nAcessos = 0;
    }
    
    // Metodos 'get'
    public int getTotalVagas(){return capacidade;}
    public int getVagasOcupadas(){return vagaOcupadas;}
    public int getVagasDisponiveis(){return capacidade-vagaOcupadas;}
    public boolean temVaga(){return vagaOcupadas < capacidade;}
    public float getValorRetorno(){return valorArrecadado*retornoContratante/100;}
}
