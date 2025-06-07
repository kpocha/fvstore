-- Reiniciar secuencias de todas las tablas

-- FVPOS_VAT
ALTER SEQUENCE fvpos_vat_vat_id_seq RESTART WITH 1;

-- FVPOS_VAT_CONDITION
ALTER SEQUENCE fvpos_vat_condition_condition_id_seq RESTART WITH 1;

-- FVPOS_SALE_CONDITION
ALTER SEQUENCE fvpos_sale_condition_condition_id_seq RESTART WITH 1;

-- FVPOS_RECEIPT_TYPE
ALTER SEQUENCE fvpos_receipt_type_receipt_type_id_seq RESTART WITH 1;

-- FVPOS_BRAND
ALTER SEQUENCE fvpos_brand_brand_id_seq RESTART WITH 1;

-- FVPOS_CREDIT_CARD
ALTER SEQUENCE fvpos_credit_card_card_id_seq RESTART WITH 1;

-- FVPOS_DEBIT_CARD
ALTER SEQUENCE fvpos_debit_card_card_id_seq RESTART WITH 1;

-- FVPOS_EMPLOYEE
ALTER SEQUENCE fvpos_employee_employee_id_seq RESTART WITH 1;

-- FVPOS_CUSTOMER
ALTER SEQUENCE fvpos_customer_customer_id_seq RESTART WITH 1;

-- FVPOS_SUPPLIER
ALTER SEQUENCE fvpos_supplier_supplier_id_seq RESTART WITH 1;

-- FVPOS_PRODUCT_CATEGORY
ALTER SEQUENCE fvpos_product_category_category_id_seq RESTART WITH 1;

-- FVPOS_PRODUCT
ALTER SEQUENCE fvpos_product_product_id_seq RESTART WITH 1;

-- FVPOS_PRODUCT_PHOTO
ALTER SEQUENCE fvpos_product_photo_photo_id_seq RESTART WITH 1;

-- FVPOS_SUPPLIER_FOR_PRODUCT
ALTER SEQUENCE fvpos_supplier_for_product_supplier_for_product_id_seq RESTART WITH 1;

-- FVPOS_PRICE_LIST
ALTER SEQUENCE fvpos_price_list_list_id_seq RESTART WITH 1;

-- FVPOS_PRODUCT_PRICE
ALTER SEQUENCE fvpos_product_price_price_id_seq RESTART WITH 1;

-- FVPOS_ORDER
ALTER SEQUENCE fvpos_order_order_id_seq RESTART WITH 1;

-- FVPOS_ORDER_LINE
ALTER SEQUENCE fvpos_order_line_order_line_id_seq RESTART WITH 1;

-- FVPOS_PURCHASE
ALTER SEQUENCE fvpos_purchase_purchase_id_seq RESTART WITH 1;

-- FVPOS_PURCHASE_LINE
ALTER SEQUENCE fvpos_purchase_line_purchase_line_id_seq RESTART WITH 1;

-- FVPOS_BUDGET
ALTER SEQUENCE fvpos_budget_budget_id_seq RESTART WITH 1;

-- FVPOS_BUDGET_LINE
ALTER SEQUENCE fvpos_budget_line_budget_line_id_seq RESTART WITH 1;

-- FVPOS_CASH_OPERATION
ALTER SEQUENCE fvpos_cash_operation_cash_operation_id_seq RESTART WITH 1;

-- FVPOS_CUSTOMER_ON_ACCOUNT_OP
ALTER SEQUENCE fvpos_customer_on_account_op_operation_id_seq RESTART WITH 1;

-- FVPOS_SUPPLIER_ON_ACCOUNT_OP
ALTER SEQUENCE fvpos_supplier_on_account_op_operation_id_seq RESTART WITH 1;

-- FVPOS_NOTA_DE_CREDITO
ALTER SEQUENCE fvpos_nota_de_credito_nota_id_seq RESTART WITH 1;

-- FVPOS_APP_CONFIG
ALTER SEQUENCE fvpos_app_config_app_config_id_seq RESTART WITH 1;

-- FVPOS_WORKSTATION_CONFIG
ALTER SEQUENCE fvpos_workstation_config_workstation_config_id_seq RESTART WITH 1;