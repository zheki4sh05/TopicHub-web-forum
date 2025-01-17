# TopicHub-web-forum
## Содержание
1. [Введение](#введение)
2. [Функциональные требования](#функциональные-требования)
3. [Архитектура](#архитектура-)
4. [Планы реализации](#планы-реализации)
   1. [Выпуск V1](#выпуск-v1)
   2. [Выпуск V2](#выпуск-v2)
## Введение
### Концепция проекта
Для специалистов, которым нужно продвигать свою экспертность, данная система TopicHub является Интернет-форумом, который обеспечивает возможность публиковать статьи и проводить ни их основе обсуждения. Система будет предлагать возможность следить за экспертами из различных областей, или хабами. Что позволит заинтересованным узнать другие мнения по данной теме, а также предложить свои идеи. Будучи экспертом в конкретной области, специалист сможет расширить круг знакомств, а также поделиться своим мнение через публикацию статей и оставляя реакцию. 

По используемым функциям пользователи делятся на 3 вида: гость, автор, модератор.

Данные для входа в роли модератора: логин: admin, пароль: 123456

### Установка
Склонировать проект: 
```bash
git clone https://github.com/zheki4sh05/TopicHub-web-forum.git
```
Запустить docker-compose:
```bash
docker-compose up
```

### ⚠️ Внимание!
По возникшим вопросам работы приложения писать сюда: e.shostak05@gmail.com

### Ссылка на дизайн-макет: https://www.figma.com/design/Szq83WYwWAIKwiNt6Kqsof/Material-UI-for-Figma-(and-MUI-X)-(Community)?node-id=8603-1335&node-type=canvas&t=0eJi3HXT8wThqRar-0

## Функциональные требования

### Для гостя

1. Просмотр статей
   1. Просмотры
   2. Лайки/Дизлайки
   3. Комментарии
   4. Просмотр по хабам
2. Поиск статьи
   1. Поиск по автору
   2. Поиск по рейтингу
   3. Поиск по теме
   4. Поиск по ключевым словам

### Для автора

1. Публикация статей
   1. Оформление статьи
      1. Список
      2. Заголовок
      3. Основной текст
      4. Изображение
   2. Добавление ключевых слов
   3. Отправление на модерацию
   4. Просмотр профиля
2.  Наблюдение за другими авторами
    1. Подписка на автора
    2. Добавление в закладки, убрать
3. Обсуждение
   1. Комментирование (Создание, Удаление, Изменение)
   2. Жалоба
   3. Лайк/дизлайк
4. Авторизация
   1. Создание аккаунта
   2. Управление профилем

### Для модератора

1. Управление статьями (Отклонение, Удаление, Модерация)
2. Управление хабами (Добавление, Удаление, Изменение)
3. Управление авторами (Блокировка, Разблокировка)
4. Управление комментариями (Просмотр жалоб, Удаление комментария)

## Архитектура 

**REST API**

### Фронтенд
- Vite
- ReactJS 18
- Redux 5.0.1
- Redux Toolkit 2.5.0
- axios 1.7.8
- MaterialUI 6.1

FSD архитектура (shared, entities, features, widgets, pages, precesses, app)

Основные страницы:
1. Login 
2. Article
3. CreateArticle
4. ProfilePage
5. ManageArticle
6. ManageAuthor
7. ManageComment

### Бекенд
- Maven
- JavaEE (Java 21, Tomcat 11)
- Flyway

3-уровневая архитектура (controllers, services, repository) 

Основные контроллеры:
1. LoginServlet 
2. ArticleServlet
3. SearchServlet
4. ReactionServlet
5. ProfileServlet
6. SandboxServlet

### БД/ORM/Connection pool

1. PostgresSQL 17
2. Hibernate 6.6
3. HikariCP 

## Планы реализации

### Выпуск v1

#### План выпуска функций приложения v1

Сроки с 28.11.2024-13.12.2024

| Стадия         |Страница       | Главная функция                  | Этап/Фича                                                     | Дедлайн   | Статус        |
|----------------|---------------|----------------------------------|---------------------------------------------------------------|-----------|---------------|
| Проектирование | Все           |    Все                           | Варианты использования                                        |28.11.2024 |  Готово       |
|                | Все           |    Все                           | Функциональные требования                                     |28.11.2024 |  Готово       |
|                | Все           |    Все                           | Архитектура                                                   |28.11.2024 |  Готово       | 
|                | Все           |    Все                           | Дизайн                                                        |30.11.2024 |  Готово       |
| Разработка     | CreateArticle |Публикация статей                 | Оформление статьи                                             |           |               |
|                |               |                                  | Добавление ключевых слов                                      | 03.12.2024|  Готово       |
|                |ManageArticle  |Управление статьями               | (Отклонение, Удаление)                                        |           |  Готово       |
|                |CreateArticle  |Публикация статей                 | Просмотр в общей ленте                                        | 04.12.2024|  Готово       |
|                |Login          |Авторизация                       |Создание аккаунта                                              |           |  Готово       |
|                |               |                                  |Управление профилем                                            |           |  Готово       |
|                |ProfilePage    |Публикация статей                 |Просмотр своего профиля                                        | 05.12.2024|  Готово       |
|                |ProfilePage    |Наблюдение за другими авторами    | Подписка на автора                                            |           |  Готово       |
|                |               |                                  | Добавление в закладки, убрать                                 |           |  Готово       |
|                |               |                                  | Просмотр статей                                               | 06.12.2024|  Готово       |
|                |ManageAuthor   |Управление авторами               |(Блокировка, Разблокировка)                                    | 07.12.2024|  Готово       |
|                |Article        |Поиск статьи                      | Поиск по автору                                               |           |  Готово       |
|                |               |                                  | Поиск по теме                                                 |           |  Готово       |
|                |               |                                  | Поиск по ключевым словам                                      | 08.12.2024|  Готово       |
|                |Article        |Обсуждение                        | Комментирование(Создание, Удаление, Изменение)                |           |  Готово       |
|                |               |                                  | Лайк/Дизлайк                                                  |           |  Готово       |
|                |               |                                  | Жалоба                                                        |10.12.2024 |  Готово       |
|                |ManageComment  |Управление комментами             |(Просмотр жалоб, Удаление комментария)                         |11.12.2024 |  Готово       |
| Тестирование   | Все           | Исправление багов                |Рефакторинг                                                    | 12.12.2024|  Готово       |
| Деплой         | Все           | Настройка деплоя                 |Dockerfile, docker-compose.yml                                 | 13.12.2024|  Готово       |


### Выпуск v2

#### План доработок следующей версии приложения

Сроки с 13.01.2025-21.01.2025

| Стадия                                            | Дедлайн    | Состояние    |
|---------------------------------------------------|------------|--------------|
| Доработка фич                                     |            |              |
| Изменение статьи                                  | 14.01.2025 | Готово       | 
| Вложенные комментарии                             | 14.01.2025 | Готово       | 
| Отправка email на новый комментарий               | 15.01.2025 | Готово       |
| Фильтрация                                        | 15.01.2025 | Готово       |
| Техническая доработка                             |            |              |
| Добавить логирование                              | 16.01.2025 | В процессе   |
| Абстрактная реализация взаимодействия с Hibernate | 16.01.2025 | Готово       |
| Настроить пулл соединений                         | 16.01.2025 | Готово       |
| Переключение             языков                   | 17.01.2025 | Готово       |
| Добавить транзакции для методов                   | 19.01.2025 | Надо сделать |
| Рефакторинг и исправление багов                   | 20.01.2025 | Надо сделать |
| Тестирование                                      | 20.01.2025 | Надо сделать |
| Отчет                                             | 20.01.2025 | Надо сделать |








