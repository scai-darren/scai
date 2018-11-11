CREATE TABLE
    db_lotest.lo_tutorial_order
    (
        order_no VARCHAR(64),
        channel VARCHAR(16),
        channel_date VARCHAR(16),
        channel_jnl_no VARCHAR(64),
        product_code VARCHAR(16),
        trans_action VARCHAR(16),
        cust_id VARCHAR(32),
        cust_name VARCHAR(64),
        product_id VARCHAR(32),
        product_name VARCHAR(64),
        apply_amount DECIMAL(22,6),
        apply_share DECIMAL(22,6),
        confirm_amount DECIMAL(22,6),
        confirm_share DECIMAL(22,6),
        contract_no VARCHAR(32),
        order_status VARCHAR(4),
        handle_status VARCHAR(4),
        ext_data VARCHAR(2048),
        created_by VARCHAR(32),
        date_created DATETIME,
        updated_by VARCHAR(32),
        date_updated DATETIME,
        PRIMARY KEY (order_no)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE
    db_lotest.lo_tutorial_product
    (
        product_id VARCHAR(32),
        product_name VARCHAR(64),
        single_min_sub DECIMAL(22,6),
        single_max_sub DECIMAL(22,6),
        single_sub_add_unit DECIMAL(22,6),
        max_user_limit DECIMAL(22,6),
        sell_status VARCHAR(4),
        ext_data VARCHAR(2048),
        created_by VARCHAR(32),
        date_created DATETIME,
        updated_by VARCHAR(32),
        date_updated DATETIME,
        PRIMARY KEY (product_id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE
    db_lotest.lo_tutorial_cust_share
    (
        cust_id VARCHAR(32),
        product_id VARCHAR(32),
        real_share DECIMAL(22,6),
        pre_share DECIMAL(22,6),
        created_by VARCHAR(32),
        date_created DATETIME,
        updated_by VARCHAR(32),
        date_updated DATETIME,
        PRIMARY KEY (cust_id,product_id)
    )
    ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO db_lotest.lo_tutorial_product (product_id, product_name, single_min_sub, single_max_sub, single_sub_add_unit, max_user_limit, sell_status, ext_data, created_by, date_created, updated_by, date_updated) VALUES ('test_prod001', '测试产品1号', 1000.000000, 500000.000000, 100.000000, 999999999999.000000, '1', null, null, null, null, null);
