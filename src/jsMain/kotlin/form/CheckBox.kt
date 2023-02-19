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

external interface CheckBoxProps : Props {
    var label: String
    var checked: Boolean
    var className: ClassName
    var onChange: (ChangeEvent<HTMLInputElement>) -> Unit
}

val CheckBox = FC<CheckBoxProps> { props ->
    p {
        input {
            type = InputType.checkbox
            checked = props.checked
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