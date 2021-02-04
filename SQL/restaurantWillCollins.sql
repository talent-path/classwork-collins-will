--
-- PostgreSQL database dump
--

-- Dumped from database version 13.1
-- Dumped by pg_dump version 13.1

-- Started on 2021-02-03 17:58:55 EST

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE "Restaurant";
--
-- TOC entry 3346 (class 1262 OID 16503)
-- Name: Restaurant; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE "Restaurant" WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE = 'C';


ALTER DATABASE "Restaurant" OWNER TO "postgres";

\connect "Restaurant"

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = "heap";

--
-- TOC entry 203 (class 1259 OID 16517)
-- Name: Dishes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "public"."Dishes" (
    "dishId" integer NOT NULL,
    "name" character varying(50) NOT NULL
);


ALTER TABLE "public"."Dishes" OWNER TO "postgres";

--
-- TOC entry 202 (class 1259 OID 16515)
-- Name: Dishes_dishId_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "public"."Dishes_dishId_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "public"."Dishes_dishId_seq" OWNER TO "postgres";

--
-- TOC entry 3347 (class 0 OID 0)
-- Dependencies: 202
-- Name: Dishes_dishId_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "public"."Dishes_dishId_seq" OWNED BY "public"."Dishes"."dishId";


--
-- TOC entry 200 (class 1259 OID 16504)
-- Name: Ingredients; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "public"."Ingredients" (
    "ingredientId" integer NOT NULL,
    "name" character varying(50) NOT NULL,
    "stock" numeric(5,2),
    "units" character varying(10),
    "isOrganic" boolean
);


ALTER TABLE "public"."Ingredients" OWNER TO "postgres";

--
-- TOC entry 201 (class 1259 OID 16507)
-- Name: Ingredients_ingredientId_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "public"."Ingredients_ingredientId_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "public"."Ingredients_ingredientId_seq" OWNER TO "postgres";

--
-- TOC entry 3348 (class 0 OID 0)
-- Dependencies: 201
-- Name: Ingredients_ingredientId_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "public"."Ingredients_ingredientId_seq" OWNED BY "public"."Ingredients"."ingredientId";


--
-- TOC entry 215 (class 1259 OID 16591)
-- Name: MenuDishes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "public"."MenuDishes" (
    "menuId" integer NOT NULL,
    "dishId" integer NOT NULL,
    "price" numeric(5,2) NOT NULL
);


ALTER TABLE "public"."MenuDishes" OWNER TO "postgres";

--
-- TOC entry 207 (class 1259 OID 16541)
-- Name: Menus; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "public"."Menus" (
    "menuId" integer NOT NULL,
    "name" character varying(50) NOT NULL
);


ALTER TABLE "public"."Menus" OWNER TO "postgres";

--
-- TOC entry 206 (class 1259 OID 16539)
-- Name: Menus_menuId_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "public"."Menus_menuId_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "public"."Menus_menuId_seq" OWNER TO "postgres";

--
-- TOC entry 3349 (class 0 OID 0)
-- Dependencies: 206
-- Name: Menus_menuId_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "public"."Menus_menuId_seq" OWNED BY "public"."Menus"."menuId";


--
-- TOC entry 217 (class 1259 OID 16624)
-- Name: OrderDishes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "public"."OrderDishes" (
    "orderId" integer NOT NULL,
    "dishId" integer NOT NULL,
    "menuId" integer NOT NULL,
    "quantity" numeric(5,2) NOT NULL
);


ALTER TABLE "public"."OrderDishes" OWNER TO "postgres";

--
-- TOC entry 213 (class 1259 OID 16565)
-- Name: Orders; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "public"."Orders" (
    "orderId" integer NOT NULL,
    "tabletopId" integer NOT NULL
);


ALTER TABLE "public"."Orders" OWNER TO "postgres";

--
-- TOC entry 212 (class 1259 OID 16563)
-- Name: Orders_orderId_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "public"."Orders_orderId_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "public"."Orders_orderId_seq" OWNER TO "postgres";

--
-- TOC entry 3350 (class 0 OID 0)
-- Dependencies: 212
-- Name: Orders_orderId_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "public"."Orders_orderId_seq" OWNED BY "public"."Orders"."orderId";


--
-- TOC entry 214 (class 1259 OID 16576)
-- Name: RecipeIngredients; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "public"."RecipeIngredients" (
    "recipeId" integer NOT NULL,
    "ingredientId" integer NOT NULL,
    "quantity" numeric(5,2) NOT NULL,
    "unit" character varying(50) NOT NULL
);


ALTER TABLE "public"."RecipeIngredients" OWNER TO "postgres";

--
-- TOC entry 205 (class 1259 OID 16525)
-- Name: Recipes; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "public"."Recipes" (
    "recipeId" bigint NOT NULL,
    "dishId" integer NOT NULL,
    "name" character varying(50) NOT NULL,
    "instructions" "text" NOT NULL
);


ALTER TABLE "public"."Recipes" OWNER TO "postgres";

--
-- TOC entry 204 (class 1259 OID 16523)
-- Name: Recipes_recipeId_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "public"."Recipes_recipeId_seq"
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "public"."Recipes_recipeId_seq" OWNER TO "postgres";

--
-- TOC entry 3351 (class 0 OID 0)
-- Dependencies: 204
-- Name: Recipes_recipeId_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "public"."Recipes_recipeId_seq" OWNED BY "public"."Recipes"."recipeId";


--
-- TOC entry 216 (class 1259 OID 16606)
-- Name: SupplierIngredients; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "public"."SupplierIngredients" (
    "supplierId" integer NOT NULL,
    "ingredientId" integer NOT NULL,
    "qtyAvailable" numeric,
    "unit" character varying(50),
    "unitCost" numeric(5,2) NOT NULL
);


ALTER TABLE "public"."SupplierIngredients" OWNER TO "postgres";

--
-- TOC entry 209 (class 1259 OID 16549)
-- Name: Suppliers; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "public"."Suppliers" (
    "supplierId" integer NOT NULL,
    "name" character varying(50) NOT NULL
);


ALTER TABLE "public"."Suppliers" OWNER TO "postgres";

--
-- TOC entry 208 (class 1259 OID 16547)
-- Name: Suppliers_supplierId_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "public"."Suppliers_supplierId_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "public"."Suppliers_supplierId_seq" OWNER TO "postgres";

--
-- TOC entry 3352 (class 0 OID 0)
-- Dependencies: 208
-- Name: Suppliers_supplierId_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "public"."Suppliers_supplierId_seq" OWNED BY "public"."Suppliers"."supplierId";


--
-- TOC entry 211 (class 1259 OID 16557)
-- Name: Tabletops; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE "public"."Tabletops" (
    "tabletopId" integer NOT NULL,
    "seatCount" smallint
);


ALTER TABLE "public"."Tabletops" OWNER TO "postgres";

--
-- TOC entry 210 (class 1259 OID 16555)
-- Name: Tabletops_tabletopId_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE "public"."Tabletops_tabletopId_seq"
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE "public"."Tabletops_tabletopId_seq" OWNER TO "postgres";

--
-- TOC entry 3353 (class 0 OID 0)
-- Dependencies: 210
-- Name: Tabletops_tabletopId_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE "public"."Tabletops_tabletopId_seq" OWNED BY "public"."Tabletops"."tabletopId";


--
-- TOC entry 3172 (class 2604 OID 16520)
-- Name: Dishes dishId; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."Dishes" ALTER COLUMN "dishId" SET DEFAULT "nextval"('"public"."Dishes_dishId_seq"'::"regclass");


--
-- TOC entry 3171 (class 2604 OID 16509)
-- Name: Ingredients ingredientId; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."Ingredients" ALTER COLUMN "ingredientId" SET DEFAULT "nextval"('"public"."Ingredients_ingredientId_seq"'::"regclass");


--
-- TOC entry 3174 (class 2604 OID 16544)
-- Name: Menus menuId; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."Menus" ALTER COLUMN "menuId" SET DEFAULT "nextval"('"public"."Menus_menuId_seq"'::"regclass");


--
-- TOC entry 3177 (class 2604 OID 16568)
-- Name: Orders orderId; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."Orders" ALTER COLUMN "orderId" SET DEFAULT "nextval"('"public"."Orders_orderId_seq"'::"regclass");


--
-- TOC entry 3173 (class 2604 OID 16528)
-- Name: Recipes recipeId; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."Recipes" ALTER COLUMN "recipeId" SET DEFAULT "nextval"('"public"."Recipes_recipeId_seq"'::"regclass");


--
-- TOC entry 3175 (class 2604 OID 16552)
-- Name: Suppliers supplierId; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."Suppliers" ALTER COLUMN "supplierId" SET DEFAULT "nextval"('"public"."Suppliers_supplierId_seq"'::"regclass");


--
-- TOC entry 3176 (class 2604 OID 16560)
-- Name: Tabletops tabletopId; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."Tabletops" ALTER COLUMN "tabletopId" SET DEFAULT "nextval"('"public"."Tabletops_tabletopId_seq"'::"regclass");


--
-- TOC entry 3181 (class 2606 OID 16522)
-- Name: Dishes Dishes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."Dishes"
    ADD CONSTRAINT "Dishes_pkey" PRIMARY KEY ("dishId");


--
-- TOC entry 3179 (class 2606 OID 16514)
-- Name: Ingredients Ingredients_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."Ingredients"
    ADD CONSTRAINT "Ingredients_pkey" PRIMARY KEY ("ingredientId");


--
-- TOC entry 3195 (class 2606 OID 16595)
-- Name: MenuDishes MenuDishes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."MenuDishes"
    ADD CONSTRAINT "MenuDishes_pkey" PRIMARY KEY ("menuId", "dishId");


--
-- TOC entry 3185 (class 2606 OID 16546)
-- Name: Menus Menus_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."Menus"
    ADD CONSTRAINT "Menus_pkey" PRIMARY KEY ("menuId");


--
-- TOC entry 3199 (class 2606 OID 16628)
-- Name: OrderDishes OrderDishes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."OrderDishes"
    ADD CONSTRAINT "OrderDishes_pkey" PRIMARY KEY ("orderId", "dishId", "menuId");


--
-- TOC entry 3191 (class 2606 OID 16570)
-- Name: Orders Orders_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."Orders"
    ADD CONSTRAINT "Orders_pkey" PRIMARY KEY ("orderId");


--
-- TOC entry 3193 (class 2606 OID 16580)
-- Name: RecipeIngredients RecipeIngredients_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."RecipeIngredients"
    ADD CONSTRAINT "RecipeIngredients_pkey" PRIMARY KEY ("recipeId", "ingredientId");


--
-- TOC entry 3183 (class 2606 OID 16533)
-- Name: Recipes Recipes_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."Recipes"
    ADD CONSTRAINT "Recipes_pkey" PRIMARY KEY ("recipeId");


--
-- TOC entry 3197 (class 2606 OID 16613)
-- Name: SupplierIngredients SupplierIngredients_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."SupplierIngredients"
    ADD CONSTRAINT "SupplierIngredients_pkey" PRIMARY KEY ("supplierId", "ingredientId");


--
-- TOC entry 3187 (class 2606 OID 16554)
-- Name: Suppliers Suppliers_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."Suppliers"
    ADD CONSTRAINT "Suppliers_pkey" PRIMARY KEY ("supplierId");


--
-- TOC entry 3189 (class 2606 OID 16562)
-- Name: Tabletops Tabletops_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."Tabletops"
    ADD CONSTRAINT "Tabletops_pkey" PRIMARY KEY ("tabletopId");


--
-- TOC entry 3200 (class 2606 OID 16534)
-- Name: Recipes dishId_fKey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."Recipes"
    ADD CONSTRAINT "dishId_fKey" FOREIGN KEY ("dishId") REFERENCES "public"."Dishes"("dishId");


--
-- TOC entry 3205 (class 2606 OID 16601)
-- Name: MenuDishes dishId_fKey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."MenuDishes"
    ADD CONSTRAINT "dishId_fKey" FOREIGN KEY ("dishId") REFERENCES "public"."Dishes"("dishId");


--
-- TOC entry 3209 (class 2606 OID 16634)
-- Name: OrderDishes dishId_fKey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."OrderDishes"
    ADD CONSTRAINT "dishId_fKey" FOREIGN KEY ("dishId") REFERENCES "public"."Dishes"("dishId");


--
-- TOC entry 3203 (class 2606 OID 16586)
-- Name: RecipeIngredients ingredientId_fKey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."RecipeIngredients"
    ADD CONSTRAINT "ingredientId_fKey" FOREIGN KEY ("ingredientId") REFERENCES "public"."Ingredients"("ingredientId");


--
-- TOC entry 3207 (class 2606 OID 16619)
-- Name: SupplierIngredients ingredientId_fKey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."SupplierIngredients"
    ADD CONSTRAINT "ingredientId_fKey" FOREIGN KEY ("ingredientId") REFERENCES "public"."Ingredients"("ingredientId");


--
-- TOC entry 3204 (class 2606 OID 16596)
-- Name: MenuDishes menuId_fKey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."MenuDishes"
    ADD CONSTRAINT "menuId_fKey" FOREIGN KEY ("menuId") REFERENCES "public"."Menus"("menuId");


--
-- TOC entry 3210 (class 2606 OID 16639)
-- Name: OrderDishes menuId_fKey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."OrderDishes"
    ADD CONSTRAINT "menuId_fKey" FOREIGN KEY ("menuId") REFERENCES "public"."Menus"("menuId");


--
-- TOC entry 3208 (class 2606 OID 16629)
-- Name: OrderDishes orderId_fKey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."OrderDishes"
    ADD CONSTRAINT "orderId_fKey" FOREIGN KEY ("orderId") REFERENCES "public"."Orders"("orderId");


--
-- TOC entry 3202 (class 2606 OID 16581)
-- Name: RecipeIngredients recipeId_fKey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."RecipeIngredients"
    ADD CONSTRAINT "recipeId_fKey" FOREIGN KEY ("recipeId") REFERENCES "public"."Recipes"("recipeId");


--
-- TOC entry 3206 (class 2606 OID 16614)
-- Name: SupplierIngredients supplierId_fKey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."SupplierIngredients"
    ADD CONSTRAINT "supplierId_fKey" FOREIGN KEY ("supplierId") REFERENCES "public"."Suppliers"("supplierId");


--
-- TOC entry 3201 (class 2606 OID 16571)
-- Name: Orders tabletopId_fKey; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY "public"."Orders"
    ADD CONSTRAINT "tabletopId_fKey" FOREIGN KEY ("tabletopId") REFERENCES "public"."Tabletops"("tabletopId");


-- Completed on 2021-02-03 17:58:56 EST

--
-- PostgreSQL database dump complete
--

