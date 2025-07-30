package com.haedus.ui.util;

/**
 * Defines a handful of ARGB colours used throughout the UI.  The values
 * have been selected to roughly match the styling defined in
 * {@code theme.css} and {@code style.css} of the original web
 * interface.  Should the design evolve, updating a colour here will
 * automatically propagate to all draw calls.
 */
public final class ColorPalette {
    private ColorPalette() {}

    /** Main window background */
    public static final int BACKGROUND       = 0xFFF3F4F6;
    /** Navigation bar fill */
    public static final int NAV_BACKGROUND   = 0xFFFFFFFF;
    /** Navigation bar border (10% black) */
    public static final int NAV_BORDER       = 0x1A131313;
    /** Card backgrounds (modules, settings) */
    public static final int CARD_BACKGROUND  = 0xFFFFFFFF;
    /** Card border */
    public static final int CARD_BORDER      = 0x1A131313;
    /** Active highlight/accent (light green) */
    public static final int ACCENT           = 0xFF96E0C7;
    /** Primary text colour */
    public static final int TEXT_PRIMARY     = 0xFF262E35;
    /** Secondary text colour */
    public static final int TEXT_SECONDARY   = 0xFF5B617A;
    /** Toggle enabled background */
    public static final int TOGGLE_ENABLED   = 0xFF21CC4C;
    /** Toggle disabled background */
    public static final int TOGGLE_DISABLED  = 0xFFC6CCD0;
}