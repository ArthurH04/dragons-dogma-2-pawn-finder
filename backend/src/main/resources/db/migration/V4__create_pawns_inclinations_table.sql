CREATE TABLE pawn_inclinations (
    pawn_id UUID NOT NULL,
    inclinations VARCHAR(50) NOT NULL,
    CONSTRAINT fk_pawn FOREIGN KEY (pawn_id) REFERENCES pawns(id)
);

ALTER TABLE pawns
DROP COLUMN inclinations;