-- Secuencias para IDs
CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START 1;

-- Crear tablas en orden considerando las dependencias

-- Tabla VAT (IVA)
CREATE TABLE fvpos_vat (
    vat_id SERIAL PRIMARY KEY,
    vat_name VARCHAR(255) UNIQUE,
    vat_value DOUBLE PRECISION
);

-- Tabla VAT_CONDITION (Condición de IVA)
CREATE TABLE fvpos_vat_condition (
    condition_id SERIAL PRIMARY KEY,
    condition_name VARCHAR(255) UNIQUE NOT NULL,
    abbreviate_name VARCHAR(255) UNIQUE NOT NULL,
    is_active BOOLEAN DEFAULT true,
    is_available_for_company BOOLEAN DEFAULT true,
    is_available_for_customer BOOLEAN DEFAULT true
);

-- Tabla SALE_CONDITION (Condición de Venta)
CREATE TABLE fvpos_sale_condition (
    condition_id SERIAL PRIMARY KEY,
    condition_name VARCHAR(255) UNIQUE NOT NULL,
    abbreviate_name VARCHAR(255) UNIQUE NOT NULL,
    is_active BOOLEAN DEFAULT true,
    is_available_for_company BOOLEAN DEFAULT true,
    is_available_for_customer BOOLEAN DEFAULT true
);

-- Tabla RECEIPT_TYPE (Tipo de Comprobante)
CREATE TABLE fvpos_receipt_type (
    receipt_type_id SERIAL PRIMARY KEY,
    type_name VARCHAR(255) UNIQUE NOT NULL,
    is_active BOOLEAN DEFAULT true,
    is_available_for_purchase BOOLEAN DEFAULT true,
    is_available_for_order BOOLEAN DEFAULT true
);

-- Tabla BRAND (Marca)
CREATE TABLE fvpos_brand (
    brand_id SERIAL PRIMARY KEY,
    brand_name VARCHAR(255) UNIQUE NOT NULL,
    photo VARCHAR(255),
    is_active BOOLEAN DEFAULT true
);

-- Tabla PRODUCT_CATEGORY (Categoría de Producto)
CREATE TABLE fvpos_product_category (
    category_id SERIAL PRIMARY KEY,
    category_name VARCHAR(255) UNIQUE NOT NULL,
    photo VARCHAR(255),
    is_active BOOLEAN DEFAULT true,
    vat_id INTEGER REFERENCES fvpos_vat(vat_id),
    parent_id INTEGER REFERENCES fvpos_product_category(category_id),
    position INTEGER DEFAULT 0 NOT NULL,
    positionInCash INTEGER DEFAULT 0 NOT NULL
);

-- Tabla EMPLOYEE (Empleado)
CREATE TABLE fvpos_employee (
    employee_id SERIAL PRIMARY KEY,
    creation_date TIMESTAMP NOT NULL,
    last_updated_date TIMESTAMP NOT NULL,
    photo VARCHAR(255),
    is_active BOOLEAN DEFAULT true,
    username VARCHAR(255) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    is_admin BOOLEAN DEFAULT false,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255) NOT NULL,
    document_type VARCHAR(255),
    document_number VARCHAR(255),
    phone VARCHAR(255),
    mobile VARCHAR(255),
    email VARCHAR(255),
    address_street VARCHAR(255),
    address_number VARCHAR(255),
    address_extra VARCHAR(255),
    postal_code VARCHAR(255),
    city VARCHAR(255),
    cuil VARCHAR(255),
    website VARCHAR(255),
    observations TEXT,
    job_position VARCHAR(255) NOT NULL,
    is_shift1 BOOLEAN DEFAULT false,
    is_shift2 BOOLEAN DEFAULT false,
    is_shift3 BOOLEAN DEFAULT false,
    allow_login BOOLEAN DEFAULT false,
    allow_open_cash BOOLEAN DEFAULT false,
    allow_close_cash BOOLEAN DEFAULT false,
    allow_create_income BOOLEAN DEFAULT false,
    allow_create_outflow BOOLEAN DEFAULT false,
    allow_create_order BOOLEAN DEFAULT false,
    allow_create_purchase BOOLEAN DEFAULT false,
    allow_modify_price BOOLEAN DEFAULT false,
    allow_apply_discount BOOLEAN DEFAULT false,
    allow_apply_surcharge BOOLEAN DEFAULT false,
    allow_module_products BOOLEAN DEFAULT false,
    allow_module_orders BOOLEAN DEFAULT false,
    allow_module_purchases BOOLEAN DEFAULT false,
    allow_module_customers BOOLEAN DEFAULT false,
    allow_module_suppliers BOOLEAN DEFAULT false,
    allow_module_lists BOOLEAN DEFAULT false,
    allow_module_reports BOOLEAN DEFAULT false,
    allow_module_tools BOOLEAN DEFAULT false,
    allow_module_cash BOOLEAN DEFAULT false,
    commission_per_sale DOUBLE PRECISION DEFAULT 0.0
);

-- Tabla CUSTOMER (Cliente)
CREATE TABLE fvpos_customer (
    customer_id SERIAL PRIMARY KEY,
    creation_date TIMESTAMP NOT NULL,
    last_updated_date TIMESTAMP NOT NULL,
    photo VARCHAR(255),
    is_active BOOLEAN DEFAULT true,
    account_type VARCHAR(255) NOT NULL,
    company_name VARCHAR(255),
    business_name VARCHAR(255),
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    document_type VARCHAR(255),
    document_number VARCHAR(255),
    phone VARCHAR(255),
    mobile VARCHAR(255),
    email VARCHAR(255),
    address_street VARCHAR(255),
    address_number VARCHAR(255),
    address_extra VARCHAR(255),
    fiscal_address_street VARCHAR(255),
    fiscal_address_number VARCHAR(255),
    fiscal_address_extra VARCHAR(255),
    postal_code VARCHAR(255),
    city VARCHAR(255),
    province VARCHAR(255),
    contact_name VARCHAR(255),
    cuit VARCHAR(255),
    cuil VARCHAR(255),
    gross_income_number VARCHAR(255),
    website VARCHAR(255),
    observations TEXT,
    vat_condition_id INTEGER NOT NULL REFERENCES fvpos_vat_condition(condition_id),
    is_allow_on_account_op BOOLEAN DEFAULT true,
    is_on_account_limited BOOLEAN DEFAULT false,
    on_account_limit DOUBLE PRECISION DEFAULT 0.0,
    on_account_total DOUBLE PRECISION DEFAULT 0.0
);

-- Tabla SUPPLIER (Proveedor)
CREATE TABLE fvpos_supplier (
    supplier_id SERIAL PRIMARY KEY,
    creation_date TIMESTAMP NOT NULL,
    last_updated_date TIMESTAMP NOT NULL,
    photo VARCHAR(255),
    is_active BOOLEAN DEFAULT true,
    company_name VARCHAR(255),
    business_name VARCHAR(255),
    phone VARCHAR(255),
    mobile VARCHAR(255),
    email VARCHAR(255),
    address_street VARCHAR(255),
    address_number VARCHAR(255),
    address_extra VARCHAR(255),
    fiscal_address_street VARCHAR(255),
    fiscal_address_number VARCHAR(255),
    fiscal_address_extra VARCHAR(255),
    postal_code VARCHAR(255),
    city VARCHAR(255),
    contact_name VARCHAR(255),
    cuit VARCHAR(255),
    gross_income_number VARCHAR(255),
    website VARCHAR(255),
    observations TEXT,
    vat_condition_id INTEGER NOT NULL REFERENCES fvpos_vat_condition(condition_id),
    is_allow_on_account_op BOOLEAN DEFAULT true,
    is_on_account_limited BOOLEAN DEFAULT false,
    on_account_limit DOUBLE PRECISION DEFAULT 0.0,
    on_account_total DOUBLE PRECISION DEFAULT 0.0
);

-- Tabla PRODUCT (Producto)
CREATE TABLE fvpos_product (
    product_id SERIAL PRIMARY KEY,
    creation_date TIMESTAMP NOT NULL,
    photo VARCHAR(255),
    bar_code VARCHAR(255) UNIQUE NOT NULL,
    product_description VARCHAR(255) NOT NULL,
    last_updated_description TIMESTAMP NOT NULL,
    cost_price DOUBLE PRECISION DEFAULT 0 NOT NULL,
    net_price DOUBLE PRECISION DEFAULT 0 NOT NULL,
    gross_margin DOUBLE PRECISION DEFAULT 0 NOT NULL,
    previous_selling_price DOUBLE PRECISION DEFAULT 0 NOT NULL,
    selling_price DOUBLE PRECISION DEFAULT 0 NOT NULL,
    internal_tax DOUBLE PRECISION DEFAULT 0 NOT NULL,
    previous_updated_price TIMESTAMP,
    last_updated_price TIMESTAMP,
    vat_id INTEGER NOT NULL REFERENCES fvpos_vat(vat_id),
    selling_unit VARCHAR(255) DEFAULT 'UN' NOT NULL,
    is_discontinued BOOLEAN DEFAULT true,
    in_offer BOOLEAN DEFAULT false,
    in_web BOOLEAN DEFAULT false,
    offer_price DOUBLE PRECISION DEFAULT 0 NOT NULL,
    offer_start_date TIMESTAMP,
    offer_end_date TIMESTAMP,
    brand_id INTEGER REFERENCES fvpos_brand(brand_id),
    category_id INTEGER REFERENCES fvpos_product_category(category_id),
    subcategory1_id INTEGER REFERENCES fvpos_product_category(category_id),
    subcategory2_id INTEGER REFERENCES fvpos_product_category(category_id),
    supplier_id INTEGER REFERENCES fvpos_supplier(supplier_id),
    last_updated_category TIMESTAMP NOT NULL,
    product_short_description VARCHAR(255),
    quantity DOUBLE PRECISION DEFAULT 0 NOT NULL,
    quantity_unit VARCHAR(255),
    previous_sale_date TIMESTAMP,
    last_sale_date TIMESTAMP,
    previous_purchase_date TIMESTAMP,
    last_purchase_date TIMESTAMP,
    stock DOUBLE PRECISION DEFAULT 0 NOT NULL,
    stock_min DOUBLE PRECISION DEFAULT 0 NOT NULL,
    stock_max DOUBLE PRECISION DEFAULT 0 NOT NULL,
    is_stock_control_enabled BOOLEAN DEFAULT false,
    expiration_date TIMESTAMP,
    alert_exp_days INTEGER DEFAULT 15,
    is_alert_exp_active BOOLEAN DEFAULT false
);

-- Tabla PRODUCT_PHOTO (Fotos de Producto)
CREATE TABLE fvpos_product_photo (
    photo_id SERIAL PRIMARY KEY,
    filename VARCHAR(255) UNIQUE NOT NULL,
    is_active BOOLEAN DEFAULT true,
    position INTEGER DEFAULT 0,
    product_id INTEGER REFERENCES fvpos_product(product_id)
);

-- Tabla SUPPLIER_FOR_PRODUCT (Proveedores por Producto)
CREATE TABLE fvpos_supplier_for_product (
    supplier_for_product_id SERIAL PRIMARY KEY,
    supplier_number INTEGER DEFAULT 0 NOT NULL,
    cost_price DOUBLE PRECISION DEFAULT 0 NOT NULL,
    default_supplier BOOLEAN DEFAULT false,
    supplier_id INTEGER REFERENCES fvpos_supplier(supplier_id),
    product_id INTEGER NOT NULL REFERENCES fvpos_product(product_id),
    last_updated_date TIMESTAMP
);

-- Tabla PRICE_LIST (Lista de Precios)
CREATE TABLE fvpos_price_list (
    list_id SERIAL PRIMARY KEY,
    list_name VARCHAR(255) UNIQUE NOT NULL,
    is_active BOOLEAN DEFAULT true
);

-- Tabla PRODUCT_PRICE (Precios por Producto)
CREATE TABLE fvpos_product_price (
    price_id SERIAL PRIMARY KEY,
    product_id INTEGER REFERENCES fvpos_product(product_id),
    price_list_id INTEGER REFERENCES fvpos_price_list(list_id),
    cost_price DOUBLE PRECISION DEFAULT 0 NOT NULL,
    net_price DOUBLE PRECISION DEFAULT 0 NOT NULL,
    gross_margin DOUBLE PRECISION DEFAULT 0 NOT NULL,
    previous_selling_price DOUBLE PRECISION DEFAULT 0 NOT NULL,
    selling_price DOUBLE PRECISION DEFAULT 0 NOT NULL,
    internal_tax DOUBLE PRECISION DEFAULT 0 NOT NULL,
    previous_updated_price TIMESTAMP,
    last_updated_price TIMESTAMP,
    vat_id INTEGER NOT NULL REFERENCES fvpos_vat(vat_id)
);

-- Tabla CREDIT_CARD (Tarjeta de Crédito)
CREATE TABLE fvpos_credit_card (
    card_id SERIAL PRIMARY KEY,
    card_name VARCHAR(255) UNIQUE NOT NULL,
    is_active BOOLEAN DEFAULT true
);

-- Tabla DEBIT_CARD (Tarjeta de Débito)
CREATE TABLE fvpos_debit_card (
    card_id SERIAL PRIMARY KEY,
    card_name VARCHAR(255) UNIQUE NOT NULL,
    is_active BOOLEAN DEFAULT true
);

-- Tabla ORDER (Pedido/Venta)
CREATE TABLE fvpos_order (
    order_id SERIAL PRIMARY KEY,
    creation_date TIMESTAMP NOT NULL,
    sale_date TIMESTAMP NOT NULL,
    custom_sale_date BOOLEAN DEFAULT false,
    items_qty INTEGER DEFAULT 0 NOT NULL,
    subtotal DOUBLE PRECISION DEFAULT 0 NOT NULL,
    inner_taxes DOUBLE PRECISION DEFAULT 0 NOT NULL,
    discount_percent DOUBLE PRECISION DEFAULT 0 NOT NULL,
    discount_amount DOUBLE PRECISION DEFAULT 0 NOT NULL,
    surcharge_percent DOUBLE PRECISION DEFAULT 0 NOT NULL,
    surcharge_amount DOUBLE PRECISION DEFAULT 0 NOT NULL,
    total DOUBLE PRECISION DEFAULT 0 NOT NULL,
    status VARCHAR(255) DEFAULT 'PENDING' NOT NULL,
    receipt_type_id INTEGER REFERENCES fvpos_receipt_type(receipt_type_id),
    receipt_number VARCHAR(255),
    delivery_note_number INTEGER,
    vat_condition_id INTEGER NOT NULL REFERENCES fvpos_vat_condition(condition_id),
    company_vat_condition_id INTEGER NOT NULL REFERENCES fvpos_vat_condition(condition_id),
    customer_id INTEGER NOT NULL REFERENCES fvpos_customer(customer_id),
    cashier_id INTEGER NOT NULL REFERENCES fvpos_employee(employee_id),
    standard_vat_value DOUBLE PRECISION DEFAULT 21.0 NOT NULL,
    reduced_vat_value DOUBLE PRECISION DEFAULT 10.5 NOT NULL,
    payment_cash_amt DOUBLE PRECISION DEFAULT 0 NOT NULL,
    payment_credit_card_amt DOUBLE PRECISION DEFAULT 0 NOT NULL,
    credit_card_id INTEGER REFERENCES fvpos_credit_card(card_id),
    payment_on_account_amt DOUBLE PRECISION DEFAULT 0 NOT NULL,
    payment_debit_card_amt DOUBLE PRECISION DEFAULT 0 NOT NULL,
    debit_card_id INTEGER REFERENCES fvpos_debit_card(card_id),
    payment_check_amt DOUBLE PRECISION DEFAULT 0 NOT NULL,
    payment_tickets_amt DOUBLE PRECISION DEFAULT 0 NOT NULL,
    payment_net_cash_amt DOUBLE PRECISION DEFAULT 0 NOT NULL,
    payment_net_credit_card_amt DOUBLE PRECISION DEFAULT 0 NOT NULL,
    payment_net_on_account_amt DOUBLE PRECISION DEFAULT 0 NOT NULL,
    payment_net_debit_card_amt DOUBLE PRECISION DEFAULT 0 NOT NULL,
    payment_net_check_amt DOUBLE PRECISION DEFAULT 0 NOT NULL,
    payment_net_tickets_amt DOUBLE PRECISION DEFAULT 0 NOT NULL,
    pos_number INTEGER DEFAULT 0 NOT NULL,
    cash_number INTEGER DEFAULT 0 NOT NULL,
    observations TEXT DEFAULT '',
    price_list_id INTEGER REFERENCES fvpos_price_list(list_id),
    -- Campos AFIP
    afip_pto_vta INTEGER DEFAULT 0,
    afip_cbte_tipo INTEGER DEFAULT 0,
    afip_concepto INTEGER DEFAULT 0,
    afip_doc_tipo INTEGER DEFAULT 0,
    afip_doc_nro BIGINT,
    afip_cbte_desde BIGINT,
    afip_cbte_hasta BIGINT,
    afip_cbte_fch VARCHAR(255) DEFAULT '',
    afip_fch_serv_desde VARCHAR(255),
    afip_fch_serv_hasta VARCHAR(255),
    afip_fch_vto_pago VARCHAR(255),
    afip_cae VARCHAR(255),
    afip_cae_fch_vto VARCHAR(255),
    afip_bar_code VARCHAR(255) DEFAULT ''
);

-- Tabla ORDER_LINE (Línea de Pedido)
CREATE TABLE fvpos_order_line (
    order_line_id SERIAL PRIMARY KEY,
    line_number INTEGER DEFAULT 0 NOT NULL,
    qty DOUBLE PRECISION DEFAULT 0 NOT NULL,
    price DOUBLE PRECISION DEFAULT 0 NOT NULL,
    vat_value DOUBLE PRECISION DEFAULT 0 NOT NULL,
    in_offer BOOLEAN DEFAULT false,
    product_id INTEGER REFERENCES fvpos_product(product_id),
    category_id INTEGER REFERENCES fvpos_product_category(category_id),
    order_id INTEGER REFERENCES fvpos_order(order_id),
    description VARCHAR(255) DEFAULT '',
    cost_price DOUBLE PRECISION DEFAULT 0 NOT NULL,
    internal_tax DOUBLE PRECISION DEFAULT 0 NOT NULL,
    profit_margin DOUBLE PRECISION DEFAULT 0 NOT NULL
);

-- Tabla PURCHASE (Compra)
CREATE TABLE fvpos_purchase (
    purchase_id SERIAL PRIMARY KEY,
    creation_date TIMESTAMP NOT NULL,
    purchase_date TIMESTAMP NOT NULL,
    custom_purchase_date BOOLEAN DEFAULT false,
    items_qty INTEGER DEFAULT 0 NOT NULL,
    subtotal DOUBLE PRECISION DEFAULT 0 NOT NULL,
    inner_taxes DOUBLE PRECISION DEFAULT 0 NOT NULL,
    total DOUBLE PRECISION DEFAULT 0 NOT NULL,
    status VARCHAR(255) DEFAULT 'PENDING' NOT NULL,
    receipt_type_id INTEGER REFERENCES fvpos_receipt_type(receipt_type_id),
    receipt_number VARCHAR(255) DEFAULT '',
    vat_condition_id INTEGER REFERENCES fvpos_vat_condition(condition_id),
    supplier_id INTEGER REFERENCES fvpos_supplier(supplier_id),
    cashier_id INTEGER NOT NULL REFERENCES fvpos_employee(employee_id)
);

-- Tabla PURCHASE_LINE (Línea de Compra)
CREATE TABLE fvpos_purchase_line (
    purchase_line_id SERIAL PRIMARY KEY,
    line_number INTEGER DEFAULT 0 NOT NULL,
    qty DOUBLE PRECISION DEFAULT 0 NOT NULL,
    price DOUBLE PRECISION DEFAULT 0 NOT NULL,
    vat_value DOUBLE PRECISION DEFAULT 0 NOT NULL,
    product_id INTEGER REFERENCES fvpos_product(product_id),
    purchase_id INTEGER REFERENCES fvpos_purchase(purchase_id),
    description VARCHAR(255) DEFAULT ''
);

-- Tabla BUDGET (Presupuesto)
CREATE TABLE fvpos_budget (
    budget_id SERIAL PRIMARY KEY,
    creation_date TIMESTAMP NOT NULL,
    budget_date TIMESTAMP NOT NULL,
    budget_number INTEGER,
    items_qty INTEGER DEFAULT 0 NOT NULL,
    subtotal DOUBLE PRECISION DEFAULT 0 NOT NULL,
    inner_taxes DOUBLE PRECISION DEFAULT 0 NOT NULL,
    total DOUBLE PRECISION DEFAULT 0 NOT NULL,
    status VARCHAR(255) DEFAULT 'PENDING' NOT NULL,
    vat_condition_id INTEGER NOT NULL REFERENCES fvpos_vat_condition(condition_id),
    sale_condition_id INTEGER NOT NULL REFERENCES fvpos_sale_condition(condition_id),
    customer_id INTEGER NOT NULL REFERENCES fvpos_customer(customer_id),
    cashier_id INTEGER NOT NULL REFERENCES fvpos_employee(employee_id),
    pos_number INTEGER DEFAULT 0 NOT NULL
);

-- Tabla BUDGET_LINE (Línea de Presupuesto)
CREATE TABLE fvpos_budget_line (
    budget_line_id SERIAL PRIMARY KEY,
    line_number INTEGER DEFAULT 0 NOT NULL,
    qty DOUBLE PRECISION DEFAULT 0 NOT NULL,
    price DOUBLE PRECISION DEFAULT 0 NOT NULL,
    vat_value DOUBLE PRECISION DEFAULT 0 NOT NULL,
    product_id INTEGER REFERENCES fvpos_product(product_id),
    budget_id INTEGER REFERENCES fvpos_budget(budget_id),
    description VARCHAR(255) DEFAULT ''
);

-- Tabla CASH_OPERATION (Operación de Caja)
CREATE TABLE fvpos_cash_operation (
    cash_operation_id SERIAL PRIMARY KEY,
    creation_date TIMESTAMP,
    operation_date TIMESTAMP,
    last_updated_date TIMESTAMP,
    is_active BOOLEAN DEFAULT true NOT NULL,
    type INTEGER DEFAULT 0 NOT NULL,
    description VARCHAR(255) DEFAULT '',
    observations TEXT DEFAULT '',
    amount DOUBLE PRECISION DEFAULT 0 NOT NULL,
    is_system BOOLEAN DEFAULT false NOT NULL,
    cash_number INTEGER DEFAULT 0 NOT NULL,
    author_id INTEGER REFERENCES fvpos_employee(employee_id)
);

-- Tabla CUSTOMER_ON_ACCOUNT_OP (Operación en Cuenta Corriente de Cliente)
CREATE TABLE fvpos_customer_on_account_op (
    operation_id SERIAL PRIMARY KEY,
    creation_date TIMESTAMP,
    operation_date TIMESTAMP,
    last_updated_date TIMESTAMP,
    is_active BOOLEAN DEFAULT true NOT NULL,
    type INTEGER DEFAULT 0 NOT NULL,
    description VARCHAR(255) DEFAULT '',
    observations TEXT DEFAULT '',
    amount DOUBLE PRECISION DEFAULT 0 NOT NULL,
    is_system BOOLEAN DEFAULT false NOT NULL,
    cash_number INTEGER DEFAULT 0 NOT NULL,
    author_id INTEGER REFERENCES fvpos_employee(employee_id)
);

-- Tabla SUPPLIER_ON_ACCOUNT_OP (Operación en Cuenta Corriente de Proveedor)
CREATE TABLE fvpos_supplier_on_account_op (
    operation_id SERIAL PRIMARY KEY,
    creation_date TIMESTAMP,
    operation_date TIMESTAMP,
    last_updated_date TIMESTAMP,
    is_active BOOLEAN DEFAULT true NOT NULL,
    type INTEGER DEFAULT 0 NOT NULL,
    description VARCHAR(255) DEFAULT '',
    observations TEXT DEFAULT '',
    amount DOUBLE PRECISION DEFAULT 0 NOT NULL,
    is_system BOOLEAN DEFAULT false NOT NULL,
    cash_number INTEGER DEFAULT 0 NOT NULL,
    author_id INTEGER REFERENCES fvpos_employee(employee_id)
);

-- Tabla NOTA_DE_CREDITO (Nota de Crédito)
CREATE TABLE fvpos_nota_de_credito (
    nota_id SERIAL PRIMARY KEY,
    creation_date TIMESTAMP NOT NULL,
    cbte_tipo_id INTEGER REFERENCES fvpos_receipt_type(receipt_type_id),
    cbte_fch TIMESTAMP NOT NULL,
    cbte_nro BIGINT DEFAULT -1,
    cashier_id INTEGER NOT NULL REFERENCES fvpos_employee(employee_id),
    cash_number INTEGER DEFAULT 0 NOT NULL,
    status VARCHAR(255) DEFAULT 'COMPLETED' NOT NULL,
    cbte_asoc_tipo_id INTEGER REFERENCES fvpos_receipt_type(receipt_type_id),
    cbte_asoc_pto_vta INTEGER DEFAULT 0,
    cbte_asoc_nro BIGINT DEFAULT -1,
    customer_id INTEGER REFERENCES fvpos_customer(customer_id),
    order_id INTEGER REFERENCES fvpos_order(order_id),
    product_code VARCHAR(255) DEFAULT '',
    product_description VARCHAR(255) DEFAULT '',
    product_qty DOUBLE PRECISION DEFAULT 0 NOT NULL,
    product_price DOUBLE PRECISION DEFAULT 0 NOT NULL,
    product_vat_value DOUBLE PRECISION DEFAULT 0 NOT NULL
);

-- Tabla APP_CONFIG (Configuración de la Aplicación)
CREATE TABLE fvpos_app_config (
    app_config_id SERIAL PRIMARY KEY,
    company_name VARCHAR(255) DEFAULT '',
    company_business_name VARCHAR(255) DEFAULT '',
    company_cuit VARCHAR(255) DEFAULT '',
    company_address_street VARCHAR(255) DEFAULT '',
    company_address_number VARCHAR(255) DEFAULT '',
    company_address_other VARCHAR(255) DEFAULT '',
    company_postal_code VARCHAR(255) DEFAULT '',
    company_city VARCHAR(255) DEFAULT '',
    company_province VARCHAR(255) DEFAULT '',
    company_phone VARCHAR(255) DEFAULT '',
    company_email VARCHAR(255) DEFAULT '',
    company_gross_income_number VARCHAR(255) DEFAULT '',
    company_start_activities_date TIMESTAMP,
    company_pos_number INTEGER DEFAULT 1 NOT NULL,
    company_cash_qty INTEGER DEFAULT 1 NOT NULL,
    company_vat_condition_id INTEGER NOT NULL REFERENCES fvpos_vat_condition(condition_id),
    company_website VARCHAR(255) DEFAULT '',
    company_owner_first_name VARCHAR(255) DEFAULT '',
    company_owner_last_name VARCHAR(255) DEFAULT '',
    company_owner_email VARCHAR(255) DEFAULT '',
    company_owner_phone VARCHAR(255) DEFAULT '',
    company_owner_mobile VARCHAR(255) DEFAULT '',
    company_logo VARCHAR(255) DEFAULT '',
    store_web_alias VARCHAR(255) DEFAULT '',
    store_web_username VARCHAR(255) DEFAULT '',
    store_web_password VARCHAR(255) DEFAULT '',
    app_name VARCHAR(255) DEFAULT 'FácilVirtual',
    app_model VARCHAR(255) DEFAULT 'Supermercados',
    app_version VARCHAR(255) DEFAULT '1.0',
    manager_password VARCHAR(255) DEFAULT '_root123'
);

-- Tabla WORKSTATION_CONFIG (Configuración de la Estación de Trabajo)
CREATE TABLE fvpos_workstation_config (
    workstation_config_id SERIAL PRIMARY KEY,
    cash_number INTEGER DEFAULT 1,
    cash_dept1_id INTEGER REFERENCES fvpos_product_category(category_id),
    cash_dept2_id INTEGER REFERENCES fvpos_product_category(category_id),
    cash_dept3_id INTEGER REFERENCES fvpos_product_category(category_id),
    cash_dept4_id INTEGER REFERENCES fvpos_product_category(category_id),
    cash_dept5_id INTEGER REFERENCES fvpos_product_category(category_id),
    cash_dept6_id INTEGER REFERENCES fvpos_product_category(category_id),
    cash_dept7_id INTEGER REFERENCES fvpos_product_category(category_id),
    cash_dept8_id INTEGER REFERENCES fvpos_product_category(category_id),
    cash_amount DOUBLE PRECISION DEFAULT 0,
    is_cash_opened BOOLEAN DEFAULT false NOT NULL,
    is_active BOOLEAN DEFAULT true NOT NULL,
    is_server BOOLEAN DEFAULT false NOT NULL,
    installation_date TIMESTAMP,
    installation_code VARCHAR(255) DEFAULT ''
); 