CREATE TABLE "kam_user"
(
    "id"          BIGINT                      NOT NULL PRIMARY KEY,
    "mobile"      VARCHAR(255)                NOT NULL,
    "name"        VARCHAR(255)                NOT NULL,
    "password"    VARCHAR(255)                NOT NULL,
    "role"        BIGINT                      NOT NULL,
    "employee_id" VARCHAR(255)                NOT NULL,
    "email"       VARCHAR(255)                NOT NULL,
    "is_active"   BOOLEAN                     NOT NULL,
    "created_at"  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at"  TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE "restaurant"
(
    "id"              BIGINT                      NOT NULL PRIMARY KEY,
    "restaurant_name" VARCHAR(255)                NOT NULL,
    "pin_code"        VARCHAR(255)                NOT NULL,
    "created_by"      BIGINT                      NOT NULL,
    "city"            VARCHAR(255)                NOT NULL,
    "state"           VARCHAR(255)                NOT NULL,
    "address"         VARCHAR(255)                NOT NULL,
    "created_at"      TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at"      TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT "restaurant_created_by_foreign" FOREIGN KEY ("created_by") REFERENCES "kam_user" ("id")
);

CREATE TABLE "lead"
(
    "lead_id"       BIGINT                      NOT NULL PRIMARY KEY,
    "restaurant_id" BIGINT                      NOT NULL,
    "created_by"    BIGINT                      NOT NULL,
    "lead_status"   BIGINT                      NOT NULL,
    "created_at"    TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at"    TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT "lead_restaurant_id_foreign" FOREIGN KEY ("restaurant_id") REFERENCES "restaurant" ("id"),
    CONSTRAINT "lead_created_by_foreign" FOREIGN KEY ("created_by") REFERENCES "kam_user" ("id")
);

CREATE TABLE "restaurant_poc"
(
    "poc_id"        BIGINT                      NOT NULL PRIMARY KEY,
    "name"          VARCHAR(255)                NOT NULL,
    "restaurant_id" BIGINT                      NOT NULL,
    "poc_role"      BIGINT                      NOT NULL,
    "contact"       VARCHAR(255)                NOT NULL,
    "created_by"    BIGINT                      NOT NULL,
    "created_at"    TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at"    TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT "restaurant_poc_restaurant_id_foreign" FOREIGN KEY ("restaurant_id") REFERENCES "restaurant" ("id"),
    CONSTRAINT "restaurant_poc_created_by_foreign" FOREIGN KEY ("created_by") REFERENCES "kam_user" ("id")
);

CREATE TABLE "call_schedule"
(
    "id"                  BIGINT                      NOT NULL PRIMARY KEY,
    "lead_id"             BIGINT                      NOT NULL,
    "poc_id"              BIGINT                      NOT NULL,
    "call_frequency"      BIGINT                      NOT NULL,
    "call_frequency_type" VARCHAR(50)                 NOT NULL DEFAULT 'MONTHLY',
    "call_timing"         TIMESTAMP WITH TIME ZONE    NULL,
    "last_call"           TIMESTAMP WITH TIME ZONE    NULL,
    "created_at"          TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at"          TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT "call_schedule_lead_id_foreign" FOREIGN KEY ("lead_id") REFERENCES "lead" ("lead_id"),
    CONSTRAINT "call_schedule_poc_id_foreign" FOREIGN KEY ("poc_id") REFERENCES "restaurant_poc" ("poc_id")
);

CREATE TABLE "interaction"
(
    "id"                  BIGINT                      NOT NULL PRIMARY KEY,
    "caller"              BIGINT                      NOT NULL,
    "lead_id"             BIGINT                      NOT NULL,
    "restaurant_id"       BIGINT                      NOT NULL,
    "poc_id"              BIGINT                      NOT NULL,
    "call_schedule_id"    BIGINT                      NULL,
    "call_duration"       BIGINT                      NOT NULL,
    "interaction_details" TEXT                        NOT NULL,
    "interaction_type"    VARCHAR(50)                 NOT NULL,
    "created_at"          TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT "interaction_caller_foreign" FOREIGN KEY ("caller") REFERENCES "kam_user" ("id"),
    CONSTRAINT "interaction_lead_id_foreign" FOREIGN KEY ("lead_id") REFERENCES "lead" ("lead_id"),
    CONSTRAINT "interaction_restaurant_id_foreign" FOREIGN KEY ("restaurant_id") REFERENCES "restaurant" ("id"),
    CONSTRAINT "interaction_poc_id_foreign" FOREIGN KEY ("poc_id") REFERENCES "restaurant_poc" ("poc_id"),
    CONSTRAINT "interaction_call_schedule_id_foreign" FOREIGN KEY ("call_schedule_id") REFERENCES "call_schedule" ("id")
);

CREATE TABLE "order"
(
    "id"                  BIGINT                      NOT NULL PRIMARY KEY,
    "order_id"            UUID                        NOT NULL,
    "lead_id"             BIGINT                      NOT NULL,
    "restaurant_id"       BIGINT                      NOT NULL,
    "interaction_id"      BIGINT                      NOT NULL,
    "restaurant_order_id" VARCHAR(255)                NOT NULL,
    "amount"              BIGINT                      NOT NULL,
    "currency"            VARCHAR(50)                 NOT NULL,
    "cart_info"           TEXT                        NOT NULL,
    "shipping_info"       TEXT                        NOT NULL,
    "offer_details"       TEXT                        NOT NULL,
    "created_by"          BIGINT                      NOT NULL,
    "payment_methods"     VARCHAR(50)                 NOT NULL,
    "remarks"             VARCHAR(255)                NOT NULL,
    "created_at"          TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT "order_lead_id_foreign" FOREIGN KEY ("lead_id") REFERENCES "lead" ("lead_id"),
    CONSTRAINT "order_restaurant_id_foreign" FOREIGN KEY ("restaurant_id") REFERENCES "restaurant" ("id"),
    CONSTRAINT "order_interaction_id_foreign" FOREIGN KEY ("interaction_id") REFERENCES "interaction" ("id"),
    CONSTRAINT "order_created_by_foreign" FOREIGN KEY ("created_by") REFERENCES "kam_user" ("id")
);

CREATE TABLE "lead_perf_matrics"
(
    "id"                BIGINT                      NOT NULL PRIMARY KEY,
    "lead_id"           BIGINT                      NOT NULL,
    "avg_order_value"   BIGINT                      NOT NULL,
    "order_freq"        BIGINT                      NOT NULL,
    "total_orders"      BIGINT                      NOT NULL,
    "interaction_count" BIGINT                      NOT NULL,
    "total_revenue"     BIGINT                      NOT NULL,
    "last_order_date"   TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "created_at"        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    "updated_at"        TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT "lead_perf_matrics_lead_id_foreign" FOREIGN KEY ("lead_id") REFERENCES "lead" ("lead_id")
);

CREATE TABLE "kam_system_metrics"
(
    "id"                BIGINT      NOT NULL PRIMARY KEY,
    "metric_name"       BIGINT      NOT NULL,
    "metric_value"      BIGINT      NOT NULL,
    "metric_value_type" BIGINT      NOT NULL,
    "timeframe"         VARCHAR(50) NOT NULL,
    "year"              BIGINT      NOT NULL,
    "month"             BIGINT      NOT NULL,
    "day"               BIGINT      NOT NULL
);

CREATE TABLE IF NOT EXISTS "kam_change_log"
(
    "change_id"   BIGINT PRIMARY KEY,
    "lead_id"     BIGINT                      NOT NULL,
    "old_kam_id"  BIGINT,
    "new_kam_id"  BIGINT,
    "change_date" TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT CURRENT_TIMESTAMP
);


ALTER TABLE public.kam_user ADD CONSTRAINT kam_user_unique_mobile UNIQUE (mobile);
ALTER TABLE public.kam_user ADD CONSTRAINT kam_user_unique_email UNIQUE (email);
ALTER TABLE public.kam_user ADD CONSTRAINT kam_user_unique_employee UNIQUE (employee_id);




CREATE INDEX lead_restaurant_id_idx ON public."lead" (restaurant_id);
CREATE INDEX lead_created_by_idx ON public."lead" (created_by);

CREATE INDEX restaurant_poc_restaurant_id_idx ON public.restaurant_poc (restaurant_id);
CREATE INDEX restaurant_poc_created_by_idx ON public.restaurant_poc (created_by);