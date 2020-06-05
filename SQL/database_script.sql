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
  password varchar(50),
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

create table calls(
  id_call int unsigned auto_increment,
  id_origin_line int unsigned,
  id_destination_line int unsigned,
  call_date datetime,
  id_rate int unsigned default null,
  call_duration int unsigned,
  call_cost double default null,
  call_price double default null,
  registered boolean default 0,
  constraint pk_id_call primary key (id_call),
  constraint fk_id_origin_line_lines_calls foreign key (id_origin_line) references phone_lines(id_line),
  constraint fk_id_destination_line_lines_calls foreign key (id_destination_line) references phone_lines(id_line),
  constraint fk_id_rate_rates_calls foreign key (id_rate) references rates(id_rate)
);

create table bills(
  id_bill int unsigned auto_increment,
  id_line int unsigned,
  total_production_cost double default 0.0,
  total_price double default 0.0,
  issue_date datetime,
  expiration_date datetime,
  status enum('PAID','UNPAID','EXPIRED'),
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
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Rate not found', MYSQL_ERRNO = 1004;
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
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Rate not found', MYSQL_ERRNO = 1004;
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
		SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT = 'Rate not found', MYSQL_ERRNO = 1005;
    end if;
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
  set vPrice = pDuration * (getCallPrice(vIdRate)/60);
  set vCost = pDuration * (getCallCost(vIdRate)/60);

  insert into calls(id_origin_line, id_destination_line, call_date, id_rate, call_duration, call_cost, call_price)
  values (getLineId(vOriginCityId,vOriginNumber), getLineId(vDestinationCityId,vDestinationNumber),pDate,vIdRate, pDuration,vCost, vPrice);
    
end //
delimiter ; 

-- INSERTS PROVINCES
INSERT INTO provinces(province_name) values ("Buenos Aires"),("La Pampa"),("Cordoba"),("Santa Fe"),("Rio Negro"),("Corrientes"),("Entre Rios");

-- INSERTS CITIES
INSERT INTO cities(city_name,id_province,prefix) values ("Mar del Plata",1,"223"),("Miramar",1,"2291"),("Necochea",1,"2262"),("Bahia Blanca",1,"291"),("Balcarce",1,"2266"),("Olavarria",1,"2284"),("Ciudad Autonoma de Buenos Aires",1,"011"),("La plata",1,"221"),("Cordoba Capital",3,"351"),("Santa Rosa",2,"2954");

-- INSERTS USERS
INSERT INTO `users` (`username`,`password`,`email`,`name`,`lastname`,`dni`,`address`,`id_city`,`role`) VALUES ("siderjonas","pastrana","soldanochristian@hotmail.com","Christian","Soldano",40020327,"Manuel Acevedo 2685",1,"client"),("Soker","tobiasrv","rodriguezviautobias@gmail.com","Tobias","Rodriguez Viau",41216459,"Calle Falsa 123",2,"client");
INSERT INTO `users` (`username`,`password`,`email`,`name`,`lastname`,`dni`,`address`,`id_city`,`role`) VALUES ('duebel0', 'qu44Uu22m1i2', 'duebel0@dailymotion.com', 'Demott', 'Uebel', 26548921, '099 Bunker Hill Trail', 7, "CLIENT"),('vgullivan1', 'szaqM5WaDFV', 'vgullivan1@netscape.com', 'Vannie', 'Gullivan', 39965555, '06 Luster Way', 3, "CLIENT"),('lbeat2', 'PICqkIHlJjvf', 'lbeat2@nyu.edu', 'Leila', 'Beat', 18055777, '70849 Blaine Center', 6, "CLIENT"),('alainge3', 'WhiyjJ3s1ZZ7', 'alainge3@woothemes.com', 'Ameline', 'Lainge', 17733987, '61 Magdeline Park', 4, "CLIENT"),('chuc4', '96SfJ35eXcV', 'chuc4@ftc.gov', 'Corby', 'Huc', 32364518, '2 Towne Drive', 10, "CLIENT"),('sguidini5', 'crione83', 'sguidini5@sfgate.com', 'Silva', 'Guidini', 32936147, '22175 Bay Circle', 9, "CLIENT"),('apeyntue6', 'ioMkitPYv6V9', 'apeyntue6@tinyurl.com', 'Alvin', 'Peyntue', 15799689, '6084 Lake View Center', 5, "CLIENT"),('amcleman7', 'knzCc5', 'amcleman7@intel.com', 'Aluin', 'McLeman', 33178319, '62 Memorial Court', 2, "CLIENT"),('zbaugh8', 'YKe4rv54o', 'zbaugh8@bing.com', 'Zahara', 'Baugh', 19061342, '80 Miller Park', 5, "CLIENT"),('wgarett9', 'VBub0bLHo6E3', 'wgarett9@flickr.com', 'Wilt', 'Garett', 31919301, '01 Grasskamp Pass', 9, "CLIENT"),('rkittoa', 'sRe1NnoM5', 'rkittoa@blogtalkradio.com', 'Ros', 'Kitto', 36236666, '5151 Mesta Trail', 3, "CLIENT");
INSERT INTO `users` (`username`,`password`,`email`,`name`,`lastname`,`dni`,`address`,`id_city`,`role`) VALUES ('ntorrib', 'hmmWaV3', 'ntorrib@tinypic.com', 'Nananne', 'Torri', 34293383, '30707 Mockingbird Point', 1, "CLIENT"),('ggarrattleyc', '8B88rXuK0', 'ggarrattleyc@nyu.edu', 'Germana', 'Garrattley', 26935740, '810 Lakewood Gardens Way', 7, "CLIENT"),('rabaded', 'e3e3zpyywHk', 'rabaded@army.mil', 'Roland', 'Abade', 24350879, '3 Holmberg Parkway', 2, "CLIENT"),('ajakubowskie', 'G95eDHRIOw3x', 'ajakubowskie@wp.com', 'Aubrie', 'Jakubowski', 17206840, '31756 Mccormick Place', 9, "CLIENT"),('yjolliffef', '3pAGfE', 'yjolliffef@fda.gov', 'Yves', 'Jolliffe', 29154178, '19 Dennis Junction', 1, "CLIENT"),('pwadesong', 'byBzEA', 'pwadesong@va.gov', 'Perry', 'Wadeson', 20099799, '935 Anthes Center', 7, "CLIENT"),('eticksallh', 'XFHvcNV', 'eticksallh@studiopress.com', 'Elston', 'Ticksall', 23375975, '6 Larry Street', 4, "CLIENT"),('brhubottomi', 'bfo3cZDsdCPc', 'brhubottomi@timesonline.co.uk', 'Brose', 'Rhubottom', 35243813, '4859 Mandrake Parkway', 1, "CLIENT"),('rvalenssmithj', 'a4bKYA9w', 'rvalenssmithj@amazon.co.jp', 'Ronnie', 'Valens-Smith', 18822039, '0670 Emmet Street', 4, "CLIENT"),('naldrenk', 'MLMYUqR2kx', 'naldrenk@istockphoto.com', 'Nessie', 'Aldren', 37723839, '2 Oakridge Place', 8, "CLIENT"),('npierrepointl', 'd705eU', 'npierrepointl@gnu.org', 'Nicky', 'Pierrepoint', 19803675, '08 Eliot Avenue', 7, "CLIENT");
INSERT INTO `users` (`username`,`password`,`email`,`name`,`lastname`,`dni`,`address`,`id_city`,`role`) VALUES ('tvigerm', 'yxOfLIEEd', 'tvigerm@amazon.co.uk', 'Thorvald', 'Viger', 43747576, '79762 Manitowish Junction', 10, "CLIENT"),('sbenn', 'mPNSlYfAhosU', 'sbenn@msn.com', 'Stu', 'Ben', 20749618, '81225 Eastwood Hill', 6, "CLIENT"),('spostianso', 'hGPYCH5F', 'spostianso@artisteer.com', 'Shelli', 'Postians', 29823498, '6 Buell Hill', 8, "CLIENT"),('bheusticep', 'vm9SlwqOw', 'bheusticep@prweb.com', 'Babara', 'Heustice', 32012826, '3 Morrow Avenue', 5, "CLIENT"),('aredmanq', '0sAwWunR1Q', 'aredmanq@miitbeian.gov.cn', 'Anet', 'Redman', 42851896, '4 Knutson Trail', 8, "CLIENT"),('efaringtonr', 'G63Uj9rzdbd', 'efaringtonr@webnode.com', 'Errol', 'Farington', 43393904, '3333 Bobwhite Center', 7, "CLIENT"),('xkemshells', '1KWiiZR', 'xkemshells@mit.edu', 'Ximenez', 'Kemshell', 40306619, '5 Stone Corner Court', 10, "CLIENT"),('mlentet', 'tOFlpfwb6', 'mlentet@hubpages.com', 'Marshall', 'Lente', 37231533, '0395 Bowman Alley', 2, "CLIENT"),('ldavidowichu', 'YyYAYS', 'ldavidowichu@google.com.hk', 'Lefty', 'Davidowich', 42337207, '6 Corscot Parkway', 10, "CLIENT"),('smcduffyv', 'QZsnhdncg13', 'smcduffyv@over-blog.com', 'Savina', 'McDuffy', 27649879, '8 Starling Point', 8, "CLIENT"),('ioveralw', 'yxENnf', 'ioveralw@phoca.cz', 'Isidoro', 'Overal', 35145338, '6 5th Pass', 4, "CLIENT");
INSERT INTO `users` (`username`,`password`,`email`,`name`,`lastname`,`dni`,`address`,`id_city`,`role`) VALUES ('pvaskovx', 'ESx2VQO', 'pvaskovx@pbs.org', 'Phillis', 'Vaskov', 42484141, '117 Anniversary Junction', 10, "CLIENT"),('jcotsfordy', 'TI07jEil', 'jcotsfordy@jalbum.net', 'Janeta', 'Cotsford', 41430251, '6 Larry Avenue', 9, "CLIENT"),('oferneleyz', 'AYudDBDdR0U', 'oferneleyz@diigo.com', 'Odo', 'Ferneley', 21290067, '9497 Bluestem Circle', 4, "CLIENT"),('jsalan10', 'mgib53q', 'jsalan10@oracle.com', 'Josepha', 'Salan', 36798337, '13329 Menomonie Pass', 10, "CLIENT"),('ddagon11', 'dKx3Gr5GMy', 'ddagon11@springer.com', 'Darren', 'Dagon', 34994083, '9 Eastlawn Point', 8, "CLIENT"),('agetten12', 'JLHOGixda', 'agetten12@wsj.com', 'Aldin', 'Getten', 15324468, '17 Miller Center', 1, "CLIENT"),('aconnors13', 'nFDCPt9', 'aconnors13@cisco.com', 'Ardisj', 'Connors', 25086590, '65 Nancy Lane', 3, "CLIENT"),('vkears14', 'jOmkgM', 'vkears14@meetup.com', 'Veriee', 'Kears', 24859648, '602 Del Mar Alley', 1, "CLIENT"),('vmcareavey15', 'U6rz8crG', 'vmcareavey15@cloudflare.com', 'Virgie', 'McAreavey', 43545251, '36623 Bay Terrace', 4, "CLIENT"),('phaville16', 'V2l2GgEsVK9L', 'phaville16@comsenz.com', 'Pierrette', 'Haville', 29797291, '2539 Lindbergh Hill', 3, "CLIENT"),('llongina17', 'p8wvN78hUBxC', 'llongina17@ehow.com', 'Lyon', 'Longina', 17579817, '23 Mayfield Road', 3, "CLIENT");
INSERT INTO `users` (`username`,`password`,`email`,`name`,`lastname`,`dni`,`address`,`id_city`,`role`) VALUES ('thannabus18', 'hRUo9hCQ', 'thannabus18@domainmarket.com', 'Teodoro', 'Hannabus', 29068216, '9767 Beilfuss Road', 1, "CLIENT"),('ofossey19', '5lN486nc5y', 'ofossey19@taobao.com', 'Ogdan', 'Fossey', 42807307, '4 Algoma Pass', 7, "CLIENT"),('dspeare1a', 'itOTJkk', 'dspeare1a@uol.com.br', 'Davey', 'Speare', 15287562, '72071 School Pass', 7, "CLIENT"),('mwiddecombe1b', '8Mxwhoa', 'mwiddecombe1b@skyrock.com', 'Meris', 'Widdecombe', 20373986, '08818 Prentice Pass', 4, "CLIENT"),('cbanister1c', 'aAEB1h', 'cbanister1c@engadget.com', 'Constantine', 'Banister', 31221463, '2779 Sugar Crossing', 2, "CLIENT"),('pstolle1d', 'KoOfnf', 'pstolle1d@acquirethisname.com', 'Pearline', 'Stolle', 33314817, '7744 Acker Lane', 10, "CLIENT"),('wpearde1e', 'WWLQa066', 'wpearde1e@jimdo.com', 'Wynny', 'Pearde', 37066451, '04065 Vernon Lane', 2, "CLIENT"),('mcamelli1f', 'YbdPbG6hAlm', 'mcamelli1f@ebay.co.uk', 'Misti', 'Camelli', 23328393, '65 Meadow Vale Pass', 9, "CLIENT"),('ttalton1g', '0u6o9ZIXKB', 'ttalton1g@quantcast.com', 'Tess', 'Talton', 27468933, '91771 Vahlen Plaza', 5, "CLIENT"),('abispham1h', 'CIBuowY', 'abispham1h@time.com', 'Agneta', 'Bispham', 28386264, '007 Sunbrook Road', 1, "CLIENT"),('lmathivet1i', 'VePE4Vk', 'lmathivet1i@ucla.edu', 'Lemar', 'Mathivet', 24067960, '684 East Park', 6, "CLIENT");
INSERT INTO `users` (`username`,`password`,`email`,`name`,`lastname`,`dni`,`address`,`id_city`,`role`) VALUES ('htroughton1j', 'ESzC1T8', 'htroughton1j@spotify.com', 'Herrick', 'Troughton', 39142270, '60 Nancy Court', 10, "CLIENT"),('jlynd1k', '0sWdV6iYHTK4', 'jlynd1k@purevolume.com', 'Jemie', 'Lynd', 34964608, '0 Pennsylvania Court', 5, "CLIENT"),('cmurrthum1l', '1MtqwBRZcezz', 'cmurrthum1l@plala.or.jp', 'Christophorus', 'Murrthum', 17325405, '1 Karstens Lane', 10, "CLIENT"),('cgeldeard1m', 'k7D0oDomG', 'cgeldeard1m@hao123.com', 'Cynthea', 'Geldeard', 32816597, '56365 Myrtle Avenue', 10, "CLIENT"),('jscoullar1n', 'FF95ZS3Skqz', 'jscoullar1n@mlb.com', 'Joyann', 'Scoullar', 32869430, '19 Haas Terrace', 9, "CLIENT"),('tmcilwraith1o', 'tnnFEr', 'tmcilwraith1o@live.com', 'Toby', 'McIlwraith', 23362978, '62 High Crossing Alley', 10, "CLIENT"),('awicher1p', 'baJUklU', 'awicher1p@latimes.com', 'Adriena', 'Wicher', 29004332, '21 Huxley Parkway', 5, "CLIENT"),('dcushelly1q', 'PnMegR', 'dcushelly1q@51.la', 'Deeanne', 'Cushelly', 23893879, '249 Victoria Junction', 8, "CLIENT"),('mgillespey1r', '6YgLAokqKw', 'mgillespey1r@irs.gov', 'Murdock', 'Gillespey', 30626404, '25386 Park Meadow Crossing', 6, "CLIENT"),('jcaines1s', 'JcsAAvZb', 'jcaines1s@dedecms.com', 'Joela', 'Caines', 23859189, '48282 Hallows Street', 5, "CLIENT"),('jproske1t', 'WS5RKMsmLRCo', 'jproske1t@ycombinator.com', 'Josephine', 'Proske', 33333356, '99 Bunker Hill Avenue', 1, "CLIENT");
INSERT INTO `users` (`username`,`password`,`email`,`name`,`lastname`,`dni`,`address`,`id_city`,`role`) VALUES ('flattimore1u', '39inhMT', 'flattimore1u@samsung.com', 'Freddy', 'Lattimore', 25637367, '66453 Florence Drive', 7, "CLIENT"),('wmoorwood1v', 'NVM362GHop6b', 'wmoorwood1v@acquirethisname.com', 'Werner', 'Moorwood', 26565007, '51921 Vahlen Junction', 10, "CLIENT"),('agathercole1w', '7AQjRi61crxT', 'agathercole1w@sphinn.com', 'Artemus', 'Gathercole', 26138229, '0 Truax Drive', 4, "CLIENT"),('scrackett1x', 'TydX8o', 'scrackett1x@cocolog-nifty.com', 'Sarita', 'Crackett', 31413381, '8621 Brickson Park Avenue', 6, "CLIENT"),('rcaslin1y', 'zUsrcV', 'rcaslin1y@ask.com', 'Rheba', 'Caslin', 24566851, '947 Annamark Park', 6, "CLIENT"),('aheinl1z', '6wOPxLDH', 'aheinl1z@meetup.com', 'Averil', 'Heinl', 15626945, '42 Namekagon Pass', 5, "CLIENT"),('whendonson20', 'sKlyfzf0sZV', 'whendonson20@mozilla.org', 'Willabella', 'Hendonson', 27999624, '08 Eagle Crest Lane', 2, "CLIENT"),('jantrim21', 'CGdoW6d', 'jantrim21@rakuten.co.jp', 'Jefferey', 'Antrim', 25556822, '956 Spenser Point', 6, "CLIENT"),('trivelin22', 'a4IwWkP', 'trivelin22@howstuffworks.com', 'Tresa', 'Rivelin', 22816778, '940 Schmedeman Trail', 5, "CLIENT"),('deburah23', 'iTtJgPz', 'deburah23@google.nl', 'Derril', 'Eburah', 40086503, '572 Sheridan Terrace', 5, "CLIENT"),('fluberto24', 'cl6Lr1rBL', 'fluberto24@meetup.com', 'Francois', 'Luberto', 28784248, '739 Acker Street', 1, "CLIENT");
INSERT INTO `users` (`username`,`password`,`email`,`name`,`lastname`,`dni`,`address`,`id_city`,`role`) VALUES ('wlansberry25', 'FjhsGzk3Nx', 'wlansberry25@list-manage.com', 'Waylan', 'Lansberry', 40425125, '94445 Lakeland Road', 5, "CLIENT"),('nwhitsun26', '1WTENj52xf', 'nwhitsun26@aboutads.info', 'Nollie', 'Whitsun', 23053784, '8 Hintze Lane', 6, "CLIENT"),('jhandslip27', 'EynJOmCCDECU', 'jhandslip27@odnoklassniki.ru', 'Jaquelyn', 'Handslip', 15859686, '4138 Trailsway Street', 2, "CLIENT"),('dmoulding28', 'WdYpbxgPRI', 'dmoulding28@pagesperso-orange.fr', 'Dosi', 'Moulding', 30077935, '1 Blaine Point', 6, "CLIENT"),('ckniveton29', 'SBVVhhq', 'ckniveton29@meetup.com', 'Clare', 'Kniveton', 41693126, '2812 Bashford Center', 8, "CLIENT"),('ggreenstead2a', '58KBswkXBQ', 'ggreenstead2a@163.com', 'Granny', 'Greenstead', 18147221, '5823 Chinook Junction', 5, "CLIENT"),('mdyers2b', 'o00Wl1z', 'mdyers2b@guardian.co.uk', 'Mayer', 'Dyers', 29099316, '01807 Elgar Drive', 7, "CLIENT"),('cbarchrameev2c', 'zhojd37wSDb', 'cbarchrameev2c@aboutads.info', 'Cesaro', 'Barchrameev', 34807799, '12 Rigney Alley', 10, "CLIENT"),('csalzen2d', 'gxSlNflpj9', 'csalzen2d@seesaa.net', 'Constantino', 'Salzen', 24996721, '484 Kensington Circle', 9, "CLIENT"),('nwitherow2e', 'cY9e2ounuQN', 'nwitherow2e@angelfire.com', 'Nicol', 'Witherow', 21344655, '9789 Lunder Hill', 6, "CLIENT"),('ascrogges2f', 'cqGKEtNvwyHP', 'ascrogges2f@yellowpages.com', 'Artair', 'Scrogges', 41751491, '54 Morning Road', 9, "CLIENT"),('ksperling2g', '9kRIV3KWi74y', 'ksperling2g@angelfire.com', 'Katya', 'Sperling', 42199093, '65000 Quincy Trail', 1, "CLIENT"),('bsyce2h', 'wUgk4LYdH3bJ', 'bsyce2h@a8.net', 'Benjamin', 'Syce', 17611888, '330 Sunnyside Avenue', 2, "CLIENT"),('jhyams2i', 'e9oRT529ov', 'jhyams2i@symantec.com', 'Jeniffer', 'Hyams', 43331088, '4605 Garrison Street', 5, "CLIENT"),('hsmeal2j', '3plAJ80anI', 'hsmeal2j@amazon.co.uk', 'Hodge', 'Smeal', 20633190, '6 Gale Trail', 4, "CLIENT"),('uciobotaru2k', '4CueA9bZ', 'uciobotaru2k@bluehost.com', 'Umberto', 'Ciobotaru', 38288305, '00417 Mallard Center', 1, "CLIENT"),('rslateford2l', 'YV5jrKKWg9', 'rslateford2l@google.ru', 'Robbin', 'Slateford', 15538079, '3235 Melrose Plaza', 8, "CLIENT"),('vspitell2m', 'Spp5RKRb3MSi', 'vspitell2m@unicef.org', 'Vivi', 'Spitell', 27035504, '1 Manitowish Junction', 3, "CLIENT"),('apaulon2n', 'ND9ImllZ', 'apaulon2n@oakley.com', 'Anatola', 'Paulon', 31357980, '4711 Harper Way', 10, "CLIENT"),('rofairy2o', 'HQXNpY', 'rofairy2o@pagesperso-orange.fr', 'Rouvin', 'O''Fairy', 19801647, '00650 John Wall Pass', 2, "CLIENT"),('lflieg2p', '2SkeWa', 'lflieg2p@admin.ch', 'Lutero', 'Flieg', 43890757, '97972 Sherman Crossing', 1, "CLIENT"),('wfenton2q', 'x1L5D4', 'wfenton2q@dell.com', 'Wells', 'Fenton', 39953715, '74108 Dwight Junction', 4, "CLIENT"),('fbeamson2r', 'yKOYpmS8qSs', 'fbeamson2r@about.com', 'Ferrel', 'Beamson', 40488116, '33 Quincy Lane', 2, "CLIENT");

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
-- insert into bills (id_line,total_production_cost, total_price, issue_date, expiration_date) values (1,50,300,"2020-03-01","2020-04-01");
-- insert into bills (id_line,total_production_cost, total_price, issue_date, expiration_date) values (1,50,300,"2020-04-01","2020-05-01");
-- insert into bills (id_line,total_production_cost, total_price, issue_date, expiration_date) values (1,50,300,"2020-05-01","2020-06-01");

-- INSERT CALL 
call sp_insert_call("223-6363325","2291-412505", now(), 10);


delimiter //
create procedure sp_generate_bills()
begin
  DECLARE vIdLine INTEGER;
  DECLARE vFinished BOOLEAN DEFAULT FALSE;

  DECLARE select_active_lines CURSOR FOR 
  select id_line from phone_lines where status = "ACTIVE";
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET vFinished = TRUE;

  OPEN select_active_lines;
  insert_bill: LOOP
  FETCH select_active_lines into vIdLine;
  if `vFinished` THEN LEAVE insert_bill; END IF;

  insert into bills(id_line,issue_date, expiration_date) values (vIdLine,curdate(),(curdate() + interval 15 day));
  END LOOP insert_bill;
  CLOSE select_active_lines;

  call sp_loop_unregistered_bills();

end //
delimiter ;

delimiter //
create procedure sp_loop_unregistered_bills()
BEGIN
  DECLARE vIdBill INTEGER;
  DECLARE vIdLine INTEGER;

  DECLARE vFinished BOOLEAN DEFAULT FALSE;

  -- CURSOR DE LAS FACTURAS
  DECLARE select_incomplete_bills CURSOR FOR 
  select id_bill, id_line from bills where (issue_date = curdate());
  DECLARE CONTINUE HANDLER FOR NOT FOUND SET vFinished = TRUE;

  OPEN select_incomplete_bills;
  complete_bill: LOOP

  FETCH select_incomplete_bills into vIdBill, vIdLine;
  if `vFinished` THEN LEAVE complete_bill; END IF;

  call sp_complete_bills(vIdBill,vIdLine);

  END LOOP complete_bill;
  CLOSE select_incomplete_bills;

end //
delimiter ;


delimiter //
create procedure sp_complete_bills(pIdBill INTEGER, pIdLine INTEGER)
BEGIN 

DECLARE vCallListFinished BOOLEAN DEFAULT FALSE;
DECLARE vCallPrice double default 0;
DECLARE vCallCost double default 0;
DECLARE vIdCall INTEGER;

DECLARE call_data CURSOR FOR select id_call, call_cost, call_price from calls where (id_origin_line = pIdLine) and (registered = 0);
DECLARE CONTINUE HANDLER FOR NOT FOUND SET vCallListFinished = TRUE;
    
OPEN call_data;
search_calls: LOOP
    
  FETCH call_data into vIdCall, vCallCost, vCallPrice;
    if `vCallListFinished` THEN LEAVE search_calls; END IF;

    update bills set total_production_cost = total_production_cost + vCallCost, total_price = total_price + vCallPrice where id_bill = pIdBill;
    update calls set registered = 1 where id_call = vIdCall;

END LOOP search_calls;
CLOSE call_data;

end //
delimiter ;

delimiter // 
CREATE EVENT create_bills
ON SCHEDULE EVERY 1 MONTH STARTS '2020-07-01 00:00:00'
DO BEGIN 
call sp_generate_bills();
END //
delimiter ;


delimiter // 
CREATE EVENT expire_unpaid_bills
ON SCHEDULE EVERY 1 MONTH STARTS '2020-07-16 00:00:00'
DO BEGIN 
  update bills set status = 'EXPIRED' where (status = 'UNPAID') and (expiration_date - interval 1 day);
END //
delimiter ;


