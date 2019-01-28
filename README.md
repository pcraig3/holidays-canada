# Canada Public Holidays API

Canada has public holidays, but when are they? UK people when [UK bank holidays](https://www.gov.uk/bank-holidays) are — Canadians who can’t yet move to Liverpool still deserve to know when they can sleep in.

This is an API that lists

- all 28 public holidays in Canada
- all 13 provinces and territories in Canada
- which provinces/territories observe which holidays

I scraped the data from [the Canadian holidays page on Wikipedia](https://en.wikipedia.org/wiki/Public_holidays_in_Canada), so if you notice an issue, please edit the page yourself and then [email me](mailto:paul@pcraig3.ca) so that I know.

This is a [Spring Boot](https://spring.io/projects/spring-boot) app, based on [the very philosophically intriguing REST tutorial](http://spring.io/guides/tutorials/rest/). It’s technically _not_ a REST API, [nor is it HATEOAS](https://www.youtube.com/watch?v=DksSPZTZES0).

Admittedly, the [Government of Canada API guidance](https://www.canada.ca/en/government/publicservice/modernizing/government-canada-standards-apis.html) wants a slightly more rigourous API design, but "design with users" is actually the first rule and none of them have complained yet.

## Docs

There are 5 endpoints.
All are `GET` requests.
As much as users would like to, it’s not yet possible to `PUT` additional public holidays.
None of the fields ever return `null` values.

### Routes

#### `/` → root

Returns a welcome message and links to the lists of data.

<details>
<summary>Example response</summary>

```json
{
  "_links": {
    "holidays": {
      "href": "url"
    },
    "self": {
      "href": "url"
    },
    "provinces": {
      "href": "url"
    }
  },
  "message": "welcome message"
}
```

</details>

#### `/v1/provinces` → GET all provinces and territories

Returns a list of provinces and territories in Canada. Each province or territory lists its associated holidays.

<details>
<summary>Example response</summary>

```json
{
  "provinces": [
    {
      "id": "AB",
      "nameEn": "Alberta",
      "nameFr": "Alberta",
      "holidays": [
        {
          "id": 1,
          "date": "2019-01-01",
          "nameEn": "New Year’s Day",
          "nameFr": "Jour de l’An",
          "national": true
        },
        ...
      ],
      "nextHoliday": {
        "id": 4,
        "date": "2019-02-18",
        "nameEn": "Family Day",
        "nameFr": "Fête de la famille",
        "national": false
      }
    },
    ...
  ],
  "_links": {
    "holidays": {
      "href": "url"
    },
    "self": {
      "href": "url"
    },
    "root": {
      "href": "url"
    }
  }
}
```

</details>

#### `/v1/provinces/{id}` → GET one province or territory

Returns one province or territory in Canada by [two-letter postal abbreviations](https://en.wikipedia.org/wiki/Canadian_postal_abbreviations_for_provinces_and_territories#Names_and_abbreviations). Case insensitive. Returns [a `400` response](https://github.com/pcraig3/holidays-canada#errors) for invalid `id`s.

<details>
<summary>Example response</summary>

```json
{
  "province": {
    "id": "AB",
    "nameEn": "Alberta",
    "nameFr": "Alberta",
    "holidays": [
      {
        "id": 1,
        "date": "2019-01-01",
        "nameEn": "New Year’s Day",
        "nameFr": "Jour de l’An",
        "national": true
      },
      ...
    ],
    "nextHoliday": {
      "id": 4,
      "date": "2019-02-18",
      "nameEn": "Family Day",
      "nameFr": "Fête de la famille",
      "national": false
    }
  },
  "_links": {
    "self": {
      "href": "url"
    },
    "provinces": {
      "href": "url"
    },
    "holidays": {
      "href": "url"
    },
    "root": {
      "href": "url"
    }
  }
}
```

</details>

#### `/v1/holidays` → GET all holidays

Returns a list of Canadian public holidays. Each holiday lists the regions that observe it.

<details>
<summary>Example response</summary>

```json
{
  "holidays": [
    {
      "id": 1,
      "date": "2019-01-01",
      "nameEn": "New Year’s Day",
      "nameFr": "Jour de l’An",
      "provinces": [
        {
          "id": "AB",
          "nameEn": "Alberta",
          "nameFr": "Alberta"
        },
        ...
      ],
      "national": true
    },
    ...
  ],
  "_links": {
    "self": {
      "href": "url"
    },
    "provinces": {
      "href": "url"
    },
    "root": {
      "href": "url"
    }
  }
}
```

</details>

#### `/v1/holidays/{id}` → GET one holiday

Returns one Canadian statutory holiday by integer id. Returns [a `404` response](https://github.com/pcraig3/holidays-canada#errors) for invalid `id`s.

<details>
<summary>Example response</summary>

```json

  "_links": {
    "self": {
      "href": "url"
    },
    "provinces": {
      "href": "url"
    },
    "holidays": {
      "href": "url"
    },
    "root": {
      "href": "url"
    }
  },
  "holiday": {
    "id": 1,
    "date": "2019-01-01",
    "nameEn": "New Year’s Day",
    "nameFr": "Jour de l’An",
    "provinces": [
      {
        "id": "AB",
        "nameEn": "Alberta",
        "nameFr": "Alberta"
      },
      ...
    ],
    "national": true
  }
}
```

</details>

### errors

Errors are thrown for invalid url paths or bad `id` values.

<details>
<summary>Example response</summary>

```json
{
  "error": {
    "message": "error message",
    "status": 400,
    "timestamp": "2019-01-27T05:33:53.241+0000"
  }
}
```

</details>

## Running

Booting up is a snap since it’s written in Java: the cleanest and most developer-friendly programming language.

#### Running locally

To run locally:

```bash
./mvnw clean spring-boot:run
```

And then visit [http://localhost:8080/](http://localhost:8080/).

#### Running as a container

To run (locally) as a Docker container:

```bash
./mvnw clean install  # compiles the .jar file
./build.sh            # builds the Docker container
./run.sh              # starts up the container on port 80
```

The Docker container is exposes port 80, so check it out at [http://localhost/](http://localhost/).

Easy.
