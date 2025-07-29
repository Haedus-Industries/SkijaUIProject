package com.haedus.ui.model;

import java.util.ArrayList;
import java.util.List;

/**
 * A collection of modules displayed under a single navigation entry.
 */
public class Category {
    private final String name;
    private final String description;
    private final List<Module> modules = new ArrayList<>();

    public Category(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() { return name; }
    public String getDescription() { return description; }

    public List<Module> getModules() { return modules; }

    public void addModule(Module module) {
        if (module != null) modules.add(module);
    }
}
