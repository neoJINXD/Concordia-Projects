-- Q1
SELECT 
    COUNT(Departments.Dep_name) AS Dep_Count, 
    COUNT(Regions.Region_Name) AS Reg_Count, 
    COUNT(Locations.City) AS Loc_Count
FROM Departments 
INNER JOIN Locations ON Locations.Location_Id = Departments.Location_Id
INNER JOIN Countries ON Countries.Country_Id = Locations.Country_Id
INNER JOIN Regions ON Regions.Region_Id = Countries.Region_Id
WHERE Departments.Dep_Id IN 
(
    SELECT Departments.*, COUNT(Departments.Dep_Id) AS Empl_Count FROM Employees
    INNER JOIN Departments ON Departments.Dep_Id = Employees.Dep_Id
    GROUP BY Departments.Dep_Id
    HAVING COUNT(Departments.Dep_Id) > 500;
);


-- Q2
UPDATE Departments
INNER JOIN Employees ON Employees.Manager_Id = Departments.Manager_Id
SET Employees.FirstName = "Picard"
WHERE Departments.Dep_Id IN
(
    SELECT Departments.Dep_Id, MAX(Employees.Salary) AS Max_Salary FROM Employees
    INNER JOIN Departments ON Departments.Dep_Id = Employees.Dep_Id
    GROUP BY Departments.Dep_Id
    HAVING MAX(Employees.Salary) > 100000;
);


-- Q3
-- Get the all the employee joining counts in Vancouver, then from that table, find the minimum 
SELECT Employee_History.Joining_date, MIN(Empl_Count) FROM 
(
    SELECT Employee_History.Joining_date, COUNT(Employee_History.Emp_ID) AS Empl_Count FROM Employee_History
    INNER JOIN Departments ON Departments.Dep_Id = Employee_History.Dep_ID
    INNER JOIN Locations ON Locations.Location_Id = Departments.Location_Id
    WHERE Locations.City = "Vancouver"
    GROUP BY Employee_History.Joining_date;
);


-- Q4
SELECT YEAR(Employee_History.Joining_date), MAX(Empl_Count) FROM 
(
    SELECT Employee_History.Joining_date, COUNT(Employee_History.Emp_ID) AS Empl_Count FROM Employee_History
    INNER JOIN Departments ON Departments.Dep_Id = Employee_History.Dep_ID
    INNER JOIN Locations ON Locations.Location_Id = Departments.Location_Id
    GROUP BY YEAR(Employee_History.Joining_date);
);


-- Q5
SELECT MONTH(Employee_History.Joining_date), COUNT(Employees.Emp_ID) FROM Employees
WHERE YEAR(Employee_History.Joining_date) IN
(
    SELECT YEAR(Employee_History.Joining_date), MAX(Empl_Count) FROM 
    (
        SELECT Employee_History.Joining_date, COUNT(Employee_History.Emp_ID) AS Empl_Count FROM Employee_History
        INNER JOIN Departments ON Departments.Dep_Id = Employee_History.Dep_ID
        INNER JOIN Locations ON Locations.Location_Id = Departments.Location_Id
        GROUP BY YEAR(Employee_History.Joining_date);
    );
);

