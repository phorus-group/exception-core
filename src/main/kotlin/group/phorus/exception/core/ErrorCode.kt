package group.phorus.exception.core

/**
 * Marks a property, field, or value parameter with an application-specific error code.
 *
 * Example:
 * ```kotlin
 * data class CreateUserRequest(
 *     @field:ErrorCode("USER_NAME_BLANK")
 *     val name: String?,
 * )
 * ```
 *
 * @property value the application-specific error code.
 */
@Target(
    AnnotationTarget.FIELD,
    AnnotationTarget.PROPERTY,
    AnnotationTarget.VALUE_PARAMETER,
)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class ErrorCode(val value: String)
