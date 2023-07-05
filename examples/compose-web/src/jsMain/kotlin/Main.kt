import org.jetbrains.compose.web.css.height
import org.jetbrains.compose.web.css.px
import org.jetbrains.compose.web.css.width
import org.jetbrains.compose.web.dom.Div
import org.jetbrains.compose.web.renderComposable

fun main() {
    kotlinext.js.require("./globals.css")
    renderComposable(rootElementId = "root") {
        Div({
            classes("bg-red-600", "hover:bg-blue-200")
            style {
                width(500.px)
                height(500.px)
            }
        })
    }
}

