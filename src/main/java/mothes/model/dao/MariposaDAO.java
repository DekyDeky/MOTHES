package mothes.model.dao;

import javafx.scene.control.Alert;
import mothes.conexao.Conexao;
import mothes.model.bean.Mariposa;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MariposaDAO {

    public boolean createMariposa(Mariposa mariposa){
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;

        try {
            String query = "INSERT INTO mariposa(nome, estagio, qntNectarReal, idUsuario) VALUES (?, ?, ?, ?)";

            stmt = con.prepareStatement(query);
            stmt.setString(1, mariposa.getNome());
            stmt.setInt(2, mariposa.getEstagio());
            stmt.setDouble(3, mariposa.getQntNectar());
            stmt.setInt(4, mariposa.getIdUsuario());

            stmt.executeUpdate();

            new Alert(Alert.AlertType.INFORMATION,
                    "Mariposa cadastrada com sucesso!"
            ).showAndWait();

            return true;

        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,
                    "Falha ao cadastrar Mariposa.\nErro: " + ex.getMessage()
            ).showAndWait();
            return false;
        }finally {
            Conexao.fecharConexao(con, stmt);
        }
    }

}
