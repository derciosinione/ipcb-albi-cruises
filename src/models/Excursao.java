package models;

public class Excursao {
    private String Nome;
    private long  Preco;
    private int NumLugares;

    public Excursao( String nome, long preco,int numLugares) {
        Nome = nome;
        Preco = preco;
        NumLugares = numLugares;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public long getPreco() {
        return Preco;
    }

    public void setPreco(long preco) {
        Preco = preco;
    }

    public int getNumLugares() {
        return NumLugares;
    }

    public void setNumLugares(int numLugares) {
        NumLugares = numLugares;
    }
}
