package SHS

import HeaderComponent
import Lamp
import csstype.ClassName
import kotlinx.coroutines.launch
import lamp.LampList
import menu.MenuComponent
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.useEffectOnce
import react.useState
import scope
import service.getAllLamps


val SmartHomeSystem = FC<Props> {
    var lampList by useState { emptyList<Lamp>() }
    val (loading, setLoading) = useState(true)
    val blindList by useState { emptyList<Any>() }
    val wallboxList by useState { emptyList<Any>() }

    useEffectOnce {
        scope.launch {
            lampList = getAllLamps()
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
            LampList {
                name = "Lampen"
                infoText = "Es wurden noch keine Lampen angelegt! Warum nicht gleich eine anlegen?"
                deviceList = lampList
            }


        }
    }


}
