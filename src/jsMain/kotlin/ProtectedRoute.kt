import react.*
import react.router.useNavigate

external interface ProtectedRouteProps : Props {
    var children: ReactNode
}

//TODO use
val ProtectedRoute = FC<ProtectedRouteProps> { props ->
    val isLoggedIn = useContext(AuthContext)
    val navigate = useNavigate()
    if (isLoggedIn) {
        props.children
    } else {
        useEffect {
            navigate("/anmelden")
        }

    }

}