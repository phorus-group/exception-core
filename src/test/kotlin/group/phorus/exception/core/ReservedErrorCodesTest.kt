package group.phorus.exception.core

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class ReservedErrorCodesTest {

    @Test
    fun `meta codes carry the documented constants`() {
        assertEquals("VALIDATION_FAILED", ReservedErrorCodes.VALIDATION_FAILED)
        assertEquals("INTERNAL_SERVER_ERROR", ReservedErrorCodes.INTERNAL_SERVER_ERROR)
    }

    @Test
    fun `http class fallback constants match HTTP names`() {
        assertEquals("BAD_REQUEST", ReservedErrorCodes.BAD_REQUEST)
        assertEquals("UNAUTHORIZED", ReservedErrorCodes.UNAUTHORIZED)
        assertEquals("FORBIDDEN", ReservedErrorCodes.FORBIDDEN)
        assertEquals("NOT_FOUND", ReservedErrorCodes.NOT_FOUND)
        assertEquals("METHOD_NOT_ALLOWED", ReservedErrorCodes.METHOD_NOT_ALLOWED)
        assertEquals("REQUEST_TIMEOUT", ReservedErrorCodes.REQUEST_TIMEOUT)
        assertEquals("CONFLICT", ReservedErrorCodes.CONFLICT)
        assertEquals("GONE", ReservedErrorCodes.GONE)
        assertEquals("PRECONDITION_FAILED", ReservedErrorCodes.PRECONDITION_FAILED)
        assertEquals("UNSUPPORTED_MEDIA_TYPE", ReservedErrorCodes.UNSUPPORTED_MEDIA_TYPE)
        assertEquals("UNPROCESSABLE_ENTITY", ReservedErrorCodes.UNPROCESSABLE_ENTITY)
        assertEquals("TOO_MANY_REQUESTS", ReservedErrorCodes.TOO_MANY_REQUESTS)
        assertEquals("BAD_GATEWAY", ReservedErrorCodes.BAD_GATEWAY)
        assertEquals("SERVICE_UNAVAILABLE", ReservedErrorCodes.SERVICE_UNAVAILABLE)
        assertEquals("GATEWAY_TIMEOUT", ReservedErrorCodes.GATEWAY_TIMEOUT)
    }

    @Test
    fun `constraint-derived constants carry the documented strings`() {
        assertEquals("REQUIRED", ReservedErrorCodes.REQUIRED)
        assertEquals("BLANK", ReservedErrorCodes.BLANK)
        assertEquals("MUST_BE_NULL", ReservedErrorCodes.MUST_BE_NULL)
        assertEquals("TOO_SHORT", ReservedErrorCodes.TOO_SHORT)
        assertEquals("TOO_LONG", ReservedErrorCodes.TOO_LONG)
        assertEquals("TOO_SMALL", ReservedErrorCodes.TOO_SMALL)
        assertEquals("TOO_LARGE", ReservedErrorCodes.TOO_LARGE)
        assertEquals("MUST_BE_POSITIVE", ReservedErrorCodes.MUST_BE_POSITIVE)
        assertEquals("MUST_BE_POSITIVE_OR_ZERO", ReservedErrorCodes.MUST_BE_POSITIVE_OR_ZERO)
        assertEquals("MUST_BE_NEGATIVE", ReservedErrorCodes.MUST_BE_NEGATIVE)
        assertEquals("MUST_BE_NEGATIVE_OR_ZERO", ReservedErrorCodes.MUST_BE_NEGATIVE_OR_ZERO)
        assertEquals("INVALID_NUMBER_FORMAT", ReservedErrorCodes.INVALID_NUMBER_FORMAT)
        assertEquals("INVALID_FORMAT", ReservedErrorCodes.INVALID_FORMAT)
        assertEquals("INVALID_EMAIL", ReservedErrorCodes.INVALID_EMAIL)
        assertEquals("MUST_BE_PAST", ReservedErrorCodes.MUST_BE_PAST)
        assertEquals("MUST_BE_PAST_OR_PRESENT", ReservedErrorCodes.MUST_BE_PAST_OR_PRESENT)
        assertEquals("MUST_BE_FUTURE", ReservedErrorCodes.MUST_BE_FUTURE)
        assertEquals("MUST_BE_FUTURE_OR_PRESENT", ReservedErrorCodes.MUST_BE_FUTURE_OR_PRESENT)
        assertEquals("MUST_BE_TRUE", ReservedErrorCodes.MUST_BE_TRUE)
        assertEquals("MUST_BE_FALSE", ReservedErrorCodes.MUST_BE_FALSE)
    }

    @Test
    fun `forStatusCode returns the matching reserved constant`() {
        assertEquals("BAD_REQUEST", ReservedErrorCodes.forStatusCode(400))
        assertEquals("UNAUTHORIZED", ReservedErrorCodes.forStatusCode(401))
        assertEquals("FORBIDDEN", ReservedErrorCodes.forStatusCode(403))
        assertEquals("NOT_FOUND", ReservedErrorCodes.forStatusCode(404))
        assertEquals("METHOD_NOT_ALLOWED", ReservedErrorCodes.forStatusCode(405))
        assertEquals("REQUEST_TIMEOUT", ReservedErrorCodes.forStatusCode(408))
        assertEquals("CONFLICT", ReservedErrorCodes.forStatusCode(409))
        assertEquals("GONE", ReservedErrorCodes.forStatusCode(410))
        assertEquals("PRECONDITION_FAILED", ReservedErrorCodes.forStatusCode(412))
        assertEquals("UNSUPPORTED_MEDIA_TYPE", ReservedErrorCodes.forStatusCode(415))
        assertEquals("UNPROCESSABLE_ENTITY", ReservedErrorCodes.forStatusCode(422))
        assertEquals("TOO_MANY_REQUESTS", ReservedErrorCodes.forStatusCode(429))
        assertEquals("INTERNAL_SERVER_ERROR", ReservedErrorCodes.forStatusCode(500))
        assertEquals("BAD_GATEWAY", ReservedErrorCodes.forStatusCode(502))
        assertEquals("SERVICE_UNAVAILABLE", ReservedErrorCodes.forStatusCode(503))
        assertEquals("GATEWAY_TIMEOUT", ReservedErrorCodes.forStatusCode(504))
    }

    @Test
    fun `forStatusCode returns null for unmapped status codes`() {
        assertNull(ReservedErrorCodes.forStatusCode(402))
        assertNull(ReservedErrorCodes.forStatusCode(423))
        assertNull(ReservedErrorCodes.forStatusCode(418))
        assertNull(ReservedErrorCodes.forStatusCode(200))
    }
}
