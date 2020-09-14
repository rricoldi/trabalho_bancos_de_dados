package jdbc;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
    
public abstract class ConnectionFactory {
    private static ConnectionFactory instance = null;
    
    protected ConnectionFactory() {
    }

    public static ConnectionFactory getInstance() throws IOException {
        if (instance == null) {
            instance = new PgConnectionFactory();
        }

        return instance;
    }

    public abstract Connection getConnection() throws IOException, SQLException, ClassNotFoundException;
    
}