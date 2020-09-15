package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Tag;

public class PgTagDAO implements TagDAO{
    
    private final Connection connection;

    private static final String CREATE_QUERY =
                                "INSERT INTO trabalho.tags_podcast(rss_feed, tag) " +
                                "VALUES(?, ?);";


    private static final String READ_QUERY =
                                "SELECT rss_feed, tag " +
                                "FROM trabalho.tags_podcast " +
                                "WHERE rss_feed = ? AND tag = ?;";

    private static final String UPDATE_QUERY =
                                "UPDATE trabalho.tags_podcast " +
                                "SET tag = ? " +
                                "WHERE rss_feed = ? AND tag = ?;";                                

    private static final String DELETE_QUERY =
                                "DELETE FROM trabalho.tags_podcast " +
                                "WHERE rss_feed = ? AND tag = ?;";

    public PgTagDAO(Connection connection) {
        this.connection = connection;
    }
                    
    @Override
    public void create(Tag tag) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, tag.getRss_feed());
            statement.setString(2, tag.getTag());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgTagDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().contains("uq_user_login")) {
                throw new SQLException("Erro ao cadastrar tag: pk já existente.");
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao cadastrar tag: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao cadastrar tag.");
            }
        }
    }

    public Tag readTag(String rss_feed, String tags) throws SQLException {
        Tag tag = new Tag();

        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setString(1, rss_feed);
            statement.setString(2, tags);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    tag.setRss_feed(result.getString("rss_feed"));
                    tag.setTag(result.getString("tag"));
                } else {
                    throw new SQLException("Erro ao visualizar: tag não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgTagDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            
            if (ex.getMessage().equals("Erro ao visualizar: tag não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar tag.");
            }
        }

        return tag;
    }

    @Override
    public Tag read(String rss_feed) throws SQLException {
        return null;
    }

    @Override
    public void update(Tag tag) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, tag.getTag());
            statement.setString(2, tag.getRss_feed());
            statement.setString(1, tag.getTag());

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao editar: tag não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgTagDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao editar: tag não encontrado.")) {
                throw ex;
            } else if (ex.getMessage().contains("uq_user_login")) {
                throw new SQLException("Erro ao editar tag: login já existente.");
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao editar tag: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao editar tag.");
            }
        }
    }

    @Override
        public void delete(String rss_feed) throws SQLException {
        
        }
    
    public void deleteTag(String rss_feed, String tag) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, rss_feed);
            statement.setString(2, tag);

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: tag não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgTagDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: tag não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir tag.");
            }
        }
    }

    @Override
    public List<Tag> all() throws SQLException {
        return null;
    }
    
}
