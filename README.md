### Дипломный проект профессии «Тестировщик» ###

![example workflow](https://github.com/FlyingDog265/NetologyDiploma/actions/workflows/mySQLTestsCI.yml/badge.svg)
![example workflow](https://github.com/FlyingDog265/NetologyDiploma/actions/workflows/postgressTestsCI.yml/badge.svg)

[![Allure report](https://img.shields.io/badge/Allure%20Report-deployed-yellowgreen)](https://flyingdog265.github.io/NetologyDiploma/)

[**Планирование автоматизации**](documentation/Plan.md)

#### Необходимое окружение: ####

* JDK, версия не ниже 11.0.9
* Docker
* убедиться, что порты 8080, 9999 и 5432 или 3306 (в зависимости от выбранной базы данных) свободны

#### Шаги для локального запуска проекта: ####

1. Скачать проект
1. Запустить контейнер, в котором разворачивается база данных (далее БД):
    * для использования Postgres:`docker-compose -f docker-compose-postgress.yml up -d --force-recreate`
    * для использования MySQL: `docker-compose -f docker-compose-mysql.yml up -d --force-recreate`
1. Убедиться в том, что БД готова к работе
1. Запустить приложение командой:
    * для использования
      Postgres: `java -Dspring.datasource.url=jdbc:postgresql://localhost:5432/postgres -jar artifacts/aqa-shop.jar`
    * для использования
      MySQL: `java -Dspring.datasource.url=jdbc:mysql://localhost:3306/app -jar artifacts/aqa-shop.jar`
1. Запустить тесты командой:
    * при работе с
      Postgres: `./gradlew test -Ddb.url=jdbc:postgresql://localhost:5432/postgres -Dlogin=app -Dpassword=pass -Dapp.url=http://localhost:8080`
    * при работе с
      MySQL: `./gradlew test -Ddb.url=jdbc:mysql://localhost:3306/app -Dlogin=app -Dpassword=pass -Dapp.url=http://localhost:8080`
