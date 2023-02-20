import kotlinx.serialization.Serializable

@Serializable
class Lamp(
    override val id: String? = null,
    override val name: String,
    override val userId: String? = null,
    var isOn: Boolean = false
) : Device() {
    companion object {
        const val path = "/api/lamp"
    }

    fun switch() {
        isOn = !isOn
    }


}
