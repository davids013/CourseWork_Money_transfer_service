# Курсовой проект "Сервис перевода денег"

Сервис предоставляет интерфейс для интеграции с пользовательским 
[веб-приложением](https://github.com/serp-ya/card-transfer) в соответствии со 
[спецификацией](https://github.com/netology-code/jd-homeworks/blob/master/diploma/MoneyTransferServiceSpecification.yaml)
и предназначен для имитации перевода денежных средств между банковскими картами.

Сервис принимает JSON-объект методом POST по адресу http://localhost:5500/transfer
с указанием данных карт списания, пополнения и сумме перевода.

### Пример тела запроса
```
{
    "cardFromNumber":   "1111111111111111",
    "cardToNumber":     "2222222222222222",
    "cardFromCVV":      "333",
    "cardFromValidTill":"02/25",
    "amount": {
        "currency":     "RUR",
        "value":        33000
    }
}
```

#### Требования к полям запроса:

1. *cardFromNumber* - номер карты отправителя, строка (16 цифровых символов);
2. *cardToNumber* - номер карты получателя, строка (16 цифровых символов);
3. _cardFromCVV_ - CVV-код карты отправителя, строка (3 цифровых символа);
4. _cardFromValidTill_ - срок действия карты отправителя в формате MM/YY, 
строка (2 цифровых символа для обозначения месяца, разделительный символ '/', 
2 цифровых символа для обозначения месяца);
5. _currency_ - обозначение валюты перевода, строка (до 4 символов);
6. _value_ - сумма перевода, целое число типа Integer.

В случае корректного запроса сервис возвращает объект с идентификатором операции, 
а в журнале событий появится новая запись со статусом *TRANSFER_REQUESTED*:
```
{
  "id": "1"
}
```

Для завершения транзакции требуется отправить код подтверждения в теле 
POST-метода по адресу http://localhost:5500/confirmOperation с указанием 
идентификатора операции (в текущей реализации пользовательского 
веб-приложения идентификатор отсутствует - сервисом присваивается 
идентификатор последней операции):
```
{
  "operationId":  "1",
  "code":         "1234"
}
```

#### Требования к полям запроса:
1. _operationId_ - идентификатор операции, строка (один или более числовых символов);
2. _code_ - код подтверждения операции, (4 числовых символа)

При успешном подтверждении перевода статус привязанной операции меняется 
на *VERIFIED*. Операция считается завершённой. В противном случае вернется
JSON-объект с ошибкой в виде сообщения и идентификатора ошибки при ее наличии 
или -1 при отсутствии операций:
```
{
    "message": "Транзакция не обнаружена или выполнена ранее",
    "id": 12
}
```
