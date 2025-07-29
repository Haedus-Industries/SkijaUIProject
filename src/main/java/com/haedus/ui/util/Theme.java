package com.haedus.ui.util;

import org.jetbrains.skija.Font;
import org.jetbrains.skija.Typeface;

/**
 * Shared fonts and color values matching the original web layout.
 */
public final class Theme {
    public static final Font FONT_REGULAR = new Font(Typeface.makeDefault(), 16);
    public static final Font FONT_TITLE = new Font(Typeface.makeDefault(), 20);

    private Theme() {}
}
