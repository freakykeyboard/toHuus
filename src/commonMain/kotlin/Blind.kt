import kotlinx.serialization.Serializable

@Serializable
class Blind(
    override val id: String? = null,
    override var name: String,
    override val userId: String? = null,
    var position: Int = 0,


    ) : Device() {
    companion object {
        const val path = "/api/blind"
    }

    fun move(position: Int) {
        this.position = position
    }
}
