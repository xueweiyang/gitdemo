package com.fcl.plugin

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import com.intellij.openapi.actionSystem.PlatformDataKeys
import com.intellij.openapi.editor.Editor
import com.intellij.openapi.project.Project
import com.intellij.openapi.ui.Messages

class MyAction extends AnAction{
    @Override
    void actionPerformed(AnActionEvent anActionEvent) {
        Project project = anActionEvent.getData(PlatformDataKeys.PROJECT)
        Messages.showMessageDialog(project, "hello world", "Information", Messages.getInformationIcon())
    }

    @Override
    void update(AnActionEvent e) {
        Editor editor = e.getData(PlatformDataKeys.EDITOR)
        if (editor != null) {
            e.getPresentation().setEnabled(true)
        } else {
            e.getPresentation().setEnabled(false)
        }
    }
}
