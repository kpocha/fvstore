package com.facilvirtual.fvstoresdesk.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MigrationRunner {
    private static final Logger logger = LoggerFactory.getLogger(MigrationRunner.class);

    public static void main(String[] args) {
        logger.info("Iniciando proceso de migración de HSQL a PostgreSQL...");
        
        try {
            // Paso 1: Verificar que PostgreSQL esté disponible
            Class.forName("org.postgresql.Driver");
            
            // Paso 2: Ejecutar la migración
            DatabaseMigrationUtil.migrateData();
            
            logger.info("Proceso de migración completado exitosamente");
            
        } catch (Exception e) {
            logger.error("Error durante el proceso de migración: " + e.getMessage(), e);
            System.exit(1);
        }
    }
} 