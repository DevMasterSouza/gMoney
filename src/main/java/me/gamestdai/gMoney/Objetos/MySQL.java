package me.gamestdai.gMoney.Objetos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.Plugin;

public class MySQL {

    protected Connection connection;
    private final String user;
    private final String database;
    private final String password;
    private final String port;
    private final String hostname;

    public MySQL(String hostname, String port, String database,
            String username, String password) {
        this.hostname = hostname;
        this.port = port;
        this.database = database;
        this.user = username;
        this.password = password;
    }

    public Connection openConnection() throws SQLException,
            ClassNotFoundException {
        if (checkConnection()) {
            return connection;
        }
        Class.forName("com.mysql.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://"
                + this.hostname + ":" + this.port + "/" + this.database,
                this.user, this.password);
        return connection;
    }

    public boolean checkConnection() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException ex) {
            return false;
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public boolean closeConnection() {
        if (connection == null) {
            return false;
        }
        try {
            connection.close();
        } catch (SQLException ex) {
            return false;
        }
        return true;
    }

    public String getUser() {
        return user;
    }

    public String getHostname() {
        return hostname;
    }

    public String getDatabase() {
        return database;
    }

    public String getPassword() {
        return password;
    }
}
