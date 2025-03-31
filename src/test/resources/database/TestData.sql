DELETE FROM customer_orders;

INSERT INTO customer_orders (order_id, customer_name, order_description, order_value, status, order_date)
VALUES 
  ('550e8400-e29b-41d4-a716-446655440000', 'Alice Smith', 'First test order', 150.75, 'PENDING', '2023-09-07 14:37:15.561754');
INSERT INTO customer_orders (order_id, customer_name, order_description, order_value, status, order_date)
VALUES
  ('550e8400-e29b-41d4-a716-446655440001', 'Bob Johnson', 'Second test order', 200.00, 'COMPLETED', '2023-09-07 14:37:15.561754');
