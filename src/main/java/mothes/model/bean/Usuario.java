package mothes.model.bean;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;

import java.util.Base64;
import java.util.List;

public class Usuario {
    private int id;
    private String apelido;
    private String email;
    private String senha;
    private int qntMoeda;
    private String salt64;
    private long totalTrabalho;
    private long totalDescanco;

    private transient List<Label> moneyLabels;

    public Usuario(){}

    public Usuario(int id, String apelido, String email, String senha, int qntMoeda, String salt) {
        this.id = id;
        this.apelido = apelido;
        this.email = email;
        this.senha = senha;
        this.qntMoeda = qntMoeda;
        this.salt64 = salt;
    }
    
    public Usuario(int id, String email, String senha, String salt) {
        this.id = id;
        this.email = email;
        this.senha = senha;
        this.salt64 = salt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getApelido() {
        return apelido;
    }

    public void setApelido(String apelido) {
        this.apelido = apelido;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public int getQntMoeda() {
        return qntMoeda;
    }

    public void setQntMoeda(int qntMoeda) {
        this.qntMoeda = qntMoeda;
        if(this.moneyLabels != null){
            for(Label mL : this.moneyLabels){
                mL.setText("$ " + this.qntMoeda);
            }
        }
    }

    public String getSalt64() {
        return salt64;
    }

    public void setSalt64(String salt64) {
        this.salt64 = salt64;
    }

    public long getTotalTrabalho() {
        return totalTrabalho;
    }

    public void setTotalTrabalho(long totalTrabalho) {
        this.totalTrabalho = totalTrabalho;
    }

    public long getTotalDescanco() {
        return totalDescanco;
    }

    public void setTotalDescanco(long totalDescanco) {
        this.totalDescanco = totalDescanco;
    }

    public void setMoneyLabels(List<Label> moneyLabels) {
        this.moneyLabels = moneyLabels;
    }
}
