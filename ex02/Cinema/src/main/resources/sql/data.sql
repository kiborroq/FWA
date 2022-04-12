INSERT INTO user_account (first_name, last_name, email, password)
VALUES ('Victor', 'Victorov', 'victor@school21.ru', '$2a$12$wB5SWvDQDqbF2ibMzr5XCumY/OasDb3hYkcD21jei7bhH0ritANse'),
       ('Ivan', 'Ivanov', 'ivan@school21.ru', '$2a$12$CvDgkZr3Y.8K1zb.6.JArOGD2/Pl2pJ6.0d0sv5zQJ22ST44l9W1O'),
       ('Anna', 'Annova', 'anna@school21.ru', '$2a$12$Y.CRGUF1Z./on1CbBnfIdOKA1XZm58e46qVXJ/g4GPo8H8nfm4XYS'),
       ('Elena', 'Elenova', 'elena@school21.ru', '$2a$12$nH9UgZ8DRzfx/f0mXzSlFOXxhN0YbftPSR0rQmMNvArDRJckMcnnO'),
       ('Petr', 'Petrov', 'petr@school21.ru', '$2a$12$Reg.lwCLggnYZv8.9SMGk.XW6uY4XVO0O962JQBDqy5MgATraLhjW')
ON CONFLICT DO NOTHING;
