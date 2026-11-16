# API  test commands 

## - inMemory data stage

### Create an event
```bash
http POST :8080/api/events name="Dublin Jazz Night" venueName="Vicar Street" startsAt="2027-06-01T19:30:00Z" capacity:=500 priceCents:=2500
```
Expect 201 with a Location header.

### List events
```bash
http GET :8080/api/events
```
Expect 200 with a list.

### Get an event
```bash
http GET :8080/api/events/1
```
Expect 200.

### Update an event
```bash
http PUT :8080/api/events/1 name="Dublin Jazz Night (Updated)" venueName="Vicar Street" startsAt="2027-06-01T20:00:00Z" capacity:=450 priceCents:=3000
```
Expect 200 with the new values.

### Error: missing fields
```bash
http POST :8080/api/events name=""
```
Expect 400 with an errors list.

### Error: wrong values
```bash
http POST :8080/api/events name="" venueName="" startsAt="2020-01-01T10:00:00Z" capacity:=-5 priceCents:=-1
```
Expect 400 with an errors list sorted by field.

### Error: unreadable body
```bash
http POST :8080/api/events name="Jazz" venueName="Vicar Street" startsAt="tomorrow" capacity:=10 priceCents:=0
```
Expect 400 "Unable to read request body".

### Error: event not found
```bash
http GET :8080/api/events/999
http PUT :8080/api/events/999 name="X" venueName="Y" startsAt="2027-06-01T20:00:00Z" capacity:=10 priceCents:=0
http DELETE :8080/api/events/999
```
Expect 404 for each.

### Error: invalid update
```bash
http PUT :8080/api/events/1 name="" capacity:=-1
```
Expect 400 with an errors list.

### Delete an event
```bash
http DELETE :8080/api/events/1
http GET :8080/api/events/1
```
Expect 204, then 404.
