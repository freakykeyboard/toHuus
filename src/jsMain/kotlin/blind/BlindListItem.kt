package blind

import Blind
import react.FC
import react.Props
import react.dom.html.ReactHTML
import react.router.useNavigate

external interface LampListItemProps : Props {
    var blind: Blind

}

val BlindListItemComponent = FC<LampListItemProps> { props ->
    val navigate = useNavigate()
    ReactHTML.li {
        +"Name: ${props.blind.name}"
        onClick = {
            navigate("/SHS/Rolladen/${props.blind.id}")
        }
    }
}