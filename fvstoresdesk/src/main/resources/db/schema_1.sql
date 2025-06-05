-- Crear las tablas en el orden correcto considerando las dependencias

-- Tabla FVPOS_VAT (IVA)
CREATE TABLE fvpos_vat (
    vat_id SERIAL PRIMARY KEY,
    vat_name VARCHAR(255) NOT NULL,
    vat_value DECIMAL(10,2) NOT NULL
);

-- Tabla FVPOS_VAT_CONDITION (Condición de IVA)
CREATE TABLE fvpos_vat_condition (
    condition_id SERIAL PRIMARY KEY,
    abbreviate_name VARCHAR(10) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    is_available_for_company BOOLEAN DEFAULT FALSE,
    is_available_for_customer BOOLEAN DEFAULT TRUE,
    condition_name VARCHAR(255) NOT NULL
);

-- Tabla FVPOS_SALE_CONDITION (Condición de Venta)
CREATE TABLE fvpos_sale_condition (
    condition_id SERIAL PRIMARY KEY,
    abbreviate_name VARCHAR(10) NOT NULL,
    is_active BOOLEAN DEFAULT TRUE,
    is_available_for_company BOOLEAN DEFAULT FALSE,
    is_available_for_customer BOOLEAN DEFAULT TRUE,
    condition_name VARCHAR(255) NOT NULL
);

-- Tabla FVPOS_RECEIPT_TYPE (Tipo de Comprobante)
CREATE TABLE fvpos_receipt_type (
    receipt_type_id SERIAL PRIMARY KEY,
    is_active BOOLEAN DEFAULT TRUE,
    is_available_for_order BOOLEAN DEFAULT TRUE,
    is_available_for_purchase BOOLEAN DEFAULT TRUE,
    type_name VARCHAR(255) NOT NULL
);

-- Tabla FVPOS_BRAND (Marca)
CREATE TABLE fvpos_brand (
    brand_id SERIAL PRIMARY KEY,
    is_active BOOLEAN DEFAULT TRUE,
    brand_name VARCHAR(255) NOT NULL,
    photo BYTEA
);

-- Tabla FVPOS_CREDIT_CARD (Tarjeta de Crédito)
CREATE TABLE fvpos_credit_card (
    card_id SERIAL PRIMARY KEY,
    is_active BOOLEAN DEFAULT TRUE,
    card_name VARCHAR(255) NOT NULL
);

-- Tabla FVPOS_DEBIT_CARD (Tarjeta de Débito)
CREATE TABLE fvpos_debit_card (
    card_id SERIAL PRIMARY KEY,
    is_active BOOLEAN DEFAULT TRUE,
    card_name VARCHAR(255) NOT NULL
);

-- Tabla FVPOS_EMPLOYEE (Empleado)
CREATE TABLE fvpos_employee (
    employee_id SERIAL PRIMARY KEY,
    is_active BOOLEAN DEFAULT TRUE,
    address_extra VARCHAR(255),
    address_number VARCHAR(50),
    address_street VARCHAR(255),
    is_admin BOOLEAN DEFAULT FALSE,
    allow_apply_discount BOOLEAN DEFAULT FALSE,
    allow_apply_surcharge BOOLEAN DEFAULT FALSE,
    allow_close_cash BOOLEAN DEFAULT FALSE,
    allow_create_income BOOLEAN DEFAULT FALSE,
    allow_create_order BOOLEAN DEFAULT FALSE,
    allow_create_outflow BOOLEAN DEFAULT FALSE,
    allow_create_purchase BOOLEAN DEFAULT FALSE,
    allow_login BOOLEAN DEFAULT TRUE,
    allow_modify_price BOOLEAN DEFAULT FALSE,
    allow_module_cash BOOLEAN DEFAULT FALSE,
    allow_module_customers BOOLEAN DEFAULT FALSE,
    allow_module_lists BOOLEAN DEFAULT FALSE,
    allow_module_orders BOOLEAN DEFAULT FALSE,
    allow_module_products BOOLEAN DEFAULT FALSE,
    allow_module_purchases BOOLEAN DEFAULT FALSE,
    allow_module_reports BOOLEAN DEFAULT FALSE,
    allow_module_suppliers BOOLEAN DEFAULT FALSE,
    allow_module_tools BOOLEAN DEFAULT FALSE,
    allow_open_cash BOOLEAN DEFAULT FALSE,
    city VARCHAR(255),
    commission_per_sale DECIMAL(10,2) DEFAULT 0,
    creation_date TIMESTAMP WITH TIME ZONE,
    cuil VARCHAR(50),
    document_number VARCHAR(50),
    document_type VARCHAR(50),
    email VARCHAR(255),
    first_name VARCHAR(255),
    job_position VARCHAR(255),
    last_name VARCHAR(255),
    last_updated_date TIMESTAMP WITH TIME ZONE,
    mobile VARCHAR(50),
    observations TEXT,
    password VARCHAR(255),
    phone VARCHAR(50),
    photo BYTEA,
    postal_code VARCHAR(20),
    is_shift1 BOOLEAN DEFAULT TRUE,
    is_shift2 BOOLEAN DEFAULT TRUE,
    is_shift3 BOOLEAN DEFAULT TRUE,
    username VARCHAR(255) UNIQUE,
    website VARCHAR(255)
);

-- Tabla FVPOS_CUSTOMER (Cliente)
CREATE TABLE fvpos_customer (
    customer_id SERIAL PRIMARY KEY,
    account_type VARCHAR(50),
    is_active BOOLEAN DEFAULT TRUE,
    address_extra VARCHAR(255),
    address_number VARCHAR(50),
    address_street VARCHAR(255),
    is_allow_on_account_op BOOLEAN DEFAULT FALSE,
    business_name VARCHAR(255),
    city VARCHAR(255),
    company_name VARCHAR(255),
    contact_name VARCHAR(255),
    creation_date TIMESTAMP WITH TIME ZONE,
    cuil VARCHAR(50),
    cuit VARCHAR(50),
    document_number VARCHAR(50),
    document_type VARCHAR(50),
    email VARCHAR(255),
    first_name VARCHAR(255),
    fiscal_address_extra VARCHAR(255),
    fiscal_address_number VARCHAR(50),
    fiscal_address_street VARCHAR(255),
    gross_income_number VARCHAR(50),
    last_name VARCHAR(255),
    last_updated_date TIMESTAMP WITH TIME ZONE,
    mobile VARCHAR(50),
    observations TEXT,
    on_account_limit DECIMAL(10,2) DEFAULT 0,
    is_on_account_limited BOOLEAN DEFAULT FALSE,
    on_account_total DECIMAL(10,2) DEFAULT 0,
    phone VARCHAR(50),
    photo BYTEA,
    postal_code VARCHAR(20),
    province VARCHAR(255),
    website VARCHAR(255),
    vat_condition_id INTEGER REFERENCES fvpos_vat_condition(condition_id)
);

-- Tabla FVPOS_SUPPLIER (Proveedor)
CREATE TABLE fvpos_supplier (
    supplier_id SERIAL PRIMARY KEY,
    is_active BOOLEAN DEFAULT TRUE,
    address_extra VARCHAR(255),
    address_number VARCHAR(50),
    address_street VARCHAR(255),
    is_allow_on_account_op BOOLEAN DEFAULT FALSE,
    business_name VARCHAR(255),
    city VARCHAR(255),
    company_name VARCHAR(255),
    contact_name VARCHAR(255),
    creation_date TIMESTAMP WITH TIME ZONE,
    cuit VARCHAR(50),
    email VARCHAR(255),
    fiscal_address_extra VARCHAR(255),
    fiscal_address_number VARCHAR(50),
    fiscal_address_street VARCHAR(255),
    gross_income_number VARCHAR(50),
    last_updated_date TIMESTAMP WITH TIME ZONE,
    mobile VARCHAR(50),
    observations TEXT,
    on_account_limit DECIMAL(10,2) DEFAULT 0,
    is_on_account_limited BOOLEAN DEFAULT FALSE,
    on_account_total DECIMAL(10,2) DEFAULT 0,
    phone VARCHAR(50),
    photo BYTEA,
    postal_code VARCHAR(20),
    website VARCHAR(255),
    vat_condition_id INTEGER REFERENCES fvpos_vat_condition(condition_id)
);

-- Tabla FVPOS_PRODUCT_CATEGORY (Categoría de Producto)
CREATE TABLE fvpos_product_category (
    category_id SERIAL PRIMARY KEY,
    is_active BOOLEAN DEFAULT TRUE,
    category_name VARCHAR(255) NOT NULL,
    photo BYTEA,
    position INTEGER DEFAULT 0,
    positionincash INTEGER DEFAULT 0,
    parent_id INTEGER REFERENCES fvpos_product_category(category_id),
    vat_id INTEGER REFERENCES fvpos_vat(vat_id)
);

-- Tabla FVPOS_PRODUCT (Producto)
CREATE TABLE fvpos_product (
    product_id SERIAL PRIMARY KEY,
    is_alert_exp_active BOOLEAN DEFAULT FALSE,
    alert_exp_days INTEGER DEFAULT 15,
    bar_code VARCHAR(255),
    cost_price DECIMAL(10,2) DEFAULT 0,
    creation_date TIMESTAMP WITH TIME ZONE,
    product_description TEXT,
    is_discontinued BOOLEAN DEFAULT FALSE,
    expiration_date TIMESTAMP WITH TIME ZONE,
    gross_margin DECIMAL(10,2) DEFAULT 0,
    in_offer BOOLEAN DEFAULT FALSE,
    in_web BOOLEAN DEFAULT FALSE,
    internal_tax DECIMAL(10,2) DEFAULT 0,
    last_purchase_date TIMESTAMP WITH TIME ZONE,
    last_sale_date TIMESTAMP WITH TIME ZONE,
    last_updated_category TIMESTAMP WITH TIME ZONE,
    last_updated_description TIMESTAMP WITH TIME ZONE,
    last_updated_price TIMESTAMP WITH TIME ZONE,
    net_price DECIMAL(10,2) DEFAULT 0,
    offer_end_date TIMESTAMP WITH TIME ZONE,
    offer_price DECIMAL(10,2) DEFAULT 0,
    offer_start_date TIMESTAMP WITH TIME ZONE,
    photo BYTEA,
    previous_purchase_date TIMESTAMP WITH TIME ZONE,
    previous_sale_date TIMESTAMP WITH TIME ZONE,
    previous_selling_price DECIMAL(10,2) DEFAULT 0,
    previous_updated_price TIMESTAMP WITH TIME ZONE,
    quantity DECIMAL(10,2) DEFAULT 0,
    quantity_unit VARCHAR(50),
    selling_price DECIMAL(10,2) DEFAULT 0,
    selling_unit VARCHAR(50),
    product_short_description VARCHAR(255),
    stock DECIMAL(10,2) DEFAULT 0,
    is_stock_control_enabled BOOLEAN DEFAULT FALSE,
    stock_max DECIMAL(10,2) DEFAULT 0,
    stock_min DECIMAL(10,2) DEFAULT 0,
    brand_id INTEGER REFERENCES fvpos_brand(brand_id),
    category_id INTEGER REFERENCES fvpos_product_category(category_id),
    subcategory1_id INTEGER REFERENCES fvpos_product_category(category_id),
    subcategory2_id INTEGER REFERENCES fvpos_product_category(category_id),
    supplier_id INTEGER REFERENCES fvpos_supplier(supplier_id),
    vat_id INTEGER REFERENCES fvpos_vat(vat_id)
);

-- Tabla FVPOS_PRODUCT_PHOTO (Fotos de Producto)
CREATE TABLE fvpos_product_photo (
    photo_id SERIAL PRIMARY KEY,
    photo BYTEA,
    product_id INTEGER REFERENCES fvpos_product(product_id)
);

-- Tabla FVPOS_SUPPLIER_FOR_PRODUCT (Proveedores por Producto)
CREATE TABLE fvpos_supplier_for_product (
    supplier_for_product_id SERIAL PRIMARY KEY,
    cost_price DECIMAL(10,2) DEFAULT 0,
    default_supplier BOOLEAN DEFAULT FALSE,
    last_updated_date TIMESTAMP WITH TIME ZONE,
    supplier_number INTEGER,
    product_id INTEGER REFERENCES fvpos_product(product_id),
    supplier_id INTEGER REFERENCES fvpos_supplier(supplier_id)
);

-- Tabla FVPOS_PRICE_LIST (Lista de Precios)
CREATE TABLE fvpos_price_list (
    list_id SERIAL PRIMARY KEY,
    is_active BOOLEAN DEFAULT TRUE,
    list_name VARCHAR(255) NOT NULL
);

-- Tabla FVPOS_PRODUCT_PRICE (Precios de Producto)
CREATE TABLE fvpos_product_price (
    price_id SERIAL PRIMARY KEY,
    cost_price DECIMAL(10,2) DEFAULT 0,
    gross_margin DECIMAL(10,2) DEFAULT 0,
    internal_tax DECIMAL(10,2) DEFAULT 0,
    last_updated_price TIMESTAMP WITH TIME ZONE,
    net_price DECIMAL(10,2) DEFAULT 0,
    previous_selling_price DECIMAL(10,2) DEFAULT 0,
    previous_updated_price TIMESTAMP WITH TIME ZONE,
    selling_price DECIMAL(10,2) DEFAULT 0,
    price_list_id INTEGER REFERENCES fvpos_price_list(list_id),
    product_id INTEGER REFERENCES fvpos_product(product_id),
    vat_id INTEGER REFERENCES fvpos_vat(vat_id)
);

-- Tabla FVPOS_ORDER (Pedido/Orden)
CREATE TABLE fvpos_order (
    order_id SERIAL PRIMARY KEY,
    afip_bar_code TEXT,
    afip_cae VARCHAR(255),
    afip_cae_fch_vto VARCHAR(50),
    afip_cbte_desde INTEGER,
    afip_cbte_fch VARCHAR(50),
    afip_cbte_hasta INTEGER,
    afip_cbte_tipo INTEGER,
    afip_concepto INTEGER,
    afip_doc_nro BIGINT,
    afip_doc_tipo INTEGER,
    afip_fch_serv_desde VARCHAR(50),
    afip_fch_serv_hasta VARCHAR(50),
    afip_fch_vto_pago VARCHAR(50),
    afip_pto_vta INTEGER,
    payment_cash_amt DECIMAL(10,2) DEFAULT 0,
    cash_number INTEGER,
    payment_check_amt DECIMAL(10,2) DEFAULT 0,
    creation_date TIMESTAMP WITH TIME ZONE,
    payment_credit_card_amt DECIMAL(10,2) DEFAULT 0,
    custom_sale_date BOOLEAN DEFAULT FALSE,
    payment_debit_card_amt DECIMAL(10,2) DEFAULT 0,
    delivery_note_number INTEGER,
    discount_amount DECIMAL(10,2) DEFAULT 0,
    discount_percent DECIMAL(5,2) DEFAULT 0,
    inner_taxes DECIMAL(10,2) DEFAULT 0,
    items_qty INTEGER DEFAULT 0,
    payment_net_cash_amt DECIMAL(10,2) DEFAULT 0,
    payment_net_check_amt DECIMAL(10,2) DEFAULT 0,
    payment_net_credit_card_amt DECIMAL(10,2) DEFAULT 0,
    payment_net_debit_card_amt DECIMAL(10,2) DEFAULT 0,
    payment_net_on_account_amt DECIMAL(10,2) DEFAULT 0,
    payment_net_tickets_amt DECIMAL(10,2) DEFAULT 0,
    observations TEXT,
    payment_on_account_amt DECIMAL(10,2) DEFAULT 0,
    pos_number INTEGER,
    receipt_number VARCHAR(50),
    reduced_vat_value DECIMAL(5,2) DEFAULT 10.5,
    sale_date TIMESTAMP WITH TIME ZONE,
    standard_vat_value DECIMAL(5,2) DEFAULT 21.0,
    status VARCHAR(50),
    subtotal DECIMAL(10,2) DEFAULT 0,
    surcharge_amount DECIMAL(10,2) DEFAULT 0,
    surcharge_percent DECIMAL(5,2) DEFAULT 0,
    payment_tickets_amt DECIMAL(10,2) DEFAULT 0,
    total DECIMAL(10,2) DEFAULT 0,
    cashier_id INTEGER REFERENCES fvpos_employee(employee_id),
    company_vat_condition_id INTEGER REFERENCES fvpos_vat_condition(condition_id),
    credit_card_id INTEGER REFERENCES fvpos_credit_card(card_id),
    customer_id INTEGER REFERENCES fvpos_customer(customer_id),
    debit_card_id INTEGER REFERENCES fvpos_debit_card(card_id),
    price_list_id INTEGER REFERENCES fvpos_price_list(list_id),
    receipt_type_id INTEGER REFERENCES fvpos_receipt_type(receipt_type_id),
    vat_condition_id INTEGER REFERENCES fvpos_vat_condition(condition_id)
);

-- Tabla FVPOS_ORDER_LINE (Línea de Pedido)
CREATE TABLE fvpos_order_line (
    order_line_id SERIAL PRIMARY KEY,
    cost_price DECIMAL(10,2) DEFAULT 0,
    description TEXT,
    in_offer BOOLEAN DEFAULT FALSE,
    internal_tax DECIMAL(10,2) DEFAULT 0,
    line_number INTEGER,
    price DECIMAL(10,2) DEFAULT 0,
    profit_margin DECIMAL(10,2) DEFAULT 0,
    qty DECIMAL(10,2) DEFAULT 0,
    vat_value DECIMAL(5,2) DEFAULT 21.0,
    category_id INTEGER REFERENCES fvpos_product_category(category_id),
    order_id INTEGER REFERENCES fvpos_order(order_id),
    product_id INTEGER REFERENCES fvpos_product(product_id)
);

-- Tabla FVPOS_PURCHASE (Compra)
CREATE TABLE fvpos_purchase (
    purchase_id SERIAL PRIMARY KEY,
    creation_date TIMESTAMP WITH TIME ZONE NOT NULL,
    purchase_date TIMESTAMP WITH TIME ZONE NOT NULL,
    custom_purchase_date BOOLEAN DEFAULT FALSE,
    items_qty INTEGER DEFAULT 0 NOT NULL,
    subtotal DECIMAL(10,2) DEFAULT 0 NOT NULL,
    inner_taxes DECIMAL(10,2) DEFAULT 0 NOT NULL,
    total DECIMAL(10,2) DEFAULT 0 NOT NULL,
    status VARCHAR(50) DEFAULT 'PENDING' NOT NULL,
    receipt_type_id INTEGER REFERENCES fvpos_receipt_type(receipt_type_id),
    receipt_number VARCHAR(50) DEFAULT '',
    vat_condition_id INTEGER REFERENCES fvpos_vat_condition(condition_id),
    supplier_id INTEGER REFERENCES fvpos_supplier(supplier_id),
    cashier_id INTEGER NOT NULL REFERENCES fvpos_employee(employee_id),
    payment_card_amt DECIMAL(10,2) DEFAULT 0 NOT NULL,
    payment_cash_amt DECIMAL(10,2) DEFAULT 0 NOT NULL,
    payment_on_account_amt DECIMAL(10,2) DEFAULT 0 NOT NULL,
    payment_check_amt DECIMAL(10,2) DEFAULT 0 NOT NULL,
    payment_tickets_amt DECIMAL(10,2) DEFAULT 0 NOT NULL,
    pos_number INTEGER DEFAULT 0 NOT NULL,
    cash_number INTEGER DEFAULT 0 NOT NULL,
    observations TEXT DEFAULT '',
    is_update_prices BOOLEAN DEFAULT TRUE NOT NULL,
    is_update_stock BOOLEAN DEFAULT TRUE NOT NULL,
    standard_vat_value DECIMAL(5,2) DEFAULT 21.0 NOT NULL,
    reduced_vat_value DECIMAL(5,2) DEFAULT 10.5 NOT NULL
);

-- Tabla FVPOS_PURCHASE_LINE (Línea de Compra)
CREATE TABLE fvpos_purchase_line (
    purchase_line_id SERIAL PRIMARY KEY,
    description TEXT,
    line_number INTEGER,
    price DECIMAL(10,2) DEFAULT 0,
    qty DECIMAL(10,2) DEFAULT 0,
    vat_value DECIMAL(5,2) DEFAULT 21.0,
    product_id INTEGER REFERENCES fvpos_product(product_id),
    purchase_id INTEGER REFERENCES fvpos_purchase(purchase_id)
);

-- Tabla FVPOS_BUDGET (Presupuesto)
CREATE TABLE fvpos_budget (
    budget_id SERIAL PRIMARY KEY,
    budget_date TIMESTAMP WITH TIME ZONE,
    budget_number INTEGER,
    cash_number INTEGER,
    creation_date TIMESTAMP WITH TIME ZONE,
    inner_taxes DECIMAL(10,2) DEFAULT 0,
    items_qty INTEGER DEFAULT 0,
    observations TEXT,
    pos_number INTEGER,
    reduced_vat_value DECIMAL(5,2) DEFAULT 10.5,
    standard_vat_value DECIMAL(5,2) DEFAULT 21.0,
    status VARCHAR(50),
    subtotal DECIMAL(10,2) DEFAULT 0,
    total DECIMAL(10,2) DEFAULT 0,
    cashier_id INTEGER REFERENCES fvpos_employee(employee_id),
    customer_id INTEGER REFERENCES fvpos_customer(customer_id),
    sale_condition_id INTEGER REFERENCES fvpos_sale_condition(condition_id),
    vat_condition_id INTEGER REFERENCES fvpos_vat_condition(condition_id)
);

-- Tabla FVPOS_BUDGET_LINE (Línea de Presupuesto)
CREATE TABLE fvpos_budget_line (
    budget_line_id SERIAL PRIMARY KEY,
    description TEXT,
    line_number INTEGER,
    price DECIMAL(10,2) DEFAULT 0,
    qty DECIMAL(10,2) DEFAULT 0,
    vat_value DECIMAL(5,2) DEFAULT 21.0,
    budget_id INTEGER REFERENCES fvpos_budget(budget_id),
    product_id INTEGER REFERENCES fvpos_product(product_id)
);

-- Tabla FVPOS_CASH_OPERATION (Operación de Caja)
CREATE TABLE fvpos_cash_operation (
    cash_operation_id SERIAL PRIMARY KEY,
    is_active BOOLEAN DEFAULT TRUE,
    amount DECIMAL(10,2) DEFAULT 0,
    cash_number INTEGER,
    creation_date TIMESTAMP WITH TIME ZONE,
    description TEXT,
    last_updated_date TIMESTAMP WITH TIME ZONE,
    observations TEXT,
    operation_date TIMESTAMP WITH TIME ZONE,
    is_system BOOLEAN DEFAULT FALSE,
    type INTEGER,
    author_id INTEGER REFERENCES fvpos_employee(employee_id),
    customer_operation_id INTEGER,
    order_id INTEGER REFERENCES fvpos_order(order_id),
    purchase_id INTEGER REFERENCES fvpos_purchase(purchase_id),
    supplier_operation_id INTEGER,
    cashiername VARCHAR(255),
    date TIMESTAMP WITH TIME ZONE,
    operationtype VARCHAR(50)
);

-- Tabla FVPOS_CUSTOMER_ON_ACCOUNT_OP (Operación en Cuenta Corriente de Cliente)
CREATE TABLE fvpos_customer_on_account_op (
    operation_id SERIAL PRIMARY KEY,
    is_active BOOLEAN DEFAULT TRUE,
    amount DECIMAL(10,2) DEFAULT 0,
    cash_number INTEGER,
    creation_date TIMESTAMP WITH TIME ZONE,
    description TEXT,
    last_updated_date TIMESTAMP WITH TIME ZONE,
    observations TEXT,
    operation_date TIMESTAMP WITH TIME ZONE,
    is_system BOOLEAN DEFAULT FALSE,
    type INTEGER,
    author_id INTEGER REFERENCES fvpos_employee(employee_id),
    customer_id INTEGER REFERENCES fvpos_customer(customer_id),
    order_id INTEGER REFERENCES fvpos_order(order_id)
);

-- Tabla FVPOS_SUPPLIER_ON_ACCOUNT_OP (Operación en Cuenta Corriente de Proveedor)
CREATE TABLE fvpos_supplier_on_account_op (
    operation_id SERIAL PRIMARY KEY,
    is_active BOOLEAN DEFAULT TRUE,
    amount DECIMAL(10,2) DEFAULT 0,
    cash_number INTEGER,
    creation_date TIMESTAMP WITH TIME ZONE,
    description TEXT,
    last_updated_date TIMESTAMP WITH TIME ZONE,
    observations TEXT,
    operation_date TIMESTAMP WITH TIME ZONE,
    is_system BOOLEAN DEFAULT FALSE,
    type INTEGER,
    author_id INTEGER REFERENCES fvpos_employee(employee_id),
    purchase_id INTEGER REFERENCES fvpos_purchase(purchase_id),
    supplier_id INTEGER REFERENCES fvpos_supplier(supplier_id)
);

-- Tabla FVPOS_NOTA_DE_CREDITO (Nota de Crédito)
CREATE TABLE fvpos_nota_de_credito (
    nota_id SERIAL PRIMARY KEY,
    afip_bar_code TEXT,
    afip_cae VARCHAR(255),
    afip_cae_fch_vto VARCHAR(50),
    afip_cbte_asoc_nro INTEGER,
    afip_cbte_asoc_pto_vta INTEGER,
    afip_cbte_asoc_tipo INTEGER,
    afip_cbte_desde INTEGER,
    afip_cbte_fch VARCHAR(50),
    afip_cbte_hasta INTEGER,
    afip_cbte_tipo INTEGER,
    afip_concepto INTEGER,
    afip_doc_nro BIGINT,
    afip_doc_tipo INTEGER,
    afip_fch_serv_desde VARCHAR(50),
    afip_fch_serv_hasta VARCHAR(50),
    afip_fch_vto_pago VARCHAR(50),
    afip_pto_vta INTEGER,
    cash_number INTEGER,
    cbte_asoc_nro INTEGER,
    cbte_asoc_pto_vta INTEGER,
    cbte_fch TIMESTAMP WITH TIME ZONE,
    cbte_nro INTEGER,
    creation_date TIMESTAMP WITH TIME ZONE,
    product_code VARCHAR(255),
    product_description TEXT,
    product_price DECIMAL(10,2) DEFAULT 0,
    product_qty DECIMAL(10,2) DEFAULT 0,
    product_vat_value DECIMAL(5,2) DEFAULT 21.0,
    status VARCHAR(50),
    cashier_id INTEGER REFERENCES fvpos_employee(employee_id),
    cbte_asoc_tipo_id INTEGER,
    cbte_tipo_id INTEGER,
    customer_id INTEGER REFERENCES fvpos_customer(customer_id),
    order_id INTEGER REFERENCES fvpos_order(order_id)
);

-- Tabla FVPOS_APP_CONFIG (Configuración de la Aplicación)
CREATE TABLE fvpos_app_config (
    app_config_id SERIAL PRIMARY KEY,
    is_afip_enabled_factura_a BOOLEAN DEFAULT FALSE,
    is_afip_enabled_factura_electronica BOOLEAN DEFAULT FALSE,
    afip_pto_vta INTEGER,
    app_model VARCHAR(255),
    app_name VARCHAR(255),
    app_version VARCHAR(50),
    budget_start_number INTEGER DEFAULT 1,
    cash_label VARCHAR(255),
    cash_number_label VARCHAR(255),
    cashier_label VARCHAR(255),
    company_address_number VARCHAR(50),
    company_address_other VARCHAR(255),
    company_address_street VARCHAR(255),
    company_business_name VARCHAR(255),
    company_cash_qty INTEGER DEFAULT 1,
    company_city VARCHAR(255),
    company_cuit VARCHAR(50),
    company_email VARCHAR(255),
    company_gross_income_number VARCHAR(50),
    company_logo BYTEA,
    company_name VARCHAR(255),
    company_owner_email VARCHAR(255),
    company_owner_first_name VARCHAR(255),
    company_owner_last_name VARCHAR(255),
    company_owner_mobile VARCHAR(50),
    company_owner_phone VARCHAR(50),
    company_phone VARCHAR(50),
    company_pos_number INTEGER DEFAULT 1,
    company_postal_code VARCHAR(20),
    company_province VARCHAR(255),
    company_start_activities_date TIMESTAMP WITH TIME ZONE,
    company_website VARCHAR(255),
    is_dbweb_ssl BOOLEAN DEFAULT FALSE,
    manager_password VARCHAR(255),
    is_module_shifts_active BOOLEAN DEFAULT FALSE,
    pto_vta INTEGER DEFAULT 1,
    sale_start_number INTEGER DEFAULT 1,
    start_shift1 TIME,
    start_shift2 TIME,
    start_shift3 TIME,
    store_web_alias VARCHAR(255),
    store_web_password VARCHAR(255),
    store_web_username VARCHAR(255),
    text_footer_factura_1 TEXT,
    text_footer_factura_2 TEXT,
    text_footer_ticket TEXT,
    company_vat_condition_id INTEGER REFERENCES fvpos_vat_condition(condition_id)
);

-- Tabla FVPOS_WORKSTATION_CONFIG (Configuración de la Estación de Trabajo)
CREATE TABLE fvpos_workstation_config (
    workstation_config_id SERIAL PRIMARY KEY,
    is_active BOOLEAN DEFAULT TRUE,
    cash_amount DECIMAL(10,2) DEFAULT 0,
    cash_number INTEGER,
    is_cash_opened BOOLEAN DEFAULT FALSE,
    cod_factura_electronica INTEGER,
    cost_price_decimals_format INTEGER DEFAULT 2,
    default_printer INTEGER DEFAULT 1,
    fiscal_printer_brand VARCHAR(255),
    fiscal_printer_copies INTEGER DEFAULT 1,
    fiscal_printer_model VARCHAR(255),
    fiscal_printer_port VARCHAR(50),
    fiscal_printer_velocity INTEGER DEFAULT 9600,
    installation_code VARCHAR(255),
    installation_date TIMESTAMP WITH TIME ZONE,
    license_activation_code VARCHAR(255),
    license_expiration_date TIMESTAMP WITH TIME ZONE,
    is_module_fiscal_active BOOLEAN DEFAULT FALSE,
    is_module_scale_active BOOLEAN DEFAULT FALSE,
    is_open_cash_on_login BOOLEAN DEFAULT FALSE,
    is_open_confirm_factura_electronica BOOLEAN DEFAULT TRUE,
    is_open_confirm_print_order BOOLEAN DEFAULT TRUE,
    is_print_code_in_tickets BOOLEAN DEFAULT TRUE,
    scale_checksum_digit INTEGER,
    scale_code VARCHAR(50),
    scale_label_type VARCHAR(50),
    scale_price_decimals_end INTEGER,
    scale_price_decimals_start INTEGER,
    scale_price_end INTEGER,
    scale_price_start INTEGER,
    scale_product_code_end INTEGER,
    scale_product_code_start INTEGER,
    scale_weight_decimals_end INTEGER,
    scale_weight_decimals_start INTEGER,
    scale_weight_end INTEGER,
    scale_weight_start INTEGER,
    selling_price_decimals_format INTEGER DEFAULT 2,
    is_server BOOLEAN DEFAULT FALSE,
    is_show_product_photos BOOLEAN DEFAULT FALSE,
    trial_days_qty INTEGER DEFAULT 7,
    trial_max_orders_qty INTEGER DEFAULT 100,
    trial_max_products_qty INTEGER DEFAULT 100,
    is_valid_cod_fact_elect BOOLEAN DEFAULT FALSE,
    cash_dept1_id INTEGER,
    cash_dept2_id INTEGER,
    cash_dept3_id INTEGER,
    cash_dept4_id INTEGER,
    cash_dept5_id INTEGER,
    cash_dept6_id INTEGER,
    cash_dept7_id INTEGER,
    cash_dept8_id INTEGER,
    receipt_type_for_orders_id INTEGER REFERENCES fvpos_receipt_type(receipt_type_id),
    receipt_type_for_purchases_id INTEGER REFERENCES fvpos_receipt_type(receipt_type_id)
); 