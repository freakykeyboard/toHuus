import kotlinx.serialization.Serializable

@Serializable
class Blind(override var name: String, override var state: DeviceState) : Device() {
    fun open() {
        state = BlindOpen
    }
    fun close() {
        state = BlindClosed
    }


}