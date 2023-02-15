import kotlinx.browser.document
import react.create
import react.dom.client.createRoot


fun main() {
    val container = document.getElementById("root") ?: error("No element with id 'root' found")



    createRoot(container).render(App.create())
}