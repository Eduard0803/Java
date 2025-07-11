package app;

public class Main {
    public static void main(String[] args) {
        Pessoa pessoa1 = new Pessoa("João", "Silva");

        System.out.println("Nome: " + pessoa1.getNome());
        System.out.println("Sobrenome: " + pessoa1.getSobrenome());
    }
}
