--- 1. Anagrafica Titoli (Per evitare ridondanza nomi)
CREATE TABLE IF NOT EXISTS security (
    isin VARCHAR(12) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    currency VARCHAR(3) DEFAULT 'EUR'
);

--- 2. Portafogli Interni (Contesto H14)
CREATE TABLE IF NOT EXISTS portfolio (
    id VARCHAR(50) PRIMARY KEY,
    description VARCHAR(255)
);

--- 3. Posizioni Interne (Da Swarm)
--- Gestisce lo Step B: una posizione può appartenere a un portafoglio specifico
CREATE TABLE IF NOT EXISTS internal_position (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    isin VARCHAR(12) NOT NULL,
    portfolio_id VARCHAR(50) NOT NULL,
    quantity DECIMAL(19, 4) NOT NULL,
    market_price DECIMAL(19, 4), --- Step D: Prezzi
    last_update TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (isin) REFERENCES security(isin),
    FOREIGN KEY (portfolio_id) REFERENCES portfolio(id)
);

--- 4. Posizioni Bancarie (Dai Feed)
--- Gestisce lo Step A: più record per lo stesso ISIN da banche diverse
CREATE TABLE IF NOT EXISTS bank_position (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    isin VARCHAR(12) NOT NULL,
    bank_name VARCHAR(100) NOT NULL, --- Es: 'Intesa', 'Mediolanum'
    quantity DECIMAL(19, 4) NOT NULL,
    price_reported DECIMAL(19, 4),   --- Step D: Prezzo comunicato dalla banca
    report_date DATE NOT NULL,
    FOREIGN KEY (isin) REFERENCES security(isin)
);

--- 5. Log di Riconciliazione Manuale (Step C)
--- Permette di "chiudere" una discrepanza con un commento
CREATE TABLE IF NOT EXISTS reconciliation_notes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    isin VARCHAR(12) NOT NULL,
    portfolio_id VARCHAR(50) NOT NULL,
    is_validated BOOLEAN DEFAULT FALSE,
    note TEXT,
    validated_by VARCHAR(100),
    validation_date TIMESTAMP,
    UNIQUE(isin, portfolio_id)
);