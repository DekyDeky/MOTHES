package mothes.model.bean;

import java.util.Base64;

public class Usuario {
    private int id;
    private String apelido;
    private String email;
    private String senha;
    private int qntMoeda;
    private String salt64;

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
    }

    public String getSalt64() {
        return salt64;
    }

    public void setSalt64(String salt64) {
        this.salt64 = salt64;
    }
}
