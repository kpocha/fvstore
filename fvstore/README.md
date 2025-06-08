# Proyecto FVStore - Estructura Multimódulo

Este repositorio contiene el proyecto FVStore, una aplicación estructurada en múltiples módulos para demostración y desarrollo.

## Estructura de Módulos

El proyecto se divide en los siguientes módulos principales:

1.  **`fvstore-core` (Java)**
    *   **Propósito:** Contiene la lógica de negocio central, modelos de dominio (entidades), servicios de negocio, e interfaces de repositorio.
    *   **Tecnologías:** Java, Maven.
    *   **Salida:** JAR consumible por otros módulos.
    *   **Responsabilidades:**
        *   Definición de entidades de dominio (e.g., `Producto`, `Cliente`, `Pedido`).
        *   Lógica de negocio pura, independiente de frameworks de UI o API.
        *   Interfaces para la persistencia de datos (los repositorios).
        *   Servicios que orquestan la lógica de negocio.

2.  **`fvstore-api` (Java Spring Boot)**
    *   **Propósito:** Expone una API REST para interactuar con la lógica de negocio del core.
    *   **Tecnologías:** Java, Spring Boot (Web, Security, Data JPA - opcional), Maven, OpenAPI (Swagger).
    *   **Depende de:** `fvstore-core`.
    *   **Responsabilidades:**
        *   Definición de Endpoints REST (Controladores).
        *   Manejo de DTOs (Data Transfer Objects) para la comunicación API.
        *   Autenticación y autorización (e.g., Spring Security con JWT).
        *   Validación de entradas.
        *   Documentación de API (Swagger/OpenAPI).
        *   Implementación de los repositorios definidos en `fvstore-core` si se usa Spring Data JPA.

3.  **`fvstore-desktop` (Java Swing/JavaFX)**
    *   **Propósito:** Aplicación de escritorio para interactuar con el sistema.
    *   **Tecnologías:** Java, Swing o JavaFX, Maven.
    *   **Depende de:** `fvstore-core` (para acceso directo a la lógica) y/o consume `fvstore-api` (vía HTTP).
    *   **Responsabilidades:**
        *   Interfaz de usuario gráfica (vistas, controladores de UI).
        *   Lógica de presentación específica del escritorio.
        *   Comunicación con `fvstore-api` si opera en modo cliente-servidor, o uso directo de servicios de `fvstore-core`.

4.  **`fvstore-web` (React)**
    *   **Propósito:** Aplicación web moderna (SPA) para interactuar con el sistema.
    *   **Tecnologías:** React, Node.js/npm/yarn, Axios (para llamadas API), React Router.
    *   **Depende de (en tiempo de ejecución):** `fvstore-api` (consume sus endpoints REST).
    *   **Responsabilidades:**
        *   Interfaz de usuario web (componentes, páginas).
        *   Gestión del estado del lado del cliente.
        *   Enrutamiento del lado del cliente.
        *   Comunicación con `fvstore-api`.

## Diagrama de Dependencias (Conceptual)

```
+-----------------+     +-----------------+     +-------------------+
|  fvstore-web    | --> |  fvstore-api    | --> |  fvstore-core     |
|  (React)        |     |  (Spring Boot)  |     |  (Java Core Logic)|
+-----------------+     +-----------------+     +-------------------+
                            ^
                            |
                            | (puede consumir API o usar core directamente)
                            |
                      +-----------------+
                      | fvstore-desktop |
                      | (JavaFX/Swing)  |
                      +-----------------+
```

## Buenas Prácticas

### Modularidad y Separación de Responsabilidades
*   **Alta Cohesión, Bajo Acoplamiento:** Cada módulo debe tener una responsabilidad clara y bien definida. Las interacciones entre módulos deben ser a través de interfaces bien definidas para minimizar el acoplamiento.
*   **`fvstore-core` como Núcleo:** Mantenlo libre de dependencias de frameworks específicos de presentación o API (como Spring MVC o JavaFX). Esto asegura su reutilización.
*   **DTOs en el Límite del API:** `fvstore-api` debe usar DTOs para exponer datos. Evita exponer entidades de dominio directamente a través del API para desacoplar la representación interna de la externa.
*   **Servicios de Aplicación vs. Dominio:** En `fvstore-core`, distingue entre servicios de dominio (lógica de negocio central) y servicios de aplicación (que orquestan y pueden manejar transacciones, seguridad a nivel de método, etc., aunque parte de esto puede estar en `fvstore-api`).

### Manejo de Dependencias
*   **Maven Parent POM:** El `pom.xml` raíz (`fvstore/pom.xml`) gestiona versiones de dependencias comunes y plugins para los módulos Java a través de `dependencyManagement` y `pluginManagement`. Esto asegura consistencia.
*   **Dependencias Transitivas:** Revisa y gestiona las dependencias transitivas para evitar conflictos y JARs innecesarios. Usa `<exclusions>` en Maven si es necesario.
*   **Alcance de Dependencias (Scope):** Usa los scopes de Maven correctamente (`compile`, `provided`, `runtime`, `test`) para controlar el classpath.
*   **Frontend Independiente:** `fvstore-web` maneja sus dependencias con `package.json` (npm/yarn). Es un proyecto Node.js separado.

### Construcción y Despliegue
*   **Construcción de Módulos Java:** `mvn clean install` desde el directorio raíz `fvstore` construirá todos los módulos Java (`core`, `api`, `desktop`).
*   **Construcción Módulo Web:** Navega a `fvstore/fvstore-web` y usa `npm install` (o `yarn install`) seguido de `npm run build` (o `yarn build`).
*   **Ejecución:**
    *   `fvstore-api`: Se puede ejecutar como un JAR Spring Boot (`java -jar fvstore-api/target/fvstore-api-1.0-SNAPSHOT.jar`).
    *   `fvstore-desktop`: Se puede ejecutar como un JAR (`java -jar fvstore-desktop/target/fvstore-desktop-1.0-SNAPSHOT-jar-with-dependencies.jar`).
    *   `fvstore-web`: Servir el contenido estático de `fvstore-web/build` con un servidor web (Nginx, Apache) o usar `npm start` para desarrollo.

## Sugerencias para Integración CI/CD

### Herramientas
*   **Jenkins, GitLab CI/CD, GitHub Actions:** Plataformas populares para automatizar la construcción, pruebas y despliegue.

### Pipeline Básico
Un pipeline podría incluir los siguientes stages:

1.  **Checkout:** Obtener el código fuente del repositorio.
2.  **Build Backend (Java):**
    *   Ejecutar `mvn clean install -DskipTests` (o `mvn clean package -DskipTests` si `install` no es necesario para el stage).
    *   Esto compilará y empaquetará `fvstore-core`, `fvstore-api`, `fvstore-desktop`.
3.  **Test Backend (Java):**
    *   Ejecutar `mvn test`.
    *   Generar reportes de pruebas y cobertura de código (e.g., con JaCoCo).
4.  **Build Frontend (React):**
    *   Navegar a `fvstore-web`.
    *   Ejecutar `npm ci` (o `yarn install --frozen-lockfile` para usar el lockfile).
    *   Ejecutar `npm run build`.
5.  **Test Frontend (React):**
    *   Ejecutar `npm test`.
    *   Considerar tests E2E con herramientas como Cypress o Playwright.
6.  **Análisis Estático de Código (Opcional pero Recomendado):**
    *   Usar SonarQube, Checkstyle, PMD para Java.
    *   Usar ESLint, Prettier para React.
7.  **Empaquetado de Artefactos:**
    *   Guardar los JARs generados (`fvstore-api.jar`, `fvstore-desktop.jar`) y el build del frontend (`fvstore-web/build/*`).
    *   Considerar la creación de imágenes Docker para `fvstore-api` y para servir `fvstore-web`.
8.  **Despliegue (a diferentes entornos: dev, staging, prod):**
    *   **API:** Desplegar el JAR de `fvstore-api` a un servidor de aplicaciones, PaaS (Heroku, AWS Elastic Beanstalk) o un orquestador de contenedores (Kubernetes).
    *   **Web:** Desplegar los archivos estáticos de `fvstore-web` a un CDN, servicio de hosting estático (Netlify, Vercel, AWS S3/CloudFront) o un servidor web.
    *   **Desktop:** El JAR de `fvstore-desktop` podría distribuirse directamente o mediante un sistema de distribución de software.

### Dockerización
*   **`fvstore-api`:** Crear un `Dockerfile` para empaquetar la aplicación Spring Boot en una imagen Docker.
    ```Dockerfile
    # Ejemplo para fvstore-api
    FROM openjdk:17-jdk-slim
    ARG JAR_FILE=target/fvstore-api-*.jar
    COPY ${JAR_FILE} app.jar
    EXPOSE 8080
    ENTRYPOINT ["java","-jar","/app.jar"]
    ```
*   **`fvstore-web`:** Crear un `Dockerfile` (multi-stage build) para servir los archivos estáticos con Nginx.
    ```Dockerfile
    # Ejemplo para fvstore-web (multi-stage)
    # Stage 1: Build React App
    FROM node:18 AS build
    WORKDIR /app
    COPY package*.json ./
    RUN npm ci
    COPY . .
    RUN npm run build

    # Stage 2: Serve with Nginx
    FROM nginx:alpine
    COPY --from=build /app/build /usr/share/nginx/html
    EXPOSE 80
    CMD ["nginx", "-g", "daemon off;"]
    ```

## Manejo de Versiones

*   **Versionado Semántico (SemVer):** `MAJOR.MINOR.PATCH` (e.g., `1.0.0`).
    *   `MAJOR`: Cambios incompatibles con versiones anteriores.
    *   `MINOR`: Nuevas funcionalidades compatibles con versiones anteriores.
    *   `PATCH`: Correcciones de errores compatibles con versiones anteriores.
*   **Git Tags:** Usar tags de Git para marcar releases (e.g., `v1.0.0`).
*   **Consistencia de Versiones:**
    *   El `pom.xml` padre (`fvstore/pom.xml`) define la versión `1.0-SNAPSHOT` para los módulos Java. Al hacer un release, esta versión se actualizaría (e.g., a `1.0.0`). El plugin `maven-release-plugin` puede ayudar a automatizar esto.
    *   `fvstore-web` tiene su propia versión en `package.json`. Puede versionarse de forma independiente o alineada con la versión del backend, dependiendo de la estrategia.
*   **Dependencias entre Módulos:** La versión de `fvstore-core` usada por `fvstore-api` y `fvstore-desktop` se gestiona en sus respectivos `pom.xml`, idealmente heredando la versión del padre (`${project.parent.version}` o `${project.version}`).

## Para Empezar

1.  **Clonar el repositorio.**
2.  **Backend (Java):**
    *   Asegúrate de tener JDK 17 y Maven instalados.
    *   Desde la raíz (`fvstore`), ejecuta `mvn clean install`.
    *   Para ejecutar el API: `java -jar fvstore-api/target/fvstore-api-1.0-SNAPSHOT.jar`. Estará disponible en `http://localhost:8080`. Swagger UI en `http://localhost:8080/swagger-ui.html`.
3.  **Frontend (React):**
    *   Asegúrate de tener Node.js y npm/yarn instalados.
    *   Navega a `fvstore/fvstore-web`.
    *   Ejecuta `npm install` (o `yarn install`).
    *   Ejecuta `npm start` (o `yarn start`). La aplicación web estará disponible en `http://localhost:3000` y usará el proxy para comunicarse con el API en el puerto 8080.

Este README provee una guía inicial. Deberá ser expandido y adaptado a medida que el proyecto evolucione.
