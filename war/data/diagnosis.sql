DROP TABLE diagnosis;
CREATE TABLE diagnosis ( id bigint NOT NULL AUTO_INCREMENT, name varchar(255) NOT NULL, version int, PRIMARY KEY (id) ) ENGINE=InnoDB DEFAULT CHARSET=latin1;

insert into diagnosis (id, name, version) values (1, 'Thyroid', 0);
insert into diagnosis (id, name, version) values (2, 'Vitamin D deficiency', 0);

DROP TABLE diagnosis_lab_sets;
CREATE TABLE diagnosis_lab_sets ( diagnosis bigint NOT NULL, lab_sets bigint NOT NULL, PRIMARY KEY (diagnosis, lab_sets) ) ENGINE=InnoDB DEFAULT CHARSET=latin1;
DROP INDEX FKCA361D51DD1BD2A2 ON diagnosis_lab_sets;
CREATE INDEX FKCA361D51DD1BD2A2 ON diagnosis_lab_sets (lab_sets);
DROP INDEX FKCA361D51F9AD2ABB ON diagnosis_lab_sets;
CREATE INDEX FKCA361D51F9AD2ABB ON diagnosis_lab_sets (diagnosis);

insert into diagnosis_lab_sets (diagnosis, lab_sets) values (1, 1);
insert into diagnosis_lab_sets (diagnosis, lab_sets) values (1, 2);

