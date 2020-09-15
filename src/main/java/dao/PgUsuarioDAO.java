package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Usuario;

public class PgUsuarioDAO implements UsuarioDAO {
    
    private final Connection connection;

    private static final String CREATE_QUERY =
                                "INSERT INTO trabalho.usuario(email, senha, nome, idade, sexo, pais) " +
                                "VALUES(?, md5(?), ?, ?, ?, ?);";

    private static final String READ_QUERY =
                                "SELECT email, senha, nome, idade, sexo, pais " +
                                "FROM trabalho.usuario " +
                                "WHERE email = ?;";

    private static final String UPDATE_QUERY =
                                "UPDATE trabalho.usuario " +
                                "SET nome = ?, idade = ?, sexo = ?, pais = ? " +
                                "WHERE email = ?;";

    private static final String UPDATE_WITH_PASSWORD_QUERY =
                                "UPDATE trabalho.usuario " +
                                "SET  senha = md5(?), nome = ?, idade = ?, sexo = ?, pais = ? " +
                                "WHERE email = ?;";

    private static final String DELETE_QUERY =
                                "DELETE FROM trabalho.usuario " +
                                "WHERE email = ?;";

    private static final String ALL_QUERY =
                                "SELECT email, nome " +
                                "FROM trabalho.usuario " +
                                "ORDER BY email;";

    private static final String AUTHENTICATE_QUERY =
                                "SELECT nome, idade, sexo, pais " +
                                "FROM trabalho.usuario " +
                                "WHERE email = ? AND senha = md5(?);";

    public PgUsuarioDAO(Connection connection) {
        this.connection = connection;
    }
                    
    @Override
    public void create(Usuario usuario) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, usuario.getEmail());
            statement.setString(2, usuario.getSenha());
            statement.setString(3, usuario.getNome());
            statement.setInt(4, usuario.getIdade());
            statement.setString(5, usuario.getSexo());
            statement.setString(6, usuario.getPais());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgUsuarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().contains("uq_user_login")) {
                throw new SQLException("Erro ao inserir usuário: e-mail já existente.");
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao inserir usuário: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao inserir usuário.");
            }
        }
    }

    @Override
    public Usuario read(String email) throws SQLException {
        Usuario usuario = new Usuario();

        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setString(1, email);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    usuario.setEmail(result.getString("email"));
                    usuario.setSenha(result.getString("senha"));
                    usuario.setNome(result.getString("nome"));
                    usuario.setIdade(result.getInt("idade"));
                    usuario.setSexo(result.getString("sexo"));
                    usuario.setPais(result.getString("pais"));
                } else {
                    throw new SQLException("Erro ao visualizar: usuário não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUsuarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            
            if (ex.getMessage().equals("Erro ao visualizar: usuário não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar usuário.");
            }
        }

        return usuario;
    }

    @Override
    public void update(Usuario usuario) throws SQLException {
        String query;

        if ((usuario.getSenha().trim().length() == 0) || (usuario.getSenha() == null)) {
            query = UPDATE_QUERY;
        } else {
            query = UPDATE_WITH_PASSWORD_QUERY;
        }

        try (PreparedStatement statement = connection.prepareStatement(query)) {
            if ((usuario.getSenha().trim().length() == 0) || (usuario.getSenha() == null)) {
                statement.setString(1, usuario.getNome());
                statement.setInt(2, usuario.getIdade());
                statement.setString(3, usuario.getSexo());
                statement.setString(4, usuario.getPais());
                statement.setString(5, usuario.getEmail());
            } else {
                statement.setString(1, usuario.getSenha());
                statement.setString(2, usuario.getNome());
                statement.setInt(3, usuario.getIdade());
                statement.setString(4, usuario.getSexo());
                statement.setString(5, usuario.getPais());
                statement.setString(6, usuario.getEmail());
            }

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao editar: usuário não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUsuarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao editar: usuário não encontrado.")) {
                throw ex;
            } else if (ex.getMessage().contains("uq_user_login")) {
                throw new SQLException("Erro ao editar usuário: login já existente.");
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao editar usuário: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao editar usuário.");
            }
        }
    }

    @Override
    public void delete(String email) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, email);

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: usuário não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUsuarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: usuário não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir usuário.");
            }
        }
    }

    @Override
    public List<Usuario> all() throws SQLException {
        List<Usuario> usuarioList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
            ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Usuario usuario = new Usuario();
                usuario.setEmail(result.getString("email"));
                usuario.setNome(result.getString("nome"));

                usuarioList.add(usuario);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUsuarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar usuários.");
        }

        return usuarioList;
    }
    
    @Override
    public void authenticate(Usuario usuario) throws SQLException, SecurityException {
        try (PreparedStatement statement = connection.prepareStatement(AUTHENTICATE_QUERY)) {
            statement.setString(1, usuario.getEmail());
            statement.setString(2, usuario.getSenha());

            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    usuario.setNome(result.getString("nome"));
                    usuario.setIdade(result.getInt("idade"));
                    usuario.setSexo(result.getString("sexo"));
                    usuario.setPais(result.getString("pais"));
                } else {
                    throw new SecurityException("Email ou senha incorretos.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgUsuarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao autenticar usuário.");
        }
    }
}
