# Лабораторная работа №3. Бюро находок

## Описание предметной области
REST API для бюро находок. Пользователи могут сообщать о пропажах, регистрировать найденные предметы, подавать претензии на находки и подтверждать факт передачи вещи владельцу.

## Бизнес-сущности
User — пользователь системы (заявитель)
FoundItem — найденный предмет (описание, категория, место находки, статус)
LossReport — заявление о пропаже (связано с User)
ItemClaim — претензия на найденный предмет (связана с LossReport и FoundItem)
HandoverRecord — акт передачи предмета (связан с ItemClaim, дата задаётся сервером)

## Связи между сущностями
- LossReport → User (Many-to-One)
- ItemClaim → LossReport (Many-to-One)
- ItemClaim → FoundItem (Many-to-One)
- HandoverRecord → ItemClaim (One-to-One)

## База данных
- СУБД: H2 (файловый режим)
- Схема: `src/main/resources/schema.sql`
- Тестовые данные: `src/main/resources/data.sql`
- Ограничения целостности: FOREIGN KEY, NOT NULL, UNIQUE

## Бизнес-операции

## 1. Создание претензии на найденный предмет
Эндпоинт: `POST /api/item-claims/claim?lossReportId={id}&foundItemId={id}&evidence={text}`

Затрагивает три сущности:
- создаёт `ItemClaim` со статусом `PENDING`
- меняет статус `LossReport` на `IN_PROGRESS`
- меняет статус `FoundItem` на `CLAIMED`

Проверяет текущее состояние: заявление должно быть в статусе `OPEN`, предмет — в статусе `AVAILABLE`. Выполняется в транзакции.

## 2. Подтверждение передачи предмета
Эндпоинт: `POST /api/handover-records/confirm/{claimId}`

Затрагивает три сущности:
- создаёт `HandoverRecord` с датой, задаваемой сервером (`LocalDateTime.now()`)
- меняет статус `ItemClaim` на `HANDED_OVER`
- меняет статус `FoundItem` на `RETURNED`

Клиент не передаёт время — оно устанавливается на стороне сервера.

## Запуск приложения
```bash
./mvnw spring-boot:run

## Тестирование
Тестирование выполнялось через расширение Thunder Client для VS Code.
Основные запросы:
- `POST /api/item-claims/claim?lossReportId=1&foundItemId=1&evidence=Receipt` — создание претензии
- `POST /api/handover-records/confirm/1` — подтверждение передачи
- `GET /api/found-items/1` — проверка статуса предмета