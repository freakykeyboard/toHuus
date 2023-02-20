package blind

import Blind
import HeaderComponent
import PanelComponent
import csstype.ClassName
import form.ButtonComponent
import form.InputComponent
import kotlinx.browser.document
import kotlinx.coroutines.launch
import menu.MenuComponent
import react.FC
import react.Props
import react.dom.html.InputType
import react.dom.html.ReactHTML.b
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.form
import react.dom.html.ReactHTML.h3
import react.dom.html.ReactHTML.input
import react.dom.html.ReactHTML.label
import react.dom.html.ReactHTML.p
import react.router.useNavigate
import react.router.useParams
import react.useEffectOnce
import react.useState
import scope
import service.addBlind
import service.changeBlind
import service.deleteBlind
import service.getBlind

val BlindForm = FC<Props> {
    val (loading, setLoading) = useState(true)
    var (blind, setBlind) = useState(Blind(null, ""))
    val (name, setName) = useState("")
    val (position, setPosition) = useState(0)
    val (showSuccess, setShowSuccess) = useState(false)
    val navigate = useNavigate()
    val params = useParams()
    val blindId = params["id"]


    useEffectOnce {
        if (blindId != null) {
            scope.launch {
                val blind = getBlind(blindId)
                setBlind(blind)
                setName(blind.name)
                setPosition(blind.position)
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
                    if (blind.id != null) {
                        +"Rolladen bearbeiten"
                    } else {
                        +"Rolladen anlegen"
                    }
                }
            }
            form {
                className = ClassName("w3-container")
                InputComponent {
                    placeHolder = "Name"
                    type = InputType.text
                    value = name
                    label = "Name"
                    className = ClassName("w3-input w3-border w3-sand")
                    onChange = { event ->
                        val button = document.getElementById("button")
                        button?.classList?.remove("w3-disabled")
                        setName(event.target.value)

                    }
                }

                if (blind.id == null) {
                    ButtonComponent {
                        id = "button"
                        text = "Rolladen anlegen"
                        className = "w3-button w3-green w3-disabled"
                        onClick = {
                            scope.launch {
                                setBlind(addBlind(blind))
                                navigate("/SHS")
                            }
                        }
                    }
                } else {
                    p {
                        input {
                            type = InputType.number
                            value = position.toString()
                            className = ClassName("w3-input w3-border w3-sand")
                            min = 0.0
                            max = 100.0
                            onChange = { event ->
                                setPosition(event.target.value.toInt())
                                val button = document.getElementById("button")
                                button?.classList?.remove("w3-disabled")
                                
                            }
                        }
                        label {
                            className = ClassName("w3-text")
                            b {
                                +"Position"
                            }
                        }
                    }

                    div {
                        ButtonComponent {
                            text = "Änderungen speichern"
                            className = "w3-button w3-left w3-green w3-disabled"
                            id = "button"
                            onClick = {
                                scope.launch {
                                    blind.name = name
                                    blind.position = position
                                    changeBlind(blind)
                                    blind = getBlind(blind.id!!)
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
                                    deleteBlind(blind)
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