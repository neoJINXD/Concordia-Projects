
USE a1_queries;
-- Question 2 Queries

-- 1)
SELECT doctor.did, dname, city
FROM doctor, specialization
WHERE doctor.did = specialization.did
GROUP BY doctor.did
HAVING COUNT(specialization.did) >= 2;

-- 2)
SELECT consults.illness, patient.pname, consults.date 
FROM patient
INNER JOIN consults ON consults.pid = patient.pid
WHERE patient.pname = 'Bob' -- Given Patient = Bob
AND consults.date BETWEEN '1990-01-01' AND '2020-02-01'; -- Given dates

-- 3)
SELECT patient.pname, doctor.dname, clinic.cname, clinic.city, illness 
FROM patient
INNER JOIN consults ON patient.pid = consults.pid
INNER JOIN doctor ON consults.did = doctor.did
INNER JOIN clinic ON consults.cid = clinic.cid
WHERE (clinic.city = 'Laval' OR clinic.city = 'Montreal')
AND consults.date BETWEEN '2020-01-01' AND '2020-03-15'
AND consults.illness = 'High Fever';

-- 4)
SELECT *
FROM patient
INNER JOIN consults ON patient.pid = consults.pid
INNER JOIN doctor ON doctor.did = consults.did
WHERE doctor.dname = "Roberto"
HAVING COUNT(doctor.dname = 'Roberto') >= 2;

-- 5)
SELECT *
FROM doctor
INNER JOIN consults ON doctor.did = consults.did
INNER JOIN clinic ON clinic.cid = consults.cid
GROUP BY doctor.did
HAVING clinic.city = 'Laval' AND clinic.city != 'West Island';

-- 6)
SELECT dname, doctor.did, doctor.city
FROM doctor
INNER JOIN works_in ON doctor.did = works_in.did
INNER JOIN clinic ON clinic.cid = works_in.cid
WHERE works_in.hours_per_week >= 60
GROUP BY doctor.did;