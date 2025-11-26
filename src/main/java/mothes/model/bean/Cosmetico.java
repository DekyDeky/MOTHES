package mothes.model.bean;

public class Cosmetico {

    private int idCosmetico;
    private String nome;
    private String ImagemUrl;
    private int preco;
    private boolean usando;
    private boolean comprado;

    public Cosmetico() {
    }

    public Cosmetico(int idCosmetico, String nome, String imagemUrl, int preco, boolean usando, boolean comprado) {
        this.idCosmetico = idCosmetico;
        this.nome = nome;
        ImagemUrl = imagemUrl;
        this.preco = preco;
        this.usando = usando;
        this.comprado = comprado;
    }

    public int getIdCosmetico() {
        return idCosmetico;
    }

    public void setIdCosmetico(int idCosmetico) {
        this.idCosmetico = idCosmetico;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getImagemUrl() {
        return ImagemUrl;
    }

    public void setImagemUrl(String imagemUrl) {
        ImagemUrl = imagemUrl;
    }

    public int getPreco() {
        return preco;
    }

    public void setPreco(int preco) {
        this.preco = preco;
    }

    public boolean isUsando() {
        return usando;
    }

    public void setUsando(boolean usando) {
        this.usando = usando;
    }

    public boolean isComprado() {
        return comprado;
    }

    public void setComprado(boolean comprado) {
        this.comprado = comprado;
    }
}
