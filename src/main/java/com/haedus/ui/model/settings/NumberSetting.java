package com.haedus.ui.model.settings;

public class NumberSetting extends Setting {
    private double value;
    private final double min;
    private final double max;
    private final double step;

    public NumberSetting(String name, String description, double value, double min, double max, double step) {
        super(name, description);
        this.value = value;
        this.min = min;
        this.max = max;
        this.step = step;
    }

    public double getValue() { return value; }
    public void setValue(double value) { this.value = value; }

    @Override
    public String getDisplayString() {
        return String.format("%s: %.2f", getName(), value);
    }
}
