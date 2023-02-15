package menu

import csstype.ClassName
import react.FC
import react.Props
import react.router.dom.Link

external interface MenuItemProps : Props {
    var name: String
    var to: String
    var isActive: Boolean
    var onShow: () -> Unit
}
val MenuItemComponent = FC<MenuItemProps> { props ->
    Link {
        +props.name
        to = props.to
        className = ClassName("w3-bar-item w3-button ${if (props.isActive)"w3-grey" else ""}")
        onClick = { props.onShow() }

    }
}