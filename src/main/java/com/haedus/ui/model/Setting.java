package com.haedus.ui.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A single configurable parameter belonging to a module.  Settings can
 * represent booleans, numeric ranges, mode selectors, free form text
 * inputs or colours.  The appropriate fields should be populated
 * depending on the {@link SettingType}.  See {@link SettingType} for
 * descriptions of each variant.
 */
public class Setting {
    /** Unique key used when communicating values back to the backend */
    public final String key;
    /** Human readable label shown alongside the control */
    public final String name;
    /** The kind of control to render */
    public final SettingType type;
    /** Current boolean state (used when {@code type == BOOLEAN}) */
    public boolean boolValue;
    /** Current numeric state (used when {@code type == RANGE}) */
    public float numericValue;
    /** Minimum allowed numeric value */
    public float minValue;
    /** Maximum allowed numeric value */
    public float maxValue;
    /** Free form text (used when {@code type == TEXT}) */
    public String stringValue;
    /** ARGB colour (used when {@code type == COLOR}) */
    public int colorValue;
    /** List of mode options (used when {@code type == MODE}) */
    public final List<String> options;
    /** Currently selected option index (used when {@code type == MODE}) */
    public int selectedOptionIndex;

    /**
     * Creates a new setting of the specified type.  Some fields such as
     * {@code options}, {@code minValue} and {@code maxValue} are only
     * meaningful for particular types; however they are still present so
     * that values can be assigned uniformly when generating dummy data.
     *
     * @param key    internal identifier
     * @param name   visible label
     * @param type   setting kind
     */
    public Setting(String key, String name, SettingType type) {
        this.key = key;
        this.name = name;
        this.type = type;
        this.boolValue = false;
        this.numericValue = 0f;
        this.minValue = 0f;
        this.maxValue = 1f;
        this.stringValue = "";
        this.colorValue = 0xFFFFFFFF;
        this.options = new ArrayList<>();
        this.selectedOptionIndex = 0;
    }
}