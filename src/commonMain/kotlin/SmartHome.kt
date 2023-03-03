import kotlinx.serialization.Serializable

@Serializable
class SmartHome(val userId: String? = null) {
    private val devices: MutableList<Device> = mutableListOf()
    private val scenes: MutableList<AutomationScene> = mutableListOf()
    companion object {
        const val path = "/api/smarthome"
    }
    fun addDevice(device: Device) {
        devices.add(device)
    }
    fun removeDevice(name: String) {
        val device = devices.find { it.name == name }
        devices.remove(device)
    }
    fun modifyDevice(device: Device) {
        val existingDevice = devices.find { it.name == device.name }
        existingDevice?.let { it.state = device.state }
    }
    fun getDevices(): List<Device> {
        return devices
    }
    fun addScene(scene: AutomationScene) {
        scenes.add(scene)
    }
    fun removeScene(name: String) {
        val scene = scenes.find { it.name == name }
        scenes.remove(scene)
    }
    fun activateScene(name: String) {
        scenes.find { it.name == name }?.activate(devices)
    }
}