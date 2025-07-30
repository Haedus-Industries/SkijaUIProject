package com.haedus.ui.components;

import com.haedus.ui.model.Category;
import com.haedus.ui.util.ColorPalette;
import com.haedus.ui.util.RenderUtil;
import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Font;
import io.github.humbleui.skija.FontStyle;
import io.github.humbleui.skija.Typeface;

import java.util.List;

/**
 * Horizontal navigation bar drawn at the top of the window.  It
 * displays one button for each category.  The selected category is
 * indicated by a coloured bar beneath the text.  Submenu rendering
 * has been intentionally omitted for brevity; future versions could
 * extend this class to draw drop‑down panels on hover.
 */
public class NavigationBar extends UIComponent {
    private final List<Category> categories;
    private final Font font;
    private int selectedIndex;

    public NavigationBar(float x, float y, float width, float height, List<Category> categories) {
        super(x, y, width, height);
        this.categories = categories;
        this.selectedIndex = 0;
        // Attempt to use the Inter typeface; fall back to default if unavailable
        Typeface typeface;
        try {
            typeface = Typeface.makeFromName("Inter", FontStyle.NORMAL);
        } catch (Throwable t) {
            typeface = Typeface.makeDefault();
        }
        this.font = new Font(typeface, 16f);
    }

    @Override
    public void render(Canvas canvas) {
        // background container with border
        float padding = 8f;
        RenderUtil.drawRoundedRect(canvas,
                x + padding, y + padding,
                width - 2 * padding, height - 2 * padding,
                16f,
                ColorPalette.NAV_BACKGROUND,
                ColorPalette.NAV_BORDER,
                3f
        );

        // compute item widths equally
        int count = Math.max(1, categories.size());
        float itemWidth  = (width  - 2 * padding) / count;
        float itemHeight = (height - 2 * padding);
        float baseX      = x + padding;
        float baseY      = y + padding;

        for (int i = 0; i < count; i++) {
            Category cat = categories.get(i);
            float itemX = baseX + i * itemWidth;

            // highlight indicator if selected
            if (i == selectedIndex) {
                float indicatorHeight = 4f;
                RenderUtil.drawRoundedRect(canvas,
                        itemX + 4f,
                        baseY + itemHeight - indicatorHeight,
                        itemWidth  - 8f,
                        indicatorHeight,
                        2f,
                        ColorPalette.ACCENT,
                        0,
                        0f
                );
            }

            // measure text width using the new API
            float textWidth = font.measureTextWidth(cat.name);  // :contentReference[oaicite:0]{index=0}
            float textX = itemX + (itemWidth - textWidth) / 2f;
            float textY = baseY + (itemHeight + font.getMetrics().getCapHeight()) / 2f;

            RenderUtil.drawText(canvas,
                    cat.name,
                    textX, textY,
                    font,
                    ColorPalette.TEXT_PRIMARY
            );
        }
    }

    /**
     * Sets which category is selected.  In the real application this
     * would trigger filtering of modules.  This simplified version
     * redraws the highlight bar only.
     *
     * @param index index of the active category
     */
    public void setSelectedIndex(int index) {
        if (index >= 0 && index < categories.size()) {
            this.selectedIndex = index;
        }
    }
}
