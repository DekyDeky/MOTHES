package mothes.model.dao;

import javafx.scene.control.Alert;
import mothes.conexao.Conexao;
import mothes.model.bean.Acessorio;
import mothes.model.bean.Cosmetico;
import mothes.model.bean.Decoracao;
import mothes.model.bean.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class CosmeticoDAO {

    public static List<Acessorio> getAcessorios(Usuario usuario) throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Acessorio> acessorios = new ArrayList<>();

        try {
            String query = "SELECT * FROM cosmetico WHERE uso = 'acessorio'";
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while(rs.next()) {
                Acessorio acessorio = new Acessorio();

                acessorio.setIdCosmetico(rs.getInt("idCosmetico"));
                acessorio.setNome(rs.getString("nome"));
                acessorio.setImagemUrl(rs.getString("image"));
                acessorio.setPreco(rs.getInt("preco"));
                acessorio.setTipo(rs.getString("tipo"));

                acessorios.add(acessorio);
            }

            rs.close();
            stmt.close();

            String query2 = "SELECT cosmetico_idcosmetico, equipado FROM cosmetico_compra WHERE usuario_idUsuario = ?";
            stmt = con.prepareStatement(query2);
            stmt.setInt(1, usuario.getId());
            rs = stmt.executeQuery();

            Map<Integer, Boolean> equipadoMap = new HashMap<>();
            while(rs.next()) {
                equipadoMap.put(rs.getInt("cosmetico_idcosmetico"), rs.getInt("equipado") != 0);
            }

            for (Acessorio a : acessorios) {
                if (equipadoMap.containsKey(a.getIdCosmetico())) {
                    a.setUsando(equipadoMap.get(a.getIdCosmetico()));
                    a.setComprado(true);
                }
            }



        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,
                    "Erro ao consultar o banco de dados.\nErro: " + ex.getMessage()
            ).showAndWait();
            acessorios = null;
        } finally {
            Conexao.fecharConexao(con, stmt, rs);
        }

        return acessorios;
    }

    public static List<Decoracao> getDecoracoes(Usuario usuario) throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Decoracao> decoracoes = new ArrayList<>();

        try {
            String query = "SELECT * FROM cosmetico WHERE uso = 'decoracao'";
            stmt = con.prepareStatement(query);
            rs = stmt.executeQuery();

            while(rs.next()) {
                Decoracao decoracao = new Decoracao();

                decoracao.setIdCosmetico(rs.getInt("idCosmetico"));
                decoracao.setNome(rs.getString("nome"));
                decoracao.setImagemUrl(rs.getString("image"));
                decoracao.setPreco(rs.getInt("preco"));
                decoracao.setTipoDeco(rs.getString("tipo"));

                decoracoes.add(decoracao);
            }

            rs.close();
            stmt.close();

            String query2 = "SELECT cosmetico_idcosmetico, equipado FROM cosmetico_compra WHERE usuario_idUsuario = ?";
            stmt = con.prepareStatement(query2);
            stmt.setInt(1, usuario.getId());
            rs = stmt.executeQuery();

            Map<Integer, Boolean> equipadoMap = new HashMap<>();
            while(rs.next()) {
                equipadoMap.put(rs.getInt("cosmetico_idcosmetico"), rs.getInt("equipado") != 0);
            }

            for (Decoracao d : decoracoes) {
                if (equipadoMap.containsKey(d.getIdCosmetico())) {
                    d.setUsando(equipadoMap.get(d.getIdCosmetico()));
                    d.setComprado(true);
                }
            }



        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,
                    "Erro ao consultar o banco de dados.\nErro: " + ex.getMessage()
            ).showAndWait();
            decoracoes = null;
        } finally {
            Conexao.fecharConexao(con, stmt, rs);
        }

        return decoracoes;
    }

    public static void saveBuyedCosmetic(Cosmetico cosmetico, Usuario usuario){
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;

        try {
            String query = "INSERT INTO cosmetico_compra(cosmetico_idcosmetico, usuario_idUsuario, equipado) VALUES (?, ?, 0)";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, cosmetico.getIdCosmetico());
            stmt.setInt(2, usuario.getId());

            stmt.executeUpdate();
            UsuarioDAO.updateUser(usuario);

        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,
                    "Falha ao cadastrar nova compra.\nErro: " + ex.getMessage()
            ).showAndWait();
        } finally {
            Conexao.fecharConexao(con, stmt);
        }

    }

//    public static void saveEquippedItems(Acessorio acessorio, Usuario usuario)


}
