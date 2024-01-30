CREATE TYPE gender_enum AS ENUM ('Male', 'Female');

CREATE TYPE status_enum AS ENUM ('Buy', 'Sell');

CREATE TABLE IF NOT EXISTS Users (
    userId SERIAL PRIMARY KEY,
    fName VARCHAR(50),
    lName VARCHAR(50),
    email VARCHAR(150),
    password VARCHAR(50),
    mobile_no BIGINT,
    dob DATE,
    gender gender_enum
);

CREATE TABLE IF NOT EXISTS TradingAccount (
   taId SERIAL PRIMARY KEY,
   userId INTEGER REFERENCES Users (user_id),
   aadhar_no BIGINT,
   pan_no VARCHAR(20),
   demat_acc_no BIGINT,
   amount DECIMAL (10, 2)
)

CREATE TABLE IF NOT EXISTS Stocks (
   stockId SERIAL PRIMARY KEY,
   stockName VARCHAR(20),
   stockPrice DECIMAL (10, 2) NOT NULL
);

CREATE TABLE IF NOT EXISTS Holdings (
   holdingsId SERIAL PRIMARY KEY,
   taId INTEGER REFERENCES TradingAccount(taId), 
   stockId INTEGER REFERENCES Stocks(stockId), 
   amount DECIMAL (10, 2) NOT NULL,
   price DECIMAL (10, 2) NOT NULL,
   quality INTEGER NOT NULL,
   hold_date DATE CURRENT_DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS Transactions (
  id SERIAL PRIMARY KEY,
  taId INTEGER REFERENCES TradingAccount(taId), 
  stockId INTEGER REFERENCES Stocks(stockId), 
  action status_enum,
  amount DECIMAL (10, 2) NOT NULL,
  price DECIMAL (10, 2) NOT NULL, 
  quality INTEGER NOT NULL, 
  transaction_date DATE CURRENT_DATE NOT NULL
);
