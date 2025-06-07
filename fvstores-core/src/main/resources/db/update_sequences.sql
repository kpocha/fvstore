-- Actualizar secuencias basándose en los valores máximos existentes

-- FVPOS_ORDER
SELECT setval('fvpos_order_order_id_seq', COALESCE((SELECT MAX(order_id) FROM fvpos_order), 0) + 1);

-- FVPOS_ORDER_LINE
SELECT setval('fvpos_order_line_order_line_id_seq', COALESCE((SELECT MAX(order_line_id) FROM fvpos_order_line), 0) + 1);

-- FVPOS_PRODUCT
SELECT setval('fvpos_product_product_id_seq', COALESCE((SELECT MAX(product_id) FROM fvpos_product), 0) + 1);

-- FVPOS_PRODUCT_PRICE
SELECT setval('fvpos_product_price_price_id_seq', COALESCE((SELECT MAX(price_id) FROM fvpos_product_price), 0) + 1);

-- FVPOS_CUSTOMER
SELECT setval('fvpos_customer_customer_id_seq', COALESCE((SELECT MAX(customer_id) FROM fvpos_customer), 0) + 1);

-- FVPOS_SUPPLIER
SELECT setval('fvpos_supplier_supplier_id_seq', COALESCE((SELECT MAX(supplier_id) FROM fvpos_supplier), 0) + 1);

-- FVPOS_PURCHASE
SELECT setval('fvpos_purchase_purchase_id_seq', COALESCE((SELECT MAX(purchase_id) FROM fvpos_purchase), 0) + 1);

-- FVPOS_PURCHASE_LINE
SELECT setval('fvpos_purchase_line_purchase_line_id_seq', COALESCE((SELECT MAX(purchase_line_id) FROM fvpos_purchase_line), 0) + 1);

-- FVPOS_BUDGET
SELECT setval('fvpos_budget_budget_id_seq', COALESCE((SELECT MAX(budget_id) FROM fvpos_budget), 0) + 1);

-- FVPOS_BUDGET_LINE
SELECT setval('fvpos_budget_line_budget_line_id_seq', COALESCE((SELECT MAX(budget_line_id) FROM fvpos_budget_line), 0) + 1);

-- FVPOS_CASH_OPERATION
SELECT setval('fvpos_cash_operation_cash_operation_id_seq', COALESCE((SELECT MAX(cash_operation_id) FROM fvpos_cash_operation), 0) + 1);

-- FVPOS_CUSTOMER_ON_ACCOUNT_OP
SELECT setval('fvpos_customer_on_account_op_operation_id_seq', COALESCE((SELECT MAX(operation_id) FROM fvpos_customer_on_account_op), 0) + 1);

-- FVPOS_SUPPLIER_ON_ACCOUNT_OP
SELECT setval('fvpos_supplier_on_account_op_operation_id_seq', COALESCE((SELECT MAX(operation_id) FROM fvpos_supplier_on_account_op), 0) + 1);

-- FVPOS_NOTA_DE_CREDITO
SELECT setval('fvpos_nota_de_credito_nota_id_seq', COALESCE((SELECT MAX(nota_id) FROM fvpos_nota_de_credito), 0) + 1); 