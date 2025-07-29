package com.haedus.ui.model.settings;

public class TextSetting extends Setting {
    private String value;

    public TextSetting(String name, String description, String value) {
        super(name, description);
        this.value = value;
    }

    public String getValue() { return value; }
    public void setValue(String value) { this.value = value; }

    @Override
    public String getDisplayString() {
        return String.format("%s: %s", getName(), value);
    }
}
