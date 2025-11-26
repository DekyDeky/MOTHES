package mothes.model.dao;

import com.mysql.cj.protocol.Resultset;
import javafx.scene.control.Alert;
import mothes.conexao.Conexao;
import mothes.model.bean.Estudo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EstudoDAO {

    public static void createEstudo(Estudo estudo, int idUsuario){
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;

        try {
            String query = "INSERT INTO estudo(nome, ciclos, tempoEstudo, tempoDescanso, idUsuario) VALUES (?, ?, ?, ?, ?)";

            stmt = con.prepareStatement(query);
            stmt.setString(1, estudo.getNome());
            stmt.setInt(2, estudo.getCiclos());
            stmt.setTime(3, estudo.getTempoEstudo());
            stmt.setTime(4, estudo.getTempoDescanso());
            stmt.setInt(5, idUsuario);

            stmt.executeUpdate();

            new Alert(Alert.AlertType.INFORMATION,
                    "Estudo cadastrado com sucesso!"
            ).showAndWait();

        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,
                    "Falha ao cadastrar estudo.\nErro: " + ex.getMessage()
            ).showAndWait();
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
    }


    public static List<Estudo> getEstudoByUsuarioID(int usuarioId) throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Estudo> estudos = new ArrayList<>();

        try {
            String query = "SELECT * FROM estudo WHERE idUsuario = ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, usuarioId);

            rs = stmt.executeQuery();

            while(rs.next()) {
                Estudo estudo = new Estudo();

                estudo.setIdEstudo(rs.getInt("idEstudo"));
                estudo.setNome(rs.getString("nome"));
                estudo.setCiclos(rs.getInt("ciclos"));
                estudo.setTempoEstudo(rs.getTime("tempoEstudo"));
                estudo.setTempoDescanso(rs.getTime("tempoDescanso"));

                estudos.add(estudo);

            }
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,
                    "Erro ao consultar o banco de dados.\nErro: " + ex.getMessage()
            ).showAndWait();
            estudos = null;
        } finally {
            Conexao.fecharConexao(con, stmt, rs);
        }

        return estudos;

    }

}
