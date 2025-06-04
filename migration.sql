-- Crear las tablas necesarias en PostgreSQL

-- Tabla de usuarios
CREATE TABLE IF NOT EXISTS users (
    id SERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de productos
CREATE TABLE IF NOT EXISTS products (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    description TEXT,
    price DECIMAL(10,2) NOT NULL,
    stock INTEGER NOT NULL DEFAULT 0,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de ventas
CREATE TABLE IF NOT EXISTS sales (
    id SERIAL PRIMARY KEY,
    user_id INTEGER REFERENCES users(id),
    total_amount DECIMAL(10,2) NOT NULL,
    sale_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de detalles de venta
CREATE TABLE IF NOT EXISTS sale_details (
    id SERIAL PRIMARY KEY,
    sale_id INTEGER REFERENCES sales(id),
    product_id INTEGER REFERENCES products(id),
    quantity INTEGER NOT NULL,
    unit_price DECIMAL(10,2) NOT NULL,
    total DECIMAL(10,2) NOT NULL
);

-- Tabla de clientes
CREATE TABLE IF NOT EXISTS customers (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    document_number VARCHAR(20),
    phone VARCHAR(20),
    email VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Tabla de configuraciones
CREATE TABLE IF NOT EXISTS configurations (
    id SERIAL PRIMARY KEY,
    key VARCHAR(50) NOT NULL UNIQUE,
    value TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Crear índices para mejorar el rendimiento
CREATE INDEX idx_products_name ON products(name);
CREATE INDEX idx_sales_user_id ON sales(user_id);
CREATE INDEX idx_sale_details_sale_id ON sale_details(sale_id);
CREATE INDEX idx_sale_details_product_id ON sale_details(product_id);
CREATE INDEX idx_customers_name ON customers(name);
CREATE INDEX idx_customers_document ON customers(document_number);

-- Configurar secuencias para que comiencen desde el valor actual de HSQLDB
SELECT setval('products_id_seq', (SELECT MAX(id) FROM products));
SELECT setval('sales_id_seq', (SELECT MAX(id) FROM sales));
SELECT setval('sale_details_id_seq', (SELECT MAX(id) FROM sale_details));
SELECT setval('customers_id_seq', (SELECT MAX(id) FROM customers));

-- NOTA: Este script es un ejemplo base. Necesitarás ajustarlo según las tablas específicas de tu base de datos HSQLDB
-- Puedes:
-- 1. Abrir este archivo en pgAdmin
-- 2. Ejecutar los comandos
-- 3. Luego exportar los datos de HSQLDB y importarlos a PostgreSQL usando pgAdmin o psql
