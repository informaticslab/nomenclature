DROP TABLE facility_lab_set;
CREATE TABLE facility_lab_set ( id bigint NOT NULL AUTO_INCREMENT, version int, service_facility bigint, PRIMARY KEY (id) ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
DROP INDEX FKB7EBC11477F9B77E ON facility_lab_set;
CREATE INDEX FKB7EBC11477F9B77E ON facility_lab_set (service_facility);

insert into facility_lab_set (id, service_facility, version) values (1, 1, 0);
insert into facility_lab_set (id, service_facility, version) values (2, 2, 0);

DROP TABLE facility_lab_set_lab_orders;
CREATE TABLE facility_lab_set_lab_orders ( facility_lab_set bigint NOT NULL, lab_orders bigint NOT NULL, PRIMARY KEY (facility_lab_set, lab_orders) ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
DROP INDEX FKE60D35E2A20BBF3 ON facility_lab_set_lab_orders;
CREATE INDEX FKE60D35E2A20BBF3 ON facility_lab_set_lab_orders (facility_lab_set);
DROP INDEX FKE60D35E289D56EAB ON facility_lab_set_lab_orders;
CREATE INDEX FKE60D35E289D56EAB ON facility_lab_set_lab_orders (lab_orders);
DROP INDEX FKE60D35E289E476AB ON facility_lab_set_lab_orders;
CREATE INDEX FKE60D35E289E476AB ON facility_lab_set_lab_orders (lab_orders);
ALTER TABLE facility_lab_set_lab_orders ADD CONSTRAINT FKE60D35E289E476AB FOREIGN KEY (lab_orders) REFERENCES loinc (id);

insert into facility_lab_set_lab_orders (facility_lab_set, lab_orders) values (1, 1);
insert into facility_lab_set_lab_orders (facility_lab_set, lab_orders) values (1, 2);
insert into facility_lab_set_lab_orders (facility_lab_set, lab_orders) values (1, 3);
insert into facility_lab_set_lab_orders (facility_lab_set, lab_orders) values (1, 4);
insert into facility_lab_set_lab_orders (facility_lab_set, lab_orders) values (1, 5);

insert into facility_lab_set_lab_orders (facility_lab_set, lab_orders) values (2, 10);
insert into facility_lab_set_lab_orders (facility_lab_set, lab_orders) values (2, 11);
insert into facility_lab_set_lab_orders (facility_lab_set, lab_orders) values (2, 12);
insert into facility_lab_set_lab_orders (facility_lab_set, lab_orders) values (2, 13);
insert into facility_lab_set_lab_orders (facility_lab_set, lab_orders) values (2, 14);
insert into facility_lab_set_lab_orders (facility_lab_set, lab_orders) values (2, 15);
insert into facility_lab_set_lab_orders (facility_lab_set, lab_orders) values (2, 16);

