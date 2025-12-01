package mothes.util;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.util.Duration;
import mothes.model.bean.Mariposa;
import mothes.model.bean.Recompensas;
import mothes.model.bean.Usuario;

import java.util.Objects;

public class Temporizador {

    private int ciclos;
    private int secTrabalho;
    private int secDescanso;
    private Label timerPrincipalLabel;
    private Label timerNextLabel;
    private Timeline timeline;
    private boolean isPaused;
    private Usuario usuario;
    private Mariposa mariposa;
    private Recompensas recompensas;

    public Temporizador(int ciclos, int secTrabalho, int secDescanso, Label timerPrincipalLabel, Label timerNextLabel, Usuario usuario, Mariposa mariposa) {
        this.ciclos = ciclos;
        this.secTrabalho = secTrabalho;
        this.secDescanso = secDescanso;
        this.timerPrincipalLabel = timerPrincipalLabel;
        this.timerNextLabel = timerNextLabel;
        this.usuario = usuario;
        this.mariposa = mariposa;
        recompensas = new Recompensas(usuario, mariposa);
    }

    public void iniciarTemporizador(){
        executarCiclo(1);
    }

    private void executarCiclo(int cicloAtual){
        if(cicloAtual > ciclos){
            System.out.println("Pomodoro Finalizado");
            return;
        }

        System.out.println("Ciclo " + cicloAtual + " iniciado!");

        //Recursividade - Ao terminar pomodoro de trabalho -> iniciar pomodoro Descanso -> Mudar Ciclo
        setTemporizador(secTrabalho, "Trabalho", () ->
                setTemporizador(secDescanso, "Descanso", () ->
                        executarCiclo(cicloAtual + 1)));

    }

    private void setTemporizador(int segundos, String tipo, Runnable onFinish){

        IntegerProperty tempo = new SimpleIntegerProperty(segundos);

        if(Objects.equals(tipo, "Trabalho")){
            this.timerNextLabel.setText(Converter.IntTimeToStrTime(secDescanso));
        }else {
            this.timerNextLabel.setText(Converter.IntTimeToStrTime(secTrabalho));
        }

        timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {

                    if(tempo.get() >= 0){
                        this.timerPrincipalLabel.setText(Converter.IntTimeToStrTime(tempo.get()));
                        tempo.set(tempo.get() - 1);
                    }
                })
        );

        timeline.setCycleCount(segundos + 1);

        timeline.setOnFinished(event -> {

            if (tipo.equals("Trabalho")) {
                recompensas.recompensar();
                usuario.setTotalTrabalho(usuario.getTotalTrabalho() + segundos);
            } else {
                usuario.setTotalDescanco(usuario.getTotalDescanco() + segundos);
            }

            onFinish.run();
        });

        timeline.play();

    }

    public void pausar(){
        if(timeline != null){
            if(this.isPaused){
                timeline.play();
            } else {
                timeline.pause();
            }

            this.isPaused = !this.isPaused;
        }
    }

    public void parar(){
        if (timeline != null){
            this.timerPrincipalLabel.setText("00:00:00");
            this.timerNextLabel.setText("00:00:00");
            timeline.stop();
        }
    }
}
