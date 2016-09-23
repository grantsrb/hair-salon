--
-- PostgreSQL database dump
--

-- Dumped from database version 9.5.4
-- Dumped by pg_dump version 9.5.4

SET statement_timeout = 0;
SET lock_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET search_path = public, pg_catalog;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: appointments; Type: TABLE; Schema: public; Owner: satchelgrant
--

CREATE TABLE appointments (
    id integer NOT NULL,
    date character varying,
    "time" character varying,
    clientid integer
);


ALTER TABLE appointments OWNER TO satchelgrant;

--
-- Name: appointments_id_seq; Type: SEQUENCE; Schema: public; Owner: satchelgrant
--

CREATE SEQUENCE appointments_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE appointments_id_seq OWNER TO satchelgrant;

--
-- Name: appointments_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: satchelgrant
--

ALTER SEQUENCE appointments_id_seq OWNED BY appointments.id;


--
-- Name: clients; Type: TABLE; Schema: public; Owner: satchelgrant
--

CREATE TABLE clients (
    id integer NOT NULL,
    name character varying,
    stylistid integer
);


ALTER TABLE clients OWNER TO satchelgrant;

--
-- Name: clients_id_seq; Type: SEQUENCE; Schema: public; Owner: satchelgrant
--

CREATE SEQUENCE clients_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE clients_id_seq OWNER TO satchelgrant;

--
-- Name: clients_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: satchelgrant
--

ALTER SEQUENCE clients_id_seq OWNED BY clients.id;


--
-- Name: stylists; Type: TABLE; Schema: public; Owner: satchelgrant
--

CREATE TABLE stylists (
    id integer NOT NULL,
    name character varying
);


ALTER TABLE stylists OWNER TO satchelgrant;

--
-- Name: stylists_id_seq; Type: SEQUENCE; Schema: public; Owner: satchelgrant
--

CREATE SEQUENCE stylists_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE stylists_id_seq OWNER TO satchelgrant;

--
-- Name: stylists_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: satchelgrant
--

ALTER SEQUENCE stylists_id_seq OWNED BY stylists.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: satchelgrant
--

CREATE TABLE users (
    id integer NOT NULL,
    username character varying,
    password character varying
);


ALTER TABLE users OWNER TO satchelgrant;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: satchelgrant
--

CREATE SEQUENCE users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE users_id_seq OWNER TO satchelgrant;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: satchelgrant
--

ALTER SEQUENCE users_id_seq OWNED BY users.id;


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: satchelgrant
--

ALTER TABLE ONLY appointments ALTER COLUMN id SET DEFAULT nextval('appointments_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: satchelgrant
--

ALTER TABLE ONLY clients ALTER COLUMN id SET DEFAULT nextval('clients_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: satchelgrant
--

ALTER TABLE ONLY stylists ALTER COLUMN id SET DEFAULT nextval('stylists_id_seq'::regclass);


--
-- Name: id; Type: DEFAULT; Schema: public; Owner: satchelgrant
--

ALTER TABLE ONLY users ALTER COLUMN id SET DEFAULT nextval('users_id_seq'::regclass);


--
-- Data for Name: appointments; Type: TABLE DATA; Schema: public; Owner: satchelgrant
--

COPY appointments (id, date, "time", clientid) FROM stdin;
\.


--
-- Name: appointments_id_seq; Type: SEQUENCE SET; Schema: public; Owner: satchelgrant
--

SELECT pg_catalog.setval('appointments_id_seq', 3, true);


--
-- Data for Name: clients; Type: TABLE DATA; Schema: public; Owner: satchelgrant
--

COPY clients (id, name, stylistid) FROM stdin;
\.


--
-- Name: clients_id_seq; Type: SEQUENCE SET; Schema: public; Owner: satchelgrant
--

SELECT pg_catalog.setval('clients_id_seq', 5, true);


--
-- Data for Name: stylists; Type: TABLE DATA; Schema: public; Owner: satchelgrant
--

COPY stylists (id, name) FROM stdin;
\.


--
-- Name: stylists_id_seq; Type: SEQUENCE SET; Schema: public; Owner: satchelgrant
--

SELECT pg_catalog.setval('stylists_id_seq', 5, true);


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: satchelgrant
--

COPY users (id, username, password) FROM stdin;
\.


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: satchelgrant
--

SELECT pg_catalog.setval('users_id_seq', 1, true);


--
-- Name: appointments_pkey; Type: CONSTRAINT; Schema: public; Owner: satchelgrant
--

ALTER TABLE ONLY appointments
    ADD CONSTRAINT appointments_pkey PRIMARY KEY (id);


--
-- Name: clients_pkey; Type: CONSTRAINT; Schema: public; Owner: satchelgrant
--

ALTER TABLE ONLY clients
    ADD CONSTRAINT clients_pkey PRIMARY KEY (id);


--
-- Name: stylists_pkey; Type: CONSTRAINT; Schema: public; Owner: satchelgrant
--

ALTER TABLE ONLY stylists
    ADD CONSTRAINT stylists_pkey PRIMARY KEY (id);


--
-- Name: users_pkey; Type: CONSTRAINT; Schema: public; Owner: satchelgrant
--

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: public; Type: ACL; Schema: -; Owner: satchelgrant
--

REVOKE ALL ON SCHEMA public FROM PUBLIC;
REVOKE ALL ON SCHEMA public FROM satchelgrant;
GRANT ALL ON SCHEMA public TO satchelgrant;
GRANT ALL ON SCHEMA public TO PUBLIC;


--
-- PostgreSQL database dump complete
--

