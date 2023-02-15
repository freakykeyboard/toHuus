package menu

import AuthContext
import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.useContext
import react.useState

val MenuComponent = FC<Props> { _ ->
    val (activeIndex, setActiveIndex) = useState(0)
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