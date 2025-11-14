package mothes.model.bean;

public class Usuario {
    private int id;
    private String apelido;
    private String email;
    private String senha;
    private int qntMoeda;

    public Usuario(int id, String apelido, String email, String senha, int qntMoeda) {
        this.id = id;
        this.apelido = apelido;
        this.email = email;
        this.senha = senha;
        this.qntMoeda = qntMoeda;
    }

    public int getId() {
        return id;
    }

    public String getApelido() {
        return apelido;
    }

    public String getEmail() {
        return email;
    }

    public String getSenha() {
        return senha;
    }

    public int getQntMoeda() {
        return qntMoeda;
    }

    public void setQntMoeda(int qntMoeda) {
        this.qntMoeda = qntMoeda;
    }
}
