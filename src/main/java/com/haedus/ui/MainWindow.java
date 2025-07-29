package com.haedus.ui;

import com.haedus.ui.components.NavigationBar;
import com.haedus.ui.components.ModulePanel;
import com.haedus.ui.components.SettingsPanel;
import com.haedus.ui.model.Category;
import com.haedus.ui.model.Module;
import com.haedus.ui.model.settings.Setting;

import java.util.ArrayList;
import java.util.List;

/**
 * Main application window that sets up Skija rendering and manages UI components.
 * This is a simplified skeleton demonstrating how the web UI can be ported to Skija.
 */
public class MainWindow {
    private final List<Category> categories = new ArrayList<>();
    private final NavigationBar navigationBar;
    private final ModulePanel modulePanel;
    private final SettingsPanel settingsPanel;

    public MainWindow() {
        // Initialize UI components
        navigationBar = new NavigationBar();
        modulePanel = new ModulePanel();
        settingsPanel = new SettingsPanel();
    }

    /** Start the main rendering loop. */
    public void start() {
        // Placeholder for Skija window setup. In a real implementation we would
        // create a JWM window and continuously draw UI components using Skija's Canvas.
        System.out.println("Starting Skija UI window...");

        // Load some test data just like the JavaScript generateTestData() call.
        generateTestData();

        // The real rendering loop would go here.
        // For this skeleton we simply output the loaded structure.
        System.out.println("Categories loaded: " + categories.size());
        for (Category cat : categories) {
            System.out.println("- " + cat.getName() + " (" + cat.getModules().size() + " modules)");
        }
    }

    /**
     * Generates dummy data to demonstrate how the JS logic can be replicated
     * in Java classes.
     */
    private void generateTestData() {
        Category combat = new Category("Combat", "Combat enhancement modules");
        Module autoAttack = new Module("AutoAttack", "Automatically attack nearby enemies");
        autoAttack.setEnabled(true);
        combat.addModule(autoAttack);

        Module killAura = new Module("KillAura", "Attack multiple enemies simultaneously");
        combat.addModule(killAura);

        categories.add(combat);

        navigationBar.setCategories(categories);
        modulePanel.setModules(combat.getModules());
    }
}
