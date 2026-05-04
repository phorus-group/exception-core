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
