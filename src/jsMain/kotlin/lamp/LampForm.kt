package lamp

import HeaderComponent
import Lamp
import PanelComponent
import csstype.ClassName
import form.ButtonComponent
import form.CheckBox
import form.InputComponent
import kotlinx.browser.document
import kotlinx.coroutines.launch
import menu.MenuComponent
import react.FC
import react.Props
import react.dom.html.InputType
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.h3
import react.router.useNavigate
import react.router.useParams
import react.useEffectOnce
import react.useState
import scope
import service.addLamp
import service.deleteLamp
import service.getLamp
import service.updateLamp

external interface LampFormProps : Props {

}

val LampForm = FC<LampFormProps> { _ ->
    val (loading, setLoading) = useState(true)
    val (name, setName) = useState("")
    val (lamp, setLamp) = useState(Lamp("", ""))
    val (on, setOn) = useState(false)
    val (showSuccess, setShowSuccess) = useState(false)
    val navigate = useNavigate()
    val params = useParams()
    val lampId = params["id"]


    useEffectOnce {
        if (lampId != null) {
            scope.launch {
                val lamp = getLamp(lampId)
                setName(lamp.name)
                setOn(lamp.isOn)
                setLamp(lamp)
                setLoading(false)
            }
        } else {
            setLoading(false)
        }
    }
    HeaderComponent {
        headerName = "toHuus"
    }
    MenuComponent {
        activeIndex = 2
    }
    if (!loading) {
        div {
            className = ClassName("w3-card-4 w3-margin w3-third")
            div {
                className = ClassName("w3-container w3-teal")
                h3 {
                    if (lampId != null) {
                        +"Lampe bearbeiten"
                    } else {
                        +"Neue Lampe anlegen"
                    }
                }
            }
            form {
                className = ClassName("w3-container")
                InputComponent {
                    placeHolder = "Name"
                    type = InputType.text
                    value = name
                    className = ClassName("w3-input w3-border w3-sand")
                    onChange = { event ->
                        val button = document.getElementById("button")
                        button?.classList?.remove("w3-disabled")
                        setName(event.target.value)
                    }
                }

                if (lampId == null) {
                    ButtonComponent {
                        id = "button"
                        text = "Lampe anlegen"
                        className = "w3-button w3-green w3-disabled"
                        onClick = {
                            scope.launch {
                                addLamp(Lamp(name = name))
                                navigate("/SHS")
                            }
                        }
                    }
                } else {
                    CheckBox {
                        checked = on == true
                        className = ClassName("w3-check")
                        onChange = { event ->
                            val button = document.getElementById("button")
                            button?.classList?.remove("w3-disabled")
                            setOn(event.target.checked)

                        }
                    }
                    div {
                        ButtonComponent {
                            text = "Änderungen speichern"
                            className = "w3-button w3-left w3-green w3-disabled"
                            id = "button"
                            onClick = {
                                scope.launch {
                                    lamp.name = name
                                    lamp.isOn = on

                                    updateLamp(lamp)
                                    val button = document.getElementById("button")
                                    button?.classList?.add("w3-disabled")
                                    setShowSuccess(true)

                                }
                            }
                        }
                        ButtonComponent {
                            text = "-"
                            className = "w3-button w3-right w3-circle w3-red"
                            onClick = {
                                scope.launch {
                                    deleteLamp(lampId)
                                    navigate("/SHS")
                                }
                            }
                        }
                    }

                }

            }
            if (showSuccess) {
                PanelComponent {
                    heading = "Info"
                    text = "Aktion erfolgreich ausgeführt"
                }
            }
        }

    }
}

