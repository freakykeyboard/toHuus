import SHS.SmartHomeSystem
import blind.BlindForm
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import lamp.LampForm
import org.w3c.dom.HTMLInputElement
import react.FC
import react.Props
import react.create
import react.dom.events.ChangeEvent
import react.router.Route
import react.router.Routes
import react.router.useNavigate
import react.useState
import service.login
import wallbox.WallboxForm

val scope = MainScope()

external interface RootProps : Props {
    var onLoginSuccess: () -> Unit
    var onLogoutSuccess: () -> Unit
}

val Root = FC<RootProps> { props ->

    val navigate = useNavigate()

    val (username, setUsername) = useState("")
    val (password, setPassword) = useState("")
    fun handleLogin() {
        scope.launch {
            login(User(null, username, password))
            props.onLoginSuccess()
            navigate("/SHS")
        }

    }

    fun handleLogout() {

        scope.launch {
            //TODO call logout
            props.onLogoutSuccess()
            navigate("/anmelden")

        }
    }

    fun handleUsernameChange(event: ChangeEvent<HTMLInputElement>) {
        setUsername(event.target.value)
    }

    fun handlePasswordChange(event: ChangeEvent<HTMLInputElement>) {
        setPassword(event.target.value)
    }


    Routes {
        Route {
            path = "/anmelden"
            element = Login.create() {
                to = "/"
                onSubmit = { handleLogin() }
                onUsernameChange = { handleUsernameChange(it) }
                onPasswordChange = { handlePasswordChange(it) }
            }
        }
        Route {
            path = "/SHS"
            element = SmartHomeSystem.create()

        }
        Route {
            path = "/SHS/lampen/:id?"
            element = LampForm.create() {

            }
        }
        Route {
            path = "/SHS/wallbox/:id?"
            element = WallboxForm.create() {

            }
        }
        Route {
            path = "/SHS/rolladen/:id?"
            element = BlindForm.create() {

            }
        }


    }
}





