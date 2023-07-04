package org.example.pages

import androidx.compose.runtime.Composable
import com.varabyte.kobweb.compose.foundation.layout.Box
import com.varabyte.kobweb.compose.ui.Modifier
import com.varabyte.kobweb.compose.ui.modifiers.classNames
import com.varabyte.kobweb.compose.ui.modifiers.size
import com.varabyte.kobweb.core.Page
import org.jetbrains.compose.web.css.px

@Page
@Composable
fun HomePage() {
    kotlinext.js.require("./globals.css")
    Box(
        modifier = Modifier
            .classNames(
                "bg-red-600",
                "hover:bg-blue-200"
            )
            .size(500.px)
    )
}

