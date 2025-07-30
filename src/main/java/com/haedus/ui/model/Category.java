package com.haedus.ui.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Top level grouping used by the navigation bar.  Each category can
 * contain multiple modules.  Categories roughly correspond to the
 * first level menu items in the HTML navigation bar.  Selecting a
 * category in the UI would normally filter the modules displayed,
 * however this simplified implementation shows modules from all
 * categories in a single list.
 */
public class Category {
    /** Display name */
    public final String name;
    /** Modules belonging to this category */
    public final List<Module> modules = new ArrayList<>();

    public Category(String name) {
        this.name = name;
    }
}