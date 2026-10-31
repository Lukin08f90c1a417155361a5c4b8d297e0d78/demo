-- Удаляем таблицы, если они есть (чтобы скрипт можно было запускать повторно)
DROP TABLE IF EXISTS handover_records;
DROP TABLE IF EXISTS item_claims;
DROP TABLE IF EXISTS loss_reports;
DROP TABLE IF EXISTS found_items;
DROP TABLE IF EXISTS users;

-- 1. Пользователи
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

-- 2. Найденные предметы
CREATE TABLE found_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL,
    location_found VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'AVAILABLE'
);

-- 3. Заявления о пропаже
CREATE TABLE loss_reports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    applicant_id BIGINT NOT NULL,
    description VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'OPEN',
    CONSTRAINT fk_loss_report_user FOREIGN KEY (applicant_id) REFERENCES users(id)
);

-- 4. Претензии на предметы
CREATE TABLE item_claims (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    loss_report_id BIGINT NOT NULL,
    found_item_id BIGINT NOT NULL,
    evidence VARCHAR(500),
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    CONSTRAINT fk_claim_loss_report FOREIGN KEY (loss_report_id) REFERENCES loss_reports(id),
    CONSTRAINT fk_claim_found_item FOREIGN KEY (found_item_id) REFERENCES found_items(id)
);

-- 5. Акты передачи
CREATE TABLE handover_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    claim_id BIGINT NOT NULL UNIQUE,
    handover_date TIMESTAMP NOT NULL,
    confirmed BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_handover_claim FOREIGN KEY (claim_id) REFERENCES item_claims(id)
);