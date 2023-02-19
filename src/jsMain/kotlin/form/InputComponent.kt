package form

import csstype.ClassName
import org.w3c.dom.HTMLInputElement
import react.FC
import react.Props
import react.dom.events.ChangeEvent
import react.dom.html.InputType
import react.dom.html.ReactHTML.b
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.p

external interface InputProps : Props {
    var label: String
    var className: ClassName
    var placeHolder: String
    var type: InputType
    var value: String
    var onChange: (ChangeEvent<HTMLInputElement>) -> Unit
}

val InputComponent = FC<InputProps> { props ->
    p {
        input {
            placeholder = props.placeHolder
            type = props.type
            value = props.value
            className = props.className
            onChange = { event -> props.onChange(event) }
        }
        label {
            className = ClassName("w3-text")
            b {
                +props.label
            }
        }
    }
}