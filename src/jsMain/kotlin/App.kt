import react.FC
import react.Props
import react.createContext
import react.router.dom.BrowserRouter
import react.useState

//TODO for production change to false
val AuthContext = createContext(true)

val App = FC<Props> { props ->

    val (isLoggedIn, setIsLoggedIn) = useState(true)
    AuthContext.Provider {
        value = isLoggedIn
        BrowserRouter {
            Root {
                onLoginSuccess = { setIsLoggedIn(true) }
                onLogoutSuccess = { setIsLoggedIn(false) }
            }

        }
    }
}

