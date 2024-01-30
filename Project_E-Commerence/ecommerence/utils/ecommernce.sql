-- Create product_status enum
CREATE TYPE product_status AS enum ('Active', 'Inactive');

-- Create delivery_status enum	
CREATE TYPE delivery_status AS enum ('YetToDeliver', 'Delivered', 'Cancelled', 'Returned');

-- Customers Table
CREATE TABLE Customers (
    customerid SERIAL PRIMARY KEY,
    firstname VARCHAR,
    lastname VARCHAR,
    email VARCHAR,
    password VARCHAR,
    mobile BIGINT,
    wallet BIGINT
);


-- Merchant Table
CREATE TABLE Merchant (
    merchantid SERIAL PRIMARY KEY,
    firstname VARCHAR,
    lastname VARCHAR,
    email VARCHAR,
    password VARCHAR,
    mobile_no BIGINT,
    companyname VARCHAR,
    gst_no VARCHAR,
    pan_no VARCHAR,
    amount BIGINT
);

-- Products Table
CREATE TABLE Products (
    productid SERIAL PRIMARY KEY,
    productname VARCHAR,
    productdesc VARCHAR,
    price INTEGER,
    categoryid INTEGER REFERENCES category(categoryid),
    merchantid INTEGER REFERENCES Merchant(merchantid),
    available INTEGER,
    ratings INTEGER,
    listdate DATE,
    p_status product_status DEFAULT 'Active',
);

-- Category Table
CREATE TABLE category (
    categoryid SERIAL PRIMARY KEY,
    category_name VARCHAR
);

-- Ratings Table
CREATE TABLE Ratings (
    ratingid SERIAL PRIMARY KEY,
    rating INTEGER,
    productid INTEGER REFERENCES Products(productid),
    customerid INTEGER REFERENCES Customers(customerid)
);

-- Address Table
CREATE TABLE Address (
    Addressid SERIAL PRIMARY KEY,
    Address VARCHAR,
    city VARCHAR,
    state_name VARCHAR,
    pincode INTEGER, 
    customerid INTEGER REFERENCES Customers(customerid)
);

-- Cart Table
CREATE TABLE Cart (
    cartid SERIAL PRIMARY KEY,
    customerid INTEGER REFERENCES Customers(customerid),
    productid INTEGER REFERENCES Products(productid),
    quantity INTEGER,
    amount DOUBLE PRECISION,
    cart_date DATE
);

-- Order Table
CREATE TABLE OrderTable (
    orderid SERIAL PRIMARY KEY,
    customerid INTEGER REFERENCES Customers(customerid),
    productid INTEGER REFERENCES Products(productid),
    merchantid INTEGER REFERENCES Merchant(merchantid),
    quantity INTEGER,
    buy_price INTEGER,
    amount INTEGER,
    order_date DATE,
    delivery_date DATE,
    d_status delivery_status DEFAULT 'YetToDeliver'
);
