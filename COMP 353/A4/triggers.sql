-- Q6
CREATE TRIGGER TooHighMoney
AFTER UPDATE OR INSERT OF Salary ON Employees
REFERENCING
OLD ROW AS o
NEW ROW AS n
FOR EACH STATEMENT
WHEN (
    -- when there is a manager
    (n.Salary > (
        SELECT Salary FROM Employees
        WHERE n.Manager_Id = Employees.Empl_Id
    )) OR -- no manager
    (n.Salary > (
        SELECT MAX(Salary)*(1.1) FROM Employees
    ))
)
BEGIN
    ROLLBACK;
END;

-- Q7
CREATE TRIGGER JobChange
AFTER UPDATE ON Employees
REFERENCING
OLD ROW AS o
NEW ROW AS n
FOR EACH STATEMENT
BEGIN

    IF () THEN


    ELSE


    END IF;

END;

-- Q8
CREATE TRIGGER NoLessMoney
AFTER UPDATE OF Salary ON Employees
REFERENCING
OLD ROW AS o
NEW ROW AS n
FOR EACH STATEMENT
WHEN (
    n.Salary < o.Salary
)
BEGIN
    ROLLBACK;
END;

-- Q9
CREATE TRIGGER PromotionCheck
AFTER UPDATE OF Salary ON Employees
REFERENCING
OLD ROW AS o
NEW ROW AS n
FOR EACH STATEMENT
WHEN (
   n.Salary > o.Salary
)
BEGIN
    
    IF (YEAR(DATEDIFF(NOW(), o.Hire_Date)) > 8) THEN
        SET Salary = Salary * 1.2
    ELSEIF (YEAR(DATEDIFF(NOW(), o.Hire_Date)) > 3) THEN
        SET Salary = Salary * 1.1
    ELSE
        SET Salary = Salary * 1.05
    END IF;

END;

-- Q10
CREATE TRIGGER MinMaxing
AFTER UPDATE OR INSERT OF Min_Salary ON Jobs
REFERENCING
OLD ROW AS o
NEW ROW AS n
FOR EACH STATEMENT
WHEN (
    n.Min_Salary > (SELECT MAX(Max_Salary) FROM Jobs)
)
BEGIN
    ROLLBACK;
END;
