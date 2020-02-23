# Get a room (in Munich)

Some cities do really suck hard when it comes about getting a room. This
application will send applications as soon as a new room matches at wg-gesucht.de.
Therefore ensuring that your application is one of the first.

## Configuration

Create the file application.yml and set the following properties:

- `letter.gender`: "MALE" or "FEMALE", blame wg-gesucht for not having "Attack Helicopter".
- `letter.firstName`: Your first name
- `letter.lastName`: Your last name
- `letter.email`: Your email address
- `letter.phone`: Your phone number
- `letter.message` Your application letter
- `maxPrice`: The maximum price of the new flat, e.g. "500"
- `city`: The id of the city, e.g. "90" for Munich. Check wg-gesucht.de to find the id of your city.
- `districts`: Comma sperated list of district ids. Check wg-gesucht.de to find the district ids.
- `receiveCopy`: true, to receive a copy of your application (default is false)
- `login.username`: Your wg-gesucht.de username
- `login.password`: Your wg-gesucht.de password
- `login.userId`: Your user id

See also application.yml.dist as an example.

## Requirements

- Java 8
- Maven

## Build

The following command will build an executable fat jar in the folder target/:

    mvn package
