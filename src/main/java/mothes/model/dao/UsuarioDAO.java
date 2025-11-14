package mothes.model.dao;

import javafx.scene.control.Alert;
import mothes.conexao.Conexao;
import mothes.model.bean.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UsuarioDAO {

    public void createUser(Usuario user){
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;

        try {
            String query = "INSERT INTO usuario(apelido, email, senhaHash) VALUES (?, ?, ?)";

            stmt = con.prepareStatement(query);
            stmt.setString(1, user.getApelido());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getSenha());

            stmt.executeUpdate();

            new Alert(Alert.AlertType.INFORMATION,
                    "Tipo de Produto cadastrado com sucesso!"
            ).showAndWait();

        }catch (SQLException ex){
            new Alert(Alert.AlertType.ERROR,
                    "Falha ao cadastrar Tipo de Produto.\nErro: " + ex.getMessage()
            ).showAndWait();
        }finally {
            Conexao.fecharConexao(con, stmt);
        }
    }

}
