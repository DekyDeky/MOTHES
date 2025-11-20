package mothes.util;

import mothes.model.bean.Mariposa;
import mothes.model.bean.Usuario;

public class LocalData {
    private Usuario usuario;
    private Mariposa mariposa;

    public LocalData(Usuario usuario, Mariposa mariposa) {
        this.usuario = usuario;
        this.mariposa = mariposa;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Mariposa getMariposa() {
        return mariposa;
    }

    public void setMariposa(Mariposa mariposa) {
        this.mariposa = mariposa;
    }
}
