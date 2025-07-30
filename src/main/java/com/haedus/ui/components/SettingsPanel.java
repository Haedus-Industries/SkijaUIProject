package com.haedus.ui.components;

import com.haedus.ui.model.Category;
import com.haedus.ui.model.Module;
import com.haedus.ui.model.Setting;
import com.haedus.ui.model.SettingGroup;
import com.haedus.ui.model.SettingType;
import com.haedus.ui.util.ColorPalette;
import com.haedus.ui.util.RenderUtil;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.FontStyle;
import io.github.humbleui.skija.Typeface;

import java.util.List;

/**
 * Right hand panel listing settings for the first pinned module.  In a
 * full implementation this panel would update based on which module
 * the user selected.  Each group is rendered with a title and the
 * contained settings drawn beneath.  Only a subset of setting types
 * (boolean, range, mode, text and colour) are visualised.
 */
public class SettingsPanel extends UIComponent {
    /**
     * Categories to search for the first pinned module.  The
     * {@link #findFirstPinnedModule()} method iterates this list
     * whenever settings are rendered.  Passing categories via the
     * constructor allows the settings panel to reflect the current
     * model without relying on global state.
     */
    private final List<Category> categories;
    private final Font groupFont;
    private final Font labelFont;
    private final Font valueFont;

    /**
     * Creates a settings panel at the given position with default
     * fonts.  The categories list is inspected to determine which
     * module's settings to draw.  Clients should pass the same
     * collection used for the navigation and module panels.
     */
    public SettingsPanel(float x, float y, float width, float height, List<Category> categories) {
        super(x, y, width, height);
        this.categories = categories;
        // Attempt to load the Inter font; fall back to the default
        Typeface typeface;
        try {
            typeface = Typeface.makeFromName("Inter", FontStyle.NORMAL);
        } catch (Throwable t) {
            typeface = Typeface.makeDefault();
        }
        this.groupFont = new Font(typeface, 14f);
        this.labelFont = new Font(typeface, 12f);
        this.valueFont = new Font(typeface, 12f);
        // HumbleUI Skija no longer exposes Font.Edging; antialiasing is
        // enabled automatically by Paint when drawing text.
    }

    @Override
    public void render(Canvas canvas) {
        float padding = 8f;
        RenderUtil.drawRoundedRect(canvas, x + padding, y + padding,
                width - 2 * padding, height - 2 * padding, 16f,
                ColorPalette.CARD_BACKGROUND, ColorPalette.CARD_BORDER, 2f);
        float curX = x + padding + 12f;
        float curY = y + padding + 12f;

        // Title
        String title = "Settings";
        RenderUtil.drawText(canvas, title, curX, curY + groupFont.getMetrics().getCapHeight(), groupFont, ColorPalette.TEXT_PRIMARY);
        curY += 28f;

        // Determine active module (first pinned module)
        Module active = findFirstPinnedModule();
        if (active == null) {
            RenderUtil.drawText(canvas, "No module selected", curX, curY + labelFont.getMetrics().getCapHeight(), labelFont, ColorPalette.TEXT_SECONDARY);
            return;
        }

        for (SettingGroup group : active.groups) {
            // group header
            RenderUtil.drawText(canvas, group.name, curX, curY + groupFont.getMetrics().getCapHeight(), groupFont, ColorPalette.TEXT_PRIMARY);
            curY += 22f;
            if (!group.expanded) {
                // expand groups by default
                group.expanded = true;
            }
            if (group.expanded) {
                for (Setting setting : group.settings) {
                    curY = renderSetting(canvas, setting, curX, curY);
                    curY += 8f;
                }
                curY += 12f;
            }
        }
    }

    /**
     * Searches through the test data for the first module marked as
     * pinned.  Returns {@code null} if none are pinned.  This method
     * looks at the globally generated categories via {@link TestData}.
     */
    private Module findFirstPinnedModule() {
        // Iterate through categories to find the first module marked as pinned
        if (categories != null) {
            for (Category cat : categories) {
                for (Module m : cat.modules) {
                    if (m.pinned) {
                        return m;
                    }
                }
            }
        }
        return null;
    }

    /**
     * Draws a single setting row based on its type.  Returns the y
     * coordinate at which the next setting should be rendered.  All
     * settings share the same card width for alignment.
     */
    private float renderSetting(Canvas canvas, Setting setting, float cx, float cy) {
        float rowHeight = 36f;
        float cardWidth = width - 32f;
        float radius = 6f;
        // background row
        RenderUtil.drawRoundedRect(canvas, cx, cy, cardWidth, rowHeight, radius,
                ColorPalette.CARD_BACKGROUND, ColorPalette.CARD_BORDER, 1f);
        // label
        float labelX = cx + 12f;
        float labelY = cy + 20f;
        RenderUtil.drawText(canvas, setting.name, labelX, labelY, labelFont, ColorPalette.TEXT_PRIMARY);
        // control area on right half
        float controlX = cx + cardWidth - 100f;
        float controlY = cy + rowHeight / 2f;
        switch (setting.type) {
            case BOOLEAN:
                int col = setting.boolValue ? ColorPalette.TOGGLE_ENABLED : ColorPalette.TOGGLE_DISABLED;
                RenderUtil.drawCircle(canvas, controlX + 30f, controlY, 6f, col);
                break;
            case RANGE:
                float barWidth = 60f;
                float barHeight = 4f;
                float trackX = controlX;
                float trackY = controlY - barHeight / 2f;
                // track
                RenderUtil.drawRoundedRect(canvas, trackX, trackY, barWidth, barHeight, 2f,
                        ColorPalette.TOGGLE_DISABLED, 0, 0f);
                // fill based on value
                float ratio = (setting.numericValue - setting.minValue) / (setting.maxValue - setting.minValue);
                RenderUtil.drawRoundedRect(canvas, trackX, trackY, barWidth * ratio, barHeight, 2f,
                        ColorPalette.TOGGLE_ENABLED, 0, 0f);
                break;
            case MODE:
                String option = setting.options.isEmpty() ? "" : setting.options.get(Math.min(setting.selectedOptionIndex, setting.options.size() - 1));
                RenderUtil.drawText(canvas, option, controlX, controlY + labelFont.getMetrics().getCapHeight() / 2f, valueFont, ColorPalette.TEXT_SECONDARY);
                break;
            case TEXT:
                RenderUtil.drawText(canvas, setting.stringValue, controlX, controlY + labelFont.getMetrics().getCapHeight() / 2f, valueFont, ColorPalette.TEXT_SECONDARY);
                break;
            case COLOR:
                // draw colour swatch
                float sw = 12f;
                RenderUtil.drawRoundedRect(canvas, controlX, controlY - sw / 2f, sw, sw, 3f,
                        setting.colorValue, ColorPalette.CARD_BORDER, 1f);
                break;
        }
        return cy + rowHeight;
    }
}