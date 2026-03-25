========================================
|                                      |
|        Peg Solitaire – Project       |
|                                      |
========================================

Popis hry
Peg Solitaire je logická hra, v ktorej hráč preskakuje figúrky po hracom poli podľa definovaných pravidiel.
Cieľom je znížiť počet zostávajúcich figúriek na čo najmenší počet.
Projekt bol vytvorený v rámci predmetu Gamestudio (KPI FEI TUKE).

Funkcionalita

Hlavné vlastnosti
- Načítavanie úrovní zo súborov (level1.txt, level2.txt, level3.txt)
- Zobrazenie hracieho poľa v konzole (farebné vykresľovanie)
- Validácia a vykonávanie ťahov podľa pravidiel hry
- Detekcia konca hry (výhra alebo prehra)
- Výpočet skóre (100 mínus počet zostávajúcich figúriek)
- Možnosť zanechať komentár
- Možnosť ohodnotiť hru (1–5)
- Zobrazenie top skóre, komentárov a priemerného hodnotenia

Integrácia s databázou PostgreSQL

ScoreService
- ukladanie výsledkov
- získanie top 10 výsledkov

CommentService
- pridávanie komentárov
- získanie zoznamu komentárov

RatingService
- nastavenie hodnotenia hry
- získanie hodnotenia konkrétneho hráča
- výpočet priemerného hodnotenia

Testovanie
Projekt obsahuje JUnit testy pre:
- ScoreService
- CommentService
- RatingService

Testy overujú:
- správne ukladanie údajov
- správne načítanie údajov
- aktualizáciu hodnotenia
- mazanie záznamov

SQL štruktúra tabuliek

CREATE TABLE score (
id SERIAL PRIMARY KEY,
player VARCHAR(100) NOT NULL,
game VARCHAR(100) NOT NULL,
points INT NOT NULL,
played_at TIMESTAMP NOT NULL
);

CREATE TABLE comment (
id SERIAL PRIMARY KEY,
player VARCHAR(100) NOT NULL,
game VARCHAR(100) NOT NULL,
comment VARCHAR(1000) NOT NULL,
commented_at TIMESTAMP NOT NULL
);

CREATE TABLE rating (
id SERIAL PRIMARY KEY,
player VARCHAR(100) NOT NULL,
game VARCHAR(100) NOT NULL,
rating INT NOT NULL,
CONSTRAINT rating_unique UNIQUE (player, game)
);

Dokumentácia
- diagram: documentation/diagram.png
- opis: documentation/description.pdf
- video link: documentation/video_link.txt

Video
Odkaz na video prezentáciu:https://www.dropbox.com/scl/fo/fptunygrdhhlyqe4pkumk/AF55ncoAdlDkBLR9HvgpzPU?rlkey=39ktrlznygbbzxrmai9ag5xx5&st=sbka2sy7&dl=0
(Cntr + LEFT Click to open)

Spustenie projektu
1. Vytvorte PostgreSQL databázu podľa SQL schémy.
2. Upravte db.properties podľa vašej konfigurácie.
3. Spustite projekt:
   mvn clean install
   java -jar target/pegsolitaire.jar

========================================
