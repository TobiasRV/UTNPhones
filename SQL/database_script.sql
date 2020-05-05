create database utnphones;
use utnphones;

create table provinces(
  id_province int unsigned auto_increment,
  province_name varchar(50),
  constraint pk_province primary key (id_province),
  constraint unq_province_name_provinces unique (province_name)
);

create table cities(
  id_city int unsigned auto_increment,
  city_name varchar(50),
  id_province int unsigned,
  prefix varchar(5),
  constraint pk_cities primary key (id_city),
  constraint fk_id_province_cities foreign key (id_province) references provinces(id_province),
  constraint unq_prefix_cities unique (prefix)
);

create table users(
  id_user int unsigned auto_increment,
  username varchar(50),
  password varchar(50),
  name varchar(50),
  lastname varchar(50),
  dni int,
  id_city int unsigned,
  role varchar(50),
  constraint pk_user primary key (id_user),
  constraint fk_id_city_user foreign key (id_city) references cities(id_city),
  constraint unq_username_users unique (username),
  constraint unq_dni_users unique (dni)
);

create table rates(
  id_rate int unsigned auto_increment,
  id_origin_city int unsigned,
  id_destination_city int unsigned,
  cost_per_minute double,
  constraint pk_rates primary key (id_rate),
  constraint fk_id_origin_city_rates foreign key (id_origin_city) references cities(id_city),
  constraint fk_id_destination_city_rates foreign key (id_destination_city) references cities(id_city)
);

create table phone_lines(
  id_line int unsigned auto_increment,
  id_user int unsigned,
  id_city int unsigned,
  phone_number varchar(50),
  line_type varchar(50),
  status varchar(50),
  constraint pk_lines primary key (id_line),
  constraint fk_id_user_lines foreign key (id_user) references users(id_user),
  constraint pk_id_city_cities foreign key (id_city) references cities(id_city),
  constraint unq_phone_number_lines unique (phone_number)
);

create table calls(
  id_call int unsigned auto_increment,
  id_origin_line int unsigned,
  id_destination_line int unsigned,
  call_date date,
  id_rate int unsigned,
  call_duration int,
  call_cost double,
  call_price double,
  constraint pk_id_call primary key (id_call),
  constraint fk_id_origin_line_lines_calls foreign key (id_origin_line) references phone_lines(id_line),
  constraint fk_id_destination_line_lines_calls foreign key (id_destination_line) references phone_lines(id_line),
  constraint fk_id_rate_rates_calls foreign key (id_rate) references rates(id_rate)
);

create table bills(
  id_bill int unsigned auto_increment,
  id_line int unsigned,
  total_production_cost double,
  total_price double,
  issue_date date,
  expiration_date date,
  paid boolean,
  constraint pk_bills primary key (id_bill),
  constraint fk_id_line_bills foreign key (id_line) references phone_lines(id_line)  
);