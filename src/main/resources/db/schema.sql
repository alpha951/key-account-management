-- PostgreSQL database schema

-- Table: kam_user
CREATE TABLE public.kam_user
(
    id          BIGSERIAL PRIMARY KEY,
    mobile      VARCHAR(255)                                          NOT NULL,
    name        VARCHAR(255)                                          NOT NULL,
    password    VARCHAR(255)                                          NOT NULL,
    role        BIGINT                                                NOT NULL,
    employee_id VARCHAR(255)                                          NOT NULL,
    email       VARCHAR(255)                                          NOT NULL,
    is_active   BOOLEAN                                               NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    UNIQUE (email),
    UNIQUE (employee_id),
    UNIQUE (mobile)
);


-- Table: restaurant
CREATE TABLE public.restaurant
(
    id              BIGSERIAL PRIMARY KEY,
    restaurant_name VARCHAR(255)                                          NOT NULL,
    pincode         VARCHAR(255)                                          NOT NULL,
    created_by      BIGINT                                                NOT NULL,
    city            VARCHAR(255)                                          NOT NULL,
    state           VARCHAR(255)                                          NOT NULL,
    address         VARCHAR(255)                                          NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at      TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (created_by) REFERENCES public.kam_user (id)
);


-- Table: restaurant_poc
CREATE TABLE public.restaurant_poc
(
    poc_id        BIGSERIAL PRIMARY KEY,
    name          VARCHAR(255)                                          NOT NULL,
    restaurant_id BIGINT                                                NOT NULL,
    poc_role      BIGINT                                                NOT NULL,
    contact       VARCHAR(255)                                          NOT NULL,
    created_by    BIGINT                                                NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at    TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES public.restaurant (id),
    FOREIGN KEY (created_by) REFERENCES public.kam_user (id)
);

-- Table: lead
CREATE TABLE public.lead
(
    lead_id       BIGSERIAL PRIMARY KEY,
    restaurant_id BIGINT                                                NOT NULL,
    created_by    BIGINT                                                NOT NULL,
    lead_status   BIGINT                                                NOT NULL,
    created_at    TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    updated_at    TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (created_by) REFERENCES public.kam_user (id),
    FOREIGN KEY (restaurant_id) REFERENCES public.restaurant (id)
);


-- Table: audit_change_log
CREATE TABLE public.audit_change_log
(
    change_id   BIGSERIAL PRIMARY KEY,
    entity_id   BIGINT                                                NOT NULL,
    old_kam_id  BIGINT,
    new_kam_id  BIGINT,
    change_date TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    entity_type VARCHAR
);

-- Table: call_schedule
CREATE TABLE public.call_schedule
(
    id                  BIGSERIAL PRIMARY KEY,
    lead_id             BIGINT                      NOT NULL,
    recurrence_type     VARCHAR(20)                 NOT NULL,
    preferred_time      TIME WITHOUT TIME ZONE      NOT NULL,
    start_date          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    end_date            TIMESTAMP WITHOUT TIME ZONE,
    weekly_days         JSONB,
    day_of_month        INT,
    custom_day_interval INT,
    last_call_date      TIMESTAMP WITHOUT TIME ZONE,
    next_call_date      TIMESTAMP WITHOUT TIME ZONE,
    time_zone           VARCHAR(50)                 NOT NULL,
    created_at          TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    is_active           BOOLEAN                     DEFAULT TRUE,
    FOREIGN KEY (lead_id) REFERENCES public.lead (lead_id)
);

-- Table: interaction
CREATE TABLE public.interaction
(
    id                  BIGSERIAL PRIMARY KEY,
    caller_id           BIGINT                                                NOT NULL,
    lead_id             BIGINT                                                NOT NULL,
    restaurant_id       BIGINT                                                NOT NULL,
    poc_id              BIGINT                                                NOT NULL,
    call_schedule_id    BIGINT,
    call_duration       NUMERIC                                                NOT NULL,
    interaction_details TEXT                                                  NOT NULL,
    interaction_type    VARCHAR(50)                                           NOT NULL,
    created_at          TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP NOT NULL,
    FOREIGN KEY (call_schedule_id) REFERENCES public.call_schedule (id),
    FOREIGN KEY (caller_id) REFERENCES public.kam_user (id),
    FOREIGN KEY (lead_id) REFERENCES public.lead (lead_id),
    FOREIGN KEY (poc_id) REFERENCES public.restaurant_poc (poc_id),
    FOREIGN KEY (restaurant_id) REFERENCES public.restaurant (id)
);

-- Table: kam_system_metrics
CREATE TABLE public.kam_system_metrics
(
    id                BIGSERIAL PRIMARY KEY,
    metric_name       VARCHAR(50)      NOT NULL,
    metric_value      NUMERIC      NOT NULL,
    metric_value_type VARCHAR(50) NOT NULL,
    timeframe         VARCHAR(50) NOT NULL,
    year              BIGINT      NOT NULL,
    month             BIGINT      NULL,
    day               BIGINT      NULL,
    created_at        TIMESTAMP WITHOUT TIME ZONE DEFAULT NOW()
);


-- Table: orders
CREATE TABLE public.orders
(
    id                  BIGSERIAL PRIMARY KEY,
    order_id            UUID                                                   NOT NULL,
    lead_id             BIGINT                                                 NOT NULL,
    restaurant_id       BIGINT                                                 NOT NULL,
    interaction_id      BIGINT                                                 NOT NULL,
    restaurant_order_id VARCHAR(255)                                           NOT NULL,
    amount              NUMERIC                                                 NOT NULL,
    currency            VARCHAR(50)                                            NOT NULL,
    cart_info           TEXT,
    shipping_info       TEXT,
    offer_details       TEXT,
    created_by          BIGINT                                                 NOT NULL,
    payment_methods     VARCHAR(50)                                            NOT NULL,
    remarks             VARCHAR(255)                                           NOT NULL,
    created_at          TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP  NOT NULL,
    order_status        VARCHAR                     DEFAULT 'PENDING'::VARCHAR NOT NULL,
    FOREIGN KEY (created_by) REFERENCES public.kam_user (id),
    FOREIGN KEY (interaction_id) REFERENCES public.interaction (id),
    FOREIGN KEY (lead_id) REFERENCES public.lead (lead_id),
    FOREIGN KEY (restaurant_id) REFERENCES public.restaurant (id)
);


-- Indexes
CREATE INDEX lead_created_by_idx ON public.lead USING btree (created_by);
CREATE INDEX lead_restaurant_id_idx ON public.lead USING btree (restaurant_id);
CREATE INDEX restaurant_poc_created_by_idx ON public.restaurant_poc USING btree (created_by);
CREATE INDEX restaurant_poc_restaurant_id_idx ON public.restaurant_poc USING btree (restaurant_id);
CREATE INDEX restaurant_created_by_idx ON public.restaurant USING btree (created_by);
CREATE INDEX orders_lead_id_idx ON public.orders USING btree (lead_id);
CREATE INDEX orders_restaurant_id_idx ON public.orders USING btree (restaurant_id);
CREATE INDEX orders_interaction_id_idx ON public.orders USING btree (interaction_id);
CREATE INDEX orders_order_id_idx ON public.orders USING btree (order_id,created_by);
