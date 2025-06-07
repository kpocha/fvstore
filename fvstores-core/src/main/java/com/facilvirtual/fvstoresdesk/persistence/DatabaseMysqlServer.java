package com.facilvirtual.fvstoresdesk.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class DatabaseMysqlServer  {
    private final Logger logger = LoggerFactory.getLogger(DatabaseMysqlServer.class);
    public static void main(String[] args) {
        Connection connection = null;
        try {
            InitialContext ctx = new InitialContext();
            DataSource ds = (DataSource) ctx.lookup("jdbc/mysql");
            connection = ds.getConnection();
            // hacer cosas con la conexión aquí
        } catch (NamingException | SQLException ex) {
            // manejar excepciones aquí
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    // manejar excepciones aquí
                }
            }
        }
    }
}