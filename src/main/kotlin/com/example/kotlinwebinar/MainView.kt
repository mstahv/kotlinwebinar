package com.example.kotlinwebinar

import com.vaadin.flow.component.UI
import com.vaadin.flow.component.button.Button
import com.vaadin.flow.component.grid.Grid
import com.vaadin.flow.component.html.Paragraph
import com.vaadin.flow.component.orderedlayout.VerticalLayout
import com.vaadin.flow.component.textfield.TextField
import com.vaadin.flow.router.Route
import com.vaadin.flow.spring.data.VaadinSpringDataHelpers
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

// Route annotation maps Vaadin components to specific URLs
@Route
internal class MainView(personRepository: PersonRepository) : VerticalLayout() {
    init {

        // Basic Vaadin UI components
        val textField = TextField("Name")

        // Low level "Element API", use for custom components
        textField.element.style.setColor("blue")

        val button = Button("Click me") {
            val name = textField.value
            add(Paragraph("Hello there $name"))
        }
        add(textField, button)

        // More advanced components and binding to JPA entity
        val grid = Grid<Person>()
        grid.addColumn{ p -> p.age }.setHeader("Age")
        grid.addColumn{ p ->p.name }.setHeader("Name")
        grid.setItems{ query ->
            // Map the Grid's query object to Spring's Pageable for efficient
            // DB access
            personRepository.findAll(VaadinSpringDataHelpers.toSpringPageRequest(query))
                .stream()
        }
        add(grid)

        // Push annotation in the application class configures
        // Websocket communication, "server push" is trivial
        val executor = Executors.newScheduledThreadPool(1)
        val ui = UI.getCurrent()

        add( Button("Start start streaming") {
            executor.scheduleAtFixedRate({
                // sync object modifications with UI.access()
                ui.access {
                    add(Paragraph("Hello from Thread!"))
                }
            }, 1000, 1000, TimeUnit.MILLISECONDS)
        })

    }
}