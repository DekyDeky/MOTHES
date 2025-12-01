package mothes.model.bean;

import javafx.scene.control.Label;

public class Mariposa {
    private int idMariposa;
    private String nome;
    private int estagio;
    private double qntNectar;
    private int idUsuario;

    private double precoEstagio = 1;
    private transient Label nectarQuantityLabel;

    public Mariposa(){}

    public Mariposa(int idMariposa, String nome, int estagio, double qntNectar, int idUsuario) {
        this.idMariposa = idMariposa;
        this.nome = nome;
        this.estagio = estagio;
        this.qntNectar = qntNectar;
        this.idUsuario = idUsuario;
    }

    public int getIdMariposa() {
        return idMariposa;
    }

    public void setIdMariposa(int idMariposa) {
        this.idMariposa = idMariposa;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getEstagio() {
        return estagio;
    }

    public void setEstagio(int estagio) {
        this.estagio = estagio;
    }

    public double getQntNectar() {
        return qntNectar;
    }

    public void setQntNectar(double qntNectar) {
        this.qntNectar = qntNectar;
        if(nectarQuantityLabel != null){
            this.nectarQuantityLabel.setText("Quantidade de Nectar Atual: " + this.qntNectar);
        }
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public void setNectarQuantityLabel(Label nectarQuantityLabel) {
        this.nectarQuantityLabel = nectarQuantityLabel;
    }

    public Label getNectarQuantityLabel() {
        return nectarQuantityLabel;
    }

    public double getPrecoEstagio() {
        return precoEstagio;
    }

    public void setPrecoEstagio(double precoEstagio) {
        this.precoEstagio = precoEstagio;
    }

    public void alimentar(Label nectarQuantityLabel, Label nextStageLabel, Label actualStageLabel, Label errorFeedLabel){
        if(qntNectar > precoEstagio){
            this.setQntNectar(this.qntNectar - this.precoEstagio);
            this.setEstagio(this.getEstagio() + 1);
            this.setPrecoEstagio(Math.ceil(this.precoEstagio * Math.pow(1.5, this.estagio)));


            actualStageLabel.setText("Estágio Atual: " + this.estagio);
            nextStageLabel.setText("Quantidade Necessária para o próximo nível: " + this.precoEstagio);
            nectarQuantityLabel.setText("Quantidade de Nectar Atual: " + this.qntNectar);
        }else {
            errorFeedLabel.setText("Você não tem nectar o suficiente!");
        }
    }
}
