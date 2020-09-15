package dao;

import java.sql.Connection;

public class PgDAOFactory extends DAOFactory{
    
    public PgDAOFactory(Connection connection) {
        this.connection = connection;
    }
    
    @Override
    public UsuarioDAO getUsuarioDAO() {
        return new PgUsuarioDAO(this.connection);
    }

    @Override
    public PodcastDAO getPodcastDAO() {
        return new PgPodcastDAO(this.connection);
    }

    @Override
    public InscricaoDAO getInscricaoDAO() {
        return new PgInscricaoDAO(this.connection);
    }
    
    @Override
    public ComentarioDAO getComentarioDAO() {
        return new PgComentarioDAO(this.connection);
    }
    
    @Override
    public TagDAO getTagDAO() {
        return new PgTagDAO(this.connection);
    }
}
