# Техническое задание - проект "Pooh JMS" 
[![Build Status](https://travis-ci.com/VitaliyNasypov/job4j_pooh.svg?branch=master)](https://travis-ci.com/VitaliyNasypov/job4j_pooh)
[![codecov](https://codecov.io/gh/VitaliyNasypov/job4j_pooh/branch/master/graph/badge.svg?token=34ZW4HBXLB)](https://codecov.io/gh/VitaliyNasypov/job4j_pooh)
<br>
В этом проекте мы делаем аналог асинхронной очереди RabbitMQ. Приложение запускает Socket и ждёт клиентов. Клиенты могут быть двух типов: отправители (publisher) и получатели (subscriver). В качестве протокола будет использоваться HTTP, а сообщения в формате JSON. Существуют два режима: queue, topic. 
<br><br>

<b>Режим Queue</b>
<br>
Отправитель посылает сообщение с указанием очереди. Получатель читает первое сообщение и удаляет его из очереди. Если приходят несколько получателей, то они читают из одной очереди. Уникальное сообщение может быть прочитано, только одним получателем.
<br>
Пример запросов:
<br>
POST /queue
{
  "queue" : "weather",
  "text" : "temperature +18 C"
}
<br>
GET /queue/weather
{
  "queue" : "weather",
  "text" : "temperature +18 C"
}
<br><br>


<b>Режим Topic</b>
<br>
Отправить посылает сообщение с указанием темы. Получатель читает первое сообщение и удаляет его из очереди. Если приходят несколько получателей, то они читают отдельные очереди.
<br>
Пример запросов:
<br>
POST /topic
{
  "topic" : "weather",
  "text" : "temperature +18 C"
}
<br>
GET /topic/weather
{
  "topic" : "weather",
  "text" : "temperature +18 C"
}
