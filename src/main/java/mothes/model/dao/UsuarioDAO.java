package mothes.model.dao;

import javafx.scene.control.Alert;
import mothes.conexao.Conexao;
import mothes.model.bean.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioDAO {

    public int createUser(Usuario user){
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;

        try {
            String query = "INSERT INTO usuario(apelido, email, senhaHash) VALUES (?, ?, ?)";

            stmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getApelido());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getSenha());

            stmt.executeUpdate();

            new Alert(Alert.AlertType.INFORMATION,
                    "Usuário cadastrado com sucesso!"
            ).showAndWait();

            ResultSet rs = stmt.getGeneratedKeys();
            int idGerado = -1;

            if(rs.next()) {
                idGerado = rs.getInt(1);
            }

            return idGerado;

        }catch (SQLException ex){
            new Alert(Alert.AlertType.ERROR,
                    "Falha ao cadastrar Usuário.\nErro: " + ex.getMessage()
            ).showAndWait();

            return 0;
        }finally {
            Conexao.fecharConexao(con, stmt);
        }
    }

}
