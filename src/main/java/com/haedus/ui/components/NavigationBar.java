package com.haedus.ui.components;

import com.haedus.ui.model.Category;
import com.haedus.ui.util.RenderUtil;
import org.jetbrains.skija.Canvas;

import java.util.ArrayList;
import java.util.List;

/**
 * Component representing the top navigation bar with categories.
 */
public class NavigationBar extends UIComponent {
    private final List<Category> categories = new ArrayList<>();

    public void setCategories(List<Category> cats) {
        categories.clear();
        if (cats != null) categories.addAll(cats);
    }

    @Override
    public void render(Canvas canvas) {
        // Draw background
        RenderUtil.fillRect(canvas, rect(), RenderUtil.COLOR_BACKGROUND);

        float padding = 16f;
        float offsetX = x + padding;
        float offsetY = y + padding + 12f;

        // Draw each category title
        for (Category c : categories) {
            RenderUtil.drawText(canvas, c.getName(), offsetX, offsetY);
            offsetX += RenderUtil.measureText(c.getName()) + 32f;
        }
    }
}
