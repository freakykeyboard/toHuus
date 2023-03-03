import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
class AutomationScene(val name: String, private val actions: List<Action>, val executionTime: ExecutionTime) {
    @Serializable
    sealed class ExecutionTime {
        @Serializable
        data class At(val time: LocalTime) : ExecutionTime()

        @Serializable
        data class AtRandomOffset(
            val time: LocalTime,
            val offset: Int? = 0,
            val lowerBoundary: Int,
            val upperBoundary: Int
        ) : ExecutionTime()

        @Serializable
        data class AtSunOffset(val time: LocalTime, val offset: Int) : ExecutionTime()

        @Serializable
        data class AtSunRandomOffset(
            val time: LocalTime,
            var offset: Int? = null,
            val lowerBoundary: Int,
            val upperBoundary: Int
        ) : ExecutionTime()

    }

    fun activate(devices: List<Device>) {
        actions.forEach { action ->
            val device = devices.find { it.name == action.deviceName }
            if (device is Lamp && action is LampAction) {
                when (action.state) {
                    is LampOn -> device.switchOn()
                    is LampOff -> device.switchOff()
                }
            }else  if (device is Wallbox && action is WallboxAction) {
                when (action.state) {
                    is WallBoxLoading -> device.switchOn()
                    is WallBoxNotLoading -> device.switchOff()
                }

            }

            else if (device is Blind && action is BlindAction) {
                when (action.state) {
                    is BlindOpen -> device.open()
                    is BlindClosed -> device.close()
                }
            }

        }
    }
}
