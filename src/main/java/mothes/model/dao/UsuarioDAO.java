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
            String query = "INSERT INTO usuarios(apelido, email, senhaHash) VALUES (?, ?, ?)";

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
                
                Usuario user = new Usuario(idUsuario, email, senha);
                
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

}
