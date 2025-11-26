package mothes.model.bean;

import java.sql.Time;

public class Estudo {

    private int idEstudo;
    private String nome;
    private int ciclos;
    private Time tempoEstudo;
    private Time tempoDescanso;

    public Estudo() {
    }

    public Estudo(String nome, int ciclos, Time tempoEstudo, Time tempoDescanso) {
        this.nome = nome;
        this.ciclos = ciclos;
        this.tempoEstudo = tempoEstudo;
        this.tempoDescanso = tempoDescanso;
    }

    public int getIdEstudo() {
        return idEstudo;
    }

    public void setIdEstudo(int idEstudo) {
        this.idEstudo = idEstudo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCiclos() {
        return ciclos;
    }

    public void setCiclos(int ciclos) {
        this.ciclos = ciclos;
    }

    public Time getTempoEstudo() {
        return tempoEstudo;
    }

    public void setTempoEstudo(Time tempoEstudo) {
        this.tempoEstudo = tempoEstudo;
    }

    public Time getTempoDescanso() {
        return tempoDescanso;
    }

    public void setTempoDescanso(Time tempoDescanso) {
        this.tempoDescanso = tempoDescanso;
    }
}
