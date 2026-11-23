CREATE TABLE events (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    venue_id BIGINT NOT NULL REFERENCES venues (id),
    starts_at TIMESTAMPTZ NOT NULL,
    capacity INTEGER NOT NULL CHECK (capacity > 0),
    remaining INTEGER NOT NULL CHECK (remaining >= 0 AND remaining <= capacity),
    price_cents BIGINT NOT NULL CHECK (price_cents >= 0)
);

CREATE INDEX idx_events_venue_id ON events (venue_id);