# Java Skija UI Framework

This document outlines the proposed structure for recreating the existing web UI
using Java and Skija.  The goal is to mirror the HTML/CSS/JavaScript based
interface while keeping styling and behaviour consistent.

## Packages

- `com.haedus.ui`
  - Entry point and main window management.
- `com.haedus.ui.components`
  - Stand‑alone UI components such as the navigation bar, module panel and
    settings panel.
- `com.haedus.ui.model`
  - Plain Java objects describing categories, modules and settings.
- `com.haedus.ui.util`
  - Rendering helpers and shared fonts/colours for consistent layout.

## Rendering

`MainWindow` sets up a Skija window and holds instances of each component.  Each
component extends `UIComponent` which exposes `render(Canvas)` and handles its
position and size.  `RenderUtil` wraps common drawing calls so margins and fonts
match the original CSS styling.  All text uses the `Inter` font family where
available.

## Behaviour

The JavaScript contained extensive logic for building modules, managing drag and
drop, and showing grouped settings.  The Java classes mirror this by storing
module data in model objects.  Interaction handlers (mouse clicks, drag events,
keyboard shortcuts) would be added in the real implementation but are omitted
here for brevity.

`MainWindow` currently populates dummy data in `generateTestData()` replicating
the `generateTestData` function from `navigation.js`.  This ensures the Skija
version can produce the same layout of categories and modules.
