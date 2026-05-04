package group.phorus.exception.core

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.memberProperties

class ErrorCodeTest {

    private data class SampleDto(
        @field:ErrorCode("USER_NAME_BLANK")
        val name: String? = null,
        val noCodeField: String? = null,
        @property:ErrorCode("PROPERTY_LEVEL_CODE")
        val propertyLevel: String? = null,
    )

    @Test
    fun `annotation has runtime retention`() {
        val retention = ErrorCode::class.annotations.filterIsInstance<Retention>().single()
        assertEquals(AnnotationRetention.RUNTIME, retention.value)
    }

    @Test
    fun `annotation targets field property and value parameter`() {
        val target = ErrorCode::class.annotations.filterIsInstance<Target>().single()
        assertTrue(target.allowedTargets.contains(AnnotationTarget.FIELD))
        assertTrue(target.allowedTargets.contains(AnnotationTarget.PROPERTY))
        assertTrue(target.allowedTargets.contains(AnnotationTarget.VALUE_PARAMETER))
    }

    @Test
    fun `annotation at property target is readable via kotlin reflection`() {
        val property = SampleDto::class.memberProperties.first { it.name == "propertyLevel" }
        val annotation = property.findAnnotation<ErrorCode>()
        assertNotNull(annotation)
        assertEquals("PROPERTY_LEVEL_CODE", annotation!!.value)
    }

    @Test
    fun `annotation is readable via java reflection on the backing field`() {
        val field = SampleDto::class.java.getDeclaredField("name")
        val annotation = field.getAnnotation(ErrorCode::class.java)
        assertNotNull(annotation)
        assertEquals("USER_NAME_BLANK", annotation.value)
    }

    @Test
    fun `field without ErrorCode returns null`() {
        val field = SampleDto::class.java.getDeclaredField("noCodeField")
        assertNull(field.getAnnotation(ErrorCode::class.java))
    }

    @Test
    fun `value carries the supplied string`() {
        val annotation = ErrorCode("ANY_CODE")
        assertEquals("ANY_CODE", annotation.value)
    }
}
