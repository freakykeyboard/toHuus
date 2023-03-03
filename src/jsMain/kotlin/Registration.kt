

import csstype.ClassName
import menu.MenuComponent
import org.w3c.dom.HTMLInputElement
import react.FC
import react.Props
import react.dom.events.ChangeEvent
import react.dom.html.InputType
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label

external interface RegistrationProps : Props {
    var to: String
    var onSubmit: () -> Unit
    var onUsernameChange: (ChangeEvent<HTMLInputElement>) -> Unit
    var onPasswordChange: (ChangeEvent<HTMLInputElement>) -> Unit

}

val Registration = FC<RegistrationProps> { props ->
    HeaderComponent {
        headerName = "toHuus"
    }
    MenuComponent()
    h1 {
        +"Registrieren"
    }
    form {
        className = ClassName("w3-container")
        label {
            +"Benutzername"
        }

        input {
            type = InputType.text
            className = ClassName("w3-input")
            name = "username"
            id = "username"
            onChange = { event -> props.onUsernameChange(event) }

        }
        label {
            +"Passwort"
        }
        input {
            type = InputType.password
            className = ClassName("w3-input")

            name = "password"
            id = "password"
            onChange = { event -> props.onPasswordChange(event) }

        }
        button {
            +"Registrieren"
            className = ClassName("w3-button w3-blue")
            onClick = {
                it.preventDefault()
                props.onSubmit()


            }

        }
    }
}
