package mothes.model.bean;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import mothes.model.dao.EstudoDAO;
import mothes.util.Converter;
import mothes.util.Validar;

import java.sql.Time;
import java.text.ParseException;
import java.util.List;

public class Estudo {

    private int idEstudo;
    private String nome;
    private int ciclos;
    private int tempoEstudo;
    private int tempoDescanso;

    public Estudo() {
    }

    public Estudo(String nome, int ciclos, int tempoEstudo, int tempoDescanso) {
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

    public int getTempoEstudo() {
        return tempoEstudo;
    }

    public void setTempoEstudo(int tempoEstudo) {
        this.tempoEstudo = tempoEstudo;
    }

    public int getTempoDescanso() {
        return tempoDescanso;
    }

    public void setTempoDescanso(int tempoDescanso) {
        this.tempoDescanso = tempoDescanso;
    }

    public void createEstudo(
            Usuario sessaoAtual,
            List<Estudo> estudos,
            ObservableList<String> options,
            ComboBox<String> studiesComboBox
    ) throws ParseException {

        if(EstudoDAO.createEstudo(this, sessaoAtual.getId())){
            estudos.add(this);
            options.add(this.getNome());
            studiesComboBox.setItems(options);
        }

    }

    public void editEstudo(String nome, int ciclos, int tempoEstudo, int tempoDescanso) {
        this.nome = nome;
        this.ciclos = ciclos;
        this.tempoEstudo = tempoEstudo;
        this.tempoDescanso = tempoDescanso;
    }
}
