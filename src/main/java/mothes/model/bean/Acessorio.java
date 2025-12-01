package mothes.model.bean;

public class Acessorio extends Cosmetico {
    private String tipo;

    public Acessorio(){}

    public Acessorio(String tipo) {
        this.tipo = tipo;
    }

    public Acessorio(int idCosmetico, String nome, String imagemUrl, int preco, boolean usando, boolean comprado, String tipo) {
        super(idCosmetico, nome, imagemUrl, preco, usando, comprado);
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
