START TRANSACTION;

INSERT INTO authors (id, name, pen_name, nationality, born, died, image_link, admin_id) VALUES (1, 'Elara Vance', 'E. V. Nocturne', 'Canadian', '1982-04-12', NULL, 'http://localhost:5173/src/assets/author.svg', 1);
INSERT INTO authors (id, name, pen_name, nationality, born, died, image_link, admin_id) VALUES (2, 'Julian Thorne', 'J. T. Sterling', 'British', '1945-11-20', '2018-09-05', 'http://localhost:5173/src/assets/author.svg', 1);
INSERT INTO authors (id, name, pen_name, nationality, born, died, image_link, admin_id) VALUES (3, 'Hiroshi Tanaka', 'H. T. Sora', 'Japanese', '1990-02-28', NULL, 'http://localhost:5173/src/assets/author.svg', 1);
INSERT INTO authors (id, name, pen_name, nationality, born, died, image_link, admin_id) VALUES (4, 'Beatrix O''Malley', 'Bea O''Malley', 'Irish', '1930-07-14', '2021-03-30', 'http://localhost:5173/src/assets/author.svg', 1);
INSERT INTO authors (id, name, pen_name, nationality, born, died, image_link, admin_id) VALUES (5, 'Marcus Valerius', 'M. V. Aurelius', 'Italian', '1975-08-09', NULL, 'http://localhost:5173/src/assets/author.svg', 1);

COMMIT;