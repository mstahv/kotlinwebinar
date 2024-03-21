package com.example.kotlinwebinar

import com.vaadin.flow.component.html.Div
import com.vaadin.flow.component.html.Span
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.router.Route
import org.vaddon.CustomMediaQuery
import org.vaddon.ElementMediaQuery
import org.vaddon.css.query.MediaQuery
import org.vaddon.css.query.values.WidthAttributes

@Route
class MediaQueryView : VerticalLayout() {
    private var div300pxOrLess = false
    private var window1024OrLess = false
    private val div: Div
    private val textWindow: Span
    private val textDiv: Span

    init {
        setSizeFull()
        div = Div()
        div.width = "50%"
        div.height = "100px"
        textWindow = Span("window1024OrLess")
        textDiv = Span("div300pxOrLess")
        add(div, textWindow, textDiv)
        val customMediaQuery1024 =
            CustomMediaQuery { window1024OrLess: Boolean -> this.updateWindows1024OrLess(window1024OrLess) }
        add(customMediaQuery1024)
        customMediaQuery1024.setQuery(MediaQuery(WidthAttributes.MaxWidth("1024px")))
        val elementMediaQuery300 =
            ElementMediaQuery { div300pxOrLess: Boolean -> this.updateDiv300OrLess(div300pxOrLess) }
        elementMediaQuery300.setElement(div)
        elementMediaQuery300.setQuery(MediaQuery(WidthAttributes.MaxWidth("300px")))
        add(elementMediaQuery300)
    }

    private fun updateWindows1024OrLess(window1024OrLess: Boolean) {
        this.window1024OrLess = window1024OrLess
        textWindow.isVisible = window1024OrLess
        updateCondition()
    }

    private fun updateDiv300OrLess(div300pxOrLess: Boolean) {
        this.div300pxOrLess = div300pxOrLess
        textDiv.isVisible = div300pxOrLess
        updateCondition()
    }

    private fun updateCondition() {
        if (window1024OrLess && div300pxOrLess) {
            div.style["background-color"] = "green"
        } else {
            div.style["background-color"] = "red"
        }
    }
}