/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     10/05/2020 3:53:14 p. m.                     */
/*==============================================================*/


alter table ARTICULADO 
   drop foreign key FK_ARTICULA_RUTA_ACTU_RUTA;

alter table FOTO_ESTACION 
   drop foreign key FK_FOTO_EST_FOTOS_ESTACION;

alter table NOTICIA 
   drop foreign key FK_NOTICIA_PUBLICO_USUARIO;

alter table PASA_POR 
   drop foreign key FK_PASA_POR_PASA_POR_ESTACION;

alter table PASA_POR 
   drop foreign key FK_PASA_POR_PASA_POR2_RUTA;

alter table RUTAS_BUSCADAS 
   drop foreign key FK_RUTAS_BU_RUTAS_BUS_RUTA;

alter table RUTAS_BUSCADAS 
   drop foreign key FK_RUTAS_BU_RUTAS_BUS_USUARIO;


alter table ARTICULADO 
   drop foreign key FK_ARTICULA_RUTA_ACTU_RUTA;

drop table if exists ARTICULADO;

drop table if exists ESTACION;


alter table FOTO_ESTACION 
   drop foreign key FK_FOTO_EST_FOTOS_ESTACION;

drop table if exists FOTO_ESTACION;


alter table NOTICIA 
   drop foreign key FK_NOTICIA_PUBLICO_USUARIO;

drop table if exists NOTICIA;


alter table PASA_POR 
   drop foreign key FK_PASA_POR_PASA_POR2_RUTA;

alter table PASA_POR 
   drop foreign key FK_PASA_POR_PASA_POR_ESTACION;

drop table if exists PASA_POR;

drop table if exists RUTA;


alter table RUTAS_BUSCADAS 
   drop foreign key FK_RUTAS_BU_RUTAS_BUS_USUARIO;

alter table RUTAS_BUSCADAS 
   drop foreign key FK_RUTAS_BU_RUTAS_BUS_RUTA;

drop table if exists RUTAS_BUSCADAS;

drop table if exists USUARIO;

/*==============================================================*/
/* Table: ARTICULADO                                            */
/*==============================================================*/
create table ARTICULADO
(
   PLACA                varchar(10) not null  comment '',
   FECHA_ADQUISICION    date not null  comment '',
   BIARTICULADO         bool not null  comment '',
   CAPACIDAD            int not null  comment '',
   CODIGO_RUTA          varchar(10)  comment '',
   primary key (PLACA)
);

/*==============================================================*/
/* Table: ESTACION                                              */
/*==============================================================*/
create table ESTACION
(
   NOMBRE_ESTACION      varchar(60) not null  comment '',
   DIRECCION            varchar(50) not null  comment '',
   ZONA                 varchar(1) not null  comment '',
   VAGONES              int not null  comment '',
   primary key (DIRECCION)
);

/*==============================================================*/
/* Table: FOTO_ESTACION                                         */
/*==============================================================*/
create table FOTO_ESTACION
(
   DIRECCION            varchar(50) not null  comment '',
   ID_FOTO              int not null  comment '',
   DIRECCION_FOTO       varchar(30) not null  comment '',
   primary key (DIRECCION, ID_FOTO)
);

/*==============================================================*/
/* Table: NOTICIA                                               */
/*==============================================================*/
create table NOTICIA
(
   ID_NOTICIA           int not null  comment '',
   CORREO               varchar(100)  comment '',
   CONTENIDO            varchar(141) not null  comment '',
   TITULO               varchar(30) not null  comment '',
   primary key (ID_NOTICIA)
);

/*==============================================================*/
/* Table: PASA_POR                                              */
/*==============================================================*/
create table PASA_POR
(
   DIRECCION            varchar(50) not null  comment '',
   CODIGO_RUTA          varchar(10) not null  comment '',
   ESTACION_INICIO      bool not null  comment '',
   ESTACION_FINAL       bool not null  comment '',
   PARA_EN_EL_VAGON     int  comment '',
   primary key (DIRECCION, CODIGO_RUTA)
);

/*==============================================================*/
/* Table: RUTA                                                  */
/*==============================================================*/
create table RUTA
(
   CODIGO_RUTA          varchar(10) not null  comment '',
   HORARIO              varchar(30) not null  comment '',
   primary key (CODIGO_RUTA)
);

/*==============================================================*/
/* Table: RUTAS_BUSCADAS                                        */
/*==============================================================*/
create table RUTAS_BUSCADAS
(
   CODIGO_RUTA          varchar(10) not null  comment '',
   CORREO               varchar(100) not null  comment '',
   FECHA_BUSQUEDA       datetime not null  comment '',
   primary key (CODIGO_RUTA, CORREO)
);

/*==============================================================*/
/* Table: USUARIO                                               */
/*==============================================================*/
create table USUARIO
(
   CORREO               varchar(100) not null  comment '',
   CONTRASENNA          int not null  comment '',
   NOMBRE_USUARIO       varchar(55) not null  comment '',
   TIPO_USUARIO         int not null  comment '',
   primary key (CORREO)
);

alter table ARTICULADO add constraint FK_ARTICULA_RUTA_ACTU_RUTA foreign key (CODIGO_RUTA)
      references RUTA (CODIGO_RUTA) on delete restrict on update restrict;

alter table FOTO_ESTACION add constraint FK_FOTO_EST_FOTOS_ESTACION foreign key (DIRECCION)
      references ESTACION (DIRECCION) on delete restrict on update restrict;

alter table NOTICIA add constraint FK_NOTICIA_PUBLICO_USUARIO foreign key (CORREO)
      references USUARIO (CORREO) on delete restrict on update restrict;

alter table PASA_POR add constraint FK_PASA_POR_PASA_POR_ESTACION foreign key (DIRECCION)
      references ESTACION (DIRECCION) on delete restrict on update restrict;

alter table PASA_POR add constraint FK_PASA_POR_PASA_POR2_RUTA foreign key (CODIGO_RUTA)
      references RUTA (CODIGO_RUTA) on delete restrict on update restrict;

alter table RUTAS_BUSCADAS add constraint FK_RUTAS_BU_RUTAS_BUS_RUTA foreign key (CODIGO_RUTA)
      references RUTA (CODIGO_RUTA) on delete restrict on update restrict;

alter table RUTAS_BUSCADAS add constraint FK_RUTAS_BU_RUTAS_BUS_USUARIO foreign key (CORREO)
      references USUARIO (CORREO) on delete restrict on update restrict;

