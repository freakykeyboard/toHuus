import kotlinx.serialization.Serializable

@Serializable
sealed class Device {
    abstract val id: String?
    abstract val name: String
    abstract val userId: String?
}