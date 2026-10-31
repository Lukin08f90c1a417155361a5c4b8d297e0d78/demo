-- Удаляем таблицы в правильном порядке (сначала те, что зависят от других)
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS handover_records;
DROP TABLE IF EXISTS item_claims;
DROP TABLE IF EXISTS loss_reports;
DROP TABLE IF EXISTS found_items;
DROP TABLE IF EXISTS users;

-- 1. Пользователи (обновленная таблица)
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE, -- Логин для входа
    password VARCHAR(255) NOT NULL,        -- Хэш пароля (BCrypt)
    name VARCHAR(255) NOT NULL,            -- Отображаемое имя
    enabled BOOLEAN NOT NULL DEFAULT TRUE  -- Состояние пользователя (активен/заблокирован)
);

-- 2. Роли пользователей (новая таблица)
CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role VARCHAR(50) NOT NULL,
    CONSTRAINT fk_user_roles_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- 3. Найденные предметы
CREATE TABLE found_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL,
    location_found VARCHAR(255) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'AVAILABLE'
);

-- 4. Заявления о пропаже
CREATE TABLE loss_reports (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    applicant_id BIGINT NOT NULL,
    description VARCHAR(255) NOT NULL,
    category VARCHAR(100) NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'OPEN',
    CONSTRAINT fk_loss_report_user FOREIGN KEY (applicant_id) REFERENCES users(id)
);

-- 5. Претензии на предметы
CREATE TABLE item_claims (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    loss_report_id BIGINT NOT NULL,
    found_item_id BIGINT NOT NULL,
    evidence VARCHAR(500),
    status VARCHAR(50) NOT NULL DEFAULT 'PENDING',
    CONSTRAINT fk_claim_loss_report FOREIGN KEY (loss_report_id) REFERENCES loss_reports(id),
    CONSTRAINT fk_claim_found_item FOREIGN KEY (found_item_id) REFERENCES found_items(id)
);

-- 6. Акты передачи
CREATE TABLE handover_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    claim_id BIGINT NOT NULL UNIQUE,
    handover_date TIMESTAMP NOT NULL,
    confirmed BOOLEAN NOT NULL DEFAULT FALSE,
    CONSTRAINT fk_handover_claim FOREIGN KEY (claim_id) REFERENCES item_claims(id)
);