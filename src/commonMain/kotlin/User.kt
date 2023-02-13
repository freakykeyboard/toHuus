import kotlinx.serialization.Serializable
@Serializable
data class User(var userId: String? = null, var username: String, var password: String) {
    companion object {
        const val path = "/api/users"
    }
}