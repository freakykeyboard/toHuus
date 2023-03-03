import kotlinx.serialization.Serializable

@Serializable
sealed class Action {
    abstract val deviceName: String
}
@Serializable
data class LampAction(override val deviceName: String, val state: LampState) : Action()
@Serializable
data class BlindAction(override val deviceName: String, val state: BlindState) : Action()
@Serializable
data class WallboxAction(override val deviceName: String, val state: WallBoxState) : Action()