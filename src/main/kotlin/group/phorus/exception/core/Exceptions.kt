package group.phorus.exception.core

/**
 * Base class for all HTTP-mapped exceptions in the Phorus exception hierarchy.
 *
 * Each instance carries a [statusCode] (standard HTTP status code integer) so that any
 * integration layer can translate it to the appropriate HTTP response.
 *
 * Optional fields for richer error responses:
 * - [code]: application-specific error code for programmatic handling (e.g. `"VALIDATION_EMAIL"`)
 * - [source]: identifies the service that produced the error (e.g. `"user-service"`)
 * - [metadata]: arbitrary key-value pairs with extra context about the error
 *
 * When [code] is `null`, the integration layer should fall back to [effectiveCode],
 * which resolves to the reserved [ReservedErrorCodes] constant for the [statusCode]
 * (for example `"BAD_REQUEST"` for status 400, `"NOT_FOUND"` for status 404).
 *
 * This class is `open`, so consumers can create custom subclasses for HTTP statuses not
 * covered by the built-in types (e.g. 402 Payment Required, 423 Locked).
 *
 * @param message Human-readable error description returned to the caller.
 * @param statusCode HTTP status code (e.g. 400, 404, 500).
 * @param code Application-specific error code for programmatic handling.
 * @param source Identifier of the service that produced this error.
 * @param metadata Extra context about the error as key-value pairs.
 */
open class BaseException(
    message: String?,
    val statusCode: Int,
    val code: String? = null,
    val source: String? = null,
    val metadata: Map<String, Any?>? = null,
) : RuntimeException(message) {

    /**
     * Resolved error code that is always non-null.
     *
     * Returns [code] when set, otherwise the reserved fallback for [statusCode] from
     * [ReservedErrorCodes.forStatusCode]. For non-standard status codes (e.g. 402, 423)
     * with no reserved constant, the result is [ReservedErrorCodes.INTERNAL_SERVER_ERROR]
     * for 5xx and [ReservedErrorCodes.BAD_REQUEST] for any other unmapped class. Custom
     * subclasses for non-mapped statuses are encouraged to pass an explicit [code].
     */
    val effectiveCode: String
        get() = code
            ?: ReservedErrorCodes.forStatusCode(statusCode)
            ?: if (statusCode in 500..599) ReservedErrorCodes.INTERNAL_SERVER_ERROR
            else ReservedErrorCodes.BAD_REQUEST
}

/** Returns HTTP 400 Bad Request */
class BadRequest(message: String?, code: String? = null, source: String? = null, metadata: Map<String, Any?>? = null) : BaseException(message, 400, code, source, metadata)

/** Returns HTTP 409 Conflict */
class Conflict(message: String?, code: String? = null, source: String? = null, metadata: Map<String, Any?>? = null) : BaseException(message, 409, code, source, metadata)

/** Returns HTTP 404 Not Found */
class NotFound(message: String?, code: String? = null, source: String? = null, metadata: Map<String, Any?>? = null) : BaseException(message, 404, code, source, metadata)

/** Returns HTTP 401 Unauthorized */
class Unauthorized(message: String?, code: String? = null, source: String? = null, metadata: Map<String, Any?>? = null) : BaseException(message, 401, code, source, metadata)

/** Returns HTTP 403 Forbidden */
class Forbidden(message: String?, code: String? = null, source: String? = null, metadata: Map<String, Any?>? = null) : BaseException(message, 403, code, source, metadata)

/** Returns HTTP 408 Request Timeout */
class RequestTimeout(message: String?, code: String? = null, source: String? = null, metadata: Map<String, Any?>? = null) : BaseException(message, 408, code, source, metadata)

/** Returns HTTP 500 Internal Server Error */
class InternalServerError(message: String?, code: String? = null, source: String? = null, metadata: Map<String, Any?>? = null) : BaseException(message, 500, code, source, metadata)

/** Returns HTTP 405 Method Not Allowed */
class MethodNotAllowed(message: String?, code: String? = null, source: String? = null, metadata: Map<String, Any?>? = null) : BaseException(message, 405, code, source, metadata)

/** Returns HTTP 429 Too Many Requests */
class TooManyRequests(message: String?, code: String? = null, source: String? = null, metadata: Map<String, Any?>? = null) : BaseException(message, 429, code, source, metadata)

/** Returns HTTP 503 Service Unavailable */
class ServiceUnavailable(message: String?, code: String? = null, source: String? = null, metadata: Map<String, Any?>? = null) : BaseException(message, 503, code, source, metadata)

/** Returns HTTP 502 Bad Gateway */
class BadGateway(message: String?, code: String? = null, source: String? = null, metadata: Map<String, Any?>? = null) : BaseException(message, 502, code, source, metadata)

/** Returns HTTP 504 Gateway Timeout */
class GatewayTimeout(message: String?, code: String? = null, source: String? = null, metadata: Map<String, Any?>? = null) : BaseException(message, 504, code, source, metadata)

/** Returns HTTP 422 Unprocessable Content (RFC 9110) */
class UnprocessableEntity(message: String?, code: String? = null, source: String? = null, metadata: Map<String, Any?>? = null) : BaseException(message, 422, code, source, metadata)

/** Returns HTTP 410 Gone */
class Gone(message: String?, code: String? = null, source: String? = null, metadata: Map<String, Any?>? = null) : BaseException(message, 410, code, source, metadata)

/** Returns HTTP 412 Precondition Failed */
class PreconditionFailed(message: String?, code: String? = null, source: String? = null, metadata: Map<String, Any?>? = null) : BaseException(message, 412, code, source, metadata)

/** Returns HTTP 415 Unsupported Media Type */
class UnsupportedMediaType(message: String?, code: String? = null, source: String? = null, metadata: Map<String, Any?>? = null) : BaseException(message, 415, code, source, metadata)
