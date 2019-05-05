-- SEQUENCE: public.database_info_id_seq

-- DROP SEQUENCE public.database_info_id_seq;

CREATE SEQUENCE public.database_info_id_seq;

ALTER SEQUENCE public.database_info_id_seq
    OWNER TO postgres;

-- Table: public.database_info

-- DROP TABLE public.database_info;

CREATE TABLE public.database_info
(
    id bigint NOT NULL,
    name character varying(200) COLLATE pg_catalog."default" NOT NULL,
    type integer NOT NULL,
    driver_name character varying(100) COLLATE pg_catalog."default" NOT NULL,
    url character varying(500) COLLATE pg_catalog."default" NOT NULL,
    user_name character varying(200) COLLATE pg_catalog."default",
    password character varying(200) COLLATE pg_catalog."default",
    created_date timestamp with time zone,
    modified_date timestamp with time zone,
    user_id integer,
    CONSTRAINT database_info_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.database_info
    OWNER to postgres;