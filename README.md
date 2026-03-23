# exception-core

[![GitHub license](https://img.shields.io/badge/license-Apache%20License%202.0-blue.svg?style=flat)](https://www.apache.org/licenses/LICENSE-2.0)
[![Maven Central](https://img.shields.io/maven-central/v/group.phorus/exception-core)](https://mvnrepository.com/artifact/group.phorus/exception-core)

Exception hierarchy with HTTP status codes and optional error codes for Kotlin/JVM.

### Notes

> The project runs a vulnerability analysis pipeline regularly,
> any found vulnerabilities will be fixed as soon as possible.

> The project dependencies are being regularly updated by [Renovate](https://github.com/phorus-group/renovate).
> Dependency updates that don't break tests will be automatically deployed with an updated patch version.

---

## Table of contents

- [What this library is](#what-this-library-is)
- [Getting started](#getting-started)
- [Exception classes](#exception-classes)
- [Error codes](#error-codes)
- [Source and metadata](#source-and-metadata)
- [Custom exception classes](#custom-exception-classes)
- [Building and contributing](#building-and-contributing)

---

## What this library is

`exception-core` provides a `BaseException` class and 16 concrete subclasses, each carrying
an integer HTTP status code and an optional error code. Throw them from any layer of your
application: controllers, services, message consumers, CLI commands, etc.

## Getting started

Make sure `mavenCentral()` is in your repository list.

<details open>
<summary>Gradle / Kotlin DSL</summary>

```kotlin
implementation("group.phorus:exception-core:1.0.0")
```
</details>

<details open>
<summary>Maven</summary>

```xml
<dependency>
    <groupId>group.phorus</groupId>
    <artifactId>exception-core</artifactId>
    <version>1.0.1</version>
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
// Without code (defaults to null)
throw BadRequest("Invalid input")

// With code
throw BadRequest("Email format is invalid", code = "VALIDATION_EMAIL")
throw BadRequest("Name exceeds 255 characters", code = "VALIDATION_NAME_LENGTH")
throw Conflict("Email already exists", code = "USER_DUPLICATE_EMAIL")
throw Forbidden("Trial expired", code = "PLAN_TRIAL_EXPIRED")
```

`code` defaults to `null`.

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

## Building and contributing

See [Contributing Guidelines](CONTRIBUTING.md).

## Authors and acknowledgment

Developed and maintained by the [Phorus Group](https://phorus.group) team.
