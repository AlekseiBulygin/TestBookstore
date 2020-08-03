INSERT INTO rack (id) VALUES
  (1),
  (12);

INSERT INTO shelf (id, level, rack_id) VALUES
  (2, 1, 1),
  (3, 2, 1),
  (4, 3, 1),
  (5, 1, 12),
  (18, 2, 12),
  (6, 3, 12);

INSERT INTO book (id, name, shelf_id) VALUES
  (7, 'War and peace', 2),
  (8, 'Idiot', 3),
  (9, 'Anna Karenina', 4),
  (10, 'Crime and punishment', 5),
  (11, 'Eugene Onegin', 6),
  (13, 'Dorian Grey', 18),
  (14, '1984', 2),
  (15, 'The Great Gatsby', 3),
  (16, 'Fahrenheit 451', 4),
  (17, 'The Lord of the Rings', 5);
