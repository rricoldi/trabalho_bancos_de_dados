package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Podcast;

public class PgPodcastDAO implements PodcastDAO{
    
    private final Connection connection;

    private static final String CREATE_QUERY =
                                "INSERT INTO trabalho.podcast(rss_feed, nome, site) " +
                                "VALUES(?, ?, ?);";

    private static final String READ_QUERY =
                                "SELECT rss_feed, nome, site " +
                                "FROM trabalho.podcast " +
                                "WHERE rss_feed = ?;";

    private static final String UPDATE_QUERY =
                                "UPDATE trabalho.podcast " +
                                "SET nome = ?, site = ? " +
                                "WHERE rss_feed = ?;";

    private static final String DELETE_QUERY =
                                "DELETE FROM trabalho.podcast " +
                                "WHERE rss_feed = ?;";

    private static final String ALL_QUERY =
                                "SELECT rss_feed, nome, site " +
                                "FROM trabalho.podcast " +
                                "ORDER BY rss_feed;";

    public PgPodcastDAO(Connection connection) {
        this.connection = connection;
    }
                    
    @Override
    public void create(Podcast podcast) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, podcast.getRss_feed());
            statement.setString(2, podcast.getNome());
            statement.setString(3, podcast.getSite());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgPodcastDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().contains("uq_user_login")) {
                throw new SQLException("Erro ao cadastrar podcast: rss_feed já existente.");
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao cadastrar podcast: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao cadastrar podcast.");
            }
        }
    }

    @Override
    public Podcast read(String rss_feed) throws SQLException {
        Podcast podcast = new Podcast();

        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setString(1, rss_feed);
            try (ResultSet result = statement.executeQuery()) {
                if (result.next()) {
                    podcast.setRss_feed(result.getString("rss_feed"));
                    podcast.setNome(result.getString("nome"));
                    podcast.setSite(result.getString("site"));
                } else {
                    throw new SQLException("Erro ao visualizar: podcast não encontrado.");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgPodcastDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            
            if (ex.getMessage().equals("Erro ao visualizar: podcast não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar podcast.");
            }
        }

        return podcast;
    }

    @Override
    public void update(Podcast podcast) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setString(1, podcast.getNome());
            statement.setString(2, podcast.getSite());
            statement.setString(3, podcast.getRss_feed());

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao editar: podcast não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgPodcastDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao editar: podcast não encontrado.")) {
                throw ex;
            } else if (ex.getMessage().contains("uq_user_login")) {
                throw new SQLException("Erro ao editar podcast: login já existente.");
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao editar podcast: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao editar podcast.");
            }
        }
    }

    @Override
    public void delete(String rss_feed) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, rss_feed);

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: podcast não encontrado.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgPodcastDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: podcast não encontrado.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir podcast.");
            }
        }
    }

    @Override
    public List<Podcast> all() throws SQLException {
        List<Podcast> podcastList = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(ALL_QUERY);
            ResultSet result = statement.executeQuery()) {
            while (result.next()) {
                Podcast podcast = new Podcast();
                podcast.setRss_feed(result.getString("rss_feed"));
                podcast.setNome(result.getString("nome"));
                podcast.setSite(result.getString("site"));

                podcastList.add(podcast);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgPodcastDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            throw new SQLException("Erro ao listar podcasts.");
        }

        return podcastList;
    }
    
}
