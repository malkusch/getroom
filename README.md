# Get a room (in Munich)

Some cities do really suck hard when it comes about getting a room. This
application will send applications as soon as a new room matches at wg-gesucht.de.
Therefore ensuring that your application is one of the first.

## Configuration

Create the file application.yml and set the following properties:

- `letter.message` Your application letter
- `maxPrice`: The maximum price of the new flat, e.g. "500"
- `city`: The id of the city, e.g. "90" for Munich. Check wg-gesucht.de to find the id of your city.
- `districts`: Comma sperated list of district ids. Check wg-gesucht.de to find the district ids.
- `login.username`: Your wg-gesucht.de username
- `login.password`: Your wg-gesucht.de password

See also application.yml.dist as an example.

## Requirements

- Java 8
- Maven

## Build

The following command will build an executable fat jar in the folder target/:

    mvn package
