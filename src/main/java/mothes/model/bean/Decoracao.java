package mothes.model.bean;

public class Decoracao extends Cosmetico {

    private String tipoDeco;

    public Decoracao() {
    }

    public Decoracao(int idCosmetico, String nome, String imagemUrl, int preco, boolean usando, boolean comprado, String tipoDeco) {
        super(idCosmetico, nome, imagemUrl, preco, usando, comprado);
        this.tipoDeco = tipoDeco;
    }

    public String getTipoDeco() {
        return tipoDeco;
    }

    public void setTipoDeco(String tipoDeco) {
        this.tipoDeco = tipoDeco;
    }
}
