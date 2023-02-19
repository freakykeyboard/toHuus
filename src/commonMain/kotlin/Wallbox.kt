import kotlinx.serialization.Serializable

@Serializable
data class Wallbox(
    val id: String? = null,
    var name: String,
    var charging: Boolean = false,
    var isConnected: Boolean = false,
    var userId: String? = null
) {
    companion object {
        const val path = "/api/wallbox"
    }
}
