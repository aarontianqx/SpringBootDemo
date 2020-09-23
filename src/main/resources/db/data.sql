INSERT INTO Reader (USERNAME, PASSWORD, FULLNAME) VALUES ('craig', '{noop}password', 'Craig Walls');
INSERT INTO Reader (USERNAME, PASSWORD, FULLNAME) VALUES ('walt', '{noop}marceline', 'Walt Disney');

INSERT INTO BOOK (READER_USERNAME, ISBN, TITLE, AUTHOR, DESCRIPTION) VALUES (
    'craig', '9781472154668', 'Where the Crawdads Sing', 'Delia Owens',
    '*The multi-million copy bestseller*  Soon to be a major film  A Number One New York Times Bestseller'
);
INSERT INTO BOOK (READER_USERNAME, ISBN, TITLE, AUTHOR, DESCRIPTION) VALUES (
    'craig', '9781501145254', 'My Own Words', 'Ruth Bader Ginsburg',
    'My Own Words "showcases Ruth Ginsburg''s astonishing intellectual range" (The New Republic).'
);
INSERT INTO BOOK (READER_USERNAME, ISBN, TITLE, AUTHOR, DESCRIPTION) VALUES (
    'walt', '9781409197119', 'How to Make Friends With Strangers and Stay Friends Until You Die: A Really Inspirational Guide to Friendship', 'Chris (Simpsons Artist)',
    'have you ever wanted to have a friend of your very own if your answer to this is yes then this is the book for you.'
);

