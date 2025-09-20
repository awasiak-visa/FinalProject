# Gry planszowe i rozgrywki

Projekt utworzony w ramach kursu CodersLab składa się z bazy gier panszowych `boardgames`, użytkowników `users`, kawiarni udostępniających gry `cafes`, recenzji gier `reviews` oraz rozgrywek `plays` w kawiarniach.
W projekcie wykorzystano Spring Boot, repozytoria Spring Data JPA oraz JPQL.

Wyszukiwanie zasobów jest dostępne dla wszystkich użytkowników bez konieczności zalogowania. Zalogowani użytkownicy mają możliwość tworzyć rozgrywki, recenzje, a także zapisywać listę ulubionych gier oraz gier, którymi są zainteresowani.
Administratorzy zarządzają grami oraz kawiarniami.

Dostęp do zasobów znajduje się pod adresami:
* gry planszowe: `localhost:8080/boardgames`
* kawiarnie: `localhost:8080/cafes`
* rozgrywki: `localhost:8080/plays`
* recenzje: `localhost:8080/reviews`
* użytkownicy: `localhost:8080/users`

Logowanie:

`POST localhost:8080/login`

`Content-Type: application/x-www-form-urlencoded`

`username='username'&password='password'`

Wylogowanie:

`POST localhost:8080/logout`
