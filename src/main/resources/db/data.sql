-- Mock Data for kam_user the password is 'password' and hashed using bcrypt
INSERT INTO public.kam_user (id, mobile, name, password, role, employee_id, email, is_active)
VALUES
(1, '9999999991', 'Alice Johnson', '$2a$10$4romSapC7DH2.FNcxqduNeTJkwdqa1XwNUJhh3A5GlFeHywvhVhx2', 1, 'EMP001', 'alice@example.com', TRUE),
(2, '9999999992', 'Bob Smith', '$2a$10$4romSapC7DH2.FNcxqduNeTJkwdqa1XwNUJhh3A5GlFeHywvhVhx2', 2, 'EMP002', 'bob@example.com', TRUE),
(3, '9999999993', 'Charlie Brown', '$2a$10$4romSapC7DH2.FNcxqduNeTJkwdqa1XwNUJhh3A5GlFeHywvhVhx2', 2, 'EMP003', 'charlie@example.com', TRUE),
(4, '9999999994', 'David Clark', '$2a$10$4romSapC7DH2.FNcxqduNeTJkwdqa1XwNUJhh3A5GlFeHywvhVhx2', 2, 'EMP004', 'david@example.com', FALSE),
(5, '9999999995', 'Emma Davis', '$2a$10$4romSapC7DH2.FNcxqduNeTJkwdqa1XwNUJhh3A5GlFeHywvhVhx2', 1, 'EMP005', 'emma@example.com', TRUE);
-- Update sequence
SELECT setval('kam_user_id_seq', (SELECT MAX(id) FROM kam_user));

-- Mock Data for restaurant
INSERT INTO public.restaurant (id, restaurant_name, pincode, created_by, city, state, address)
VALUES
(1, 'The Food Place', '110001', 1, 'Delhi', 'Delhi', '123 Street, Delhi'),
(2, 'Bistro Bliss', '560001', 2, 'Bangalore', 'Karnataka', '456 Street, Bangalore'),
(3, 'Urban Eats', '400001', 3, 'Mumbai', 'Maharashtra', '789 Street, Mumbai'),
(4, 'Culinary Heaven', '600001', 2, 'Chennai', 'Tamil Nadu', '101 Street, Chennai');
-- Update sequence
SELECT setval('restaurant_id_seq', (SELECT MAX(id) FROM restaurant));

-- Mock Data for restaurant_poc
INSERT INTO public.restaurant_poc (poc_id, name, restaurant_id, poc_role, contact, created_by)
VALUES
(1, 'Michael', 1, 1, '8888888881', 1),
(2, 'Sarah', 2, 2, '8888888882', 2),
(3, 'Tom', 3, 1, '8888888883', 3),
(4, 'Anna', 4, 2, '8888888884', 2);
-- Update sequence
SELECT setval('restaurant_poc_poc_id_seq', (SELECT MAX(poc_id) FROM restaurant_poc));

-- Mock Data for lead
INSERT INTO public.lead (lead_id, restaurant_id, created_by, lead_status)
VALUES
(1, 1, 1, 'ORDER_PLACED'),
(2, 2, 2, 'CONVERTED'),
(3, 3, 3, 'PITCHED'),
(4, 4, 2, 'DISQUALIFIED');
-- Update sequence
SELECT setval('lead_lead_id_seq', (SELECT MAX(lead_id) FROM lead));

-- Mock Data for audit_change_log
INSERT INTO public.audit_change_log (change_id, entity_id, old_kam_id, new_kam_id, entity_type)
VALUES
(1, 1, 1, 2, 'RESTAURANT'),
(2, 2, 2, 3, 'RESTAURANT'),
(3, 3, 3, NULL, 'LEAD');
-- Update sequence
SELECT setval('audit_change_log_change_id_seq', (SELECT MAX(change_id) FROM audit_change_log));

-- Mock Data for call_schedule
INSERT INTO public.call_schedule (id, lead_id, recurrence_type, preferred_time, start_date, time_zone)
VALUES
(1, 1, 'daily', '09:00:00', '2024-01-01', 'IST'),
(2, 2, 'weekly', '10:00:00', '2024-01-02', 'IST'),
(3, 3, 'monthly', '11:00:00', '2024-01-03', 'IST');
-- Update sequence
SELECT setval('call_schedule_id_seq', (SELECT MAX(id) FROM call_schedule));

-- Mock Data for interaction
INSERT INTO public.interaction (id, caller_id, lead_id, restaurant_id, poc_id, call_duration, interaction_details, interaction_type)
VALUES
(1, 1, 1, 1, 1, 300, 'Follow-up call with POC', 'FOLLOW_UP_CALL'),
(2, 2, 2, 2, 2, 450, 'Discussed requirements', 'DISCOVERY_CALL'),
(3, 3, 3, 3, 3, 200, 'Demo presentation', 'PLATFORM_DEMO');
-- Update sequence
SELECT setval('interaction_id_seq', (SELECT MAX(id) FROM interaction));

-- Mock Data for kam_system_metrics
INSERT INTO public.kam_system_metrics (id, metric_name, metric_value, metric_value_type, timeframe, year, month, day)
VALUES
(1, 'AVERAGE_ORDER_VALUE', 100, 'NUMBER', 'DAILY', 2024, 12, 25),
(2, 'LEAD_CONVERTION_RATE', 65, 'PERCENTAGE', 'MONTHLY', 2024, 12, NULL),
(3, 'INTERACTION_SUCCESS_RATE', 45, 'PERCENTAGE', 'YEARLY', 2024, NULL, NULL);
-- Update sequence
SELECT setval('kam_system_metrics_id_seq', (SELECT MAX(id) FROM kam_system_metrics));

-- Mock Data for orders
INSERT INTO public.orders (id, order_id, lead_id, restaurant_id, interaction_id, restaurant_order_id, amount, currency, created_by, payment_methods, remarks, order_status)
VALUES
(1, gen_random_uuid(), 1, 1, 1, 'ORDER001', 5000, 'INR', 1, 'UPI', 'Order delivered', 'DELIVERED'),
(2, gen_random_uuid(), 2, 2, 2, 'ORDER002', 7500, 'INR', 2, 'CHEQUE', 'Order pending', 'PENDING'),
(3, gen_random_uuid(), 3, 3, 3, 'ORDER003', 10000, 'INR', 3, 'CREDIT_CARD', 'Order confirmed', 'CONFIRMED');
-- Update sequence
SELECT setval('orders_id_seq', (SELECT MAX(id) FROM orders));