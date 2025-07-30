package com.haedus.ui.components;

import io.github.humbleui.skija.Canvas;

/**
 * Abstract base class for all renderable UI elements.  Each
 * component stores its position and dimensions, and provides a
 * template method for drawing onto a {@link Canvas}.  Subclasses
 * implement the {@link #render(Canvas)} method to perform their
 * custom painting.  Components do not manage child layout;
 * positioning is controlled by the parent when the component is
 * instantiated.
 */
public abstract class UIComponent {
    protected float x;
    protected float y;
    protected float width;
    protected float height;

    protected UIComponent(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Updates the position and size of this component.  Parents
     * should call this method whenever the window is resized to
     * reposition children accordingly.
     *
     * @param x      new x coordinate
     * @param y      new y coordinate
     * @param width  new width
     * @param height new height
     */
    public void setBounds(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Draws this component.  Implementations should not translate the
     * canvas; instead they should offset draw calls by {@link #x} and
     * {@link #y}.  The main window resets the canvas origin on each
     * frame so absolute coordinates can be used.
     *
     * @param canvas the surface to draw on
     */
    public abstract void render(Canvas canvas);
}