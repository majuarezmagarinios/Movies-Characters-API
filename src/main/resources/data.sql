INSERT INTO _characters (id, image, name, age, weight, history, deleted) VALUES (1, '/img/characters/Bill_Murray.jpg', 'Bill_Murray', 44, 85, 'Phil is a reporter dissatisfied with life', false);
INSERT INTO _characters (id, image, name, age, weight, history, deleted) VALUES (2, '/img/characters/Jack_Nicholson_The_Departed.jpg', 'Jack Nicholson', 45, 67, 'Impulsive detective', false);
INSERT INTO _characters (id, image, name, age, weight, history, deleted) VALUES (3, '/img/characters/Jack_Nicholson.jpg', 'Jack_Nicholson', 37, 45, 'Jack Torrance becomes winter caretaker at the Overlook Hotel', false);

INSERT INTO _genre (id, image, name) VALUES (1, 'img/genere/comedy', 'comedy');
INSERT INTO _genre (id, image, name) VALUES (2, 'img/genere/police', 'police');
INSERT INTO _genre (id, image, name) VALUES (3, 'img/genere/terror', 'terror');

INSERT INTO _movies (id, image, title, creation_date, qualification, deleted, genre_id) VALUES (1, 'img/movies/The_Groundhog_Day', 'Groundhog Day', '1993/02/12', 10, false, 1);
INSERT INTO _movies (id, image, title, creation_date, qualification, deleted, genre_id) VALUES (2, 'img/movies/The_Departed', 'The_Departed', '2006/10/06', 10, false, 3);
INSERT INTO _movies (id, image, title, creation_date, qualification, deleted, genre_id) VALUES (3, 'img/movies/The_Shining', 'The Shining', '1980/05/23', 10, false, 2);

INSERT INTO characters_movie (characters_id, movies_id) VALUES (1, 1);
INSERT INTO characters_movie (characters_id, movies_id) VALUES (2, 2);
INSERT INTO characters_movie (characters_id, movies_id) VALUES (3, 2);
INSERT INTO characters_movie (characters_id, movies_id) VALUES (3, 3);
