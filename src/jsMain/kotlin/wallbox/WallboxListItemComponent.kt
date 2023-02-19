package wallbox

import Wallbox
import react.FC
import react.Props
import react.dom.html.ReactHTML.li
import react.router.useNavigate

external interface WallboxListItemProps : Props {
    var wallbox: Wallbox
}

val WallboxListItemComponent = FC<WallboxListItemProps> { props ->
    val navigate = useNavigate()
    li {
        +"Name: ${props.wallbox.name}"
        onClick = {
            navigate("/SHS/wallbox/${props.wallbox.id}")
        }
    }
}