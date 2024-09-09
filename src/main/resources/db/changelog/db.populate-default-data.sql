-- liquibase formatted sql

-- changeset hungmb:add orders
INSERT INTO orders(id, customer_id, shop_id, status, item_ids, created_at, last_modified_at, version)
VALUES ('744491bb-4610-4c5a-84b7-141a84170eca', '2f2675ee-f5ad-4e71-bef8-4d729d8c3744',
        '365d106a-5ec4-4504-84c2-c59231762e03', 'WAIT_FOR_CONFIRM', '["item1","item2"]', '2024-09-09 04:06:31.116598',
        '2024-09-09 04:06:31.116598', 0);
INSERT INTO orders (id, customer_id, shop_id, status, item_ids, created_at, last_modified_at, version)
VALUES ('7034dcfa-5adc-4c70-acc0-cdb3f25cfa8d', '2f2675ee-f5ad-4e71-bef8-4d729d8c3744',
        '365d106a-5ec4-4504-84c2-c59231762e03', 'PROCESSING', '["item1","item2","item3"]',
        '2024-09-09 04:07:18.792249', '2024-09-09 04:07:18.792249', 0);

