package mothes.model.dao;

import javafx.scene.control.Alert;
import mothes.conexao.Conexao;
import mothes.model.bean.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UsuarioDAO {

    public int createUser(Usuario user){
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;

        try {
            String query = "INSERT INTO usuarios(apelido, email, senhaHash, salt) VALUES (?, ?, ?, ?)";

            stmt = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            stmt.setString(1, user.getApelido());
            stmt.setString(2, user.getEmail());
            stmt.setString(3, user.getSenha());
            stmt.setString(4, user.getSalt64());

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
    
    public ArrayList<Usuario> readUsers() throws SQLException {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        ArrayList<Usuario> listaUsuarios = new ArrayList();
        
        try {
            String query = "SELECT * FROM usuarios";
            stmt = con.prepareStatement(query);
            
            rs = stmt.executeQuery();
            
            while(rs.next()) {
                int idUsuario = rs.getInt("idUsuario");
                String email = rs.getString("email");
                String senha = rs.getString("senhaHash");
                String salt64 = rs.getString("salt");
                
                Usuario user = new Usuario(idUsuario, email, senha, salt64);
                
                listaUsuarios.add(user);
               
                
            }
        }catch(SQLException ex){
            new Alert(Alert.AlertType.ERROR,
                    "Falha ao cadastrar Usuário.\nErro: " + ex.getMessage()
            ).showAndWait();
            return null;
        }finally {
            Conexao.fecharConexao(con, stmt, rs);
        }
        
        return listaUsuarios;
        
    }

    public Usuario getUserByEmail(String email){
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        Usuario userInfo = new Usuario();

        try {
            String query = "SELECT * FROM usuarios WHERE email = ?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, email);

            rs = stmt.executeQuery();

            if(!rs.next()){
                return null;
            }

            userInfo.setId(rs.getInt("idUsuario"));
            userInfo.setApelido(rs.getString("apelido"));
            userInfo.setEmail(rs.getString("email"));
            userInfo.setSenha(rs.getString("senhaHash"));
            userInfo.setQntMoeda(rs.getInt("qntMoeda"));
            userInfo.setSalt64(rs.getString("salt"));

        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,
                    "Erro ao consultar o banco de dados.\nErro: " + ex.getMessage()
            ).showAndWait();
            userInfo = null;
        }finally {
            Conexao.fecharConexao(con, stmt, rs);
        }

        return userInfo;
    }

    public static boolean updateUser(Usuario usuario) {
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;

        try {
            String query = "UPDATE usuarios SET qntMoeda = ? WHERE idUsuario = ?";
            stmt = con.prepareStatement(query);
            stmt.setInt(1, usuario.getQntMoeda());
            stmt.setInt(2, usuario.getId());

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

    public static boolean editUser(Usuario usuario){
        Connection con = Conexao.getConexao();
        PreparedStatement stmt = null;

        try {
            String query = "UPDATE usuarios SET apelido = ?, email = ?, senhaHash = ?, salt = ? WHERE idUsuario = ?";
            stmt = con.prepareStatement(query);
            stmt.setString(1, usuario.getApelido());
            stmt.setString(2, usuario.getEmail());
            stmt.setString(3, usuario.getSenha());
            stmt.setString(4, usuario.getSalt64());
            stmt.setInt(5, usuario.getId());

            stmt.executeUpdate();

            return true;
        } catch (SQLException ex) {
            new Alert(Alert.AlertType.ERROR,
                    "Erro ao editar usuário no banco de dados.\nErro: " + ex.getMessage()
            ).showAndWait();
            return false;
        } finally {
            Conexao.fecharConexao(con, stmt);
        }
    }

}
