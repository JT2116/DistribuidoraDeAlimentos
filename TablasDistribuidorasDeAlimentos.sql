drop database if exists DAlimentos;
Create database DAlimentos;

use DAlimentos;


Create table Suplidor(
	ID_Suplidor int not null auto_increment primary key,
	Nombre_Compania varchar(60) not null,
	Nombre_Representante varchar(120),
	Apellido_Representante varchar(120),
	Email varchar(80),
	Telefono varchar(10) not null,
	Enable binary not null
);


Create table Producto(
	ID_Producto int not null auto_increment primary key,
	Suplidor int not null,
	Nombre varchar(150) not null,
	Cantidad int not null,
	PrecioCompra numeric(10,2) not null,
	PrecioVenta numeric(10,2) not null,
	Tipo varchar(40) not null,
	Descripcion varchar(300),
	Enable binary not null,
	constraint FK_SUPLIDOR foreign key (Suplidor) references Suplidor(ID_Suplidor)
);


Create table Cliente(
	ID_Cliente int not null auto_increment primary key,
	Nombres varchar(120) not null,
	Apellidos varchar(120) not null,
	Email varchar(80),
	Telefono varchar(10) not null,
	Credito numeric(10,2) not null,
	Deuda numeric(10,2) not null,
	Enable binary not null
);

Create table Factura(
	ID_Factura int not null auto_increment primary key,
	Cliente int not null,
	Fecha_Creacion datetime not null,
	TipoDePago varchar(30) not null,
	constraint FK_CLIENTE foreign key (Cliente) references Cliente(ID_Cliente)

);

Create table DetallesCompra(
	ID_Detalle int not null,
	Factura int not null,
	Producto int not null,
	Precio numeric(10,2) not null,
	CantidadComprado int not null,
	constraint FK_FACTURA foreign key (Factura) references Factura(ID_Factura),
	constraint FK_PRODUCTO foreign key (Producto) references Producto(ID_Producto),	
	constraint PK_PF primary key (Producto,Factura,ID_Detalle)
);


select Nombre,PrecioVenta,CantidadComprado,Precio from DetallesCompra join Producto on DetallesCompra.Producto = Producto.ID_Producto where Factura = 2

select ID_Producto,Nombre,Cantidad,PrecioCompra,PrecioVenta,Tipo,Descripcion from Producto join Suplidor on Producto.Suplidor = Suplidor.ID_Suplidor where Producto.Suplidor =1 and Producto.Enable = 1

select ID_Factura,TipoDePago,Fecha_Creacion,Nombres,Apellidos,Email,Telefono from Factura join cliente on Factura.Cliente = Cliente.ID_Cliente where Factura.Cliente = 3 order by ID_Factura