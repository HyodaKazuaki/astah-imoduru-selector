package com.example.astah

import com.change_vision.jude.api.inf.AstahAPI
import com.change_vision.jude.api.inf.exception.ProjectNotFoundException
import com.change_vision.jude.api.inf.model.IGeneralization
import com.change_vision.jude.api.inf.presentation.INodePresentation
import com.change_vision.jude.api.inf.ui.IPluginActionDelegate
import com.change_vision.jude.api.inf.ui.IPluginActionDelegate.UnExpectedException
import com.change_vision.jude.api.inf.ui.IWindow
import java.lang.Exception
import javax.swing.JOptionPane
import kotlin.jvm.Throws

class SelectInheritanceAction: IPluginActionDelegate {
    @Throws(UnExpectedException::class)
    override fun run(window: IWindow) {
        try {
            val diagramViewManager = AstahAPI.getAstahAPI().viewManager.diagramViewManager
            val targetNodes = diagramViewManager.selectedPresentations
                    .filterIsInstance<INodePresentation>()
                    .flatMap { node ->
                        node.links
                                .filter { it.model is IGeneralization }
                                .filter { it.target == node }
                                .map { it.source }
                    }.toTypedArray()
            println(targetNodes.first().label)
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