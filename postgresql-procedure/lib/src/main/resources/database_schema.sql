CREATE TABLE employees (
    id INT PRIMARY KEY,
    name VARCHAR(50),
    city VARCHAR(60)
);

-- For demo purpose we have inserting this record and test it
insert into employees values(1,'Shiv','Noida'),(2,'Mohan','Delhi'),(3,'Shivmohan','Noida sector');

-- This procedure for in as id and return name
CREATE OR REPLACE FUNCTION get_employee_name_by_id(
    in_employee_id INT,
    OUT out_employee_name VARCHAR
)
AS $$
BEGIN
    SELECT name INTO out_employee_name FROM employees WHERE id = in_employee_id;
END;
$$ LANGUAGE plpgsql;

-- This procedure for in as a city and return name
CREATE OR REPLACE FUNCTION get_employee_name_by_city(
    in_city INT,
    OUT out_employee_name VARCHAR
)
AS $$
BEGIN
    SELECT name INTO out_employee_name FROM employees WHERE city = in_city;
END;
$$ LANGUAGE plpgsql;
