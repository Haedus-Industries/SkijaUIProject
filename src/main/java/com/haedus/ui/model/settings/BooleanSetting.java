package com.haedus.ui.model.settings;

public class BooleanSetting extends Setting {
    private boolean value;

    public BooleanSetting(String name, String description, boolean value) {
        super(name, description);
        this.value = value;
    }

    public boolean isValue() { return value; }
    public void setValue(boolean value) { this.value = value; }

    @Override
    public String getDisplayString() {
        return String.format("%s: %s", getName(), value);
    }
}
