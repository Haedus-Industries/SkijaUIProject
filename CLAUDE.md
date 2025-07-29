# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Java-based project that integrates Skija (Skia Java bindings) for native graphics rendering with a web-based user interface. The project creates a desktop application with embedded web content that mimics a Hex-Rays styled interface.

**Key Technologies:**
- Java 23 with Maven build system
- Skija (io.github.humbleui:skija-windows-x64:0.116.4) for native graphics
- Web frontend with HTML/CSS/JavaScript for the UI layer
- CEF (Chromium Embedded Framework) integration via `window.cefQuery`

## Build and Development Commands

**Build the project:**
```bash
mvn compile
```

**Run tests:**
```bash
mvn test
```

**Package the application:**
```bash
mvn package
```

**Clean build artifacts:**
```bash
mvn clean
```

## Architecture

### Backend (Java)
- Uses Skija for native graphics rendering on Windows x64
- Provides an API interface that the web UI communicates with via CEF queries
- Main Java source files should be located in `src/main/java` (currently empty)

### Frontend (Web UI)
- Located in `src/main/resources/web/`
- **index.html**: Main UI template with navigation, modules, and settings components
- **navigation.js**: Core JavaScript handling UI interactions, module management, and CEF communication
- **style.css, theme.css, icons_and_fonts.css**: Styling and theming

### Communication Layer
The web frontend communicates with the Java backend through CEF queries using these API endpoints:
- `clickui/cats` - Retrieve categories
- `clickui/mods?{category}` - Get modules for a category  
- `clickui/values?{module}` - Get values/settings for a module
- `clickui/set?{module},{key},{value}` - Set a module setting value

### Key Frontend Components
- **Module System**: Toggleable modules with drag-and-drop functionality
- **Settings Management**: Dynamic settings with support for:
  - Boolean toggles (checkboxes)
  - Numeric ranges (sliders)
  - Mode selection (radio buttons)
  - Text inputs
  - Color settings
- **Navigation**: Multi-level dropdown menus with hover effects
- **Pinned Modules**: Drag-and-drop module organization

## Development Notes

- The project targets Java 23 and requires Maven for dependency management
- Web assets are served from `src/main/resources/web/`
- The UI is designed to mimic Hex-Rays styling and branding
- CEF integration allows bidirectional communication between Java and JavaScript
- Module states persist through the backend API calls
- The interface supports both desktop navigation and responsive design patterns