package Models;

import java.time.LocalTime;
import java.util.ArrayList;

public class Escala {
    private int Dia;
    private String  CodigoPorto;
    private LocalTime HoraChegada;
    private LocalTime HoraPartida;
    private int NumExcursoes;

    private ArrayList<Excursao> Excursaos;

    public Escala(int dia, String codigoPorto, LocalTime horaChegada, LocalTime horaPartida, int numExcursoes) {
        Dia = dia;
        this.CodigoPorto = codigoPorto;
        HoraChegada = horaChegada;
        HoraPartida = horaPartida;
        NumExcursoes = numExcursoes;
        Excursaos = new ArrayList<>();
    }

    public Escala(int dia, String codigoPorto, int numExcursoes) {
        Dia = dia;
        this.CodigoPorto = codigoPorto;
        NumExcursoes = numExcursoes;
    }

    public int getDia() {
        return Dia;
    }

    public void setDia(int dia) {
        Dia = dia;
    }

    public String getCodigoPorto() {
        return CodigoPorto;
    }

    public void setCodigoPorto(String codigoPorto) {
        CodigoPorto = codigoPorto;
    }

    public LocalTime getHoraChegada() {
        return HoraChegada;
    }

    public void setHoraChegada(LocalTime horaChegada) {
        if (horaChegada != null){
            HoraChegada = horaChegada;
        }
    }

    public LocalTime getHoraPartida() {
        return HoraPartida;
    }

    public void setHoraPartida(LocalTime horaPartida) {
        if (horaPartida!=null){
            HoraPartida = horaPartida;
        }
    }

    public int getNumExcursoes() {
        return NumExcursoes;
    }

    public void setNumExcursoes(int numExcursoes) {
        NumExcursoes = numExcursoes;
    }

    public ArrayList<Excursao> getExcursaos() {
        return Excursaos;
    }

    public void setExcursaos(ArrayList<Excursao> excursaos) {
        Excursaos = excursaos;
    }

    public void addExcursao(Excursao excursao) {
        this.Excursaos.add(excursao);
    }
}
