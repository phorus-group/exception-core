# exception-core

[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/group.phorus/exception-core)](https://mvnrepository.com/artifact/group.phorus/exception-core)

Exception hierarchy with HTTP status codes and error codes for Kotlin/JVM.

### Notes

> The project runs a vulnerability analysis pipeline regularly,
> any found vulnerabilities will be fixed as soon as possible.

> The project dependencies are being regularly updated by [Renovate](https://github.com/phorus-group/renovate).

---

## Table of contents

- [What this library is](#what-this-library-is)
- [Getting started](#getting-started)
- [Exception classes](#exception-classes)
- [Error codes](#error-codes)
- [Reserved error codes](#reserved-error-codes)
- [Source and metadata](#source-and-metadata)
- [Custom exception classes](#custom-exception-classes)
- [Building and contributing](#building-and-contributing)

---

## What this library is

`exception-core` provides a `BaseException` class and 16 concrete subclasses, each carrying
an integer HTTP status code and an optional error code. Throw them from any layer of your
application: controllers, services, message consumers, CLI commands, etc.

The library also exposes the `ReservedErrorCodes` object and the `effectiveCode` property
on `BaseException`. See the sections below.

## Getting started

Make sure `mavenCentral()` is in your repository list.

<details open>
<summary>Gradle / Kotlin DSL</summary>

```kotlin
implementation("group.phorus:exception-core:x.y.z")
```
</details>

<details open>
<summary>Maven</summary>

```xml
<dependency>
    <groupId>group.phorus</groupId>
    <artifactId>exception-core</artifactId>
    <version>x.y.z</version>
</dependency>
```
</details>

## Exception classes

Every subclass carries a `statusCode: Int` that maps to the corresponding HTTP status code.

```kotlin
throw BadRequest("Invalid input")           // 400
throw Unauthorized("Token expired")         // 401
throw Forbidden("Insufficient privileges")  // 403
throw NotFound("User not found")            // 404
throw MethodNotAllowed("Use POST")          // 405
throw RequestTimeout("Upstream timeout")    // 408
throw Conflict("Email already exists")      // 409
throw Gone("Resource deleted")              // 410
throw PreconditionFailed("ETag mismatch")   // 412
throw UnsupportedMediaType("Use JSON")      // 415
throw UnprocessableEntity("Invalid data")   // 422
throw TooManyRequests("Rate limited")       // 429
throw InternalServerError("Unexpected")     // 500
throw BadGateway("Upstream error")          // 502
throw ServiceUnavailable("Maintenance")     // 503
throw GatewayTimeout("Upstream timeout")    // 504
```

All extend `BaseException(message, statusCode)` which extends `RuntimeException`. They can be
caught with a single `catch (e: BaseException)` block:

```kotlin
val response = try {
    doSomething()
} catch (e: BaseException) {
    handleError(e.statusCode, e.message)
}
```

## Error codes

Every exception accepts an optional `code: String?` parameter for programmatic error
identification. Callers can use it to distinguish specific error conditions within the same
HTTP status:

```kotlin
// Without explicit code (defaults to null)
throw BadRequest("Invalid input")

// With explicit code
throw BadRequest("Email format is invalid", code = "VALIDATION_EMAIL")
throw BadRequest("Name exceeds 255 characters", code = "VALIDATION_NAME_LENGTH")
throw Conflict("Email already exists", code = "USER_DUPLICATE_EMAIL")
throw Forbidden("Trial expired", code = "PLAN_TRIAL_EXPIRED")
```

The `code` argument defaults to `null`. Independently, every exception exposes an
`effectiveCode` property that is always non-null. When `code` is supplied, `effectiveCode`
returns it. When `code` is `null`, `effectiveCode` returns the reserved constant for the
exception's HTTP status (see below).

```kotlin
val ex = BadRequest("error")
ex.code           // null
ex.effectiveCode  // "BAD_REQUEST"

val ex2 = BadRequest("Email format is invalid", code = "VALIDATION_EMAIL")
ex2.code           // "VALIDATION_EMAIL"
ex2.effectiveCode  // "VALIDATION_EMAIL"
```

Callers choose which property to read. `code` preserves the explicit-or-absent
distinction. `effectiveCode` is convenient when a non-null string is always required.

## Reserved error codes

The `ReservedErrorCodes` object exposes a canonical list of constants. It groups them in
three categories.

**Meta codes**

| Constant | Notes |
|---|---|
| `VALIDATION_FAILED` | Code for validation failures. |
| `INTERNAL_SERVER_ERROR` | Fallback for any 5xx status without a specific reserved constant. |

**HTTP class fallbacks**

One per HTTP status covered by the built-in `BaseException` subclasses. Returned by
`BaseException.effectiveCode` when the exception is thrown without an explicit `code`
argument.

| HTTP status | Reserved constant |
|---|---|
| 400 | `BAD_REQUEST` |
| 401 | `UNAUTHORIZED` |
| 403 | `FORBIDDEN` |
| 404 | `NOT_FOUND` |
| 405 | `METHOD_NOT_ALLOWED` |
| 408 | `REQUEST_TIMEOUT` |
| 409 | `CONFLICT` |
| 410 | `GONE` |
| 412 | `PRECONDITION_FAILED` |
| 415 | `UNSUPPORTED_MEDIA_TYPE` |
| 422 | `UNPROCESSABLE_ENTITY` |
| 429 | `TOO_MANY_REQUESTS` |
| 500 | `INTERNAL_SERVER_ERROR` |
| 502 | `BAD_GATEWAY` |
| 503 | `SERVICE_UNAVAILABLE` |
| 504 | `GATEWAY_TIMEOUT` |

**Constraint-derived codes**

One per Jakarta Bean Validation constraint annotation, plus directional codes for
constraints with `min` and `max` sides. The constants are pure strings, exception-core
itself does not depend on Jakarta validation.

| Constant | Mirrors |
|---|---|
| `REQUIRED` | `@NotNull`, `@NotEmpty` |
| `BLANK` | `@NotBlank` |
| `MUST_BE_NULL` | `@Null` |
| `TOO_SHORT` | `@Size`, `@Length` (Hibernate) when below `min` |
| `TOO_LONG` | `@Size`, `@Length` (Hibernate) when above `max` |
| `TOO_SMALL` | `@Min`, `@DecimalMin`, `@Range` (Hibernate) when below `min` |
| `TOO_LARGE` | `@Max`, `@DecimalMax`, `@Range` (Hibernate) when above `max` |
| `MUST_BE_POSITIVE` | `@Positive` |
| `MUST_BE_POSITIVE_OR_ZERO` | `@PositiveOrZero` |
| `MUST_BE_NEGATIVE` | `@Negative` |
| `MUST_BE_NEGATIVE_OR_ZERO` | `@NegativeOrZero` |
| `INVALID_NUMBER_FORMAT` | `@Digits` |
| `INVALID_FORMAT` | `@Pattern` |
| `INVALID_EMAIL` | `@Email` |
| `MUST_BE_PAST` | `@Past` |
| `MUST_BE_PAST_OR_PRESENT` | `@PastOrPresent` |
| `MUST_BE_FUTURE` | `@Future` |
| `MUST_BE_FUTURE_OR_PRESENT` | `@FutureOrPresent` |
| `MUST_BE_TRUE` | `@AssertTrue` |
| `MUST_BE_FALSE` | `@AssertFalse` |

`ReservedErrorCodes.forStatusCode(statusCode)` returns the matching HTTP-class fallback
constant for a given HTTP status, or `null` for unmapped statuses such as 402 or 423.

```kotlin
ReservedErrorCodes.forStatusCode(404)  // "NOT_FOUND"
ReservedErrorCodes.forStatusCode(402)  // null
```

## Source and metadata

Two additional optional parameters provide context in microservice architectures:

- **`source`**: identifies the service that produced the error (e.g. `"user-service"`)
- **`metadata`**: arbitrary key-value pairs with extra context

```kotlin
throw NotFound(
    "User not found",
    code = "USER_NOT_FOUND",
    source = "user-service",
    metadata = mapOf("userId" to requestedId),
)
```

Both default to `null`.

## Custom exception classes

`BaseException` is `open`, so you can create custom subclasses for HTTP statuses not covered
by the 16 built-in types:

```kotlin
class PaymentRequired(message: String?, code: String? = null) :
    BaseException(message, 402, code)

class Locked(message: String?, code: String? = null) :
    BaseException(message, 423, code)

// Usage
throw PaymentRequired("Subscription expired", code = "SUBSCRIPTION_EXPIRED")
throw Locked("Resource is being edited by another user")
```

For statuses that have no reserved constant, prefer passing an
explicit `code` argument. When the code is omitted, `effectiveCode` resolves to
`INTERNAL_SERVER_ERROR` for any 5xx status and `BAD_REQUEST` for any other unmapped class.

## Building and contributing

See [Contributing Guidelines](CONTRIBUTING.md).

## Authors and acknowledgment

Developed and maintained by the [Phorus Group](https://phorus.group) team.
