package mothes.model.bean;

public class Mariposa {
    private int idMariposa;
    private String nome;
    private int estagio;
    private double qntNectar;
    private int idUsuario;

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
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
