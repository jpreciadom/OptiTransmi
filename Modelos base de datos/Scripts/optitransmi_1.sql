/*==============================================================*/
/* DBMS name:      MySQL 5.0                                    */
/* Created on:     19/05/2020 11:48:33                          */
/*==============================================================*/


drop table if exists ARTICULADO;

drop table if exists ESTACION;

drop table if exists FOTO_ESTACION;

drop table if exists NOTICIA;

drop table if exists PASA_POR;

drop table if exists RUTA;

drop table if exists RUTAS_BUSCADAS;

drop table if exists USUARIO;

/*==============================================================*/
/* Table: ARTICULADO                                            */
/*==============================================================*/
create table ARTICULADO
(
   PLACA                varchar(10) not null,
   FECHA_ADQUISICION    date not null,
   BIARTICULADO         bool not null,
   CAPACIDAD            int not null,
   CODIGO_RUTA          varchar(40),
   DIA                  varchar(10),
   primary key (PLACA)
);

/*==============================================================*/
/* Table: ESTACION                                              */
/*==============================================================*/
create table ESTACION
(
   NOMBRE_ESTACION      varchar(60) not null,
   DIRECCION            varchar(50) not null,
   ZONA                 varchar(1) not null,
   VAGONES              int not null,
   primary key (DIRECCION)
);

/*==============================================================*/
/* Table: FOTO_ESTACION                                         */
/*==============================================================*/
create table FOTO_ESTACION
(
   DIRECCION            varchar(50) not null,
   ID_FOTO              int not null,
   DIRECCION_FOTO       varchar(30) not null,
   primary key (DIRECCION, ID_FOTO)
);

/*==============================================================*/
/* Table: NOTICIA                                               */
/*==============================================================*/
create table NOTICIA
(
   ID_NOTICIA           int not null,
   CORREO               varchar(100),
   CONTENIDO            varchar(141) not null,
   TITULO               varchar(30) not null,
   primary key (ID_NOTICIA)
);

/*==============================================================*/
/* Table: PASA_POR                                              */
/*==============================================================*/
create table PASA_POR
(
   DIRECCION            varchar(50) not null,
   CODIGO_RUTA          varchar(40) not null,
   DIA                  varchar(10) not null,
   ESTACION_INICIO      bool not null,
   ESTACION_FINAL       bool not null,
   PARA_EN_EL_VAGON     int,
   primary key (DIRECCION, CODIGO_RUTA, DIA)
);

/*==============================================================*/
/* Table: RUTA                                                  */
/*==============================================================*/
create table RUTA
(
   CODIGO_RUTA          varchar(40) not null,
   DIA                  varchar(10) not null,
   INICIO               time not null,
   FIN                  time not null,
   primary key (CODIGO_RUTA, DIA)
);

/*==============================================================*/
/* Table: RUTAS_BUSCADAS                                        */
/*==============================================================*/
create table RUTAS_BUSCADAS
(
   CODIGO_RUTA          varchar(10) not null,
   DIA                  varchar(10) not null,
   CORREO               varchar(100) not null,
   FECHA_BUSQUEDA       datetime not null,
   primary key (CODIGO_RUTA, DIA, CORREO)
);

/*==============================================================*/
/* Table: USUARIO                                               */
/*==============================================================*/
create table USUARIO
(
   CORREO               varchar(100) not null,
   CONTRASENNA          varchar(20) not null,
   NOMBRE_USUARIO       varchar(55) not null,
   TIPO_USUARIO         int not null,
   primary key (CORREO)
);

alter table ARTICULADO add constraint FK_RUTA_ACTUAL foreign key (CODIGO_RUTA, DIA)
      references RUTA (CODIGO_RUTA, DIA) on delete restrict on update restrict;

alter table FOTO_ESTACION add constraint FK_FOTOS foreign key (DIRECCION)
      references ESTACION (DIRECCION) on delete restrict on update restrict;

alter table NOTICIA add constraint FK_PUBLICO foreign key (CORREO)
      references USUARIO (CORREO) on delete restrict on update restrict;

alter table PASA_POR add constraint FK_PASA_POR foreign key (DIRECCION)
      references ESTACION (DIRECCION) on delete restrict on update restrict;

alter table PASA_POR add constraint FK_PASA_POR2 foreign key (CODIGO_RUTA, DIA)
      references RUTA (CODIGO_RUTA, DIA) on delete restrict on update restrict;

alter table RUTAS_BUSCADAS add constraint FK_RUTAS_BUSCADAS foreign key (CODIGO_RUTA, DIA)
      references RUTA (CODIGO_RUTA, DIA) on delete restrict on update restrict;

alter table RUTAS_BUSCADAS add constraint FK_RUTAS_BUSCADAS2 foreign key (CORREO)
      references USUARIO (CORREO) on delete restrict on update restrict;

