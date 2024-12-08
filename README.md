# TopicHub-web-forum

**Концепия проекта:** Для специалистов, которым нужно продвигать свою экспертность, данная система TopicHub является Интернет-форумом, который обеспечивает возможность публиковать статьи и проводить ни их основе обсуждения. Система будет предлагать возможность следить за экспертами из различных областей, или хабами. Что позволит заинтересованным узнать другие мнения по данной теме, а также предложить свои идеи. Будучи экспертом в конкретной области, специалист сможет расширить круг знакомств, а также поделиться своим мнение через публикацию статей и оставляя реакцию. 

По используемым функциям пользователи делятся на два вида: авторы, модератор.

### Ссылка на дизайн-макет: https://www.figma.com/design/Szq83WYwWAIKwiNt6Kqsof/Material-UI-for-Figma-(and-MUI-X)-(Community)?node-id=8603-1335&node-type=canvas&t=0eJi3HXT8wThqRar-0

## Функциональные требования: 

### Для автора

1. Публикация статей
   1. Оформление статьи
      1. Список
      2. Заголовок
      3. Основной текст
      4. Жирный текст
   2. Добавление ключевых слов
   3. Отправление на модерацию
   4. Просмотр профиля
2.  Наблюдение за другими авторами
    1. Подписка на автора
    2. Добавление в закладки, убрать 
    3. Просмотр статей
       1. Просмотры
       2. Лайки/Дизлайки
       3. Комментарии
       4. Добавление/удаление в закладки
       5. Рекомендации
3. Обсуждение
   1. Комментирование (Создание, Удаление, Изменение)
   2. Жалоба
   3. Лайк/дизлайк
4. Поиск статьи
   1. Поиск по автору
   2. Поиск по рейтингу
   3. Поиск по теме
   4. Поиск по ключевым словам
5. Авторизация
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
- ReactJS(Redux) 18
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

3-уровневая архитектура (controllers, services, respository) 

Основные контроллеры:
1. LoginServlet 
2. ArticleServlet
3. SearchServlet
4. ReactionServlet
5. ProfileServlet
6. SandboxServlet

### Сторонние сервисы/БД/ORM

1. Minio S3 3.0
2. Сервис для модерации сообщений
3. PostgreSQL 17
4. Hibernate 6.6

## План выпуска функций приложения

Сроки с 28.11.2024-13.12.2024

| Стадия         |Страница       | Главная функция                  | Этап/Фича                                                     | Дедлайн   | Статус        |
|----------------|---------------|----------------------------------|---------------------------------------------------------------|-----------|---------------|
| Проектирование | Все           |    Все                           | Варианты использования                                        |28.11.2024 |  Готово       |
|                | Все           |    Все                           | Функциональные требования                                     |28.11.2024 |  Готово       |
|                | Все           |    Все                           | Архитектура                                                   |28.11.2024 |  Готово       | 
|                | Все           |    Все                           | Дизайн                                                        |30.11.2024 |  Готово       |
| Разработка     | CreateArticle |Публикация статей                 | Оформление статьи                                             |           |               |
|                |               |                                  | Добавление ключевых слов                                      | 03.12.2024|  Готово       |
|                |ManageArticle  |Управление статьями               | (Отклонение, Удаление, Модерация)                             |           |  Готово       |
|                |CreateArticle  |Публикация статей                 | Отправление на модерацию                                      | 04.12.2024|  В процессе   |
|                |Login          |Авторизация                       |Создание аккаунта                                              |           |  Готово       |
|                |               |                                  |Управление профилем                                            |           |  Готово       |
|                |ProfilePage    |Публикация статей                 |Просмотр своего профиля                                        | 05.12.2024|  Готово       |
|                |ProfilePage    |Наблюдение за другими авторами    | Подписка на автора                                            |           |  В процессе   |
|                |               |                                  | Добавление в закладки, убрать                                 |           |  В процессе   |
|                |               |                                  | Просмотр статей                                               | 06.12.2024|  Готово       |
|                |ManageAuthor   |Управление авторами               |(Блокировка, Разблокировка)                                    | 07.12.2024|  Надо сделать |
|                |Article        |Поиск статьи                      | Поиск по автору                                               |           |  Надо сделать |
|                |               |                                  | Поиск по рейтингу                                             |           |  Надо сделать |
|                |               |                                  | Поиск по теме                                                 |           |  Надо сделать |
|                |               |                                  | Поиск по ключевым словам                                      | 08.12.2024|  Надо сделать |
|                |Article        |Обсуждение                        | Комментирование(Создание, Удаление, Изменение)                |           |  Надо сделать |
|                |               |                                  | Жалоба                                                        |10.12.2024 |  Надо сделать |
|                |ManageComment  |Управление комментами             |(Просмотр жалоб, Удаление комментария)                         |11.12.2024 |  Надо сделать |
| Тестирование   | Все           | Исправление багов                |Рефакторинг                                                    | 12.12.2024|  Надо сделать |
| Деплой         | Все           | Настройка деплоя                 |Dockerfile, docker-compose.yml                                 | 13.12.2024|  Надо сделать |


   










