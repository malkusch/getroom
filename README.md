# Get a room (in Munich)

Some cities do really suck hard when it comes about getting a room. This
application will send applications to every new room which is found
at wg-gesucht.de.

## Build

    mvn package

## Configuration

Create the file application.properties and set the following properties:

- `letter.gender`: "MALE" or "FEMALE", blame wg-gesucht for not having "Attack Helicopter".
- `letter.firstName`: Your first name
- `letter.lastName`: Your last name
- `letter.email`: Your email address
- `letter.phone`: Your phone number
- `letter.message` Your application letter. Line breaks are "\n".
- `maxPrice`: The maximum price of the new flat, e.g. "500"
- `city`: The id of the city, e.g. "90" for Munich. Check wg-gesucht.de to find the id of your city.

Note: application.properties is in ISO 8859-1. You can encode Umlaute with \uXXXX format. Or just
use an application.yml instead which is UTF-8 encoded.
