package form

import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.button

external interface ButtonProps : Props {
    var text: String
    var onClick: () -> Unit
    var className: String
    var id: String
}

val ButtonComponent = FC<ButtonProps> { props ->
    button {
        +props.text
        id = props.id
        className = ClassName(props.className)
        onClick = {
            it.preventDefault()
            props.onClick()

        }
    }
}