package com.haedus.ui.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user togglable module within a category.  Modules are
 * displayed in the centre panel and may also appear in the pinned
 * modules column when {@link #pinned} is true.  Each module contains
 * one or more {@link SettingGroup}s controlling its behaviour.
 */
public class Module {
    /** Display name */
    public final String title;
    /** Short description shown under the title */
    public final String description;
    /** Whether this module is currently enabled */
    public boolean enabled;
    /** Whether this module appears in the pinned section */
    public boolean pinned;
    /** Settings organised by group */
    public final List<SettingGroup> groups = new ArrayList<>();

    public Module(String title, String description) {
        this.title = title;
        this.description = description;
        this.enabled = false;
        this.pinned = false;
    }
}