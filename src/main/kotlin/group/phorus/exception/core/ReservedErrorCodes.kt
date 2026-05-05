package group.phorus.exception.core

/**
 * Reserved error code constants.
 *
 * The HTTP class fallback constants ([BAD_REQUEST], [NOT_FOUND], [CONFLICT], etc.) match
 * the [BaseException] subclass names and are returned by [BaseException.effectiveCode]
 * when an exception is thrown without an explicit `code = "..."` argument.
 *
 * The constraint-derived constants ([REQUIRED], [BLANK], [TOO_SHORT], [TOO_LONG], etc.)
 * mirror the Jakarta Bean Validation constraint annotation names. The mapping is
 * documented on each constant.
 *
 * [VALIDATION_FAILED] and [INTERNAL_SERVER_ERROR] do not map to a single HTTP status.
 * [INTERNAL_SERVER_ERROR] is also reused as the fallback for any 5xx status without a
 * more specific reserved constant.
 */
object ReservedErrorCodes {
    // Meta codes
    const val VALIDATION_FAILED: String = "VALIDATION_FAILED"
    const val INTERNAL_SERVER_ERROR: String = "INTERNAL_SERVER_ERROR"

    // HTTP class fallbacks
    const val BAD_REQUEST: String = "BAD_REQUEST"
    const val UNAUTHORIZED: String = "UNAUTHORIZED"
    const val FORBIDDEN: String = "FORBIDDEN"
    const val NOT_FOUND: String = "NOT_FOUND"
    const val METHOD_NOT_ALLOWED: String = "METHOD_NOT_ALLOWED"
    const val REQUEST_TIMEOUT: String = "REQUEST_TIMEOUT"
    const val CONFLICT: String = "CONFLICT"
    const val GONE: String = "GONE"
    const val PRECONDITION_FAILED: String = "PRECONDITION_FAILED"
    const val UNSUPPORTED_MEDIA_TYPE: String = "UNSUPPORTED_MEDIA_TYPE"
    const val UNPROCESSABLE_ENTITY: String = "UNPROCESSABLE_ENTITY"
    const val TOO_MANY_REQUESTS: String = "TOO_MANY_REQUESTS"
    const val BAD_GATEWAY: String = "BAD_GATEWAY"
    const val SERVICE_UNAVAILABLE: String = "SERVICE_UNAVAILABLE"
    const val GATEWAY_TIMEOUT: String = "GATEWAY_TIMEOUT"

    // Constraint-derived codes (Jakarta Bean Validation)

    /** Mirrors `@NotNull` and `@NotEmpty`. */
    const val REQUIRED: String = "REQUIRED"

    /** Mirrors `@NotBlank`. */
    const val BLANK: String = "BLANK"

    /** Mirrors `@Null`. */
    const val MUST_BE_NULL: String = "MUST_BE_NULL"

    /** Mirrors the lower-bound side of `@Size` and `@Length`. */
    const val TOO_SHORT: String = "TOO_SHORT"

    /** Mirrors the upper-bound side of `@Size` and `@Length`. */
    const val TOO_LONG: String = "TOO_LONG"

    /** Mirrors `@Min`, `@DecimalMin`, and the lower-bound side of `@Range` (Hibernate Validator). */
    const val TOO_SMALL: String = "TOO_SMALL"

    /** Mirrors `@Max`, `@DecimalMax`, and the upper-bound side of `@Range` (Hibernate Validator). */
    const val TOO_LARGE: String = "TOO_LARGE"

    /** Mirrors `@Positive`. */
    const val MUST_BE_POSITIVE: String = "MUST_BE_POSITIVE"

    /** Mirrors `@PositiveOrZero`. */
    const val MUST_BE_POSITIVE_OR_ZERO: String = "MUST_BE_POSITIVE_OR_ZERO"

    /** Mirrors `@Negative`. */
    const val MUST_BE_NEGATIVE: String = "MUST_BE_NEGATIVE"

    /** Mirrors `@NegativeOrZero`. */
    const val MUST_BE_NEGATIVE_OR_ZERO: String = "MUST_BE_NEGATIVE_OR_ZERO"

    /** Mirrors `@Digits`. */
    const val INVALID_NUMBER_FORMAT: String = "INVALID_NUMBER_FORMAT"

    /** Mirrors `@Pattern`. */
    const val INVALID_FORMAT: String = "INVALID_FORMAT"

    /** Mirrors `@Email`. */
    const val INVALID_EMAIL: String = "INVALID_EMAIL"

    /** Mirrors `@Past`. */
    const val MUST_BE_PAST: String = "MUST_BE_PAST"

    /** Mirrors `@PastOrPresent`. */
    const val MUST_BE_PAST_OR_PRESENT: String = "MUST_BE_PAST_OR_PRESENT"

    /** Mirrors `@Future`. */
    const val MUST_BE_FUTURE: String = "MUST_BE_FUTURE"

    /** Mirrors `@FutureOrPresent`. */
    const val MUST_BE_FUTURE_OR_PRESENT: String = "MUST_BE_FUTURE_OR_PRESENT"

    /** Mirrors `@AssertTrue`. */
    const val MUST_BE_TRUE: String = "MUST_BE_TRUE"

    /** Mirrors `@AssertFalse`. */
    const val MUST_BE_FALSE: String = "MUST_BE_FALSE"

    /**
     * Returns the reserved fallback code for the given HTTP status code, or `null` when no
     * reserved constant applies (e.g. 402 Payment Required, 423 Locked).
     */
    fun forStatusCode(statusCode: Int): String? = when (statusCode) {
        400 -> BAD_REQUEST
        401 -> UNAUTHORIZED
        403 -> FORBIDDEN
        404 -> NOT_FOUND
        405 -> METHOD_NOT_ALLOWED
        408 -> REQUEST_TIMEOUT
        409 -> CONFLICT
        410 -> GONE
        412 -> PRECONDITION_FAILED
        415 -> UNSUPPORTED_MEDIA_TYPE
        422 -> UNPROCESSABLE_ENTITY
        429 -> TOO_MANY_REQUESTS
        500 -> INTERNAL_SERVER_ERROR
        502 -> BAD_GATEWAY
        503 -> SERVICE_UNAVAILABLE
        504 -> GATEWAY_TIMEOUT
        else -> null
    }
}
