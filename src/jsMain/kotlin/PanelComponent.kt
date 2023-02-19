import csstype.ClassName
import react.FC
import react.Props
import react.dom.html.ReactHTML.div
import react.dom.html.ReactHTML.h3
import react.dom.html.ReactHTML.p

external interface PanelProps : Props {
    var heading: String
    var text: String
}

var PanelComponent = FC<PanelProps> { props ->
    div {
        id = "info"
        className = ClassName("w3-panel w3-round-large w3-green w3-display-container")

        h3 {
            +props.heading
        }
        p {
            +props.text
        }

    }
}