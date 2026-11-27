# API examples

Numbers use `:=`. Keep `startsAt` in the future.

## Setup
Need a venue first (until venue endpoints exist):
```sql
INSERT INTO venues (name, city, latitude, longitude) VALUES ('Croke Park', 'Dublin', 53.3609, -6.2511);
```
psql: `docker compose exec postgres psql -U tixdub -d tixdub`

## Events
Create (201 + Location, remaining = capacity)
```bash
http POST :8080/api/events name="Winter Gig" venueId:=1 startsAt="2026-12-20T19:00:00Z" capacity:=100 priceCents:=2500
```
Get one (200)
```bash
http :8080/api/events/1
```
List (200)
```bash
http :8080/api/events
```
Update (200)
```bash
http PUT :8080/api/events/1 name="Winter Gig" venueId:=1 startsAt="2026-12-20T19:00:00Z" capacity:=150 priceCents:=2500
```
Delete (204, then GET = 404)
```bash
http DELETE :8080/api/events/1
```

## Errors
Bad venue (404)
```bash
http POST :8080/api/events name="X" venueId:=99 startsAt="2026-12-20T19:00:00Z" capacity:=100 priceCents:=2500
```
Missing event (404)
```bash
http :8080/api/events/999
```
Missing name (400 + errors list)
```bash
http POST :8080/api/events venueId:=1 startsAt="2026-12-20T19:00:00Z" capacity:=100 priceCents:=2500
```
Bad body (400)
```bash
http POST :8080/api/events startsAt="not-a-date"
```
Capacity below sold (409). Fake sold tickets first:
```sql
UPDATE events SET remaining = 30 WHERE id = 1;
```
Then PUT with capacity:=100 (sold = 120). 120 or more works.

## Check DB
```sql
SELECT id, name, venue_id, capacity, remaining FROM events;
```

## Persistence
Create an event, restart app, list again. Event still there.