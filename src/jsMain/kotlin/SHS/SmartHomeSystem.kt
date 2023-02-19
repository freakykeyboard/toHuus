package SHS

import Blind
import HeaderComponent
import Lamp
import Wallbox
import blind.BlindList
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
import service.getAllBlinds
import service.getAllLamps
import service.getAllWallboxes
import wallbox.WallboxList


val SmartHomeSystem = FC<Props> {
    var lampList by useState { emptyList<Lamp>() }
    val (loading, setLoading) = useState(true)
    var blindList by useState { emptyList<Blind>() }
    var wallboxList by useState { emptyList<Wallbox>() }

    useEffectOnce {
        scope.launch {
            lampList = getAllLamps()
            wallboxList = getAllWallboxes()
            blindList = getAllBlinds()
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
                deviceList = wallboxList
            }
            BlindList {
                name = "Rollläden"
                infoText = "Es wurden noch keine Rollläden angelegt! Warum nicht gleich einen anlegen?"
                deviceList = blindList
            }
        }
    }
}
