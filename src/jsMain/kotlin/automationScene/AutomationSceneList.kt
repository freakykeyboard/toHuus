package automationScene

import Lamp
import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h2
import react.dom.html.ReactHTML.h3
import react.dom.html.ReactHTML.p
import react.dom.html.ReactHTML.ul
import react.router.useNavigate

external interface LampListProps : Props {
    var name: String
    var infoText: String
    var deviceList: List<Lamp>

}

val LampList = FC<LampListProps> { props ->
    val navigate = useNavigate()

    div {
        className = ClassName("w3-col s4 w3-center")
        h2 {
            +props.name
        }
        ul {
            className = ClassName("w3-ul w3-card-4 w3-hoverable")
            props.deviceList.forEach {
                LampListItemComponent {
                    lamp = it
                }
            }
        }

        if (props.deviceList.isEmpty()) {
            div {
                className = ClassName("w3-panel w3-round-large w3-blue")
                h3 {
                    +"Info"
                }
                p {
                    +props.infoText
                }
                button {
                    className = ClassName("w3-button w3-circle w3-black")
                    +"+"
                    onClick = {
                        navigate("/SHS/lampen")
                    }
                }
            }
        }
    }
}


