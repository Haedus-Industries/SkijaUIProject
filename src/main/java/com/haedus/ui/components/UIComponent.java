package com.haedus.ui.components;

import org.jetbrains.skija.Canvas;
import org.jetbrains.skija.Rect;

/**
 * Base class for all UI components drawn with Skija.
 */
public abstract class UIComponent {
    protected float x;
    protected float y;
    protected float width;
    protected float height;

    public float getX() { return x; }
    public float getY() { return y; }
    public float getWidth() { return width; }
    public float getHeight() { return height; }

    public void setBounds(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Render this component using the given canvas.
     */
    public abstract void render(Canvas canvas);

    /**
     * Convenience method to return this component's drawing rectangle.
     */
    protected Rect rect() {
        return Rect.makeXYWH(x, y, width, height);
    }
}
