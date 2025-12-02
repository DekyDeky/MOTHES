package mothes.util;

import javafx.scene.control.Label;
import mothes.model.bean.Estudo;

import java.util.Objects;

public class Validar {

    public static boolean checkEmptyTime(String h, String m, String s){
        return  (h == null || h.isEmpty()) &&
                (m == null || m.isEmpty()) &&
                (s == null || s.isEmpty());
    }

    public static boolean isNumeric(String str){
        if (str == null || str.isEmpty()) return true; // agora "" é considerado numérico
        String regex = "[-+]?\\d+(\\.\\d+)?";
        return str.matches(regex);
    }

    public static boolean isInt(String str){
        try {
            int parsedInt = Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e){
            return false;
        }
    }

    public static String checkIfNull(String nullValue){
        if(nullValue.isEmpty()){
            return "0";
        }

        return nullValue;
    }

    public static String validateNumberStrFields(
            String numberStr
    ) {

        if(numberStr.isEmpty()){
            numberStr = "0";
        }

        if(!isInt(numberStr)){
            return null;
        }

        return numberStr;
    }

    public static Estudo validateNewStudy(
            String[] numberStrFields,
            String estudoNome,
            Label timerErroLabel
    ){
        boolean haveErrors = false;
        String errorLabel = "";

        for(int i = 0; i < numberStrFields.length; i++) {
            if (numberStrFields[i].isEmpty()) {
                numberStrFields[i] = "0"; // Altera diretamente o array
            }

            if (!Validar.isInt(numberStrFields[i])) {
                haveErrors = true;
                errorLabel = "Campo numérico inválido";
                break;
            }
        }

        if(estudoNome.isEmpty()){
            if(haveErrors){
                errorLabel = errorLabel + ", o título é obrigatório";
            }else {
                haveErrors = true;
                errorLabel = "O título é obrigatório";
            }
        }

        if(numberStrFields[6].isEmpty()){
            if(haveErrors){
                errorLabel = errorLabel + ", os ciclos são obrigatórios";
            }else {
                haveErrors = true;
                errorLabel = "Os ciclos são obrigatórios";
            }
        }

        if(Integer.parseInt(numberStrFields[6]) <= 0){
            if(haveErrors){
                errorLabel = errorLabel + ", a quant. de ciclos deve ser maior que 0";
            }else {
                haveErrors = true;
                errorLabel = "A quant. de ciclos deve ser maior que 0";
            }
        }

        if(haveErrors) {
            timerErroLabel.setText(errorLabel + "!");
            return null;
        }

        Estudo newEstudo = new Estudo(
                estudoNome,
                Integer.parseInt(numberStrFields[6]),
                Converter.valuesToSeconds(
                        Integer.parseInt(numberStrFields[0]),
                        Integer.parseInt(numberStrFields[1]),
                        Integer.parseInt(numberStrFields[2])
                ),
                Converter.valuesToSeconds(
                        Integer.parseInt(numberStrFields[3]),
                        Integer.parseInt(numberStrFields[4]),
                        Integer.parseInt(numberStrFields[5])
                )
        );

        return newEstudo;
    }

    public static String emailIsValid(String email){
        if(email.isEmpty()){
            return "";
        }

        if(!email.contains("@")){
            return "Email inválido!";
        }

        return "";
    }

    public static String oldPassworldIsValid(String oldPassword){
        if(oldPassword.isEmpty()){
            return "Insira sua senha atual!";
        }

        return "";
    }

    public static String passwordIsValid(String password, String confirmPassword){
        if (!password.equals(confirmPassword)) {
            return "As senhas não são iguais!";
        }

        if(!password.isEmpty() && confirmPassword.isEmpty()){
            return "A senha está vazia!";
        }

        if(password.isEmpty() && !confirmPassword.isEmpty()){
            return "A senha está vazia!";
        }

        return "";
    }

    public static String apelidoIsValid(String apelido){
        if(apelido.length() > 45){
            return "Apelido deve ter menos de 45 caracteres";
        }

        return "";
    }


}
