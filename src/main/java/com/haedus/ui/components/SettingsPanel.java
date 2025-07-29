package com.haedus.ui.components;

import com.haedus.ui.model.Module;
import com.haedus.ui.model.settings.Setting;
import com.haedus.ui.util.RenderUtil;
import org.jetbrains.skija.Canvas;

import java.util.List;

/**
 * Panel that displays settings for the currently selected module.
 */
public class SettingsPanel extends UIComponent {
    private Module module;

    public void setModule(Module module) {
        this.module = module;
    }

    @Override
    public void render(Canvas canvas) {
        RenderUtil.fillRect(canvas, rect(), RenderUtil.COLOR_PANEL);

        if (module == null) {
            RenderUtil.drawText(canvas, "Select a module to view settings", x + 16f, y + 32f);
            return;
        }

        float offsetY = y + 24f;
        RenderUtil.drawText(canvas, module.getName() + " Settings", x + 16f, offsetY);
        offsetY += RenderUtil.LINE_HEIGHT + 8f;

        List<Setting> settings = module.getSettings();
        for (Setting s : settings) {
            RenderUtil.drawText(canvas, s.getDisplayString(), x + 16f, offsetY);
            offsetY += RenderUtil.LINE_HEIGHT + 8f;
        }
    }
}
