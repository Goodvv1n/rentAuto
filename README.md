# rentAuto
Тестовое задание

В файле application.properties необходимо настроить URL к БД MySQL, а так же логин и пароль. Я по умолчанию оставил свои.
После запуска приложения автоматически будут созданы необходимые таблицы.

Исходя из условий задания, я позволил себе ввести несколько условий:
 1. Имя клиента и Марка автомобиля должны быть уникальными.
 2. При попытке добавления клиента с уже существующим наименованием будет выдана ошибка о том,
 что такой пользователь уже существует.
 3. При попытке добавления авто с уже существующей маркой, ошибки выдано не будет.
 Работа будет продолжена с экземпляром авто, существующим в БД
 4. При удалении записи о бронировании пользователь с указанным именем удаляется.
 У авто с указанной маркой удаляются данные о забронировавшем его клиенте. Запись авто остается в БД.
 5. Если авто существует и занято - будет выведена ошибка о том, что данное авто занято другим клиентом.