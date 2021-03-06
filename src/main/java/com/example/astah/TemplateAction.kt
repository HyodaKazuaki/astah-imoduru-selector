package com.example.astah

import com.change_vision.jude.api.inf.ui.IPluginActionDelegate
import kotlin.Throws
import com.change_vision.jude.api.inf.ui.IPluginActionDelegate.UnExpectedException
import com.change_vision.jude.api.inf.ui.IWindow
import com.change_vision.jude.api.inf.AstahAPI
import javax.swing.JOptionPane
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException
import com.change_vision.jude.api.inf.presentation.INodePresentation
import java.lang.Exception

class TemplateAction : IPluginActionDelegate {
    @Throws(UnExpectedException::class)
    override fun run(window: IWindow) {
        try {
            val diagramViewManager = AstahAPI.getAstahAPI().viewManager.diagramViewManager
            val targetNodes = diagramViewManager.selectedPresentations
                    .filterIsInstance<INodePresentation>()
                    .flatMap { node ->
                        node.links
                                .filter { it.source == node }
                                .map { it.target }
                    }.toTypedArray()
            diagramViewManager.select(targetNodes)
        } catch (e: ProjectNotFoundException) {
            val message = "Project is not opened.Please open the project or create new project."
            JOptionPane.showMessageDialog(window.getParent(), message, "Warning", JOptionPane.WARNING_MESSAGE)
        } catch (e: Exception) {
            JOptionPane.showMessageDialog(window.getParent(), "Unexpected error has occurred.", "Alert", JOptionPane.ERROR_MESSAGE)
            throw UnExpectedException()
        }
    }
}