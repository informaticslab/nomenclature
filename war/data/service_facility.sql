DROP TABLE service_facility;
CREATE TABLE service_facility ( id bigint NOT NULL AUTO_INCREMENT, name varchar(255), version int, PRIMARY KEY (id) ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
insert into service_facility (id, name, version) values (1, 'LabCorp', 0);
insert into service_facility (id, name, version) values (2, 'Quest', 0);
