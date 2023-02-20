package menu

import AuthContext
import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.button
import react.dom.html.ReactHTML.div
import react.useContext
import react.useState

external interface MenuProps : Props {
    var activeIndex: Int
}

val MenuComponent = FC<MenuProps> { props ->
    val (activeIndex, setActiveIndex) = useState(props.activeIndex)
    val isLoggedIn = useContext(AuthContext)

    div {
        className = ClassName("w3-bar w3-black")
        MenuItemComponent {
            name = "Home"
            to = "/"
            isActive = activeIndex == 0
            onShow = { setActiveIndex(0) }
        }
        MenuItemComponent {
            name = "SHS"
            to = "/SHS"
            isActive = activeIndex == 2
            onShow = { setActiveIndex(2) }
        }

        div {
            className = ClassName("w3-dropdown-hover")
            button {
                className = ClassName("w3-bar-item w3-bar-button w3-black ${if (activeIndex == 3) "w3-grey" else ""}")

                +"Lampen"
                div {
                    className = ClassName("w3-dropdown-content w3-bar-block w3-card-4 w3-black")
                    MenuItemComponent {
                        name = "Neue Lampe"
                        to = "/SHS/lampen"
                        isActive = activeIndex == 3
                        onShow = { setActiveIndex(3) }
                    }
                }
            }
        }
        div {
            className = ClassName("w3-dropdown-hover")
            button {
                className = ClassName("w3-bar-item w3-bar-button w3-black ${if (activeIndex == 3) "w3-grey" else ""}")

                +"Wallboxen"
                div {
                    className = ClassName("w3-dropdown-content w3-bar-block w3-card-4 w3-black")
                    MenuItemComponent {
                        name = "Neue Wallbox"
                        to = "/SHS/wallbox"
                        isActive = activeIndex == 4
                        onShow = { setActiveIndex(4) }
                    }
                }
            }
        }
        div {
            className = ClassName("w3-dropdown-hover")
            button {
                className = ClassName("w3-bar-item w3-bar-button w3-black ${if (activeIndex == 3) "w3-grey" else ""}")

                +"Rolläden"
                div {
                    className = ClassName("w3-dropdown-content w3-bar-block w3-card-4 w3-black")
                    MenuItemComponent {
                        name = "Neue Rolläden"
                        to = "/SHS/rolladen"
                        isActive = activeIndex == 5
                        onShow = { setActiveIndex(5) }
                    }
                }
            }
        }
        MenuItemComponent {
            name = "Szene"
            to = "/SHS/szene"
            isActive = activeIndex == 6
            onShow = { setActiveIndex(6) }
        }

        if (!isLoggedIn) {
            MenuItemComponent {
                name = "Registrieren"
                to = "/registrieren"
                isActive = activeIndex == 1
                onShow = { setActiveIndex(1) }
            }
        }


    }
}