package mothes.model.dao;

import javafx.scene.control.Alert;
import mothes.conexao.Conexao;
import mothes.model.bean.Mariposa;
import mothes.model.bean.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    public static Mariposa getMariposaByUsuarioID(int UsuarioID){
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        Mariposa mariposa = new Mariposa();

        try {
            String query = "SELECT * FROM mariposa WHERE idUsuario = ?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, String.valueOf(UsuarioID));

            rs = stmt.executeQuery();

            if(!rs.next()){
                return null;
            }

            mariposa.setIdMariposa(rs.getInt("idMariposa"));
            mariposa.setNome(rs.getString("nome"));
            mariposa.setEstagio(rs.getInt("estagio"));
            mariposa.setQntNectar(rs.getDouble("qntNectarReal"));
            mariposa.setIdUsuario(rs.getInt("idUsuario"));

        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,
                    "Erro ao consultar o banco de dados.\nErro: " + ex.getMessage()
            ).showAndWait();
            mariposa = null;
        } finally {
            Conexao.fecharConexao(con, stmt, rs);
        }

        return mariposa;
    }

    public static boolean updateMariposa(Mariposa mariposa) {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;

        try {
            String query = "UPDATE mariposa SET estagio = ?, qntNectarReal = ? WHERE idMariposa = ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, mariposa.getEstagio());
            stmt.setDouble(2, mariposa.getQntNectar());
            stmt.setInt(3, mariposa.getIdMariposa());

            stmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,
                    "Erro ao atualizar banco de dados.\nErro: " + ex.getMessage()
            ).showAndWait();
            return false;
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
    }

    public static boolean changeMariposaName(String newName, int idMariposa){
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;

        try {
            String query = "UPDATE mariposa SET nome = ? WHERE idMariposa = ?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, newName);
            stmt.setInt(2, idMariposa);

            stmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,
                    "Erro ao editar nome da Mariposa no banco de dados.\nErro: " + ex.getMessage()
            ).showAndWait();
            return false;
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
    }
}
