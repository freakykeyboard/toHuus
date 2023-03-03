import kotlinx.serialization.Serializable
@Serializable
sealed class Device () {
    abstract val name: String
    abstract var state: DeviceState

}