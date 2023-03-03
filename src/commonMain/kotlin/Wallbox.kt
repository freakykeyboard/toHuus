import kotlinx.serialization.Serializable

@Serializable
class Wallbox(override var  name: String, override var state: DeviceState) : Device(), Switchable {

    override fun switchOn() {
        state = WallBoxLoading
    }

    override fun switchOff() {
        state = WallBoxNotLoading
    }
}