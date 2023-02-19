import kotlinx.serialization.Serializable

@Serializable
data class Blind(val id: String? = null, var name: String, var position: Byte? = 0, val userId: String? = null) {
    companion object {
        const val path = "/api/blind"
    }
}
