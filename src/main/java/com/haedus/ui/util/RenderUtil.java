package com.haedus.ui.util;

import org.jetbrains.skija.Canvas;
import org.jetbrains.skija.Font;
import org.jetbrains.skija.Paint;
import org.jetbrains.skija.Rect;
import org.jetbrains.skija.PaintMode;

/**
 * Utility helpers for drawing shapes and text consistently.
 */
public final class RenderUtil {
    private static final Paint PAINT = new Paint().setAntiAlias(true);
    private static final Font FONT = new Font(Font.getDefault().getTypeface(), 16);

    public static final int COLOR_BACKGROUND = 0xFFF5F5F5;
    public static final int COLOR_PANEL = 0xFFFFFFFF;
    public static final float LINE_HEIGHT = 20f;

    private RenderUtil() {}

    public static void fillRect(Canvas canvas, Rect rect, int color) {
        PAINT.setMode(PaintMode.FILL);
        PAINT.setColor(color);
        canvas.drawRect(rect, PAINT);
    }

    public static void drawText(Canvas canvas, String text, float x, float y) {
        PAINT.setColor(0xFF000000);
        canvas.drawString(text, x, y, FONT, PAINT);
    }

    public static float measureText(String text) {
        return FONT.measureText(text);
    }
}
