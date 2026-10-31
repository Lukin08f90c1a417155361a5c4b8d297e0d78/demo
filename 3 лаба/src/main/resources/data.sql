INSERT INTO users (name) VALUES ('Иван Иванов');
INSERT INTO users (name) VALUES ('Петр Петров');

INSERT INTO found_items (description, category, location_found, status)
VALUES ('Черный кошелек', 'Кошелек', 'Парк Горького', 'AVAILABLE');
INSERT INTO found_items (description, category, location_found, status)
VALUES ('Смартфон iPhone', 'Телефон', 'Метро Арбатская', 'AVAILABLE');

INSERT INTO loss_reports (applicant_id, description, category, status)
VALUES (1, 'Потерял кошелек в парке', 'Кошелек', 'OPEN');
INSERT INTO loss_reports (applicant_id, description, category, status)
VALUES (2, 'Потерял телефон в метро', 'Телефон', 'OPEN');