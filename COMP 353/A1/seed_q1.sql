-- seed patients
INSERT INTO patient VALUES(1, 'Bob', 21, 'Montreal');
INSERT INTO patient VALUES(2, 'Bobby', 21, 'Montreal');
INSERT INTO patient VALUES(3, 'John', 21, 'Vancouver');
INSERT INTO patient VALUES(4, 'Sarah', 21, 'Montreal');
INSERT INTO patient VALUES(5, 'Josh', 21, 'Toronto');
INSERT INTO patient VALUES(6, 'Anik', 21, 'Montreal');
INSERT INTO patient VALUES(7, 'Jefferey', 21, 'Montreal');
INSERT INTO patient VALUES(8, 'Bob1', 21, 'Montreal');
INSERT INTO patient VALUES(9, 'Bob2', 21, 'Montreal');
INSERT INTO patient VALUES(10, 'Bob3', 21, 'Montreal');

-- seed doctors
INSERT INTO doctor VALUES(1, 'Roberto', 'Montreal');
INSERT INTO doctor VALUES(2, 'Michael', 'Montreal');
INSERT INTO doctor VALUES(3, 'Tony Stark', 'Montreal');
INSERT INTO doctor VALUES(4, 'Simp', 'Montreal');
INSERT INTO doctor VALUES(5, 'Incel', 'Montreal');

-- seed clinic 
INSERT INTO clinic VALUES(1, 'La Salle Clinic', 'Montreal');
INSERT INTO clinic VALUES(2, 'Clinic Lavvy', 'Laval');
INSERT INTO clinic VALUES(3, 'The potion', 'Laval');
INSERT INTO clinic VALUES(4, 'Crack house', 'Montreal');
INSERT INTO clinic VALUES(5, 'Pokemon Center', 'West Island');
INSERT INTO clinic VALUES(6, 'La West Island Clinic', 'West Island');
-- join specialization
INSERT INTO specialization VALUES(1, 'Cancer', '2016-12-12');
INSERT INTO specialization VALUES(2, 'Cancer', '2016-12-12');
INSERT INTO specialization VALUES(3, 'Cancer', '2016-12-12');
INSERT INTO specialization VALUES(2, 'Corona Virus', '2016-12-12');
INSERT INTO specialization VALUES(1, 'Family', '2016-12-12');

-- join works_in
INSERT INTO works_in VALUES(1, 1, 70);
INSERT INTO works_in VALUES(1, 2, 60);
INSERT INTO works_in VALUES(2, 1, 30);
INSERT INTO works_in VALUES(2, 4, 80);
INSERT INTO works_in VALUES(2, 5, 40);
INSERT INTO works_in VALUES(3, 2, 70);
INSERT INTO works_in VALUES(3, 3, 40);
INSERT INTO works_in VALUES(3, 5, 30);
INSERT INTO works_in VALUES(4, 2, 20);
INSERT INTO works_in VALUES(5, 6, 40);

-- consults joins
INSERT INTO consults VALUES(1, 1, 2, '2020-01-5', 'High Fever');
INSERT INTO consults VALUES(1, 1, 2, '2020-04-5', 'Black Death');
INSERT INTO consults VALUES(1, 2, 3, '2020-01-5', 'High Fever');
INSERT INTO consults VALUES(2, 1, 2, '2020-01-5', 'Cancer');
INSERT INTO consults VALUES(2, 2, 2, '2020-01-5', 'Small Pox');
INSERT INTO consults VALUES(3, 1, 2, '2020-01-5', 'High Fever');