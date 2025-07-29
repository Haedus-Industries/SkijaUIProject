package com.haedus.ui.model;

import com.haedus.ui.model.settings.Setting;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents an individual module with optional settings.
 */
public class Module {
    private final String name;
    private final String description;
    private boolean enabled;
    private final List<Setting> settings = new ArrayList<>();

    public Module(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }
    public boolean isEnabled() { return enabled; }

    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public List<Setting> getSettings() { return settings; }

    public void addSetting(Setting setting) {
        if (setting != null) settings.add(setting);
    }
}
