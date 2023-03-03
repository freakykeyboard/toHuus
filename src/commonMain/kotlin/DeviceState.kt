import kotlinx.serialization.Serializable

@Serializable
sealed class DeviceState
@Serializable
sealed class LampState : DeviceState()
@Serializable
object LampOn : LampState()
@Serializable
object LampOff : LampState()
@Serializable
sealed class BlindState():DeviceState()
@Serializable
object BlindOpen : BlindState()
@Serializable
object BlindClosed : BlindState()
@Serializable
sealed class WallBoxState : DeviceState()
@Serializable
object WallBoxLoading : WallBoxState()
@Serializable
object WallBoxNotLoading : WallBoxState()

