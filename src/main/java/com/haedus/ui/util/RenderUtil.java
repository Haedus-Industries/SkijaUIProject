package com.haedus.ui.util;

import io.github.humbleui.skija.Canvas;
import io.github.humbleui.skija.Paint;
import io.github.humbleui.skija.*;
import io.github.humbleui.types.*;
import io.github.humbleui.skija.Font;
// Point is not currently used; omit its import.
import io.github.humbleui.skija.PaintMode;

/**
 * Collection of convenience methods to draw common shapes and text on a
 * {@link Canvas}.  Wrapping Skija calls in static helpers reduces
 * boilerplate in the UI components and centralises anti‑alias,
 * colour and stroke handling so that changes to the rendering
 * pipeline can be made in one place.
 */
public final class RenderUtil {
    private RenderUtil() {}

    /**
     * Draws a rounded rectangle with optional fill and stroke.  Passing
     * {@code null} for {@code fillColor} or {@code strokeColor} will skip
     * filling or stroking respectively.  Anti‑aliasing is enabled
     * automatically to smooth edges.
     *
     * @param canvas       target canvas
     * @param x            left coordinate
     * @param y            top coordinate
     * @param w            width
     * @param h            height
     * @param radius       radius for all four corners
     * @param fillColor    ARGB colour to fill, or {@code 0} to disable
     * @param strokeColor  ARGB colour to stroke, or {@code 0} to disable
     * @param strokeWidth  width of the stroke in pixels
     */
    public static void drawRoundedRect(Canvas canvas, float x, float y, float w, float h,
                                       float radius, int fillColor, int strokeColor, float strokeWidth) {
        RRect rrect = RRect.makeXYWH(x, y, w, h, radius);
        if (fillColor != 0) {
            try (Paint paint = new Paint()) {
                paint.setColor(fillColor);
                paint.setAntiAlias(true);
                canvas.drawRRect(rrect, paint);
            }
        }
        if (strokeColor != 0 && strokeWidth > 0f) {
            try (Paint paint = new Paint()) {
                paint.setColor(strokeColor);
                paint.setMode(PaintMode.STROKE);
                paint.setStrokeWidth(strokeWidth);
                paint.setAntiAlias(true);
                canvas.drawRRect(rrect, paint);
            }
        }
    }

    /**
     * Draws a simple rectangle.  Provided primarily for completeness –
     * most rounded rectangles can be drawn with radius zero instead.
     */
    public static void drawRect(Canvas canvas, float x, float y, float w, float h,
                                int fillColor, int strokeColor, float strokeWidth) {
        Rect rect = Rect.makeXYWH(x, y, w, h);
        if (fillColor != 0) {
            try (Paint paint = new Paint()) {
                paint.setColor(fillColor);
                paint.setAntiAlias(true);
                canvas.drawRect(rect, paint);
            }
        }
        if (strokeColor != 0 && strokeWidth > 0f) {
            try (Paint paint = new Paint()) {
                paint.setColor(strokeColor);
                paint.setMode(PaintMode.STROKE);
                paint.setStrokeWidth(strokeWidth);
                paint.setAntiAlias(true);
                canvas.drawRect(rect, paint);
            }
        }
    }

    /**
     * Draws text at the specified position.  Text is aligned such that
     * the baseline is at {@code y}.  For multi‑line text consider
     * using {@link io.github.humbleui.skija.paragraph.Paragraph}.  For
     * performance reasons a new Paint is created on each call; if
     * drawing many strings consider caching the Paint instance.
     *
     * @param canvas target canvas
     * @param text   string to draw
     * @param x      horizontal position
     * @param y      baseline vertical position
     * @param font   font describing face and size
     * @param color  ARGB colour
     */
    public static void drawText(Canvas canvas, String text, float x, float y, Font font, int color) {
        try (Paint paint = new Paint()) {
            paint.setColor(color);
            paint.setAntiAlias(true);
            canvas.drawString(text, x, y, font, paint);
        }
    }

    /**
     * Draws a filled circle.  Useful for toggles and indicators.
     *
     * @param canvas target canvas
     * @param cx     centre x
     * @param cy     centre y
     * @param radius radius in pixels
     * @param color  fill colour
     */
    public static void drawCircle(Canvas canvas, float cx, float cy, float radius, int color) {
        try (Paint paint = new Paint()) {
            paint.setColor(color);
            paint.setAntiAlias(true);
            canvas.drawCircle(cx, cy, radius, paint);
        }
    }
}