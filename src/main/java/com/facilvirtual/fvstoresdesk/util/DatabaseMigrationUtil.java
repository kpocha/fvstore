import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Logger;

public class DatabaseMigrationUtil {

    private static final Logger logger = Logger.getLogger(DatabaseMigrationUtil.class.getName());
    private static final String IMAGES_PATH = "src/main/resources/images/"; // Ajusta esta ruta según tu estructura

    private static byte[] handleImageFile(String fileName) {
        if (fileName == null || fileName.trim().isEmpty()) {
            return null;
        }

        // Intentar leer el archivo desde el directorio de imágenes
        try {
            File imageFile = new File(IMAGES_PATH + fileName);
            if (imageFile.exists()) {
                return Files.readAllBytes(imageFile.toPath());
            } else {
                logger.warning("Archivo de imagen no encontrado: " + fileName);
                return null;
            }
        } catch (IOException e) {
            logger.severe("Error al leer archivo de imagen " + fileName + ": " + e.getMessage());
            return null;
        }
    }

    private static void migrarTabla(Connection hsqlConn, Connection pgConn, String tableName) throws SQLException {
        logger.info("Migrando tabla: " + tableName);
        
        // Desactivar auto-commit para manejar transacciones manualmente
        boolean originalAutoCommit = pgConn.getAutoCommit();
        pgConn.setAutoCommit(false);
        
        PreparedStatement pgInsert = null;
        Statement hsqlStmt = null;
        ResultSet rs = null;
        
        try {
            // Obtener estructura de la tabla
            hsqlStmt = hsqlConn.createStatement();
            rs = hsqlStmt.executeQuery("SELECT * FROM " + tableName + " LIMIT 0");
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
            pgInsert = pgConn.prepareStatement(insertQuery.toString());
            rs = hsqlStmt.executeQuery("SELECT * FROM " + tableName);

            // Migrar datos
            int batchSize = 0;
            int totalRows = 0;
            int currentRow = 0;
            
            while (rs.next()) {
                currentRow++;
                try {
                    for (int i = 1; i <= columnCount; i++) {
                        Object value = rs.getObject(i);
                        String columnName = rsmd.getColumnName(i).toLowerCase();
                        
                        // Manejar columnas de tipo photo y company_logo como VARCHAR
                        if (columnName.equals("photo") || columnName.equals("company_logo")) {
                            if (value == null) {
                                pgInsert.setNull(i, java.sql.Types.VARCHAR);
                                logger.info("Valor nulo para columna " + columnName + " en fila " + currentRow);
                            } else if (value instanceof String) {
                                pgInsert.setString(i, (String) value);
                                logger.info("String para columna " + columnName + " en fila " + currentRow + ": " + value);
                            } else if (value instanceof byte[]) {
                                // Si es un byte array, lo convertimos a string
                                pgInsert.setString(i, new String((byte[]) value, "UTF-8"));
                                logger.info("Byte array convertido a String para columna " + columnName + " en fila " + currentRow);
                            } else {
                                pgInsert.setNull(i, java.sql.Types.VARCHAR);
                                logger.warning("Tipo de dato no soportado para columna " + columnName + " en fila " + currentRow + ": " + value.getClass().getName());
                            }
                        } else {
                            pgInsert.setObject(i, value);
                        }
                    }
                    pgInsert.addBatch();
                    totalRows++;
                    
                    if (++batchSize % 100 == 0) {
                        try {
                            pgInsert.executeBatch();
                            pgConn.commit();
                            batchSize = 0;
                            logger.info("Procesadas " + totalRows + " filas de " + tableName);
                        } catch (SQLException e) {
                            pgConn.rollback();
                            logger.severe("Error en batch de " + tableName + " en fila " + currentRow + ": " + e.getMessage());
                            throw e;
                        }
                    }
                } catch (SQLException | UnsupportedEncodingException e) {
                    pgConn.rollback();
                    logger.severe("Error procesando fila " + currentRow + " de " + tableName + ": " + e.getMessage());
                    throw new SQLException(e);
                }
            }
            
            // Ejecutar batch final
            if (batchSize > 0) {
                try {
                    pgInsert.executeBatch();
                    pgConn.commit();
                } catch (SQLException e) {
                    pgConn.rollback();
                    logger.severe("Error en batch final de " + tableName + ": " + e.getMessage());
                    throw e;
                }
            }

            logger.info("Tabla " + tableName + " migrada exitosamente. " + totalRows + " filas migradas.");
            
        } catch (SQLException e) {
            pgConn.rollback();
            logger.severe("Error migrando tabla " + tableName + ": " + e.getMessage());
            throw e;
        } finally {
            // Cerrar recursos
            if (rs != null) try { rs.close(); } catch (SQLException e) { }
            if (hsqlStmt != null) try { hsqlStmt.close(); } catch (SQLException e) { }
            if (pgInsert != null) try { pgInsert.close(); } catch (SQLException e) { }
            
            // Restaurar auto-commit
            try { pgConn.setAutoCommit(originalAutoCommit); } catch (SQLException e) { }
        }
    }
} 