package com.haedus.ui.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for constructing dummy model objects.  In the original
 * web application the data is provided by backend API calls.  To
 * allow for stand‑alone rendering this class synthesises a few
 * categories with modules and settings mimicking the structure
 * described in the project documentation.
 */
public final class TestData {
    private TestData() {}

    /**
     * Generates a list of categories with nested modules and settings.
     * Titles, descriptions and values are intentionally generic; they
     * should be replaced with real data once connected to a backend.
     *
     * @return list of populated categories
     */
    public static List<Category> generate() {
        List<Category> cats = new ArrayList<>();

        // Category: Modules
        Category modulesCat = new Category("Modules");
        Module fly = new Module("Fly", "Allows flying through the air");
        fly.enabled = true;
        fly.pinned = true;
        // group
        SettingGroup flyGeneral = new SettingGroup("General");
        Setting flyEnabled = new Setting("fly_enabled", "Enabled", SettingType.BOOLEAN);
        flyEnabled.boolValue = true;
        flyGeneral.settings.add(flyEnabled);
        Setting flySpeed = new Setting("fly_speed", "Speed", SettingType.RANGE);
        flySpeed.minValue = 0f;
        flySpeed.maxValue = 10f;
        flySpeed.numericValue = 5f;
        flyGeneral.settings.add(flySpeed);
        fly.groups.add(flyGeneral);
        modulesCat.modules.add(fly);

        Module sprint = new Module("Sprint", "Run faster than normal");
        sprint.enabled = false;
        sprint.pinned = true;
        SettingGroup sprintGeneral = new SettingGroup("General");
        Setting sprintEnabled = new Setting("sprint_enabled", "Enabled", SettingType.BOOLEAN);
        sprintEnabled.boolValue = false;
        sprintGeneral.settings.add(sprintEnabled);
        Setting sprintSpeed = new Setting("sprint_speed", "Speed", SettingType.RANGE);
        sprintSpeed.minValue = 0f;
        sprintSpeed.maxValue = 10f;
        sprintSpeed.numericValue = 7f;
        sprintGeneral.settings.add(sprintSpeed);
        sprint.groups.add(sprintGeneral);
        modulesCat.modules.add(sprint);

        Module xray = new Module("XRay", "See through walls");
        xray.enabled = false;
        xray.pinned = false;
        SettingGroup xrayGeneral = new SettingGroup("General");
        Setting xrayEnabled = new Setting("xray_enabled", "Enabled", SettingType.BOOLEAN);
        xrayEnabled.boolValue = false;
        xrayGeneral.settings.add(xrayEnabled);
        xray.groups.add(xrayGeneral);
        modulesCat.modules.add(xray);

        cats.add(modulesCat);

        // Category: Configs
        Category configsCat = new Category("Configs");
        Module defaultCfg = new Module("Default", "Standard configuration");
        defaultCfg.enabled = true;
        defaultCfg.pinned = false;
        SettingGroup cfgGeneral = new SettingGroup("General");
        Setting difficulty = new Setting("cfg_difficulty", "Difficulty", SettingType.MODE);
        difficulty.options.add("Easy");
        difficulty.options.add("Normal");
        difficulty.options.add("Hard");
        difficulty.selectedOptionIndex = 1;
        cfgGeneral.settings.add(difficulty);
        defaultCfg.groups.add(cfgGeneral);
        configsCat.modules.add(defaultCfg);

        Module customCfg = new Module("Custom", "Personalised settings");
        customCfg.enabled = false;
        customCfg.pinned = false;
        SettingGroup customGeneral = new SettingGroup("General");
        Setting customName = new Setting("cfg_name", "Name", SettingType.TEXT);
        customName.stringValue = "MyPreset";
        customGeneral.settings.add(customName);
        customCfg.groups.add(customGeneral);
        configsCat.modules.add(customCfg);
        cats.add(configsCat);

        // Category: Help
        Category helpCat = new Category("Help");
        Module faqs = new Module("FAQs", "Commonly asked questions");
        helpCat.modules.add(faqs);
        Module docs = new Module("Documentation", "Read the docs");
        helpCat.modules.add(docs);
        cats.add(helpCat);

        return cats;
    }
}