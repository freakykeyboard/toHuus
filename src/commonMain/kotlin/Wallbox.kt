import kotlinx.serialization.Serializable

@Serializable
class Wallbox(
    override val id: String? = null,
    override var name: String,
    override val userId: String? = null,
    var charging: Boolean = false,
    var isConnected: Boolean = false,

    ) : Device() {
    companion object {
        const val path = "/api/wallbox"
    }

    fun switch() {
        charging = !charging
    }

    fun connect() {
        isConnected = true
    }

    fun disconnect() {
        isConnected = false
    }
}
