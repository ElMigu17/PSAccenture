PGDMP                      }         	   fornempre    17.3    17.3     .           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                           false            /           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                           false            0           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                           false            1           1262    16386 	   fornempre    DATABASE     o   CREATE DATABASE fornempre WITH TEMPLATE = template0 ENCODING = 'UTF8' LOCALE_PROVIDER = libc LOCALE = 'pt-BR';
    DROP DATABASE fornempre;
                     postgres    false                        2615    2200    public    SCHEMA        CREATE SCHEMA public;
    DROP SCHEMA public;
                     pg_database_owner    false            2           0    0    SCHEMA public    COMMENT     6   COMMENT ON SCHEMA public IS 'standard public schema';
                        pg_database_owner    false    4            �            1259    16387    empresa    TABLE     �   CREATE TABLE public.empresa (
    id integer NOT NULL,
    cnpj character varying(14),
    nome_fantasia character varying,
    cep integer
);
    DROP TABLE public.empresa;
       public         heap r       postgres    false    4            �            1259    16396    empresa_fornecedor    TABLE     ^   CREATE TABLE public.empresa_fornecedor (
    empresa_id integer,
    fornecedor_id integer
);
 &   DROP TABLE public.empresa_fornecedor;
       public         heap r       postgres    false    4            �            1259    16420    empresa_seq    SEQUENCE     t   CREATE SEQUENCE public.empresa_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 "   DROP SEQUENCE public.empresa_seq;
       public               postgres    false    4            �            1259    16399 
   fornecedor    TABLE        CREATE TABLE public.fornecedor (
    id integer NOT NULL,
    cnpj character varying(14),
    cpf integer,
    nome character varying,
    email character varying,
    cep integer,
    is_pessoa_fisica boolean,
    rg character varying,
    data_nascimento timestamp without time zone
);
    DROP TABLE public.fornecedor;
       public         heap r       postgres    false    4            �            1259    16421    fornecedor_seq    SEQUENCE     w   CREATE SEQUENCE public.fornecedor_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;
 %   DROP SEQUENCE public.fornecedor_seq;
       public               postgres    false    4            �           2606    16395    empresa empresa_CNPJ_key 
   CONSTRAINT     U   ALTER TABLE ONLY public.empresa
    ADD CONSTRAINT "empresa_CNPJ_key" UNIQUE (cnpj);
 D   ALTER TABLE ONLY public.empresa DROP CONSTRAINT "empresa_CNPJ_key";
       public                 postgres    false    217            �           2606    16393    empresa empresa_pkey 
   CONSTRAINT     R   ALTER TABLE ONLY public.empresa
    ADD CONSTRAINT empresa_pkey PRIMARY KEY (id);
 >   ALTER TABLE ONLY public.empresa DROP CONSTRAINT empresa_pkey;
       public                 postgres    false    217            �           2606    16407    fornecedor fornecedor_CNPJ_key 
   CONSTRAINT     [   ALTER TABLE ONLY public.fornecedor
    ADD CONSTRAINT "fornecedor_CNPJ_key" UNIQUE (cnpj);
 J   ALTER TABLE ONLY public.fornecedor DROP CONSTRAINT "fornecedor_CNPJ_key";
       public                 postgres    false    219            �           2606    16409    fornecedor fornecedor_CPF_key 
   CONSTRAINT     Y   ALTER TABLE ONLY public.fornecedor
    ADD CONSTRAINT "fornecedor_CPF_key" UNIQUE (cpf);
 I   ALTER TABLE ONLY public.fornecedor DROP CONSTRAINT "fornecedor_CPF_key";
       public                 postgres    false    219            �           2606    16405    fornecedor fornecedor_pkey 
   CONSTRAINT     X   ALTER TABLE ONLY public.fornecedor
    ADD CONSTRAINT fornecedor_pkey PRIMARY KEY (id);
 D   ALTER TABLE ONLY public.fornecedor DROP CONSTRAINT fornecedor_pkey;
       public                 postgres    false    219            �           2606    16410 5   empresa_fornecedor empresa_fornecedor_empresa_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.empresa_fornecedor
    ADD CONSTRAINT empresa_fornecedor_empresa_id_fkey FOREIGN KEY (empresa_id) REFERENCES public.empresa(id);
 _   ALTER TABLE ONLY public.empresa_fornecedor DROP CONSTRAINT empresa_fornecedor_empresa_id_fkey;
       public               postgres    false    217    4754    218            �           2606    16415 8   empresa_fornecedor empresa_fornecedor_fornecedor_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.empresa_fornecedor
    ADD CONSTRAINT empresa_fornecedor_fornecedor_id_fkey FOREIGN KEY (fornecedor_id) REFERENCES public.fornecedor(id);
 b   ALTER TABLE ONLY public.empresa_fornecedor DROP CONSTRAINT empresa_fornecedor_fornecedor_id_fkey;
       public               postgres    false    4760    218    219           