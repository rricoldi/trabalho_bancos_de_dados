package dao;

import java.sql.SQLException;
import model.Inscricao;

public interface InscricaoDAO extends DAO<Inscricao>{
    public boolean isSubscribed(String rss_feed, String email) throws SQLException;
    public void cancelSubscription(String rss_feed, String email) throws SQLException;
}
