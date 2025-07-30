package com.haedus.ui.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Groups multiple settings under a single collapsible header.  Groups
 * correspond to the nested lists within the web UI and can be
 * independently expanded or collapsed.  The {@link expanded} flag
 * controls whether the settings should be rendered by the UI.
 */
public class SettingGroup {
    /** Name displayed above the settings */
    public final String name;
    /** Child settings contained within this group */
    public final List<Setting> settings = new ArrayList<>();
    /** Whether this group is expanded by default */
    public boolean expanded;

    public SettingGroup(String name) {
        this.name = name;
        this.expanded = false;
    }
}