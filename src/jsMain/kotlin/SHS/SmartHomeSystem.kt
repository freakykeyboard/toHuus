package SHS

import HeaderComponent
import csstype.ClassName
import kotlinx.coroutines.launch
import menu.MenuComponent
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.useEffectOnce
import react.useState
import scope


val SmartHomeSystem = FC<Props> {
    val (loading, setLoading) = useState(true)

    useEffectOnce {
        scope.launch {
            setLoading(false)
        }

    }
    HeaderComponent {
        headerName = "toHuus "
    }
    MenuComponent {
        activeIndex = 2
    }
    if (!loading) {
        div {

            className = ClassName("w3-row")

        }
    }
}
