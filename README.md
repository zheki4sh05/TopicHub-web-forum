# TopicHub-web-forum

**Концепия проекта:** Для специалистов, которым нужно продвигать свою экспертность, данная система TopicHub является Интернет-форумом, который обеспечивает возможность публиковать статьи и проводить ни их основе обсуждения. Система будет предлагать возможность следить за экспертами из различных областей, или хабами. Что позволит заинтересованным узнать другие мнения по данной теме, а также предложить свои идеи. Будучи экспертом в конкретной области, специалист сможет расширить круг знакомств, а также поделиться своим мнение через публикацию статей и оставляя реакцию. 

По используемым функциям пользователи делятся на два вида: авторы, модератор.

## Функциональные требования: 

### Для автора

1. Публикация статей
   1. Оформление статьи
      1. Список
      2. Заголовок
      3. Основной текст
      4. Жирный текст
   2. Загрузка документа
   3. Добавление ключевых слов
   4. Отправление на модерацию
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
   2. Лайк/дизлайк
4. Поиск статьи
   1. Поиск по автору
   2. Поиск по рейтингу
   3. Поиск по теме
   4. Поиск по ключевым словам

### Для модератора

1. Управление статьями (Отклонение, Удаление, Модерация)
2. Управление хабами (Добавление, Удаление, Изменение)
3. Управление авторами (Блокировка, Разблокировка)
4. Управление комментариями (Просмотр жалоб, Удаление комментария)

## План выпуска функций приложения

Сроки с 28.11.2024-13.12.2024

| Стадия         | Этап/Фича                                                     | Дедлайн   | Статус        |
|----------------|---------------------------------------------------------------|-----------|---------------|
| Проектирование | Варианты использования                                        |28.11.2024 |  Готово       |
|                | Функциональные требования                                     |28.11.2024 |  Готово       |
|                | Архитектура                                                   |28.11.2024 |  Готово       | 
|                | Дизайн                                                        |30.11.2024 |  Надо сделать |
|                | БД                                                            |01.12.2024 |  Надо сделать |
| Разработка     | Оформление статьи                                             |           |  Надо сделать |
|                | Загрузка документа                                            |02.12.2024 |  Надо сделать |
|                | Добавление ключевых слов                                      |           |  Надо сделать |
|                | Отправление на модерацию                                      | 03.12.2024|  Надо сделать |
|                | Подписка на автора                                            |           |  Надо сделать |
|                | Добавление в закладки, убрать                                 | 04.12.2024|  Надо сделать |
|                | Просмотр статей                                               |           |  Надо сделать |
|                | Лайк/дизлайк                                                  | 05.12.2024|  Надо сделать |
|                | Комментирование (Создание, Удаление, Изменение)               |           |  Надо сделать |
|                | Лайк/дизлайк                                                  | 06.12.2024|  Надо сделать |
|                | Поиск по автору                                               |           |  Надо сделать |
|                | Поиск по рейтингу                                             | 07.12.2024|  Надо сделать |
|                | Поиск по теме                                                 |           |  Надо сделать |
|                | Поиск по ключевым словам                                      | 08.12.2024|  Надо сделать |
|                | Управление статьями (Отклонение, Удаление, Модерация)         |           |  Надо сделать |
|                |Управление хабами (Добавление, Удаление, Изменение)            | 09.12.2024|  Надо сделать |
|                | Управление авторами (Блокировка, Разблокировка)               |           |  Надо сделать |
|                |Управление комментариями (Просмотр жалоб, Удаление комментария)| 10.12.2024|  Надо сделать |
| Тестирование   | Исправление багов                                             | 12.12.2024|  Надо сделать |
| Деплой         | Исправление багов                                             | 13.12.2024|  Надо сделать |
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

### Бекенд
- Maven
- JavaEE (Java 21, Tomcat 11)

3-уровневая архитектура (controllers, services, respository) 

Основные контроллеры:
1. AuthenticationController 
2. ArticleController
3. SearchController
4. ReactionController

### Сторонние сервисы/БД/ORM

1. Minio S3 3.0
2. Сервис для модерации сообщений
3. PostgreSQL 17
4. Hibernate 6.6

   










