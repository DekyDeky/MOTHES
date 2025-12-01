package mothes.model.bean;

public class Recompensas {
    private Usuario userBeneficiado;
    private Mariposa maripBeneficiada;;

    public Recompensas(Usuario userBeneficiado, Mariposa maripBeneficiada) {
        this.userBeneficiado = userBeneficiado;
        this.maripBeneficiada = maripBeneficiada;
    }

    private int genRecompensa(boolean ehUser){
        if(ehUser){
            return (int) Math.round(5 * Math.pow(1.05, maripBeneficiada.getEstagio()));
        }else {
            return (int) Math.round(1 * Math.pow(1.05, maripBeneficiada.getEstagio()));
        }
    }

    public void recompensar(){
        userBeneficiado.setQntMoeda(userBeneficiado.getQntMoeda() + genRecompensa(true));
        maripBeneficiada.setQntNectar(maripBeneficiada.getQntNectar() + genRecompensa(false));
    }


}
