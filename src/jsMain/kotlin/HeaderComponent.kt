import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.h1
import react.dom.html.ReactHTML.header

external interface HeaderProps : Props {
    var headerName: String
}

val HeaderComponent = FC<HeaderProps> { props ->

    header {
        className = ClassName("w3-container w3-teal")
        h1 { +props.headerName }


    }
}