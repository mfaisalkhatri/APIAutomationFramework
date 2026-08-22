# API Test Automation Framework

A lightweight, extensible API test automation framework built with **Java, Rest-Assured, TestNG, Google Truth, Lombok,
and Allure**.

The framework is designed to keep API tests simple and readable while hiding configuration, HTTP-client, and
Rest-Assured implementation details behind framework abstractions.

## Tech Stack

* **Java**
* **Maven**
* **TestNG** — test execution
* **Rest-Assured** — HTTP/API communication
* **Google Truth** — assertions
* **Lombok** — boilerplate reduction
* **SLF4J + Log4j2** — logging
* **Allure** — test reporting
* **JSON** — framework configuration and test data

---

## Project Goals

The framework is designed around the following principles:

* Simple and readable API tests
* Support for multiple environments
* JSON-based configuration
* No `given()`, `when()`, `then()` syntax in tests
* No hard-coded HTTP method strings
* Google Truth assertions
* Centralized API request execution
* Rest-Assured hidden behind framework abstractions
* Configurable request/response logging
* Allure test reporting
* Support for parallel test execution

---

# Architecture

The framework follows a layered architecture.

```text
                     TestNG Tests
                          │
                          ▼
                 ApiRequestContext
                          │
                          ▼
                      ApiClient
                    <<interface>>
                          │
                          ▼
                RestAssuredClient
                          │
                          ▼
                     Rest-Assured
                          │
                          ▼
                         API
                          │
                          ▼
                     ApiResponse
                          │
                          ▼
                   Google Truth
```

Configuration is handled independently:

```text
                       config.json
                            │
                            ▼
                     JsonConfigProvider
                            │
                            ▼
                      ConfigManager
                            │
                            ▼
                  ApiRequestContext /
                  RestAssuredClient
```

---

# Key Design Decisions

## 1. `ApiRequest`

`ApiRequest` represents an HTTP request.

It contains:

* HTTP method
* Endpoint
* Headers
* Query parameters
* Path parameters
* Request body

Requests are created using a fluent API:

```java
ApiRequest request = ApiRequest.builder ()
    .get ()
    .endpoint ("/users/{id}")
    .pathParams (Map.of ("id", "1"))
    .build ();
```

POST requests can be created using:

```java
ApiRequest request = ApiRequest.builder ()
    .post ()
    .endpoint ("/users")
    .body (user)
    .build ();
```

The framework uses an `HttpMethod` enum rather than hard-coded strings such as `"GET"` or `"POST"`.

---

# 2. `ApiClient`

`ApiClient` is the framework abstraction responsible for executing API requests.

```java
public interface ApiClient {

    ApiResponse execute (ApiRequest request);
}
```

The tests depend on the interface rather than directly depending on Rest-Assured.

---

# 3. `RestAssuredClient`

`RestAssuredClient` is the concrete implementation of `ApiClient`.

It is responsible for:

* Creating Rest-Assured requests
* Applying the configured base URL
* Applying timeout configuration
* Applying headers
* Applying query parameters
* Applying path parameters
* Applying request bodies
* Executing HTTP requests
* Converting Rest-Assured responses into `ApiResponse`

Rest-Assured therefore remains an implementation detail of the framework.

---

# 4. `ApiRequestContext`

`ApiRequestContext` provides the user-facing entry point for API communication.

The test does not need to know about:

* `ConfigManager`
* `RestAssuredClient`
* Rest-Assured configuration
* Base URL configuration

The test simply works with:

```java
ApiRequestContext apiRequestContext;
```

and executes:

```java
ApiResponse response = apiRequestContext.execute (request);
```

This is conceptually similar to Playwright's `APIRequestContext`.

---

# Configuration

Framework configuration is stored in:

```text
src/main/resources/config.json
```

Example:

```json
{
  "activeEnvironment": "qa",
  "environments": {
    "qa": {
      "baseUrl": "https://qa.example.com"
    },
    "preprod": {
      "baseUrl": "https://preprod.example.com"
    }
  },
  "timeouts": {
    "connection": 10000
  },
  "logging": {
    "enabled": true,
    "request": true,
    "response": true
  }
}
```

## Supported Environments

Multiple environments can be configured:

```json
{
  "environments": {
    "qa": {
      "baseUrl": "https://qa.example.com"
    },
    "preprod": {
      "baseUrl": "https://preprod.example.com"
    }
  }
}
```

The default environment is configured using:

```json
{
  "activeEnvironment": "qa"
}
```

An environment can also be overridden at runtime:

```text
-Denv=preprod
```

For example:

```bash
mvn test -Denv=preprod
```

The runtime environment takes precedence over the environment configured in `config.json`.

---

# Configuration Architecture

```text
config.json
     │
     ▼
ConfigProvider
   <<interface>>
     │
     ▼
JsonConfigProvider
     │
     ▼
FrameworkConfig
     │
     ▼
ConfigManager

```

ConfigProvider allows the configuration source to be changed in the future without changing the rest of the framework.

# Writing API Tests

Tests extend `BaseTest`.

```java
public class UserTest extends BaseTest {

    @Test
    public void shouldGetUser () {

        final ApiRequest request = ApiRequest.builder ()
            .get ()
            .endpoint ("/users/{id}")
            .pathParams (Map.of ("id", "1"))
            .build ();

        final ApiResponse response = apiRequestContext.execute (request);

        assertThat (response.getStatusCode ()).isEqualTo (200);
    }
}
```

# Google Truth Assertions

Google Truth is used for assertions.

Example:

```java
assertThat (response.getStatusCode())
    .

isEqualTo (200);
```

Additional assertions can be performed against the response:

```java
assertThat (response.getBody())
    .

contains ("John");
```

# Request Examples

## GET

```java
ApiRequest request = ApiRequest.builder ()
    .get ()
    .endpoint ("/users/{id}")
    .pathParams (Map.of ("id", "1"))
    .build ();
```

## POST

```java
ApiRequest request = ApiRequest.builder ()
    .post ()
    .endpoint ("/users")
    .body (user)
    .build ();
```

## PUT

```java
ApiRequest request = ApiRequest.builder ()
    .put ()
    .endpoint ("/users/{id}")
    .pathParams (Map.of ("id", "1"))
    .body (user)
    .build ();
```

## PATCH

```java
ApiRequest request = ApiRequest.builder ()
    .patch ()
    .endpoint ("/users/{id}")
    .pathParams (Map.of ("id", "1"))
    .body (user)
    .build ();
```

## DELETE

```java
ApiRequest request = ApiRequest.builder ()
    .delete ()
    .endpoint ("/users/{id}")
    .pathParams (Map.of ("id", "1"))
    .build ();
```

---

# Headers

Headers can be added using:

```java
ApiRequest request = ApiRequest.builder ()
    .get ()
    .endpoint ("/users")
    .headers (Map.of ("Authorization", "Bearer token", "Accept", "application/json"))
    .build ();
```

---

# Query Parameters

Query parameters can be added using:

```java
ApiRequest request = ApiRequest.builder ()
    .get ()
    .endpoint ("/users")
    .queryParams (Map.of ("page", "1", "limit", "10"))
    .build ();
```

---

# Path Parameters

Path parameters can be added using:

```java
ApiRequest request = ApiRequest.builder ()
    .get ()
    .endpoint ("/users/{id}")
    .pathParams (Map.of ("id", "123"))
    .build ();
```

---

# Running Tests

Run all tests:

```bash
mvn test
```

Run against QA:

```bash
mvn test -Denv=qa
```

Run against Pre-Production:

```bash
mvn test -Denv=preprod
```

---

## Allure Reporting

The framework uses Allure for test reporting.

Allure provides the test execution results Test status Test steps Request/response information Failure information
Execution history

#### Run tests:

```bash
mvn test
```

#### Generate the report:

```bash
allure generate allure-results -o allure-report
```

#### Open the report:

```bash
allure open allure-report
```

# Design Patterns Used

## Builder Pattern

`ApiRequest` uses a fluent builder-style API:

```

ApiRequest.builder ()
.post ()
.endpoint ("/users")
.body (user)
.build ();

```

This makes requests readable and avoids large constructors.

## Strategy / Interface-based Design

`ApiClient` defines the API execution contract:

```java
public interface ApiClient {
    ApiResponse execute (ApiRequest request);
}
```

`RestAssuredClient` provides the implementation.

This allows the underlying HTTP library to be replaced without changing the tests.

## Provider Pattern

Configuration is abstracted using:

```
ConfigProvider
```

with:

```
JsonConfigProvider
```

as the current implementation.

## Context Pattern

`ApiRequestContext` provides a simplified entry point to the framework while hiding configuration and HTTP-client
implementation details.

---

# Framework Responsibilities

The framework is responsible for:

* Configuration
* Environment selection
* HTTP request construction
* HTTP request execution
* Timeout configuration
* Request/response logging
* Response mapping
* Test reporting integration

Tests are responsible for:

* Defining test scenarios
* Providing test data
* Creating `ApiRequest`
* Executing the request
* Validating the `ApiResponse`

---

# Example Test

A complete test should remain concise:

```java
public class UserTest extends BaseTest {

    @Test
    public void shouldGetUser () {

        final ApiRequest request = ApiRequest.builder ()
            .get ()
            .endpoint ("/users/{id}")
            .pathParams (Map.of ("id", "1"))
            .build ();

        final ApiResponse response = apiRequestContext.execute (request);

        assertThat (response.getStatusCode ()).isEqualTo (200);

        assertThat (response.getBody ()).contains ("John");
    }
}
```

The test author does not need to know how:

* the base URL is loaded
* the environment is selected
* Rest-Assured is configured
* the HTTP request is executed
* logging is configured

Those concerns are handled by the framework.

---

# Future Enhancements

The framework can be extended with:

* Request/response logging
* Allure Report Support
* Allure attachments
* Authentication support
* Response schema validation
* JSON test-data reader
* Dynamic test data
* Retry mechanism
* Parallel test execution
* TestNG groups and tags
* API contract testing
* Custom assertion utilities
* Request builders for common API patterns

---

## :question: Need Assistance?

- Discuss your queries by writing to me @ `mohammadfaisalkhatri@gmail.com`
  OR ping me on any of the social media sites using the below link:
    - [Linktree](https://linktr.ee/faisalkhatri)

## :thought_balloon: Checkout the tutorial articles and videos on the following links:

- [Medium Blog](https://medium.com/@iamfaisalkhatri)
- [YouTube Channel](https://www.youtube.com/@faisalkhatriqa)
