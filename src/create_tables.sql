-- Vytvorenie tabulky pre skore
CREATE TABLE IF NOT EXISTS score (
                                     id SERIAL PRIMARY KEY,
                                     player VARCHAR(100) NOT NULL,
    game VARCHAR(100) NOT NULL,
    points INT NOT NULL,
    played_at TIMESTAMP NOT NULL
    );

-- Vytvorenie tabulky pre komentare
CREATE TABLE IF NOT EXISTS comment (
                                       id SERIAL PRIMARY KEY,
                                       player VARCHAR(100) NOT NULL,
    game VARCHAR(100) NOT NULL,
    comment VARCHAR(1000) NOT NULL,
    commented_at TIMESTAMP NOT NULL
    );

-- Vytvorenie tabulky pre hodnotenia
CREATE TABLE IF NOT EXISTS rating (
                                      id SERIAL PRIMARY KEY,
                                      player VARCHAR(100) NOT NULL,
    game VARCHAR(100) NOT NULL,
    rating INT NOT NULL,
    CONSTRAINT rating_unique UNIQUE (player, game)
    );