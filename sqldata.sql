create database cophieu
go 
use cophieu


create table COPHIEU 
(
   MACK             varchar(256)                      not null,
   TENCONGTY        nvarchar(256)                      not null,
   THONGTIN         nvarchar(256)                      null,
   GIATHAMCHIEU     float                     null,
   GIATRAN			float                     null,
   GIASAN			float                     null,
   GIACAONHAT		float                     null,
   GIATHAPNHAT		float                     null
);


alter table COPHIEU
   add constraint PK_COPHIEU primary key clustered (MACK);

create table LICHSUGD 
(
   MACK             varchar(256)                      not null,
   NGAY				date                      not null,
   GIADIEUCHINH     float                      null,
   GIADONGCUA		float                     null,
   KHOILUONG		int						null,
   GIATRI			int						null,
   GIAMOCUA			float                     null,
   GIACAONHAT		float                     null,
   GIATHAPNHAT		float                     null
);
