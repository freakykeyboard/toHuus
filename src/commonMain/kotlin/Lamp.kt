import kotlinx.serialization.Serializable

@Serializable
class Lamp(

    override var name: String,
    override var state: DeviceState,

    ) :Switchable, Device()  {
    override fun switchOn() {
        state = LampOn
    }

    override fun switchOff() {
        state = LampOff
    }


}
