CREATE TYPE account_enum AS ENUM ('Savings', 'Current');

CREATE TYPE gender_enum AS ENUM('Male', 'Female');

CREATE TYPE status_enum AS ENUM('Sucess', 'Failed');

CREATE SEQUENCE branch_branch_id_seq START WITH 0143;

CREATE TABLE Customer (
    customer_id SERIAL PRIMARY KEY,
    first_name VARCHAR(50),
    last_name VARCHAR(50),
    email VARCHAR(150),
    gender gender_enum,
    date_of_birth DATE,
    street_address VARCHAR(255),
    city VARCHAR(50),
    state_name CHAR(2),
    zip_code CHAR(5)
);

CREATE TABLE Account (
    account_id SERIAL PRIMARY KEY,
    account_no INT,
    account_type account_enum,
    customer_id INT REFERENCES Customer(customer_id),
    branch_id INT REFERENCES Branch(branch_id),
    balance DECIMAL
);

CREATE TABLE Branch (
   branch_id SERIAL PRIMARY KEY,
   branch_name VARCHAR (255) NOT NULL,
   ifsc_code VARCHAR (255) DEFAULT 'SBI' || '00000' || nextval('branch_branch_id_seq')
);

CREATE TABLE Transaction (
   transaction_id SERIAL PRIMARY KEY,
   from_account_id INT REFERENCES Account(account_id), 
   to_account_id INT REFERENCES Account(account_id), 
   amount DECIMAL NOT NULL, 
   transaction_type VARCHAR (50) NOT NULL, 
   transaction_date DATE NOT NULL,
   t_status status_enum
);