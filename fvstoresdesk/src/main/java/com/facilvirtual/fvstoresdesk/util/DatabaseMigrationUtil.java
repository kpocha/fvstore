package com.facilvirtual.fvstoresdesk.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DatabaseMigrationUtil {
    private static final Logger logger = LoggerFactory.getLogger(DatabaseMigrationUtil.class);
    
    // Configuración HSQL
    private static final String HSQL_DRIVER = "org.hsqldb.jdbcDriver";
    private static final String HSQL_URL = "jdbc:hsqldb:file:C:/facilvirtual/data/fvposdb;shutdown=true";
    private static final String HSQL_USER = "adminFvPos";
    private static final String HSQL_PASSWORD = "fvPos2014";
    
    // Configuración PostgreSQL
    private static final String PG_URL = "jdbc:postgresql://localhost:5432/fvstore";
    private static final String PG_USER = "postgres";
    private static final String PG_PASSWORD = "danilo";

    // Orden de migración de tablas basado en dependencias
    private static final List<String> TABLE_MIGRATION_ORDER = Arrays.asList(
        // Tablas base sin dependencias
        "FVPOS_VAT",
        "FVPOS_VAT_CONDITION",
        "FVPOS_SALE_CONDITION",
        "FVPOS_RECEIPT_TYPE",
        "FVPOS_BRAND",
        "FVPOS_CREDIT_CARD",
        "FVPOS_DEBIT_CARD",
        
        // Tablas con dependencias simples
        "FVPOS_EMPLOYEE",
        "FVPOS_CUSTOMER",
        "FVPOS_SUPPLIER",
        
        // Tablas con dependencias complejas
        "FVPOS_PRODUCT_CATEGORY",
        "FVPOS_PRODUCT",
        "FVPOS_PRODUCT_PHOTO",
        "FVPOS_SUPPLIER_FOR_PRODUCT",
        "FVPOS_PRICE_LIST",
        "FVPOS_PRODUCT_PRICE",
        
        // Tablas de operaciones
        "FVPOS_ORDER",
        "FVPOS_ORDER_LINE",
        "FVPOS_PURCHASE",
        "FVPOS_PURCHASE_LINE",
        "FVPOS_BUDGET",
        "FVPOS_BUDGET_LINE",
        "FVPOS_CASH_OPERATION",
        "FVPOS_CUSTOMER_ON_ACCOUNT_OP",
        "FVPOS_SUPPLIER_ON_ACCOUNT_OP",
        "FVPOS_NOTA_DE_CREDITO",
        
        // Configuración
        "FVPOS_APP_CONFIG",
        "FVPOS_WORKSTATION_CONFIG"
    );

    public static void migrateData() {
        Connection hsqlConn = null;
        Connection pgConn = null;

        try {
            // Conectar a ambas bases de datos
            Class.forName(HSQL_DRIVER);
            hsqlConn = DriverManager.getConnection(HSQL_URL, HSQL_USER, HSQL_PASSWORD);
            pgConn = DriverManager.getConnection(PG_URL, PG_USER, PG_PASSWORD);
            
            // Deshabilitar auto-commit para PostgreSQL
            pgConn.setAutoCommit(false);

            // Migrar tablas en el orden especificado
            for (String tableName : TABLE_MIGRATION_ORDER) {
                try {
                    migrarTabla(hsqlConn, pgConn, tableName);
                } catch (SQLException e) {
                    logger.error("Error migrando tabla " + tableName + ": " + e.getMessage(), e);
                    // Continuar con la siguiente tabla
                }
            }

            // Commit final
            pgConn.commit();
            logger.info("Migración completada exitosamente");

        } catch (Exception e) {
            logger.error("Error durante la migración: " + e.getMessage(), e);
            if (pgConn != null) {
                try {
                    pgConn.rollback();
                } catch (SQLException ex) {
                    logger.error("Error al hacer rollback: " + ex.getMessage(), ex);
                }
            }
        } finally {
            try {
                if (hsqlConn != null) hsqlConn.close();
                if (pgConn != null) pgConn.close();
            } catch (SQLException e) {
                logger.error("Error al cerrar conexiones: " + e.getMessage(), e);
            }
        }
    }

    private static void migrarTabla(Connection hsqlConn, Connection pgConn, String tableName) throws SQLException {
        logger.info("Migrando tabla: " + tableName);
        
        // Temporalmente deshabilitamos la limpieza de tablas
        /*try {
            String deleteSQL = "DELETE FROM " + tableName.toLowerCase();
            try (Statement stmt = pgConn.createStatement()) {
                stmt.execute(deleteSQL);
            }
        } catch (SQLException e) {
            logger.warn("Error al limpiar tabla " + tableName + ": " + e.getMessage());
        }*/
        
        // Obtener estructura de la tabla
        Statement hsqlStmt = hsqlConn.createStatement();
        ResultSet rs = hsqlStmt.executeQuery("SELECT * FROM " + tableName + " LIMIT 0");
        ResultSetMetaData rsmd = rs.getMetaData();
        int columnCount = rsmd.getColumnCount();

        // Construir consulta de inserción
        StringBuilder insertQuery = new StringBuilder("INSERT INTO " + tableName.toLowerCase() + " (");
        StringBuilder valuePlaceholders = new StringBuilder("VALUES (");
        
        for (int i = 1; i <= columnCount; i++) {
            String columnName = rsmd.getColumnName(i).toLowerCase();
            insertQuery.append(columnName);
            valuePlaceholders.append("?");
            
            if (i < columnCount) {
                insertQuery.append(", ");
                valuePlaceholders.append(", ");
            }
        }
        insertQuery.append(") ").append(valuePlaceholders.append(")"));

        // Preparar statements
        PreparedStatement pgInsert = pgConn.prepareStatement(insertQuery.toString());
        rs = hsqlStmt.executeQuery("SELECT * FROM " + tableName);

        // Migrar datos
        int batchSize = 0;
        int totalRows = 0;
        
        while (rs.next()) {
            for (int i = 1; i <= columnCount; i++) {
                Object value = rs.getObject(i);
                pgInsert.setObject(i, value);
            }
            pgInsert.addBatch();
            totalRows++;
            
            if (++batchSize % 1000 == 0) {
                pgInsert.executeBatch();
                pgConn.commit();
            }
        }
        
        // Ejecutar batch final
        if (batchSize > 0) {
            pgInsert.executeBatch();
            pgConn.commit();
        }

        logger.info("Tabla " + tableName + " migrada exitosamente. " + totalRows + " filas migradas.");
        rs.close();
        hsqlStmt.close();
        pgInsert.close();
    }
} 