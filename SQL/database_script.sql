drop database if exists utnphones;
create database utnphones;
use utnphones;
SET GLOBAL event_scheduler = ON;


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
  password varchar(72),
  email varchar(50),
  name varchar(50),
  lastname varchar(50),
  dni int,
  id_city int unsigned,
  address varchar(50),
  role enum('ADMIN','CLIENT','EMPLOYEE','INFRAESTRUCTURE') default 'CLIENT',
  status enum('ACTIVE','DELETED') default 'ACTIVE',
  constraint pk_user primary key (id_user),
  constraint fk_id_city_user foreign key (id_city) references cities(id_city),
  constraint unq_username_users unique (username),
  constraint unq_dni_users unique (dni),
  constraint unq_email_users unique (email)
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

create table bills(
  id_bill int unsigned auto_increment,
  id_line int unsigned,
  id_user int unsigned,
  qty_calls int unsigned default 0,
  total_production_cost double default 0.0,
  total_price double default 0.0,
  issue_date datetime,
  expiration_date datetime,
  status enum('PAID','UNPAID','EXPIRED') default 'UNPAID',
  constraint pk_bills primary key (id_bill),
  constraint fk_id_line_bills foreign key (id_line) references phone_lines(id_line),
  constraint fk_id_user_bills foreign key (id_user) references users (id_user)
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
  id_bill int unsigned default null,
  constraint pk_id_call primary key (id_call),
  constraint fk_id_origin_line_lines_calls foreign key (id_origin_line) references phone_lines(id_line),
  constraint fk_id_destination_line_lines_calls foreign key (id_destination_line) references phone_lines(id_line),
  constraint fk_id_rate_rates_calls foreign key (id_rate) references rates(id_rate),
  constraint fk_id_bill_calls foreign key (id_bill) references bills(id_bill)
);










-- FUNCION QUE DEVUELVE EL ID DE UN NUMERO DE TELEFONO
delimiter //
create function getLineId(pIdCity int, pLineNumer varchar(10))
returns int
reads sql data
begin
    declare vResult int;
    select id_line into vResult from phone_lines where (id_city = pIdCity) and (phone_number = pLineNumer);
	if(vResult is not null) then
		return vResult;
    else
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Line not found', MYSQL_ERRNO = 1000;
    end if;
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
	if (vResult is not null) then
		return vResult;
    else
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'City not found', MYSQL_ERRNO = 1004;
    end if;

end //
delimiter ;

-- TRIGGER PARA EVITAR QUE UN NUMERO SE LLAME A SI MISMO
delimiter //
create trigger tbi_calls_prevent_self_calling before insert on calls for each row
begin
	if (new.id_origin_line = new.id_destination_line) then
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Origin and Destination ID can not be the same', MYSQL_ERRNO = 1002;
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
    if(vResult is not null) then
		return vResult;
    else
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Rate not found', MYSQL_ERRNO = 1003;
    end if;
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
	if(vResult is not null) then
		return vResult;
    else
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Rate not found call price', MYSQL_ERRNO = 1004;
    end if;
end //
delimiter ;

-- FUNCION PARA OBTENER EL COSTO DE UN RATE
delimiter //
create function getCallCost(pId_rate int)
returns double
reads sql data
begin
    declare vResult double;
    select cost_per_minute into vResult from rates where id_rate = pId_rate;
  	if(vResult is not null) then
		return vResult;
    else
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Rate not found call cost', MYSQL_ERRNO = 1005;
    end if;
end //
delimiter ;








-- STORED PROCEDURE INSERT BILLS 
drop procedure if exists sp_generate_bills;
delimiter //
create procedure sp_generate_bills()
begin
  DECLARE vIdLine INTEGER;
  DECLARE vIdUser INTEGER;
  DECLARE vFinished BOOLEAN DEFAULT FALSE;
  DECLARE vQtyCalls INTEGER default 0;
  DECLARE vTotalProductionCost double default 0;
  DECLARE vTotalPrice double default 0;
  
  DECLARE select_active_lines CURSOR FOR
  select id_line, id_user from phone_lines where status = "ACTIVE";
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET vFinished = TRUE;

  DECLARE EXIT HANDLER FOR SQLEXCEPTION
    BEGIN
        ROLLBACK;
    END;
    
  OPEN select_active_lines;
    insert_bill: LOOP
      set autocommit=0;
      start transaction;

      FETCH select_active_lines into vIdLine, vIdUser;
      if `vFinished` THEN LEAVE insert_bill; END IF;

      select count(c.id_call), sum(c.call_price), sum(c.call_cost) into vQtyCalls, vTotalPrice, vTotalProductionCost
      from calls c where (c.id_origin_line = vIdLine) and (id_bill is null);
      
      if(vQtyCalls > 0) then
        insert into bills(id_line, id_user, qty_calls, total_production_cost, total_price, issue_date, expiration_date) values
        (vIdLine, vIdUser, vQtyCalls, vTotalProductionCost, vTotalPrice, curdate(), (curdate() + interval 15 day));
      else 
        insert into bills(id_line, id_user, qty_calls, total_production_cost, total_price, issue_date, expiration_date) values
        (vIdLine, vIdUser, 0, 0, 0, curdate(), (curdate() + interval 15 day));
      end if;

      update calls set id_bill = LAST_INSERT_ID() where id_origin_line = vIdLine;

      commit;
    END LOOP insert_bill;
  CLOSE select_active_lines;

end //
delimiter ;



-- ORIGINAL SP INSERT CALLS 

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
  set vPrice = pDuration * (getCallPrice(vIdRate)/60);
  set vCost = pDuration * (getCallCost(vIdRate)/60);

  insert into calls(id_origin_line, id_destination_line, call_date, id_rate, call_duration, call_cost, call_price)
  values (getLineId(vOriginCityId,vOriginNumber), getLineId(vDestinationCityId,vDestinationNumber),pDate,vIdRate, pDuration,vCost, vPrice);

end //
delimiter ;


-- SP INSERTAR CALLS SUPONIENDO QUE TODOS LOS NUMEROS SIN EL PREFIJO TIENEN UNA LONGITUD DE 7 
-- O SEA QUE NO SE PUEDE DAR EL CASO DE 223-6363325(7) Y 2291-412505(6) (CASO REAL)

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

  set vOriginPrefix = reverse(substring(reverse(pOriginNumber),8));
  set vDestinationPrefix = reverse(substring(reverse(pDestinationNumber),8));

  set vOriginNumber = substring(pOriginNumber,char_length(vOriginPrefix)+1);
  set vDestinationNumber = substring(pDestinationNumber,char_length(vDestinationPrefix)+1);

  set vOriginCityId = getCityIdByPrefix(vOriginPrefix);
  set vDestinationCityId = getCityIdByPrefix(vDestinationPrefix);

  set vIdRate = getIdRate(vOriginCityId,vDestinationCityId);
  set vPrice = pDuration * (getCallPrice(vIdRate)/60);
  set vCost = pDuration * (getCallCost(vIdRate)/60);

  insert into calls(id_origin_line, id_destination_line, call_date, id_rate, call_duration, call_cost, call_price)
  values (getLineId(vOriginCityId,vOriginNumber), getLineId(vDestinationCityId,vDestinationNumber),pDate,vIdRate, pDuration,vCost, vPrice);

end //
delimiter ;


-- SE PUEDE DAR EL CASO DE ENTRE RIOS, CIERTAS LOCALIDADES INICIAN CON (343) Y OTRAS CON (3435)
-- EJEMPLO SI BUSCO EL 343-5363325 HAY AMBIGUEDAD CON EL PREFIJO 3435 

-- EJ:
-- SELECT c.prefix FROM cities c WHERE "3435363325" LIKE CONCAT(c.prefix, '%') ORDER BY LENGTH(c.prefix) DESC LIMIT 1;

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

  SELECT c.prefix INTO vOriginPrefix FROM cities c WHERE pOriginNumber LIKE CONCAT(c.prefix, '%') ORDER BY LENGTH(c.prefix) DESC LIMIT 1;
  SELECT c.prefix INTO vDestinationPrefix FROM cities c WHERE pDestinationNumber LIKE CONCAT(c.prefix, '%') ORDER BY LENGTH(c.prefix) DESC LIMIT 1;

  set vOriginNumber = substring(pOriginNumber,char_length(vOriginPrefix)+1);
  set vDestinationNumber = substring(pDestinationNumber,char_length(vDestinationPrefix)+1);

  set vOriginCityId = getCityIdByPrefix(vOriginPrefix);
  set vDestinationCityId = getCityIdByPrefix(vDestinationPrefix);

  set vIdRate = getIdRate(vOriginCityId,vDestinationCityId);
  set vPrice = pDuration * (getCallPrice(vIdRate)/60);
  set vCost = pDuration * (getCallCost(vIdRate)/60);

  insert into calls(id_origin_line, id_destination_line, call_date, id_rate, call_duration, call_cost, call_price)
  values (getLineId(vOriginCityId,vOriginNumber), getLineId(vDestinationCityId,vDestinationNumber),pDate,vIdRate, pDuration,vCost, vPrice);

end //
delimiter ;






-- EVENTS 
delimiter //
CREATE EVENT create_bills
ON SCHEDULE EVERY 1 MONTH STARTS date_format (date_add(subdate(now(), dayofmonth(now()) - 1), interval 1 month),'%Y-%m-%d 00:00:00')
DO BEGIN
set autocommit = 0;
start transaction;
call sp_generate_bills();
commit;
END //
delimiter;

delimiter //
CREATE EVENT expire_unpaid_bills
ON SCHEDULE EVERY 1 MONTH STARTS date_format (date_add(subdate(now(), dayofmonth(now()) - 1), interval 14 DAY),'%Y-%m-%d 00:00:00')
DO BEGIN
  set autocommit = 0;
  start transaction;
  update bills set status = 'EXPIRED' where (status = 'UNPAID') and (expiration_date - interval 1 day);
  commit;
END //
delimiter ;






-- INDEX
create index idx_calls_user_date on calls(id_origin_line, call_date) using btree;
create index idx_bills_user on bills(id_user) using btree;
create index idx_bills_user_date on bills(id_user, issue_date) using btree;






-- USERS 
-- ## Client
drop user if exists 'client'@'%';
create user 'client'@'%' identified by 'userpassword';

grant select,update on utnphones.users to 'client'@'%';
grant select on utnphones.phone_lines to 'client'@'%';
grant select on utnphones.calls to 'client'@'%';
grant select on utnphones.bills to 'client'@'%';


-- ## Backoffice
drop user if exists 'backoffice'@'%';
create user 'backoffice'@'%' identified by 'backofficepassword';

grant select,insert,update,delete on utnphones.users to 'backoffice'@'%';
grant select,insert,update,delete on utnphones.phone_lines to 'backoffice'@'%';
grant select,insert,update,delete on utnphones.rates to 'backoffice'@'%';
grant select on utnphones.calls to 'backoffice'@'%';
grant select on utnphones.bills to 'backoffice'@'%';


-- ## Infraestructure
drop user if exists 'infraestructure'@'%';
create user 'infraestructure'@'%' identified by 'infraestructurepassword';
GRANT EXECUTE ON PROCEDURE utnphones.sp_insert_call TO 'infraestructure'@'%';


-- ## Billing
drop user if exists 'billing'@'%';
create user 'billing'@'%' identified by 'billingpassword';
GRANT EXECUTE ON PROCEDURE utnphones.sp_generate_bills TO 'billing'@'%';
grant event on utnphones.* to 'billing'@'%';












-- INSERTS PROVINCES
INSERT INTO provinces(province_name) values ("Buenos Aires"),("La Pampa"),("Cordoba"),("Santa Fe"),("Rio Negro"),("Corrientes"),("Entre Rios");

-- INSERTS CITIES
INSERT INTO cities(city_name,id_province,prefix) values ("Mar del Plata",1,"223"),("Miramar",1,"2291"),("Necochea",1,"2262"),("Bahia Blanca",1,"291"),("Balcarce",1,"2266"),("Olavarria",1,"2284"),("Ciudad Autonoma de Buenos Aires",1,"011"),("La plata",1,"221"),("Cordoba Capital",3,"351"),("Santa Rosa",2,"2954");
insert into cities(city_name, id_province, prefix) values ("ENTRE RIOS 1", 1, "343");
insert into cities(city_name, id_province, prefix) values ("ENTRE RIOS 2", 1, "3435");

-- INSERTS USERS
INSERT INTO `users` VALUES (1,'siderjonas','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','soldanochristian@hotmail.com','Christian','Soldano',40020327,1,'Manuel Acevedo 2685','ADMIN','ACTIVE'),(2,'Soker','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','rodriguezviautobias@gmail.com','Tobias','Rodriguez Viau',41216459,2,'Calle Falsa 123','ADMIN','ACTIVE'),(3,'duebel0','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','duebel0@dailymotion.com','Demott','Uebel',26548921,7,'099 Bunker Hill Trail','CLIENT','ACTIVE'),(4,'vgullivan1','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','vgullivan1@netscape.com','Vannie','Gullivan',39965555,3,'06 Luster Way','CLIENT','ACTIVE'),(5,'lbeat2','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','lbeat2@nyu.edu','Leila','Beat',18055777,6,'70849 Blaine Center','CLIENT','ACTIVE'),(6,'alainge3','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','alainge3@woothemes.com','Ameline','Lainge',17733987,4,'61 Magdeline Park','CLIENT','ACTIVE'),(7,'chuc4','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','chuc4@ftc.gov','Corby','Huc',32364518,10,'2 Towne Drive','CLIENT','ACTIVE'),(8,'sguidini5','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','sguidini5@sfgate.com','Silva','Guidini',32936147,9,'22175 Bay Circle','CLIENT','ACTIVE'),(9,'apeyntue6','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','apeyntue6@tinyurl.com','Alvin','Peyntue',15799689,5,'6084 Lake View Center','CLIENT','ACTIVE'),(10,'amcleman7','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','amcleman7@intel.com','Aluin','McLeman',33178319,2,'62 Memorial Court','CLIENT','ACTIVE'),(11,'zbaugh8','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','zbaugh8@bing.com','Zahara','Baugh',19061342,5,'80 Miller Park','CLIENT','ACTIVE'),(12,'wgarett9','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','wgarett9@flickr.com','Wilt','Garett',31919301,9,'01 Grasskamp Pass','CLIENT','ACTIVE'),(13,'rkittoa','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','rkittoa@blogtalkradio.com','Ros','Kitto',36236666,3,'5151 Mesta Trail','CLIENT','ACTIVE'),(14,'ntorrib','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','ntorrib@tinypic.com','Nananne','Torri',34293383,1,'30707 Mockingbird Point','CLIENT','ACTIVE'),(15,'ggarrattleyc','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','ggarrattleyc@nyu.edu','Germana','Garrattley',26935740,7,'810 Lakewood Gardens Way','CLIENT','ACTIVE'),(16,'rabaded','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','rabaded@army.mil','Roland','Abade',24350879,2,'3 Holmberg Parkway','CLIENT','ACTIVE'),(17,'ajakubowskie','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','ajakubowskie@wp.com','Aubrie','Jakubowski',17206840,9,'31756 Mccormick Place','CLIENT','ACTIVE'),(18,'yjolliffef','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','yjolliffef@fda.gov','Yves','Jolliffe',29154178,1,'19 Dennis Junction','CLIENT','ACTIVE'),(19,'pwadesong','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','pwadesong@va.gov','Perry','Wadeson',20099799,7,'935 Anthes Center','CLIENT','ACTIVE'),(20,'eticksallh','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','eticksallh@studiopress.com','Elston','Ticksall',23375975,4,'6 Larry Street','CLIENT','ACTIVE'),(21,'brhubottomi','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','brhubottomi@timesonline.co.uk','Brose','Rhubottom',35243813,1,'4859 Mandrake Parkway','CLIENT','ACTIVE'),(22,'rvalenssmithj','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','rvalenssmithj@amazon.co.jp','Ronnie','Valens-Smith',18822039,4,'0670 Emmet Street','CLIENT','ACTIVE'),(23,'naldrenk','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','naldrenk@istockphoto.com','Nessie','Aldren',37723839,8,'2 Oakridge Place','CLIENT','ACTIVE'),(24,'npierrepointl','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','npierrepointl@gnu.org','Nicky','Pierrepoint',19803675,7,'08 Eliot Avenue','CLIENT','ACTIVE'),(25,'tvigerm','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','tvigerm@amazon.co.uk','Thorvald','Viger',43747576,10,'79762 Manitowish Junction','CLIENT','ACTIVE'),(26,'sbenn','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','sbenn@msn.com','Stu','Ben',20749618,6,'81225 Eastwood Hill','CLIENT','ACTIVE'),(27,'spostianso','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','spostianso@artisteer.com','Shelli','Postians',29823498,8,'6 Buell Hill','CLIENT','ACTIVE'),(28,'bheusticep','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','bheusticep@prweb.com','Babara','Heustice',32012826,5,'3 Morrow Avenue','CLIENT','ACTIVE'),(29,'aredmanq','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','aredmanq@miitbeian.gov.cn','Anet','Redman',42851896,8,'4 Knutson Trail','CLIENT','ACTIVE'),(30,'efaringtonr','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','efaringtonr@webnode.com','Errol','Farington',43393904,7,'3333 Bobwhite Center','CLIENT','ACTIVE'),(31,'xkemshells','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','xkemshells@mit.edu','Ximenez','Kemshell',40306619,10,'5 Stone Corner Court','CLIENT','ACTIVE'),(32,'mlentet','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','mlentet@hubpages.com','Marshall','Lente',37231533,2,'0395 Bowman Alley','CLIENT','ACTIVE'),(33,'ldavidowichu','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','ldavidowichu@google.com.hk','Lefty','Davidowich',42337207,10,'6 Corscot Parkway','CLIENT','ACTIVE'),(34,'smcduffyv','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','smcduffyv@over-blog.com','Savina','McDuffy',27649879,8,'8 Starling Point','CLIENT','ACTIVE'),(35,'ioveralw','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','ioveralw@phoca.cz','Isidoro','Overal',35145338,4,'6 5th Pass','CLIENT','ACTIVE'),(36,'pvaskovx','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','pvaskovx@pbs.org','Phillis','Vaskov',42484141,10,'117 Anniversary Junction','CLIENT','ACTIVE'),(37,'jcotsfordy','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','jcotsfordy@jalbum.net','Janeta','Cotsford',41430251,9,'6 Larry Avenue','CLIENT','ACTIVE'),(38,'oferneleyz','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','oferneleyz@diigo.com','Odo','Ferneley',21290067,4,'9497 Bluestem Circle','CLIENT','ACTIVE'),(39,'jsalan10','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','jsalan10@oracle.com','Josepha','Salan',36798337,10,'13329 Menomonie Pass','CLIENT','ACTIVE'),(40,'ddagon11','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','ddagon11@springer.com','Darren','Dagon',34994083,8,'9 Eastlawn Point','CLIENT','ACTIVE'),(41,'agetten12','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','agetten12@wsj.com','Aldin','Getten',15324468,1,'17 Miller Center','CLIENT','ACTIVE'),(42,'aconnors13','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','aconnors13@cisco.com','Ardisj','Connors',25086590,3,'65 Nancy Lane','CLIENT','ACTIVE'),(43,'vkears14','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','vkears14@meetup.com','Veriee','Kears',24859648,1,'602 Del Mar Alley','CLIENT','ACTIVE'),(44,'vmcareavey15','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','vmcareavey15@cloudflare.com','Virgie','McAreavey',43545251,4,'36623 Bay Terrace','CLIENT','ACTIVE'),(45,'phaville16','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','phaville16@comsenz.com','Pierrette','Haville',29797291,3,'2539 Lindbergh Hill','CLIENT','ACTIVE'),(46,'llongina17','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','llongina17@ehow.com','Lyon','Longina',17579817,3,'23 Mayfield Road','CLIENT','ACTIVE'),(47,'thannabus18','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','thannabus18@domainmarket.com','Teodoro','Hannabus',29068216,1,'9767 Beilfuss Road','CLIENT','ACTIVE'),(48,'ofossey19','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','ofossey19@taobao.com','Ogdan','Fossey',42807307,7,'4 Algoma Pass','CLIENT','ACTIVE'),(49,'dspeare1a','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','dspeare1a@uol.com.br','Davey','Speare',15287562,7,'72071 School Pass','CLIENT','ACTIVE'),(50,'mwiddecombe1b','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','mwiddecombe1b@skyrock.com','Meris','Widdecombe',20373986,4,'08818 Prentice Pass','CLIENT','ACTIVE'),(51,'cbanister1c','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','cbanister1c@engadget.com','Constantine','Banister',31221463,2,'2779 Sugar Crossing','CLIENT','ACTIVE'),(52,'pstolle1d','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','pstolle1d@acquirethisname.com','Pearline','Stolle',33314817,10,'7744 Acker Lane','CLIENT','ACTIVE'),(53,'wpearde1e','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','wpearde1e@jimdo.com','Wynny','Pearde',37066451,2,'04065 Vernon Lane','CLIENT','ACTIVE'),(54,'mcamelli1f','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','mcamelli1f@ebay.co.uk','Misti','Camelli',23328393,9,'65 Meadow Vale Pass','CLIENT','ACTIVE'),(55,'ttalton1g','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','ttalton1g@quantcast.com','Tess','Talton',27468933,5,'91771 Vahlen Plaza','CLIENT','ACTIVE'),(56,'abispham1h','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','abispham1h@time.com','Agneta','Bispham',28386264,1,'007 Sunbrook Road','CLIENT','ACTIVE'),(57,'lmathivet1i','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','lmathivet1i@ucla.edu','Lemar','Mathivet',24067960,6,'684 East Park','CLIENT','ACTIVE'),(58,'htroughton1j','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','htroughton1j@spotify.com','Herrick','Troughton',39142270,10,'60 Nancy Court','CLIENT','ACTIVE'),(59,'jlynd1k','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','jlynd1k@purevolume.com','Jemie','Lynd',34964608,5,'0 Pennsylvania Court','CLIENT','ACTIVE'),(60,'cmurrthum1l','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','cmurrthum1l@plala.or.jp','Christophorus','Murrthum',17325405,10,'1 Karstens Lane','CLIENT','ACTIVE'),(61,'cgeldeard1m','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','cgeldeard1m@hao123.com','Cynthea','Geldeard',32816597,10,'56365 Myrtle Avenue','CLIENT','ACTIVE'),(62,'jscoullar1n','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','jscoullar1n@mlb.com','Joyann','Scoullar',32869430,9,'19 Haas Terrace','CLIENT','ACTIVE'),(63,'tmcilwraith1o','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','tmcilwraith1o@live.com','Toby','McIlwraith',23362978,10,'62 High Crossing Alley','CLIENT','ACTIVE'),(64,'awicher1p','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','awicher1p@latimes.com','Adriena','Wicher',29004332,5,'21 Huxley Parkway','CLIENT','ACTIVE'),(65,'dcushelly1q','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','dcushelly1q@51.la','Deeanne','Cushelly',23893879,8,'249 Victoria Junction','CLIENT','ACTIVE'),(66,'mgillespey1r','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','mgillespey1r@irs.gov','Murdock','Gillespey',30626404,6,'25386 Park Meadow Crossing','CLIENT','ACTIVE'),(67,'jcaines1s','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','jcaines1s@dedecms.com','Joela','Caines',23859189,5,'48282 Hallows Street','CLIENT','ACTIVE'),(68,'jproske1t','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','jproske1t@ycombinator.com','Josephine','Proske',33333356,1,'99 Bunker Hill Avenue','CLIENT','ACTIVE'),(69,'flattimore1u','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','flattimore1u@samsung.com','Freddy','Lattimore',25637367,7,'66453 Florence Drive','CLIENT','ACTIVE'),(70,'wmoorwood1v','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','wmoorwood1v@acquirethisname.com','Werner','Moorwood',26565007,10,'51921 Vahlen Junction','CLIENT','ACTIVE'),(71,'agathercole1w','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','agathercole1w@sphinn.com','Artemus','Gathercole',26138229,4,'0 Truax Drive','CLIENT','ACTIVE'),(72,'scrackett1x','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','scrackett1x@cocolog-nifty.com','Sarita','Crackett',31413381,6,'8621 Brickson Park Avenue','CLIENT','ACTIVE'),(73,'rcaslin1y','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','rcaslin1y@ask.com','Rheba','Caslin',24566851,6,'947 Annamark Park','CLIENT','ACTIVE'),(74,'aheinl1z','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','aheinl1z@meetup.com','Averil','Heinl',15626945,5,'42 Namekagon Pass','CLIENT','ACTIVE'),(75,'whendonson20','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','whendonson20@mozilla.org','Willabella','Hendonson',27999624,2,'08 Eagle Crest Lane','CLIENT','ACTIVE'),(76,'jantrim21','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','jantrim21@rakuten.co.jp','Jefferey','Antrim',25556822,6,'956 Spenser Point','CLIENT','ACTIVE'),(77,'trivelin22','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','trivelin22@howstuffworks.com','Tresa','Rivelin',22816778,5,'940 Schmedeman Trail','CLIENT','ACTIVE'),(78,'deburah23','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','deburah23@google.nl','Derril','Eburah',40086503,5,'572 Sheridan Terrace','CLIENT','ACTIVE'),(79,'fluberto24','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','fluberto24@meetup.com','Francois','Luberto',28784248,1,'739 Acker Street','CLIENT','ACTIVE'),(80,'wlansberry25','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','wlansberry25@list-manage.com','Waylan','Lansberry',40425125,5,'94445 Lakeland Road','CLIENT','ACTIVE'),(81,'nwhitsun26','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','nwhitsun26@aboutads.info','Nollie','Whitsun',23053784,6,'8 Hintze Lane','CLIENT','ACTIVE'),(82,'jhandslip27','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','jhandslip27@odnoklassniki.ru','Jaquelyn','Handslip',15859686,2,'4138 Trailsway Street','CLIENT','ACTIVE'),(83,'dmoulding28','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','dmoulding28@pagesperso-orange.fr','Dosi','Moulding',30077935,6,'1 Blaine Point','CLIENT','ACTIVE'),(84,'ckniveton29','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','ckniveton29@meetup.com','Clare','Kniveton',41693126,8,'2812 Bashford Center','CLIENT','ACTIVE'),(85,'ggreenstead2a','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','ggreenstead2a@163.com','Granny','Greenstead',18147221,5,'5823 Chinook Junction','CLIENT','ACTIVE'),(86,'mdyers2b','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','mdyers2b@guardian.co.uk','Mayer','Dyers',29099316,7,'01807 Elgar Drive','CLIENT','ACTIVE'),(87,'cbarchrameev2c','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','cbarchrameev2c@aboutads.info','Cesaro','Barchrameev',34807799,10,'12 Rigney Alley','CLIENT','ACTIVE'),(88,'csalzen2d','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','csalzen2d@seesaa.net','Constantino','Salzen',24996721,9,'484 Kensington Circle','CLIENT','ACTIVE'),(89,'nwitherow2e','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','nwitherow2e@angelfire.com','Nicol','Witherow',21344655,6,'9789 Lunder Hill','CLIENT','ACTIVE'),(90,'ascrogges2f','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','ascrogges2f@yellowpages.com','Artair','Scrogges',41751491,9,'54 Morning Road','CLIENT','ACTIVE'),(91,'ksperling2g','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','ksperling2g@angelfire.com','Katya','Sperling',42199093,1,'65000 Quincy Trail','CLIENT','ACTIVE'),(92,'bsyce2h','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','bsyce2h@a8.net','Benjamin','Syce',17611888,2,'330 Sunnyside Avenue','CLIENT','ACTIVE'),(93,'jhyams2i','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','jhyams2i@symantec.com','Jeniffer','Hyams',43331088,5,'4605 Garrison Street','CLIENT','ACTIVE'),(94,'hsmeal2j','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','hsmeal2j@amazon.co.uk','Hodge','Smeal',20633190,4,'6 Gale Trail','CLIENT','ACTIVE'),(95,'uciobotaru2k','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','uciobotaru2k@bluehost.com','Umberto','Ciobotaru',38288305,1,'00417 Mallard Center','CLIENT','ACTIVE'),(96,'rslateford2l','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','rslateford2l@google.ru','Robbin','Slateford',15538079,8,'3235 Melrose Plaza','CLIENT','ACTIVE'),(97,'vspitell2m','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','vspitell2m@unicef.org','Vivi','Spitell',27035504,3,'1 Manitowish Junction','CLIENT','ACTIVE'),(98,'apaulon2n','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','apaulon2n@oakley.com','Anatola','Paulon',31357980,10,'4711 Harper Way','CLIENT','ACTIVE'),(99,'rofairy2o','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','rofairy2o@pagesperso-orange.fr','Rouvin','O\'Fairy',19801647,2,'00650 John Wall Pass','CLIENT','ACTIVE'),(100,'lflieg2p','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','lflieg2p@admin.ch','Lutero','Flieg',43890757,1,'97972 Sherman Crossing','CLIENT','ACTIVE'),(101,'wfenton2q','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','wfenton2q@dell.com','Wells','Fenton',39953715,4,'74108 Dwight Junction','CLIENT','ACTIVE'),(102,'fbeamson2r','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','fbeamson2r@about.com','Ferrel','Beamson',40488116,2,'33 Quincy Lane','CLIENT','ACTIVE'),(103,'nuevoser','$2a$10$rPiEAgQNIT1TCoKi3Eqq8eVaRYIRlR29mxZcEAnNATPNwDg4alTz.','falsolian@hotmail.com.ar','Christian','Soldano',444444,1,'Manuel Acevedo 2685','CLIENT','ACTIVE');
-- INSERTS RATES
INSERT INTO rates(id_origin_city, id_destination_city, cost_per_minute, price_per_minute) values (1,1,6,5), (1,2,15,5), (1,3,9,5), (1,4,17,5), (1,5,15,5), (1,6,8,5), (1,7,10,5), (1,8,9,5), (1,9,11,5), (1,10,19,5), (2,1,11,5), (2,2,12,5), (2,3,14,5), (2,4,9,5), (2,5,17,5), (2,6,5,5), (2,7,12,5), (2,8,14,5), (2,9,8,5), (2,10,14,5), (3,1,6,5), (3,2,12,5), (3,3,10,5), (3,4,18,5), (3,5,6,5), (3,6,19,5), (3,7,14,5), (3,8,5,5), (3,9,13,5), (3,10,15,5), (4,1,15,5), (4,2,13,5), (4,3,19,5), (4,4,13,5), (4,5,15,5), (4,6,15,5), (4,7,19,5), (4,8,19,5), (4,9,19,5), (4,10,7,5), (5,1,10,5), (5,2,13,5), (5,3,17,5), (5,4,13,5), (5,5,14,5), (5,6,15,5), (5,7,10,5), (5,8,8,5), (5,9,8,5), (5,10,7,5), (6,1,8,5), (6,2,7,5), (6,3,14,5), (6,4,9,5), (6,5,8,5), (6,6,5,5), (6,7,5,5), (6,8,12,5), (6,9,7,5), (6,10,12,5), (7,1,7,5), (7,2,10,5), (7,3,19,5), (7,4,5,5), (7,5,8,5), (7,6,5,5), (7,7,10,5), (7,8,8,5), (7,9,7,5), (7,10,5,5), (8,1,15,5), (8,2,9,5), (8,3,7,5), (8,4,17,5), (8,5,13,5), (8,6,10,5), (8,7,5,5), (8,8,10,5), (8,9,19,5), (8,10,19,5), (9,1,7,5), (9,2,11,5), (9,3,11,5), (9,4,8,5), (9,5,5,5), (9,6,14,5), (9,7,6,5), (9,8,9,5), (9,9,11,5), (9,10,15,5), (10,1,7,5), (10,2,13,5), (10,3,12,5), (10,4,11,5), (10,5,16,5), (10,6,12,5), (10,7,5,5), (10,8,6,5), (10,9,9,5), (10,10,6,5);

-- INSERTS PHONE_LINES
INSERT INTO `phone_lines` (`id_user`,`id_city`,`phone_number`,`line_type`,`status` ) VALUES (1,1,"6363325","MOBILE","ACTIVE"),(2,2,"4125051","MOBILE","ACTIVE"),(49,5,"4155176","MOBILE","ACTIVE"),(50,2,"9464855","MOBILE","ACTIVE"),(3,2,"3456156","RESIDENTIAL","ACTIVE"),(43,3,"7113169","RESIDENTIAL","ACTIVE"),(88,6,"4855135","MOBILE","ACTIVE"),(13,10,"5862927","MOBILE","ACTIVE"),(63,8,"1471419","MOBILE","ACTIVE"),(24,7,"9605421","RESIDENTIAL","ACTIVE");
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
-- insert into bills (id_line,total_production_cost, total_price, issue_date, expiration_date) values (1,50,300,"2020-03-01","2020-04-01");
-- insert into bills (id_line,total_production_cost, total_price, issue_date, expiration_date) values (1,50,300,"2020-04-01","2020-05-01");
-- insert into bills (id_line,total_production_cost, total_price, issue_date, expiration_date) values (1,50,300,"2020-05-01","2020-06-01");

-- INSERT CALL
-- call sp_insert_call("223-6363325","2291-4125051",now(),60);
-- call sp_insert_call("223-6363325","2291-4125051",now(),60);
-- call sp_insert_call("223-6363325","2291-4125051",now(),60);
-- call sp_insert_call("2291-4125051","223-6363325",now(),60);
-- call sp_insert_call("2291-4125051","223-6363325",now(),60);

--
call sp_insert_call("2236363325","22914125051",now(),60);
call sp_insert_call("2236363325","22914125051",now(),60);
call sp_insert_call("2236363325","22914125051",now(),60);
call sp_insert_call("22914125051","2236363325",now(),60);
call sp_insert_call("22914125051","2236363325",now(),60);
