# Script de migración para PowerShell

Write-Host "Iniciando proceso de migración de base de datos..."

# Configurar variables de entorno
$env:PGPASSWORD = "danilo"

# Crear la base de datos
Write-Host "Creando base de datos..."
& psql -U postgres -c "CREATE DATABASE fvstore;" 2>$null

# Ejecutar el script de schema
Write-Host "Creando esquema de la base de datos..."
$schemaPath = Join-Path $PSScriptRoot "schema.sql"
& psql -U postgres -d fvstore -f $schemaPath

# Ejecutar la migración de datos
Write-Host "Ejecutando migración de datos..."
$jarPath = Join-Path $PSScriptRoot "../../../target/facilvirtual-1.0.0.jar"
& java -cp $jarPath com.facilvirtual.fvstoresdesk.util.MigrationRunner

Write-Host "Proceso de migración completado."
Read-Host -Prompt "Presiona Enter para continuar" 