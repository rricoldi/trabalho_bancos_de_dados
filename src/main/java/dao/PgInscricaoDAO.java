package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Inscricao;

public class PgInscricaoDAO implements InscricaoDAO{
    
    private final Connection connection;

    private static final String CREATE_QUERY =
                                "INSERT INTO trabalho.inscricao(rss_feed, email, classificacao) " +
                                "VALUES(?, ?, ?);";

    private static final String READ_QUERY =
                                "SELECT rss_feed, email, classificacao " +
                                "FROM trabalho.usuario_esta_inscrito_no_podcast " +
                                "WHERE rss_feed = ? AND email = ?;";

    private static final String UPDATE_QUERY =
                                "UPDATE trabalho.usuario_esta_inscrito_no_podcast " +
                                "SET classificacao = ? " +
                                "WHERE rss_feed = ? AND email = ?;";                                

    private static final String DELETE_QUERY =
                                "DELETE FROM trabalho.usuario_esta_inscrito_no_podcast " +
                                "WHERE rss_feed = ? AND email = ?;";

    public PgInscricaoDAO(Connection connection) {
        this.connection = connection;
    }
                    
    @Override
    public void create(Inscricao inscricao) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(CREATE_QUERY)) {
            statement.setString(1, inscricao.getRss_feed());
            statement.setString(2, inscricao.getEmail());
            statement.setInt(3, inscricao.getClassificacao());

            statement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(PgInscricaoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().contains("uq_user_login")) {
                throw new SQLException("Erro ao cadastrar inscricao: usuario ja inscrito no podcast.");
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao cadastrar inscricao: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao cadastrar inscricao.");
            }
        }
    }

    @Override
    public Inscricao read(String rss_feed) throws SQLException {
        return null;
    }

    public boolean isSubscribed(String rss_feed, String email) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(READ_QUERY)) {
            statement.setString(1, rss_feed);
            statement.setString(2, email);
            try (ResultSet result = statement.executeQuery()) {
                return result.next();
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgInscricaoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);
            
            if (ex.getMessage().equals("Erro ao visualizar: inscricao não encontrada.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao visualizar inscricao.");
            }
        }
    }

    public void cancelSubscription(String rss_feed, String email) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_QUERY)) {
            statement.setString(1, rss_feed);
            statement.setString(2, email);

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao excluir: inscricao não encontrada.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgInscricaoDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao excluir: inscricao não encontrada.")) {
                throw ex;
            } else {
                throw new SQLException("Erro ao excluir inscricao.");
            }
        }
    }

    @Override
    public void update(Inscricao inscricao) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_QUERY)) {
            statement.setInt(1, inscricao.getClassificacao());
            statement.setString(2, inscricao.getRss_feed());
            statement.setString(3, inscricao.getEmail());

            if (statement.executeUpdate() < 1) {
                throw new SQLException("Erro ao editar: inscricao não encontrada.");
            }
        } catch (SQLException ex) {
            Logger.getLogger(PgPodcastDAO.class.getName()).log(Level.SEVERE, "DAO", ex);

            if (ex.getMessage().equals("Erro ao editar: inscricao não encontrado.")) {
                throw ex;
            } else if (ex.getMessage().contains("uq_user_login")) {
                throw new SQLException("Erro ao editar inscricao: login já existente.");
            } else if (ex.getMessage().contains("not-null")) {
                throw new SQLException("Erro ao editar inscricao: pelo menos um campo está em branco.");
            } else {
                throw new SQLException("Erro ao editar inscricao.");
            }
        }
    }

    @Override
    public void delete(String rss_feed) throws SQLException {
    }

    @Override
    public List<Inscricao> all() throws SQLException {
        return null;
    }
    
}
