package automationScene

import Lamp
import react.FC
import react.Props
import react.dom.html.ReactHTML.li
import react.router.useNavigate

external interface LampListItemProps : Props {
    var lamp: Lamp

}

val LampListItemComponent = FC<LampListItemProps> { props ->
    val navigate = useNavigate()
    li {
        +"Name: ${props.lamp.name}"
        onClick = {
            navigate("/SHS/lampen/${props.lamp.id}")
        }
    }
}