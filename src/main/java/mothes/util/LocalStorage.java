package mothes.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.scene.control.Alert;
import mothes.model.bean.Mariposa;
import mothes.model.bean.Usuario;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class LocalStorage {

    private static final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    private static final String path = System.getProperty("user.home") + File.separator + "mothes" + File.separator + "user.json";

    public static void saveUserInfo(Usuario user, Mariposa mariposa) throws IOException {
        File file = new File(path);
        file.getParentFile().mkdirs();

        LocalData localData = new LocalData(user, mariposa);

        try (FileWriter writer = new FileWriter((file))) {
            gson.toJson(localData, writer);
        } catch (IOException ex){
            new Alert(Alert.AlertType.ERROR,
                    "Erro ao salvar os dados do usuário.\nErro: " + ex.getMessage()
            ).showAndWait();
        }
    }

    public static Usuario loadUser() {
        File file = new File(path);

        if(!file.exists()) {
            return null;
        }

        try (FileReader reader = new FileReader(file)){
            LocalData localData = gson.fromJson(reader, LocalData.class);
            Usuario user = localData.getUsuario();
            reader.close();
            return user;
        } catch (IOException ex){
            new Alert(Alert.AlertType.ERROR,
                    "Erro ao carregar dados locais do usuario.\nErro: " + ex.getMessage()
            ).showAndWait();
            return null;
        }
    }

    public static Mariposa loadMariposa() {
        File file = new File(path);

        if(!file.exists()) {
            new Alert(Alert.AlertType.ERROR,
                    "Falha ao carregar os dados locais da Mariposa. Arquivo inexistente."
            ).showAndWait();
            return null;
        }

        try (FileReader reader = new FileReader(file)){
            LocalData localData = gson.fromJson(reader, LocalData.class);
            Mariposa mariposa = localData.getMariposa();
            reader.close();
            return mariposa;
        } catch (IOException ex){
            new Alert(Alert.AlertType.ERROR,
                    "Erro ao carregar dados locais da mariposa.\nErro: " + ex.getMessage()
            ).showAndWait();
            return null;
        }
    }

    public static boolean deleteUserData() {
        File file = new File(path);
        return file.delete();
    }

    public static boolean userExists() {
        return new File(path).exists();
    }

    public static String getPath() {
        return path;
    }

}