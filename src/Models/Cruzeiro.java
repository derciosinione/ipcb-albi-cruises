package Models;

import java.time.LocalDate;
import java.util.ArrayList;

public class Cruzeiro {
    private int Id;
    private String Nome;
    private LocalDate DataPartida;
    private int NumeroCamarotes;
    private long Preco;
    private int NumeroEscalas;

    public ArrayList<Escala> Escalas = new ArrayList<>();
    public ArrayList<Experiencia> Experiencias = new ArrayList<>();

    public Cruzeiro(int id, String nome, LocalDate dataPartida, int numeroCamarotes, long preco, int numeroEscalas) {
        Id = id;
        Nome = nome;
        DataPartida = dataPartida;
        NumeroCamarotes = numeroCamarotes;
        Preco = preco;
        NumeroEscalas = numeroEscalas;
    }

    public Cruzeiro() {
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public LocalDate getDataPartida() {
        return DataPartida;
    }

    public void setDataPartida(LocalDate dataPartida) {
        DataPartida = dataPartida;
    }

    public int getNumeroCamarotes() {
        return NumeroCamarotes;
    }

    public void setNumeroCamarotes(int numeroCamarotes) {
        NumeroCamarotes = numeroCamarotes;
    }

    public long getPreco() {
        return Preco;
    }

    public void setPreco(long preco) {
        Preco = preco;
    }

    public int getNumeroEscalas() {
        return NumeroEscalas;
    }

    public void setNumeroEscalas(int numeroEscalas) {
        NumeroEscalas = numeroEscalas;
    }

    public ArrayList<Escala> getEscalas() {
        return Escalas;
    }

    public void setEscalas(ArrayList<Escala> escalas) {
        this.Escalas = escalas;
    }

    public ArrayList<Experiencia> getExperiencias() {
        return Experiencias;
    }

    public void setExperiencias(ArrayList<Experiencia> experiencias) {
        Experiencias = experiencias;
    }
}
