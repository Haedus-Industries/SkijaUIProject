package com.haedus.ui.components;

import com.haedus.ui.model.Category;
import com.haedus.ui.model.Module;
import com.haedus.ui.util.ColorPalette;
import com.haedus.ui.util.RenderUtil;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.FontStyle;
import io.github.humbleui.skija.Typeface;

import java.util.ArrayList;
import java.util.List;

/**
 * Central panel showing a list of modules.  Pinned modules are
 * displayed at the top under their own heading.  Additional modules
 * follow under a separate heading.  Each module is rendered as a
 * rounded card with its title, description and a circular toggle
 * indicating whether the module is enabled.  No interaction is
 * implemented – toggles reflect the boolean state on the model.
 */
public class ModulePanel extends UIComponent {
    private final List<Module> pinned = new ArrayList<>();
    private final List<Module> others = new ArrayList<>();
    private final Font titleFont;
    private final Font descFont;

    public ModulePanel(float x, float y, float width, float height, List<Category> categories) {
        super(x, y, width, height);
        // flatten modules and split into pinned/unpinned
        for (Category cat : categories) {
            for (Module m : cat.modules) {
                if (m.pinned) {
                    pinned.add(m);
                } else {
                    others.add(m);
                }
            }
        }
        Typeface typeface;
        try {
            typeface = Typeface.makeFromName("Inter", FontStyle.NORMAL);
        } catch (Throwable t) {
            typeface = Typeface.makeDefault();
        }
        this.titleFont = new Font(typeface, 14f);
        this.descFont = new Font(typeface, 12f);
    }

    @Override
    public void render(Canvas canvas) {
        float padding = 8f;
        // container background and border
        RenderUtil.drawRoundedRect(canvas, x + padding, y + padding,
                width - 2 * padding, height - 2 * padding, 16f,
                ColorPalette.CARD_BACKGROUND, ColorPalette.CARD_BORDER, 2f);

        float contentX = x + padding + 12f;
        float contentY = y + padding + 12f;

        // pinned section
        if (!pinned.isEmpty()) {
            RenderUtil.drawText(canvas, "Pinned", contentX, contentY + titleFont.getMetrics().getCapHeight(), titleFont, ColorPalette.TEXT_PRIMARY);
            contentY += 24f;
            for (Module m : pinned) {
                contentY = renderModuleCard(canvas, m, contentX, contentY);
                contentY += 8f;
            }
            contentY += 12f;
        }

        // modules section
        if (!others.isEmpty()) {
            RenderUtil.drawText(canvas, "Modules", contentX, contentY + titleFont.getMetrics().getCapHeight(), titleFont, ColorPalette.TEXT_PRIMARY);
            contentY += 24f;
            for (Module m : others) {
                contentY = renderModuleCard(canvas, m, contentX, contentY);
                contentY += 8f;
            }
        }
    }

    /**
     * Draws a single module card.  Returns the next vertical position.
     */
    private float renderModuleCard(Canvas canvas, Module m, float cx, float cy) {
        float cardWidth = width - 32f; // padding left+right and border
        float cardHeight = 56f;
        float radius = 8f;
        // card background
        RenderUtil.drawRoundedRect(canvas, cx, cy, cardWidth, cardHeight, radius,
                ColorPalette.CARD_BACKGROUND, ColorPalette.CARD_BORDER, 1f);
        // text positions
        float textX = cx + 12f;
        float titleY = cy + 20f;
        RenderUtil.drawText(canvas, m.title, textX, titleY, titleFont, ColorPalette.TEXT_PRIMARY);
        float descY = titleY + 14f;
        RenderUtil.drawText(canvas, m.description, textX, descY, descFont, ColorPalette.TEXT_SECONDARY);
        // toggle indicator
        float toggleRadius = 6f;
        float toggleX = cx + cardWidth - 20f;
        float toggleY = cy + cardHeight / 2f;
        int toggleColor = m.enabled ? ColorPalette.TOGGLE_ENABLED : ColorPalette.TOGGLE_DISABLED;
        RenderUtil.drawCircle(canvas, toggleX, toggleY, toggleRadius, toggleColor);
        return cy + cardHeight;
    }
}