package com.haedus.ui.model.settings;

import java.util.List;

public class ModeSetting extends Setting {
    private String value;
    private final List<String> modes;

    public ModeSetting(String name, String description, String value, List<String> modes) {
        super(name, description);
        this.value = value;
        this.modes = modes;
    }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }
    public List<String> getModes() { return modes; }

    @Override
    public String getDisplayString() {
        return String.format("%s: %s", getName(), value);
    }
}
