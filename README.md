========================================
|                                      |
|  Peg Solitaire – Gamestudio Project  |
|                                      |
========================================
  Popis hry
Peg Solitaire je logická hra, v ktorej hráč preskakuje figúrky po hracom poli podľa definovaných pravidiel.  
Cieľom je znížiť počet zostávajúcich figúrok na čo najmenší počet.

Projekt bol vytvorený v rámci predmetu Gamestudio (KPI FEI TUKE).


Functionality

  Hlavné vlastnosti
 - Načítavanie úrovní zo súborov (level1.txt, level2.txt, level3.txt)
 - Zobrazenie hracieho poľa v konzole
 - Validácia a vykonávanie ťahov podľa pravidiel hry
 - Detekcia konca hry (výhra alebo prehra)
 - Výpočet skóre (100 mínus počet zostávajúcich figúriek)

   Integrácia s databázou PostgreSQL
 Projekt obsahuje tri služby:

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


 SQL štruktúra tabuliek

```sql
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