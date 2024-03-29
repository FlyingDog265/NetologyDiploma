# Отчёт по итогам автоматизации

    1. Что было запланировано и что было сделано
    1. Причины, по которым что-то не было сделано
    1. Сработавшие риски
    1. Общий итог по времени (сколько запланировали/сколько потратили с обоснованием расхождения)

### Что было запланировано и что было сделано ###

Было запланировано автоматизировать тестирование приложения для покупки тура/ Были написаны 26 сценариев для покрытия.
Впоследствии, после нахождения багов, было добавлено еще 6 сценариев для проверки исправлений. Все инструменты, которые
были запланированы для использования показали себя с лучшей стороны. При расширении функционала данный проект легко
масштабируется и это не вызовет особых проблем.

При поднятии окружения Аллюр-отчет был включен непосредственно в CI и выведен в файл Readme, что позволяет увидеть
подробный отчет по проваленным кейсам сразу после пуша в master, без дополнительных манипуляций с командной строкой.
Плюс вся отчетность хранится непосредственно рядом с кодом тестов.

### Причины, по которым что-то не было сделано ###

Все запланированные активности были выполнены.

### Сработавшие риски ###

Заявленные риски не сработали.

### Общий итог по времени ###

**Поднятие окружения, настройка CI:**

* Запланировано - 15 часов
* Затрачено - 14 часов

Стоит учесть, что в настройку CI уместился и Allure-отчет, что встроен в Gitlab Actions

**Разработка тест-кейсов:**

* Запланировано - 65 часов
* Затрачено - 30 часов

Стоит учесть, что тест-кейсы для покупки тура по карте и покупки тура в кредит идентичны за малыми исключениями, что
позволило очень быстро написать их.

**Отладка тестовых прогонов, оформление отчетности**

* Запланировано - 32 часов
* Затрачено - 4 часа

Оформление отчета удалось реализовать при настройке CI и написания тестов (с помощью аннотаций аллюра)
В дальнейшем вся отладка отчета сводилась к корректировке аннотаций.

**Оформление отчета по итогам внедрения автоматизации**

* Запланировано - 8 часов
* Затрачено - 2 часа

При грамотно оформленном аллюр-отчете и подробно заведенных issues надобность дублирования их в отчете отпадает и
достаточно было приложить ссылку, что очень сэкономило время.

#### Итого затрачено:

* **50 часов**