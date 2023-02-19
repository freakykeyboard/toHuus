import kotlinx.serialization.Serializable

@Serializable
data class Lamp(val id: String? = null, var name: String, var isOn: Boolean = false, var userId: String? = null) {


    companion object {
        const val path = "/api/lamp"
    }


}
