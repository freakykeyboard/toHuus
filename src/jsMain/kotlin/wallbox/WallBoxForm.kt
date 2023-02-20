package wallbox

import HeaderComponent
import PanelComponent
import Wallbox
import csstype.ClassName
import form.ButtonComponent
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
import service.addWallbox
import service.deleteWallbox
import service.getWallbox
import service.updateWallbox

val WallboxForm = FC<Props> {
    val navigate = useNavigate()
    val (showSuccess, setShowSuccess) = useState(false)
    var wallbox by useState(Wallbox(null, ""))
    val (loading, setLoading) = useState(true)
    val params = useParams()
    val wallboxId = params["id"]
    useEffectOnce {
        if (wallboxId != null) {
            scope.launch {
                wallbox = getWallbox(wallboxId)
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
        activeIndex = 3
    }
    if (!loading) {
        div {
            className = ClassName("w3-card-4 w3-margin w3-third")
            div {
                className = ClassName("w3-container w3-teal")
                h3 {
                    if (wallbox.id != null) {
                        +"Wallbox bearbeiten"
                    } else {
                        +"Neue Wallbox anlegen"
                    }
                }
            }
            form {
                className = ClassName("w3-container")
                InputComponent {
                    placeHolder = "z.b. Carport 1"
                    label = "Name"
                    type = InputType.text
                    value = wallbox.name
                    className = ClassName("w3-input w3-border w3-sand")
                    onChange = { event ->
                        val button = document.getElementById("button")
                        button?.classList?.remove("w3-disabled")
                        wallbox.name = event.target.value
                    }
                }

                if (wallbox.id == null) {
                    ButtonComponent {
                        id = "button"
                        text = "Wallbox anlegen"
                        className = "w3-button w3-green w3-disabled"
                        onClick = {
                            scope.launch {
                                wallbox = addWallbox(wallbox)
                                navigate("/SHS")
                            }
                        }
                    }
                } else {
                    div {
                        ButtonComponent {
                            text = "Änderungen speichern"
                            className = "w3-button w3-left w3-green w3-disabled"
                            id = "button"
                            onClick = {
                                scope.launch {
                                    updateWallbox(wallbox)
                                    wallbox = getWallbox(wallbox.id!!)
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
                                    deleteWallbox(wallbox)
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