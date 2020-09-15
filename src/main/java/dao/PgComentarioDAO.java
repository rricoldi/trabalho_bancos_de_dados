package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Comentario;

public class PgComentarioDAO implements ComentarioDAO{
    
    private final Connection connection;

    private static final String CREATE_QUERY =
                                "INSERT INTO trabalho.comentarios_podcast_usuario(rss_feed, email, comentario) " +
                                "VALUES(?, ?, ?);";


    private static final String READ_QUERY =
                                "SELECT rss_feed, email, comentario " +
                                "FROM trabalho.comentarios_podcast_usuario " +
                                "WHERE rss_feed = ? AND email = ?;";

    private static final String UPDATE_QUERY =
                                "UPDATE trabalho.comentarios_podcast_usuario " +
                                "SET comentario = ? " +
                                "WHERE rss_feed = ? AND email = ?;";                                

    private static final String DELETE_QUERY =
                                "DELETE FROM trabalho.comentarios_podcast_usuario " +
                                "WHERE rss_feed = ? AND email = ?;";

    public PgComentarioDAO(Connection connection) {
        this.connection = connection;
    }
                    
    @Override
    public void create(Comentario comentario) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, comentario.getRss_feed());
            statement.setString(2, comentario.getEmail());
            statement.setString(3, comentario.getComentario());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgComentarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().contains("uq_user_login")) {
                throw new SQLException("Erro ao cadastrar comentario: pk já existente.");
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao cadastrar comentario: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao cadastrar comentario.");
            }
        }
    }

    public Comentario readComment(String rss_feed, String email) throws SQLException {
        Comentario comentario = new Comentario();

        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setString(1, rss_feed);
            statement.setString(2, email);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    comentario.setRss_feed(result.getString("rss_feed"));
                    comentario.setEmail(result.getString("email"));
                    comentario.setComentario(result.getString("comentario"));
                } else {
                    throw new SQLException("Erro ao visualizar: comentario não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgComentarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            
            if (ex.getMessage().equals("Erro ao visualizar: comentario não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar comentario.");
            }
        }

        return comentario;
    }

    @Override
    public Comentario read(String rss_feed) throws SQLException {
        return null;
    }

    @Override
    public void update(Comentario comentario) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, comentario.getComentario());
            statement.setString(2, comentario.getRss_feed());
            statement.setString(3, comentario.getEmail());

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao editar: comentario não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgComentarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao editar: comentario não encontrado.")) {
                throw ex;
            } else if (ex.getMessage().contains("uq_user_login")) {
                throw new SQLException("Erro ao editar comentario: login já existente.");
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao editar comentario: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao editar comentario.");
            }
        }
    }

    @Override
        public void delete(String rss_feed) throws SQLException {
        
        }
    
    public void deleteComment(String rss_feed, String email) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, rss_feed);
            statement.setString(2, email);

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: comentario não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgComentarioDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: comentario não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir comentario.");
            }
        }
    }

    @Override
    public List<Comentario> all() throws SQLException {
        return null;
    }
    
}
