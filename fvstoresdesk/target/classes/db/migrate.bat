@echo off
echo Iniciando proceso de migración de base de datos...

REM Crear la base de datos
echo Creando base de datos...
set PGPASSWORD=danilo
psql -U postgres -c "CREATE DATABASE fvstore;" 2>nul

REM Crear el esquema de la base de datos
echo Creando esquema de la base de datos...
psql -U postgres -d fvstore -f schema.sql

REM Ejecutar la migración de datos
echo Ejecutando migración de datos...
java -cp "../../../target/facilvirtual-1.0.0.jar" com.facilvirtual.fvstoresdesk.util.MigrationRunner

echo Proceso de migración completado.
pause 