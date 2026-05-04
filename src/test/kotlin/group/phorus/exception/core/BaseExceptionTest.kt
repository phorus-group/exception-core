package group.phorus.exception.core

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class BaseExceptionTest {

    @Test
    fun `code defaults to null`() {
        val ex = BadRequest("error")
        assertNull(ex.code)
    }

    @Test
    fun `effectiveCode falls back to the reserved constant for the status`() {
        assertEquals("BAD_REQUEST", BadRequest("x").effectiveCode)
        assertEquals("NOT_FOUND", NotFound("x").effectiveCode)
        assertEquals("CONFLICT", Conflict("x").effectiveCode)
        assertEquals("UNAUTHORIZED", Unauthorized("x").effectiveCode)
        assertEquals("FORBIDDEN", Forbidden("x").effectiveCode)
        assertEquals("REQUEST_TIMEOUT", RequestTimeout("x").effectiveCode)
        assertEquals("INTERNAL_SERVER_ERROR", InternalServerError("x").effectiveCode)
        assertEquals("METHOD_NOT_ALLOWED", MethodNotAllowed("x").effectiveCode)
        assertEquals("TOO_MANY_REQUESTS", TooManyRequests("x").effectiveCode)
        assertEquals("SERVICE_UNAVAILABLE", ServiceUnavailable("x").effectiveCode)
        assertEquals("BAD_GATEWAY", BadGateway("x").effectiveCode)
        assertEquals("GATEWAY_TIMEOUT", GatewayTimeout("x").effectiveCode)
        assertEquals("UNPROCESSABLE_ENTITY", UnprocessableEntity("x").effectiveCode)
        assertEquals("GONE", Gone("x").effectiveCode)
        assertEquals("PRECONDITION_FAILED", PreconditionFailed("x").effectiveCode)
        assertEquals("UNSUPPORTED_MEDIA_TYPE", UnsupportedMediaType("x").effectiveCode)
    }

    @Test
    fun `effectiveCode returns the explicit code when supplied`() {
        val ex = BadRequest("x", code = "USER_NAME_TAKEN")
        assertEquals("USER_NAME_TAKEN", ex.effectiveCode)
        assertEquals("USER_NAME_TAKEN", ex.code)
    }

    @Test
    fun `custom subclass for a 5xx status falls back to INTERNAL_SERVER_ERROR`() {
        class CustomFiveOhEight(message: String?) : BaseException(message, 508)
        assertEquals("INTERNAL_SERVER_ERROR", CustomFiveOhEight("x").effectiveCode)
    }

    @Test
    fun `custom subclass for a 4xx status without a reserved constant falls back to BAD_REQUEST`() {
        class PaymentRequired(message: String?) : BaseException(message, 402)
        assertEquals("BAD_REQUEST", PaymentRequired("x").effectiveCode)
    }

    @Test
    fun `custom subclass with explicit code keeps the explicit code`() {
        class PaymentRequired(message: String?, code: String?) : BaseException(message, 402, code)
        assertEquals("SUBSCRIPTION_EXPIRED", PaymentRequired("x", "SUBSCRIPTION_EXPIRED").effectiveCode)
    }
}
