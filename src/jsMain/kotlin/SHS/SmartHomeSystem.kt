package SHS

import HeaderComponent
import Lamp
import Wallbox
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
import service.getAllWallboxes
import wallbox.WallboxList


val SmartHomeSystem = FC<Props> {
    var lampList by useState { emptyList<Lamp>() }
    val (loading, setLoading) = useState(true)
    val blindList by useState { emptyList<Any>() }
    var wallboxList by useState { emptyList<Wallbox>() }

    useEffectOnce {
        scope.launch {
            lampList = getAllLamps()
            wallboxList = getAllWallboxes()
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
            WallboxList {
                name = "Wallboxen"
                infoText = "Es wurden noch keine Wallboxen angelegt! Warum nicht gleich eine anlegen?"
                this.wallboxList = wallboxList
            }

        }


    }


}
