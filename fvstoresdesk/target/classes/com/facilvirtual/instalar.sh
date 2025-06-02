#!/bin/bash

# Ruta a la carpeta donde están los .jar (modificá si hace falta)
LIB_DIR="./lib"

# GroupId genérico para tus jars locales
GROUP_ID="com.facilvirtual.lib"

# Versión por defecto
VERSION="1.0.0"

echo "🔧 Instalando todos los .jar de $LIB_DIR al repositorio Maven local..."

for jar in "$LIB_DIR"/*.jar; do
  # Extraer nombre base del archivo sin extensión
  filename=$(basename -- "$jar")
  artifactId="${filename%.jar}"

  echo "📦 Instalando $filename como $GROUP_ID:$artifactId:$VERSION"

  mvn install:install-file \
    -Dfile="$jar" \
    -DgroupId="$GROUP_ID" \
    -DartifactId="$artifactId" \
    -Dversion="$VERSION" \
    -Dpackaging=jar \
    -DgeneratePom=true
done

echo "✅ Todos los JAR fueron instalados localmente."