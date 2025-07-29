package com.haedus.ui.components;

import com.haedus.ui.model.Module;
import com.haedus.ui.util.RenderUtil;
import org.jetbrains.skija.Canvas;

import java.util.ArrayList;
import java.util.List;

/**
 * Component showing the list of toggleable modules.
 */
public class ModulePanel extends UIComponent {
    private final List<Module> modules = new ArrayList<>();

    public void setModules(List<Module> moduleList) {
        modules.clear();
        if (moduleList != null) modules.addAll(moduleList);
    }

    @Override
    public void render(Canvas canvas) {
        RenderUtil.fillRect(canvas, rect(), RenderUtil.COLOR_PANEL);

        float padding = 16f;
        float offsetY = y + padding + 14f;
        for (Module m : modules) {
            RenderUtil.drawText(canvas, (m.isEnabled() ? "[x] " : "[ ] ") + m.getName(), x + padding, offsetY);
            offsetY += RenderUtil.LINE_HEIGHT + 8f;
        }
    }
}
