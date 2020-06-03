drop database if exists utnphones;
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
  role enum('ADMIN','CLIENT','EMPLOYEE','INFRAESTRUCTURE') default 'CLIENT',
  status enum('ACTIVE','DELETED') default 'ACTIVE',
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
  price_per_minute double,
  constraint pk_rates primary key (id_rate),
  constraint fk_id_origin_city_rates foreign key (id_origin_city) references cities(id_city),
  constraint fk_id_destination_city_rates foreign key (id_destination_city) references cities(id_city),
  constraint unq_rate_id_cities unique (id_origin_city,id_destination_city)
);

create table phone_lines(
  id_line int unsigned auto_increment,
  id_user int unsigned,
  id_city int unsigned,
  phone_number varchar(10),
  line_type enum('RESIDENTIAL','MOBILE'),
  status enum('ACTIVE','DELETED','SUSPENDED') default 'ACTIVE',
  constraint pk_lines primary key (id_line),
  constraint fk_id_user_lines foreign key (id_user) references users(id_user),
  constraint fk_id_city_phone_lines foreign key (id_city) references cities(id_city),
  constraint unq_phone_number_lines unique (id_city, phone_number)
);

create table calls(
  id_call int unsigned auto_increment,
  id_origin_line int unsigned,
  id_destination_line int unsigned,
  call_date datetime,
  id_rate int unsigned default null,
  call_duration int unsigned,
  call_cost double default null,
  call_price double default null,
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
  issue_date datetime,
  expiration_date datetime,
  paid boolean default 0,
  constraint pk_bills primary key (id_bill),
  constraint fk_id_line_bills foreign key (id_line) references phone_lines(id_line)  
);


-- FUNCION QUE DEVUELVE EL ID DE UN NUMERO DE TELEFONO 
delimiter //
create function getLineId(pIdCity int, pLineNumer varchar(10))
returns int
reads sql data
begin
    declare vResult int;
    select id_line into vResult from phone_lines where (id_city = pIdCity) and (phone_number = pLineNumer);
    return vResult;
end //
delimiter ;

-- FUNCION QUE DEVUELVE EL ID DE UNA CITY BASADO EN UN PREFIJO 
delimiter //
create function getCityIdByPrefix(pPrefix varchar(5))
returns int
reads sql data
begin
    declare vResult int;
    select id_city into vResult from cities where (prefix = pPrefix);
    return vResult;
end //
delimiter ;

-- TRIGGER PARA EVITAR QUE UN NUMERO SE LLAME A SI MISMO
delimiter //
create trigger tbi_calls_prevent_self_calling before insert on calls for each row
begin
	if (new.id_origin_line = new.id_destination_line) then
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Origin and Destination ID can not be the same', MYSQL_ERRNO = 1001;
  end if;
end //
delimiter ;

-- FUNCION PARA OBTENER EL ID_RATE BASADO EN ID_ORIGEN_CITY E ID_DESTINO_CITY
delimiter //
create function getIdRate(pIdOriginLine int, pIdDestinationLine int)
returns int
reads sql data
begin 
    declare vResult int;
    select id_rate into vResult from rates where (id_origin_city = pIdOriginLine) and (id_destination_city = pIdDestinationLine);
    return vResult;
end //
delimiter ;

-- FUNCION PARA OBTENER EL PRECIO DE UN RATE
delimiter //
create function getCallPrice(pId_rate int)
returns double
reads sql data
begin 
    declare vResult double;
    select price_per_minute into vResult from rates where id_rate = pId_rate;
    return vResult;
end //
delimiter ;

-- FUNCION PARA OBTENER EL PRECIO DE UN RATE
delimiter //
create function getCallCost(pId_rate int)
returns double
reads sql data
begin 
    declare vResult double;
    select cost_per_minute into vResult from rates where id_rate = pId_rate;
    return vResult;
end //
delimiter ;

-- STORED PROCEDURE QUE INSERTA UNA NUEVA CALL 
delimiter // 
create procedure sp_insert_call(pOriginNumber varchar(16), pDestinationNumber varchar(16), pDate datetime, pDuration int)
begin
  declare vOriginPrefix varchar(5);
  declare vDestinationPrefix varchar(5);
  declare vOriginNumber varchar(10);
  declare vDestinationNumber varchar(10);
  declare vOriginCityId int;
  declare vDestinationCityId int;
  declare vIdRate int;
  declare vPrice double;
  declare vCost double;

  set vOriginPrefix = substring_index(pOriginNumber,"-",1);
  set vDestinationPrefix = substring_index(pDestinationNumber,"-",1);
  set vOriginNumber = substring_index(pOriginNumber,"-",-1);
  set vDestinationNumber = substring_index(pDestinationNumber,"-",-1);
  set vOriginCityId = getCityIdByPrefix(vOriginPrefix);
  set vDestinationCityId = getCityIdByPrefix(vDestinationPrefix);

  set vIdRate = getIdRate(vOriginCityId,vDestinationCityId);
  set vPrice = pDuration * getCallPrice(vIdRate);
  set vCost = pDuration * getCallCost(vIdRate);

  insert into calls(id_origin_line, id_destination_line, call_date, id_rate, call_duration, call_cost, call_price)
  values (getLineId(vOriginCityId,vOriginNumber), getLineId(vDestinationCityId,vDestinationNumber),pDate,vIdRate, pDuration,vCost, vPrice);
    
end //
delimiter ; 

-- INSERTS PROVINCES
INSERT INTO provinces(province_name) values ("Buenos Aires"),("La Pampa"),("Cordoba"),("Santa Fe"),("Rio Negro"),("Corrientes"),("Entre Rios");

-- INSERTS CITIES
INSERT INTO cities(city_name,id_province,prefix) values ("Mar del Plata",1,"223"),("Miramar",1,"2291"),("Necochea",1,"2262"),("Bahia Blanca",1,"291"),("Balcarce",1,"2266"),("Olavarria",1,"2284"),("Ciudad Autonoma de Buenos Aires",1,"011"),("La plata",1,"221"),("Cordoba Capital",3,"351"),("Santa Rosa",2,"2954");

-- INSERTS USERS
INSERT INTO `users` (`username`,`password`,`name`,`lastname`,`dni`,`id_city`,`role`) VALUES ("siderjonas","pastrana","Christian","Soldano",40020327,1,"client"),("Soker","tobiasrv","Tobias","Rodriguez Viau",41216459,2,"client"),("Hadley","IQQ01AEE8RZ","Vivian","Lynch",17086970,8,"client"),("Gloria","FDB42QHB8IH","Demetrius","Brennan",41571814,7,"client"),("Macon","ULK20VWV2DC","Damon","Salinas",15314357,7,"client"),("Ezekiel","PRA31VXO7OH","Haley","Allison",24202784,10,"client"),("Briar","CNT80TQJ4DL","Nomlanga","Alford",19115569,6,"client"),("Eric","PCG80MPR8EE","Hilary","Wynn",20803324,5,"client");
INSERT INTO `users` (`username`,`password`,`name`,`lastname`,`dni`,`id_city`,`role`) VALUES ("Maisie","FIK63URR0YI","Gray","Rollins",15964934,1,"client"),("Macaulay","TTJ86BEY7FI","Kaden","Mcclain",30595780,6,"client"),("Taylor","XLV26VKY5DB","Zena","May",15141960,10,"client"),("Isaac","KJY84VCT9XI","Abraham","Gilmore",42436632,4,"client"),("Asher","BUM04FXQ4UK","Mia","Tyson",23981647,5,"client"),("Rachel","ABS32BGY0TL","Lucius","Burke",27673033,1,"client"),("Hall","OGU32OFB7BL","Daria","Burnett",23478504,5,"client"),("Martin","CXF84DBZ4GM","Dahlia","Chambers",19944189,3,"client"),("Brianna","PDE12CQR3MN","Zelenia","Reyes",42484766,7,"client"),("Prescott","ROY90PPO4PW","Yuri","Charles",38944538,3,"client");
INSERT INTO `users` (`username`,`password`,`name`,`lastname`,`dni`,`id_city`,`role`) VALUES ("Kieran","XLQ80FPF3NQ","Rhonda","Pierce",42811839,9,"client"),("Cairo","WXU31PZN5GA","Illiana","Conrad",39898544,4,"client"),("Price","ILL15LCF3UZ","Lewis","Williams",34182879,5,"client"),("Deirdre","NPT68VZD1ML","Carson","Harrell",22459768,1,"client"),("Edan","CJF10YBW5WQ","Chastity","Nicholson",43414428,10,"client"),("Aaron","IZC36PRX2AW","Halee","Pickett",28862585,1,"client"),("Hilel","UGG76EOS8MT","Tiger","Peterson",37854344,1,"client"),("Hollee","TAV42TPX6HS","Phelan","Rush",25436042,4,"client"),("Jarrod","HEA51UKU8KK","Dexter","Hensley",19006015,3,"client"),("Eaton","YYY95KOZ6ZF","Jenette","Thomas",27646304,2,"client");
INSERT INTO `users` (`username`,`password`,`name`,`lastname`,`dni`,`id_city`,`role`) VALUES ("Vera","LDI35URH9FJ","Rinah","Conner",33472672,5,"client"),("Hermione","JVV52PJX5KB","Chester","Key",32162195,6,"client"),("Juliet","TGN10FSR9CR","Brock","Sweet",17112028,6,"client"),("Maggie","YHA91QNL3LK","Yuri","Vega",40371930,4,"client"),("Violet","AHB73VJD2ND","Blair","Lambert",32816169,8,"client"),("Aubrey","AAF58VOE0HL","Emerald","Fulton",18851271,10,"client"),("Stella","IJK13SAR8UK","Kennan","Whitley",25481781,9,"client"),("Bevis","MHZ07BWE3QQ","Louis","Wilkinson",34116407,6,"client"),("Signe","ZGC25BIB9PI","Hedwig","Leonard",18237951,2,"client"),("Brady","ENL37VXB9LP","Cruz","Coffey",29329606,3,"client");
INSERT INTO `users` (`username`,`password`,`name`,`lastname`,`dni`,`id_city`,`role`) VALUES ("Rafael","SEE14GRD5XJ","Garrett","Vinson",30365693,10,"client"),("Catherine","MTN45APA7JE","Ifeoma","Merrill",22276210,7,"client"),("Colin","IWK06TMZ9FL","Jared","Reeves",31034213,7,"client"),("Wayne","EHO73STJ1CR","Rebecca","Bright",22992019,10,"client"),("Reed","PIP95LDC5NC","Hermione","Parsons",39888957,9,"client"),("Rhoda","OVT65HKM5CG","Luke","Hale",29294315,8,"client"),("Angelica","XYK24TNE0RB","Ivor","Alford",39267598,10,"client"),("Kevin","BHP79AYK1RO","Lacy","Delacruz",17227827,6,"client"),("Stacey","SCD42MWN1CL","Tamekah","Mccormick",38603726,5,"client"),("Carl","KLP73SYG7CR","Pandora","Bridges",16939251,10,"client");
INSERT INTO `users` (`username`,`password`,`name`,`lastname`,`dni`,`id_city`,`role`) VALUES ("Jordan","MCP03JTB1FQ","Erin","Goodman",18836155,1,"client"),("Sylvester","BRE25HFH6FF","Patricia","Shelton",19746495,9,"client"),("Francis","SPW56EPO5SV","Brent","Hancock",32974553,9,"client"),("Leilani","TVQ41YRK1BG","Kellie","Cruz",39368815,8,"client"),("Dalton","RMH41SXR0OC","Holmes","Rosales",17550414,3,"client"),("Hope","PEV02NZB3QP","Brendan","Peters",31725805,4,"client"),("Constance","ZID92CSZ9HT","Xaviera","Gross",35391849,6,"client"),("Oprah","ZXN27BPC8LK","Phelan","Lawson",22901376,7,"client"),("Charissa","WXL95LAF5TZ","Anastasia","Dejesus",41614333,1,"client"),("Geraldine","JWB01NJS6WY","Laura","Wolfe",35912723,10,"client");
INSERT INTO `users` (`username`,`password`,`name`,`lastname`,`dni`,`id_city`,`role`) VALUES ("Cecilia","CQT97KFH7YV","Ezekiel","Farley",23961021,10,"client"),("Ferdinand","EJM04DHR9NF","Jaden","Haley",27083204,3,"client"),("Amena","MTP95JQW3RU","Kelly","Rosario",31801509,2,"client"),("Silas","VOD77MAN2HL","Jerome","Franco",17651481,2,"client"),("Nehru","AYS58QIM1XB","Macey","Garcia",28179472,5,"client"),("Phelan","DPE20BCX9ZP","Rhonda","Morrison",19503016,7,"client"),("Uta","RZK98QHR5FJ","Jelani","Knowles",27236970,10,"client"),("Kathleen","MDJ14AGI4US","Eaton","Carrillo",39290158,8,"client"),("Perry","XJL63FSY1OB","Rebekah","Peterson",30219905,6,"client"),("Quinn","BLB79FUC6MU","Alvin","Bentley",33822765,2,"client");
INSERT INTO `users` (`username`,`password`,`name`,`lastname`,`dni`,`id_city`,`role`) VALUES ("Hasad","WYA70ZLI3SE","Mallory","Jacobs",17034038,9,"client"),("Amy","SZI63XXD2BF","Zena","Calhoun",42500779,5,"client"),("Iola","OGX50FDV4DR","Georgia","Lopez",15784358,4,"client"),("Lenore","FND18UDS3UT","Jerome","Downs",37072568,5,"client"),("Gage","RUC21DOF0BZ","Moses","Reed",15650456,8,"client"),("Emi","TTO68AUM4MI","Casey","Santiago",16637494,5,"client"),("Kirestin","DTE17WHV8RS","Cara","Mosley",39711496,6,"client"),("Willow","XKY89EXZ5XQ","Armando","Turner",19160732,1,"client"),("Cleo","WHO80YFO2EJ","Barry","Acosta",30234860,10,"client"),("Amethyst","VMS06NFS1MJ","Leila","Morton",40554560,4,"client");
INSERT INTO `users` (`username`,`password`,`name`,`lastname`,`dni`,`id_city`,`role`) VALUES ("Abdul","ONE22XMF1CY","Stuart","Garcia",22483592,5,"client"),("Heidi","IJF22DRO9VW","Drake","Tran",26393838,3,"client"),("Winter","UNI51WGY2YK","Miranda","Pugh",19770404,9,"client"),("Jorden","UJZ35DCF6UM","Dominic","Dillard",16311824,5,"client"),("Sarah","YHO01MHZ7SX","Aiko","Phelps",23624910,3,"client"),("Patience","TUN01VUH9AV","Justine","Blackburn",41568528,2,"client"),("Kyla","ZXT82UQL2QB","Lance","Cash",38259590,7,"client"),("Dennis","LIQ87PEK2GH","Yasir","York",42388428,7,"client");
INSERT INTO `users` (`username`,`password`,`name`,`lastname`,`dni`,`id_city`,`role`) VALUES ("Jacob","BEH09LYI2KU","Myra","Valencia",27881912,8,"client"),("Pearl","RWU98FMC2SC","Raja","Graves",16667714,4,"client"),("Maya","SKR57CXO3IA","Tashya","Avila",19509946,1,"client"),("Denton","CMY15BOK8HY","Reece","Avila",43029942,5,"client"),("Cherokee","LFT62NJD6NW","Sloane","Fisher",27327464,2,"client"),("Wade","SIA49HJC1KA","Kimberly","Hart",32923400,5,"client"),("Dylan","QLV00HDS6TM","Hilel","Mcintyre",39169169,3,"client"),("Nicholas","FGJ48ZLF0OQ","Buckminster","Rose",16851802,2,"client"),("Teagan","COG55PIF2EM","Jeremy","Winters",43400128,8,"client"),("Keegan","OUT90GKQ2MM","Dante","Taylor",38077433,9,"client");

-- INSERTS RATES
INSERT INTO rates(id_origin_city, id_destination_city, cost_per_minute, price_per_minute) values (1,1,6,5), (1,2,15,5), (1,3,9,5), (1,4,17,5), (1,5,15,5), (1,6,8,5), (1,7,10,5), (1,8,9,5), (1,9,11,5), (1,10,19,5), (2,1,11,5), (2,2,12,5), (2,3,14,5), (2,4,9,5), (2,5,17,5), (2,6,5,5), (2,7,12,5), (2,8,14,5), (2,9,8,5), (2,10,14,5), (3,1,6,5), (3,2,12,5), (3,3,10,5), (3,4,18,5), (3,5,6,5), (3,6,19,5), (3,7,14,5), (3,8,5,5), (3,9,13,5), (3,10,15,5), (4,1,15,5), (4,2,13,5), (4,3,19,5), (4,4,13,5), (4,5,15,5), (4,6,15,5), (4,7,19,5), (4,8,19,5), (4,9,19,5), (4,10,7,5), (5,1,10,5), (5,2,13,5), (5,3,17,5), (5,4,13,5), (5,5,14,5), (5,6,15,5), (5,7,10,5), (5,8,8,5), (5,9,8,5), (5,10,7,5), (6,1,8,5), (6,2,7,5), (6,3,14,5), (6,4,9,5), (6,5,8,5), (6,6,5,5), (6,7,5,5), (6,8,12,5), (6,9,7,5), (6,10,12,5), (7,1,7,5), (7,2,10,5), (7,3,19,5), (7,4,5,5), (7,5,8,5), (7,6,5,5), (7,7,10,5), (7,8,8,5), (7,9,7,5), (7,10,5,5), (8,1,15,5), (8,2,9,5), (8,3,7,5), (8,4,17,5), (8,5,13,5), (8,6,10,5), (8,7,5,5), (8,8,10,5), (8,9,19,5), (8,10,19,5), (9,1,7,5), (9,2,11,5), (9,3,11,5), (9,4,8,5), (9,5,5,5), (9,6,14,5), (9,7,6,5), (9,8,9,5), (9,9,11,5), (9,10,15,5), (10,1,7,5), (10,2,13,5), (10,3,12,5), (10,4,11,5), (10,5,16,5), (10,6,12,5), (10,7,5,5), (10,8,6,5), (10,9,9,5), (10,10,6,5);

-- INSERTS PHONE_LINES 
INSERT INTO `phone_lines` (`id_user`,`id_city`,`phone_number`,`line_type`,`status` ) VALUES (1,1,"6363325","MOBILE","ACTIVE"),(2,2,"412505","MOBILE","ACTIVE"),(49,5,"4155176","MOBILE","ACTIVE"),(50,2,"9464855","MOBILE","ACTIVE"),(3,2,"3456156","RESIDENTIAL","ACTIVE"),(43,3,"7113169","RESIDENTIAL","ACTIVE"),(88,6,"4855135","MOBILE","ACTIVE"),(13,10,"5862927","MOBILE","ACTIVE"),(63,8,"1471419","MOBILE","ACTIVE"),(24,7,"9605421","RESIDENTIAL","ACTIVE");
INSERT INTO `phone_lines` (`id_user`,`id_city`,`phone_number`,`line_type`,`status` ) VALUES (39,2,"3730060","RESIDENTIAL","ACTIVE"),(25,10,"8118949","RESIDENTIAL","ACTIVE"),(88,9,"9676506","MOBILE","ACTIVE"),(31,2,"4678017","MOBILE","ACTIVE"),(93,10,"3027481","RESIDENTIAL","ACTIVE"),(40,6,"3415217","RESIDENTIAL","ACTIVE"),(86,7,"9788484","RESIDENTIAL","ACTIVE"),(79,3,"3655135","MOBILE","ACTIVE"),(52,4,"5311449","RESIDENTIAL","ACTIVE"),(5,4,"6191557","RESIDENTIAL","ACTIVE");
INSERT INTO `phone_lines` (`id_user`,`id_city`,`phone_number`,`line_type`,`status` ) VALUES (92,7,"5628958","RESIDENTIAL","ACTIVE"),(69,5,"2567757","RESIDENTIAL","ACTIVE"),(64,7,"6975705","RESIDENTIAL","ACTIVE"),(30,9,"2221318","RESIDENTIAL","ACTIVE"),(5,9,"3138217","MOBILE","ACTIVE"),(96,7,"7603644","RESIDENTIAL","ACTIVE"),(75,8,"6232279","RESIDENTIAL","ACTIVE"),(67,3,"3692339","MOBILE","ACTIVE"),(87,7,"6997606","MOBILE","ACTIVE"),(13,4,"1377869","RESIDENTIAL","ACTIVE");
INSERT INTO `phone_lines` (`id_user`,`id_city`,`phone_number`,`line_type`,`status` ) VALUES (44,1,"2090542","RESIDENTIAL","ACTIVE"),(71,10,"4531090","RESIDENTIAL","ACTIVE"),(25,8,"5226104","MOBILE","ACTIVE"),(66,4,"1194363","RESIDENTIAL","ACTIVE"),(69,3,"6759301","MOBILE","ACTIVE"),(30,3,"9101212","RESIDENTIAL","ACTIVE"),(11,2,"7608900","RESIDENTIAL","ACTIVE"),(38,5,"4669203","MOBILE","ACTIVE"),(33,8,"1732779","RESIDENTIAL","ACTIVE"),(80,3,"2046194","MOBILE","ACTIVE");
INSERT INTO `phone_lines` (`id_user`,`id_city`,`phone_number`,`line_type`,`status` ) VALUES (19,9,"9870643","MOBILE","ACTIVE"),(32,6,"3432216","RESIDENTIAL","ACTIVE"),(18,10,"2099555","RESIDENTIAL","ACTIVE"),(56,4,"1343833","MOBILE","ACTIVE"),(15,4,"5036041","RESIDENTIAL","ACTIVE"),(86,4,"4185732","RESIDENTIAL","ACTIVE"),(76,9,"9954776","MOBILE","ACTIVE"),(35,9,"5487503","MOBILE","ACTIVE"),(68,9,"1513662","MOBILE","ACTIVE"),(69,5,"5982281","RESIDENTIAL","ACTIVE");
INSERT INTO `phone_lines` (`id_user`,`id_city`,`phone_number`,`line_type`,`status` ) VALUES (37,2,"8081915","RESIDENTIAL","ACTIVE"),(19,5,"4897767","RESIDENTIAL","ACTIVE"),(9,7,"6150966","RESIDENTIAL","ACTIVE"),(5,7,"1573570","MOBILE","ACTIVE"),(30,8,"3461076","MOBILE","ACTIVE"),(34,6,"7505563","RESIDENTIAL","ACTIVE"),(26,1,"1229731","MOBILE","ACTIVE"),(25,7,"4883478","RESIDENTIAL","ACTIVE"),(49,1,"6339009","RESIDENTIAL","ACTIVE"),(4,2,"9413192","MOBILE","ACTIVE");
INSERT INTO `phone_lines` (`id_user`,`id_city`,`phone_number`,`line_type`,`status` ) VALUES (70,9,"8532006","RESIDENTIAL","ACTIVE"),(61,6,"4111354","MOBILE","ACTIVE"),(14,2,"3255592","MOBILE","ACTIVE"),(46,9,"6625944","RESIDENTIAL","ACTIVE"),(26,5,"1597786","MOBILE","ACTIVE"),(45,9,"2560024","MOBILE","ACTIVE"),(12,1,"6339984","RESIDENTIAL","ACTIVE"),(2,10,"8460643","RESIDENTIAL","ACTIVE"),(17,10,"6673729","MOBILE","ACTIVE"),(77,5,"1629195","MOBILE","ACTIVE");
INSERT INTO `phone_lines` (`id_user`,`id_city`,`phone_number`,`line_type`,`status` ) VALUES (81,7,"5905747","MOBILE","ACTIVE"),(64,1,"4497365","MOBILE","ACTIVE"),(15,7,"1038533","MOBILE","ACTIVE"),(25,6,"2317101","RESIDENTIAL","ACTIVE"),(69,5,"4555869","RESIDENTIAL","ACTIVE"),(38,1,"1912831","RESIDENTIAL","ACTIVE"),(24,3,"1684975","RESIDENTIAL","ACTIVE"),(83,4,"2401886","RESIDENTIAL","ACTIVE"),(46,7,"4769340","RESIDENTIAL","ACTIVE"),(70,7,"6309753","RESIDENTIAL","ACTIVE");
INSERT INTO `phone_lines` (`id_user`,`id_city`,`phone_number`,`line_type`,`status` ) VALUES (67,4,"2796632","MOBILE","ACTIVE"),(56,4,"9376108","RESIDENTIAL","ACTIVE"),(73,1,"1860250","MOBILE","ACTIVE"),(44,1,"9917384","MOBILE","ACTIVE"),(72,3,"8995537","RESIDENTIAL","ACTIVE"),(6,1,"7485195","MOBILE","ACTIVE"),(1,8,"7261560","RESIDENTIAL","ACTIVE"),(7,3,"7117741","MOBILE","ACTIVE"),(84,6,"3429158","MOBILE","ACTIVE"),(71,8,"4236322","RESIDENTIAL","ACTIVE");
INSERT INTO `phone_lines` (`id_user`,`id_city`,`phone_number`,`line_type`,`status` ) VALUES (96,7,"5982727","MOBILE","ACTIVE"),(60,2,"5099304","MOBILE","ACTIVE"),(60,9,"9526473","MOBILE","ACTIVE"),(25,10,"2763612","MOBILE","ACTIVE"),(60,10,"7674983","RESIDENTIAL","ACTIVE"),(74,1,"9868979","MOBILE","ACTIVE"),(89,3,"6453135","RESIDENTIAL","ACTIVE"),(12,1,"3788082","MOBILE","ACTIVE"),(73,2,"2638911","MOBILE","ACTIVE"),(18,4,"2106568","RESIDENTIAL","ACTIVE");


-- INSERT EN BILLS (PARA TESTING)
insert into bills (id_line,total_production_cost, total_price, issue_date, expiration_date) values (1,50,300,"2020-03-01","2020-04-01");
insert into bills (id_line,total_production_cost, total_price, issue_date, expiration_date) values (1,50,300,"2020-04-01","2020-05-01");
insert into bills (id_line,total_production_cost, total_price, issue_date, expiration_date) values (1,50,300,"2020-05-01","2020-06-01");

-- INSERT CALL 
call sp_insert_call("223-6363325","2291-412505", now(), 300);