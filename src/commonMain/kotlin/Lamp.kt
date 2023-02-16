import kotlinx.serialization.Serializable

@Serializable
data class Lamp(val id: String = "", val name: String, val isOn: Boolean = false) {
    var userId: String? = null

    companion object {
        const val path = "/api/lamp"
    }


}
