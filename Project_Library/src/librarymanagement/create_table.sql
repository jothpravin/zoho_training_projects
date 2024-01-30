CREATE DATABASE IF NOT EXISTS LibraryManagement;

USE LibraryManagement;

CREATE TYPE gender_enum AS ENUM('Male', 'Female', 'Other');

CREATE TYPE Status_enum AS ENUM('Pending', 'Approved', 'Declined');

CREATE TABLE IF NOT EXISTS Users (
    UserID SERIAL PRIMARY KEY,
    Username VARCHAR(50),
    Email VARCHAR(50),
    Password VARCHAR(50),
    isAdmin BOOLEAN,
    Gender gender_enum,
    dob DATE
);

CREATE TABLE IF NOT EXISTS Publishers (
   PublisherID SERIAL PRIMARY KEY,
   PublisherName VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS Rack (
   RackID SERIAL PRIMARY KEY,
   RackName VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS Book (
    BookID SERIAL PRIMARY KEY,
    BookName VARCHAR(100),
    Author VARCHAR(100),
    PublisherID INT REFERENCES Publishers(PublisherID),
    Available INT
);

CREATE TABLE IF NOT EXISTS BookCopy (
   BC_ID SERIAL PRIMARY KEY,
   BookID INT REFERENCES Book(BookID),
   RackID INT REFERENCES Rack(RackID),
   Book_Token_No SERIAL 
);

CREATE TABLE IF NOT EXISTS BookIssue (    
  BI_ID SERIAL PRIMARY KEY,
  UserID INT REFERENCES Users(UserID),
  BC_ID INT REFERENCES BookCopy(BC_ID), 
  DateOfIssue DATE, 
  ReturnDate DATE, 
  Fine DECIMAL
);

CREATE TABLE IF NOT EXISTS BookReservation(
   BR_ID SERIAL PRIMARY KEY,
   UserID INT REFERENCES Users(UserID),
   BC_ID INT REFERENCES BookCopy(BC_ID), 
   DateOfReservation Date DEFAULT current_date,
   Status Status_enum
);

SELECT DISTINCT ON (b.BookID)
   b.BookName,
   b.Author,
   p.PublisherName,
   b.Available
   

FROM BookCopy AS bc

JOIN BOOK as b ON bc.BookID = b.BookID

JOIN Publishers as p on b.PublisherID = p.PublisherID

WHERE bc.RackID = 1 AND bc.isAvailable = 'true';