package com.haedus.ui.model;

/**
 * Enumeration describing the different kinds of settings available for
 * modules.  Mirrored from the HTML/JavaScript implementation where
 * toggles, ranges, modes, text inputs and colour pickers appear.  New
 * setting types can be added here without affecting rendering code
 * thanks to the type‑safe switch statements used in the UI layer.
 */
public enum SettingType {
    /** Boolean on/off toggle */
    BOOLEAN,
    /** Numeric slider constrained between {@link Setting#minValue} and {@link Setting#maxValue} */
    RANGE,
    /** One of a finite set of options */
    MODE,
    /** Arbitrary text input */
    TEXT,
    /** ARGB colour value */
    COLOR;
}