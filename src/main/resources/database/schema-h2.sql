CREATE TABLE IF NOT EXISTS rack(id serial PRIMARY KEY);

CREATE TABLE IF NOT EXISTS shelf(id serial PRIMARY KEY,
                                 level integer,
                                 rack_id integer,
                                 FOREIGN KEY (rack_id) REFERENCES rack (id) ON DELETE CASCADE);

CREATE TABLE IF NOT EXISTS book(id serial PRIMARY KEY,
                                name varchar(50),
                                shelf_id integer,
                                FOREIGN KEY (shelf_id) REFERENCES shelf (id) ON DELETE CASCADE);