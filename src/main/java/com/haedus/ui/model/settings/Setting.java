package com.haedus.ui.model.settings;

/**
 * Base class for all module settings.
 */
public abstract class Setting {
    private final String name;
    private final String description;

    protected Setting(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }

    /** Returns a display string used in simple text rendering. */
    public abstract String getDisplayString();
}
