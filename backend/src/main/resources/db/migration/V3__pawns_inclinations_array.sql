ALTER TABLE pawns
    ALTER COLUMN inclinations TYPE inclinations_enum[] USING ARRAY[inclinations];

CREATE INDEX idx_pawn_inclinations ON pawns USING GIN (inclinations);
