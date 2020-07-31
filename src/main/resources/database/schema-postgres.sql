CREATE TABLE IF NOT EXISTS rack(id serial PRIMARY KEY);
CREATE TABLE IF NOT EXISTS shelf(id serial PRIMARY KEY, level serial, rack_id serial);
CREATE TABLE IF NOT EXISTS book(id serial PRIMARY KEY, name varchar(50), shelf_id serial);