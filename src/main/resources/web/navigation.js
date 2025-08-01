document.addEventListener("DOMContentLoaded", function () {
    function setSubmenuWidthsAndPositions() {
        const wrapperWidth = document.querySelector(".nav-desktop .submenus-wrapper").offsetWidth;
        const submenuCount = document.querySelectorAll(".nav-desktop .top-level-menu > li.has-submenu").length;
        const submenus = document.querySelectorAll(".nav-desktop .submenus");
        submenus.forEach(function (submenu) {
            submenu.style.width = (wrapperWidth * submenuCount) + "px";
        });

        document.querySelectorAll(".nav-desktop .submenu").forEach(function (submenu) {
            submenu.style.width = wrapperWidth + "px";
        });
    }

    function adjustNavigationHeight(show) {
        const baseHeight = document.querySelector(".nav-desktop .level-0").offsetHeight;
        let submenuHeight = 0;

        if (show) {
            const activeSubmenuId = document.querySelectorAll(".nav-desktop .top-level-menu > li.has-submenu")[show - 1].getAttribute("data-submenu");
            submenuHeight = document.querySelector("#" + activeSubmenuId).offsetHeight;
        }

        const navDesktop = document.querySelector(".nav-desktop");
        navDesktop.style.height = (baseHeight + submenuHeight) + "px";
    }

    // Setting up the menu indicator
    const firstSubmenu = document.querySelector(".nav-desktop .top-level-menu > li.has-submenu");
    const menuIndicator = document.querySelector(".nav-desktop .menu-indicator");
    menuIndicator.style.width = firstSubmenu.offsetWidth + "px";
    menuIndicator.style.left = "0";
    menuIndicator.style.height = firstSubmenu.offsetHeight + "px";
    menuIndicator.style.opacity = "0";

    // Store link click event
    document.getElementById("store-link").addEventListener("click", function () {
        gtag("event", "view_cart", {});
    });

    // Button click event
    document.querySelector(".navigation-wrapper .primary-button").addEventListener("click", function () {
        window.dataLayer = window.dataLayer || [];
        window.dataLayer.push({
            event: "button_click",
            label: "My account",
            source: "Website navigation"
        });
    });

    // Setting widths and positions
    setSubmenuWidthsAndPositions();
    window.addEventListener("resize", setSubmenuWidthsAndPositions);

    // Hover event for desktop menu
    document.querySelectorAll(".nav-desktop .top-level-menu > li").forEach(function (item, index) {
        item.addEventListener("mouseenter", function () {
            document.querySelectorAll(".nav-desktop .top-level-menu > li").forEach(function (li) {
                li.classList.remove("submenu-opened");
            });

            if (item.classList.contains("has-submenu")) {
                const submenuIndex = Array.from(document.querySelectorAll(".nav-desktop .top-level-menu > li.has-submenu")).indexOf(item);
                const translateValue = -submenuIndex * document.querySelector(".submenus-wrapper").offsetWidth;
                document.querySelector(".nav-desktop .submenus").style.transform = "translateX(" + translateValue + "px)";
                setTimeout(function () {
                    document.querySelector(".nav-desktop .submenus-wrapper").style.opacity = "1";
                }, 500);
                adjustNavigationHeight(submenuIndex + 1);
                item.classList.add("submenu-opened");
            } else {
                adjustNavigationHeight();
            }


            // Updated position calculation for menu indicator
            const itemRect = item.getBoundingClientRect();  // Get the bounding rectangle of the current item
            const level0Rect = document.querySelector(".nav-desktop .level-0").getBoundingClientRect(); // Get the position of the .level-0 container
            const toplevelRect = document.querySelector(".nav-desktop .top-level-menu").getBoundingClientRect(); // Get the position of the .top-level-menu container
            const leftPosition = itemRect.left - toplevelRect.left; // this should be the abs left pos of the selected item


            menuIndicator.style.width = item.offsetWidth + "px";
            menuIndicator.style.left = leftPosition + "px"; // Corrected left position
            menuIndicator.style.height = item.offsetHeight + "px";
            menuIndicator.style.opacity = "1";

        });
    });

    // Mouseleave event for navigation wrapper
    document.querySelector(".navigation-wrapper").addEventListener("mouseleave", function () {
        document.querySelectorAll(".nav-desktop .top-level-menu > li").forEach(function (item) {
            item.classList.remove("submenu-opened");
        });
        setTimeout(function () {
            document.querySelector(".nav-desktop .submenus-wrapper").style.opacity = "0";
        }, 0);
        adjustNavigationHeight();
        menuIndicator.style.opacity = "0";
    });

    // Mobile submenu toggle
    document.querySelectorAll(".nav-mobile .has-submenu > div, .nav-mobile .has-submenu > div i, .nav-mobile .has-submenu > a > i").forEach(function (element) {
        element.addEventListener("click", function (e) {
            e.preventDefault();
            e.stopPropagation();
            const parentLi = this.closest(".top-level-menu-link");
            const mobileSubmenu = parentLi.querySelector(".mobile-submenu");
            mobileSubmenu.style.display = mobileSubmenu.style.display === "block" ? "none" : "block";
            parentLi.classList.toggle("opened");
        });
    });

    // Prevent propagation on submenu links
    document.querySelectorAll(".nav-mobile .has-submenu a").forEach(function (link) {
        link.addEventListener("click", function (e) {
            if (!this.querySelector("i")) {
                e.stopPropagation();
            }
        });
    });

    // Use event delegation on the container that holds all setting groups.
    const settingsContainer = document.querySelector(".settings-column");
    if (settingsContainer) {
        settingsContainer.addEventListener("click", function (e) {
            // Check if the click came from or inside a .setting-group-title element.
            const groupTitle = e.target.closest(".setting-group-title");
            if (!groupTitle || !settingsContainer.contains(groupTitle)) return;

            console.log("Setting group clicked:", groupTitle);
            const settingGroup = groupTitle.parentElement;
            const settingWrapper = settingGroup.querySelector(".setting-wrapper");

            console.log("Selected settingGroup:", settingGroup);
            console.log("Selected settingWrapper:", settingWrapper);

            if (settingWrapper) {
                settingGroup.classList.toggle("expanded");
                settingWrapper.classList.toggle("expanded");
                const icon = groupTitle.querySelector("i");
                if (icon) {
                    icon.classList.toggle("icon-chevron-up");
                }
                adjustNavigationHeight(2); // Update navigation height if needed.
            } else {
                console.error("settingWrapper not found for", settingGroup);
            }
        });
    } else {
        console.error("Settings container not found.");
    }

    // Add event delegation for config groups in submenu-4
    const configsContainer = document.querySelector("#submenu-4 .configs-container");
    if (configsContainer) {
        configsContainer.addEventListener("click", function (e) {
            // Check if the click came from or inside a .setting-group-title element
            const groupTitle = e.target.closest(".setting-group-title");
            if (!groupTitle || !configsContainer.contains(groupTitle)) return;

            console.log("Config group clicked:", groupTitle);
            const settingGroup = groupTitle.parentElement;
            const settingWrapper = settingGroup.querySelector(".setting-wrapper");

            console.log("Selected config group:", settingGroup);
            console.log("Selected config wrapper:", settingWrapper);

            if (settingWrapper) {
                settingGroup.classList.toggle("expanded");
                settingWrapper.classList.toggle("expanded");
                const icon = groupTitle.querySelector("i");
                if (icon) {
                    icon.classList.toggle("icon-chevron-up");
                }
                adjustNavigationHeight(4); // Update navigation height for submenu-4
            } else {
                console.error("Config wrapper not found for", settingGroup);
            }
        });
    } else {
        console.error("Configs container not found.");
    }

    function createModuleElement({ title, description, link = "/", enabled = false }) {
        const template = document.getElementById("module-template");
        if (!template) {
            console.error("Module template not found");
            return null;
        }
        const clone = document.importNode(template.content, true);
        const moduleElement = clone.firstElementChild;
        const anchor = clone.querySelector("a");

        // Set module properties
        anchor.href = link;
        const titleEl = clone.querySelector(".module-title");
        if (titleEl) titleEl.textContent = title;
        const descEl = clone.querySelector(".description");
        if (descEl) descEl.textContent = description;

        // Set initial state
        moduleElement.setAttribute('data-enabled', enabled);
        if (enabled) {
            moduleElement.classList.add('module-enabled');
        }

        // Add click handler to toggle enabled state
        moduleElement.addEventListener('click', function(e) {
            e.preventDefault();
            e.stopPropagation();

            const currentState = this.getAttribute('data-enabled') === 'true';
            const newState = !currentState;
            this.setAttribute('data-enabled', newState);

            if (newState) {
                this.classList.add('module-enabled');
                console.log(`Module "${title}" enabled`);
            } else {
                this.classList.remove('module-enabled');
                console.log(`Module "${title}" disabled`);
            }
        });

        return moduleElement;
    }

//     apis to this ui
    function createCategoryElement({ title, description, link = "/" }) {
        const template = document.getElementById("module-template");
        if (!template) {
            console.error("Module template not found");
            return null;
        }
        const clone = document.importNode(template.content, true);
        const moduleElement = clone.firstElementChild;
        const anchor = clone.querySelector("a");

        // Set module properties
        anchor.href = link;
        const titleEl = clone.querySelector(".module-title");
        if (titleEl) titleEl.textContent = title;
        const descEl = clone.querySelector(".description");
        if (descEl) descEl.textContent = description;

        // Categories are not toggleable - no click handler or enabled state
        moduleElement.classList.add('category-item');

        return moduleElement;
    }

    function addCategory(name, description) {
        const moduleEl = createCategoryElement({ title: name, description, link: "/" });
        if (!moduleEl) return;
        const parentContainer = document.querySelector(".column.categories");
        const highlightedCategories = parentContainer.querySelector(".categories.highlighted");
        parentContainer.insertBefore(moduleEl, highlightedCategories);
    }


    function addPinnedModule(name, description) {
        const moduleEl = createModuleElement({ title: name, description, link: "/" });
        if (!moduleEl) return;
        const parentContainer = document.querySelector(".pinned-content");
        // check if the module already exist
        const existingModule = parentContainer.querySelector(`.module a[title="${name}"]`);
        if (existingModule) {
            console.error("Module already exists:", name);
            return;
        }

        parentContainer.appendChild(moduleEl);
    }

    function addToggleModule(title, description, link = "/") {
        const moduleEl = createModuleElement({ title, description, link });
        if (!moduleEl) return;
        const container = document.querySelector(".modules.module-container");
        if (!container) {
            console.error("Module container not found.");
            return;
        }
        container.appendChild(moduleEl);
    }


    function createSettingGroup(groupTitle, settingsArray = []) {
        const template = document.getElementById("setting-group-template");
        if (!template) {
            console.error("Setting group template not found");
            return null;
        }
        const clone = document.importNode(template.content, true);
        // Set the group title
        const titleEl = clone.querySelector(".setting-group-title .Title-text");
        if (titleEl) titleEl.textContent = groupTitle;
        // Add each setting element if provided
        const wrapper = clone.querySelector(".setting-wrapper");
        if (wrapper && Array.isArray(settingsArray)) {
            settingsArray.forEach(settingObj => {
                const settingEl = createSettingElement(settingObj);
                if (settingEl) wrapper.appendChild(settingEl);
            });
        }
        return clone.firstElementChild;
    }

    function createSettingElement({ title, description, value }) {
        const template = document.getElementById("setting-element-template");
        if (!template) {
            console.error("Setting element template not found");
            return null;
        }
        const clone = document.importNode(template.content, true);
        const titleEl = clone.querySelector(".setting-title");
        if (titleEl) titleEl.textContent = title;
        const descEl = clone.querySelector(".setting-description");
        if (descEl) descEl.textContent = description;
        const valueEl = clone.querySelector(".setting-value");
        if (valueEl) valueEl.textContent = value;
        return clone.firstElementChild;
    }

    /**
     * Adds a single setting to an existing setting group.
     * @param {HTMLElement} groupElement - The setting group element created by createSettingGroup.
     * @param {Object} settingObj - An object with properties: title, description, value.
     */
    function addSettingToGroup(groupElement, settingObj) {
        // Find the setting-wrapper inside the group.
        const wrapper = groupElement.querySelector(".setting-wrapper");
        if (!wrapper) {
            console.error("No setting-wrapper found in the provided group element.");
            return;
        }

        const newSetting = createSettingElement(settingObj);
        wrapper.appendChild(newSetting);
    }

    function createConfigGroup(groupTitle, settingsArray = []) {
        const template = document.getElementById("setting-group-template");
        if (!template) {
            console.error("Config group template not found");
            return null;
        }
        const clone = document.importNode(template.content, true);
        // Set the group title
        const titleEl = clone.querySelector(".setting-group-title .Title-text");
        if (titleEl) titleEl.textContent = groupTitle;
        // Add each setting element if provided
        const wrapper = clone.querySelector(".setting-wrapper");
        if (wrapper && Array.isArray(settingsArray)) {
            settingsArray.forEach(settingObj => {
                const settingEl = createModuleSetting(settingObj);
                if (settingEl) wrapper.appendChild(settingEl);
            });
        }
        return clone.firstElementChild;
    }

    function addConfigGroup(groupTitle, settingsArray = []) {
        const configGroup = createConfigGroup(groupTitle, settingsArray);
        if (!configGroup) return;
        
        const configsContainer = document.querySelector("#submenu-4 .configs-container");
        if (!configsContainer) {
            console.error("Configs container not found.");
            return;
        }
        
        configsContainer.appendChild(configGroup);
    }

    // Store module settings data
    const moduleSettingsData = {};

    function createSettingFromData(moduleName, key, data) {
        let [type, name] = key.split(":");
        let settingElement;
        
        switch (type) {
            case "bool":
                settingElement = createModuleSetting({
                    type: 'checkbox',
                    title: name,
                    description: data.description,
                    value: data.value
                });
                if (settingElement) {
                    const checkbox = settingElement.querySelector('input[type="checkbox"]');
                    if (checkbox) {
                        checkbox.addEventListener('change', () => {
                            console.log(`${moduleName}.${key} = ${checkbox.checked}`);
                        });
                    }
                }
                break;
            case "number":
                settingElement = createModuleSetting({
                    type: 'range',
                    title: name,
                    description: data.description,
                    value: parseFloat(data.value),
                    options: {
                        min: parseFloat(data.min),
                        max: parseFloat(data.max),
                        step: parseFloat(data.inc)
                    }
                });
                if (settingElement) {
                    const slider = settingElement.querySelector('input[type="range"]');
                    if (slider) {
                        slider.addEventListener('change', () => {
                            console.log(`${moduleName}.${key} = ${slider.value}`);
                        });
                    }
                }
                break;
            case "mode":
                const options = data.modes.map(mode => ({
                    label: mode,
                    value: mode
                }));
                settingElement = createModuleSetting({
                    type: 'radio',
                    title: name,
                    description: data.description,
                    value: data.value,
                    options: {
                        name: `${moduleName}-${name}`,
                        options: options
                    }
                });
                if (settingElement) {
                    const radioInputs = settingElement.querySelectorAll('input[type="radio"]');
                    radioInputs.forEach(input => {
                        input.addEventListener('change', () => {
                            if (input.checked) {
                                console.log(`${moduleName}.${key} = ${input.value}`);
                            }
                        });
                    });
                }
                break;
            case "text":
                settingElement = createModuleSetting({
                    type: 'text',
                    title: name,
                    description: data.description,
                    value: data.value
                });
                if (settingElement) {
                    const textInput = settingElement.querySelector('input[type="text"]');
                    if (textInput) {
                        textInput.addEventListener('change', () => {
                            console.log(`${moduleName}.${key} = ${textInput.value}`);
                        });
                    }
                }
                break;
        }
        
        return settingElement;
    }

    function showModuleSettings(moduleName) {
        const settingsContainer = document.querySelector("#submenu-3 .settings-wrapper");
        if (!settingsContainer) return;

        // Clear existing settings
        settingsContainer.innerHTML = '';

        // Get settings for this module
        const moduleData = moduleSettingsData[moduleName];
        if (!moduleData) {
            settingsContainer.innerHTML = '<p style="padding: 20px; text-align: center; color: #888;">No settings available for this module</p>';
            return;
        }

        // Add module title
        const titleDiv = document.createElement('div');
        titleDiv.innerHTML = `<h3 style="margin: 0 0 16px 0; padding: 0 16px; color: rgba(38, 46, 53, 1.0);">${moduleName} Settings</h3>`;
        settingsContainer.appendChild(titleDiv);

        // Handle both individual settings and grouped settings
        if (moduleData.settings) {
            // New format with groups and individual settings
            
            // Add individual settings first
            if (moduleData.individual) {
                Object.entries(moduleData.individual).forEach(([key, data]) => {
                    const settingElement = createSettingFromData(moduleName, key, data);
                    if (settingElement) {
                        settingsContainer.appendChild(settingElement);
                    }
                });
            }
            
            // Add grouped settings
            if (moduleData.groups) {
                Object.entries(moduleData.groups).forEach(([groupName, groupSettings]) => {
                    const settingGroup = createSettingGroup(groupName);
                    if (settingGroup) {
                        const wrapper = settingGroup.querySelector('.setting-wrapper');
                        if (wrapper) {
                            Object.entries(groupSettings).forEach(([key, data]) => {
                                const settingElement = createSettingFromData(moduleName, key, data);
                                if (settingElement) {
                                    wrapper.appendChild(settingElement);
                                }
                            });
                        }
                        settingsContainer.appendChild(settingGroup);
                    }
                });
            }
        } else {
            // Legacy format - treat all as individual settings
            Object.entries(moduleData).forEach(([key, data]) => {
                const settingElement = createSettingFromData(moduleName, key, data);
                if (settingElement) {
                    settingsContainer.appendChild(settingElement);
                }
            });
        }
    }

    function createModule(title, description, link = "/") {
        // Locate the container where modules for submenu-3 are placed.
        const container = document.querySelector("#submenu-3 .sidemodules .modules-wrapper");
        if (!container) {
            console.error("Module container in submenu-3 not found.");
            return;
        }

        // Create a new module element.
        const moduleDiv = document.createElement("div");
        moduleDiv.classList.add("module");

        // Create an anchor element that wraps the module content.
        const anchor = document.createElement("a");
        anchor.href = link;
        // Set the text content of the anchor to the module title.
        anchor.appendChild(document.createTextNode(title));

        // Create a paragraph element for the description and assign the "description" class.
        const descriptionP = document.createElement("p");
        descriptionP.classList.add("description");
        descriptionP.textContent = description;

        // Append the description to the anchor.
        anchor.appendChild(descriptionP);

        // Append the anchor to the module container.
        moduleDiv.appendChild(anchor);

        // Add click handler to show module settings
        moduleDiv.addEventListener('click', function(e) {
            e.preventDefault();
            e.stopPropagation();
            
            // Remove active class from all modules
            container.querySelectorAll('.module').forEach(m => m.classList.remove('module-active'));
            
            // Add active class to clicked module
            this.classList.add('module-active');
            
            // Show settings for this module
            showModuleSettings(title);
        });

        // Finally, append the new module to the target container.
        container.appendChild(moduleDiv);
    }

    function updateRangeSliderBackground(slider) {
        const min = slider.min;
        const max = slider.max;
        const val = slider.value;
        const percentage = ((val - min) / (max - min)) * 100;
        // Adjust colors as needed. Left part colored (#21cc4c), right part light gray.
        slider.style.background = `linear-gradient(to right, #888 0%, #888 ${percentage}%, #f1f1f1 ${percentage}%, #f1f1f1 100%)`;
    }

    function createModuleSetting({ type, title, description, value, options = {} }) {
        let templateId;
        switch (type) {
            case 'checkbox':
                templateId = 'checkbox-setting-template';
                break;
            case 'radio':
            case 'dropdown':
                templateId = 'dropdown-setting-template';
                break;
            case 'range':
                templateId = 'range-setting-template';
                break;
            case 'text':
                templateId = 'text-setting-template';
                break;
            case 'number':
                templateId = 'number-setting-template';
                break;
            default:
                console.error('Unsupported setting type:', type);
                return null;
        }

        const template = document.getElementById(templateId);
        if (!template) {
            console.error(`Template ${templateId} not found`);
            return null;
        }

        const clone = document.importNode(template.content, true);
        const titleEl = clone.querySelector('.setting-title');
        if (titleEl) titleEl.textContent = title;
        const descEl = clone.querySelector('.setting-description');
        if (descEl) descEl.textContent = description;

        if (type === 'checkbox') {
            const checkbox = clone.querySelector('input[type="checkbox"]');
            if (checkbox) checkbox.checked = !!value;
        } else if (type === 'radio' || type === 'dropdown') {
            const select = clone.querySelector('.dropdown-select');
            if (select && options.options && options.options.length) {
                select.innerHTML = '';
                options.options.forEach((option, index) => {
                    const optionElement = document.createElement('option');
                    optionElement.value = option.value;
                    optionElement.textContent = option.label;
                    if (value !== undefined) {
                        if (option.value === value) optionElement.selected = true;
                    } else if (index === 0) {
                        optionElement.selected = true;
                    }
                    select.appendChild(optionElement);
                });
            }
        } else if (type === 'range') {
            const rangeInput = clone.querySelector('input[type="range"]');
            if (rangeInput) {
                rangeInput.min = options.min !== undefined ? options.min : 0;
                rangeInput.max = options.max !== undefined ? options.max : 100;
                rangeInput.step = options.step !== undefined ? options.step : 1;
                rangeInput.value = value !== undefined ? value : 50;
                updateRangeSliderBackground(rangeInput);
                rangeInput.addEventListener('input', function () {
                    updateRangeSliderBackground(rangeInput);
                });
            }
        } else if (type === 'text') {
            const textInput = clone.querySelector('input[type="text"]');
            if (textInput) textInput.value = value || '';
        } else if (type === 'number') {
            const numberInput = clone.querySelector('input[type="number"]');
            if (numberInput) numberInput.value = value !== undefined ? value : '';
        }

        return clone.firstElementChild;
    }

    function setupDragAndDrop() {
        const moduleContainer = document.querySelector(".modules.module-container");
        const pinnedContainer = document.querySelector(".pinned-modules");
        let draggedModule = null;
        let placeholder = null;

        // Initialize drag events on all modules
        function initializeDragEvents() {
            document.querySelectorAll('.module').forEach(module => {
                // Skip already initialized modules
                if (module.getAttribute('data-drag-initialized') === 'true') return;

                module.setAttribute('draggable', 'true');
                module.setAttribute('data-drag-initialized', 'true');

                module.addEventListener('dragstart', handleDragStart);
                module.addEventListener('dragend', handleDragEnd);
            });
        }

        // Set up drop zones
        if (pinnedContainer) {
            pinnedContainer.addEventListener('dragover', handleDragOver);
            pinnedContainer.addEventListener('dragleave', handleDragLeave);
            pinnedContainer.addEventListener('drop', handleDrop);

            // For reordering within pinned container
            const pinnedContent = pinnedContainer.querySelector('.pinned-content');
            if (pinnedContent) {
                pinnedContent.addEventListener('dragover', handlePinnedDragOver);
            }
        }

        if (moduleContainer) {
            moduleContainer.addEventListener('dragover', handleDragOver);
            moduleContainer.addEventListener('dragleave', handleDragLeave);
            moduleContainer.addEventListener('drop', handleDrop);
        }

        // Initialize all existing modules
        initializeDragEvents();

        // Watch for new modules
        const observer = new MutationObserver(initializeDragEvents);

        if (moduleContainer) observer.observe(moduleContainer, { childList: true });
        if (pinnedContainer) {
            const pinnedContent = pinnedContainer.querySelector('.pinned-content');
            if (pinnedContent) {
                observer.observe(pinnedContent, { childList: true });
            }
        }

        function handleDragStart(e) {
            // Only start drag if it's not from a click action
            if (e.detail > 0) {
                e.preventDefault();
                return;
            }

            draggedModule = this;
            e.dataTransfer.setData('text/plain', this.outerHTML);
            e.dataTransfer.setData('source-container',
                this.closest('.pinned-content') ? 'pinned' : 'modules');
            e.dataTransfer.setData('module-enabled', this.getAttribute('data-enabled'));

            // Create placeholder for reordering
            placeholder = document.createElement('div');
            placeholder.classList.add('module', 'placeholder');
            placeholder.style.height = `${this.offsetHeight}px`;
            placeholder.style.opacity = '0.4';
            placeholder.style.border = '2px dashed #888';
            placeholder.style.backgroundColor = '#f5f5f5';

            // Delay to make initial dragging more visible
            setTimeout(() => this.classList.add('dragging'), 0);
        }

        function handleDragEnd() {
            this.classList.remove('dragging');
            // Remove placeholder if it exists
            if (placeholder && placeholder.parentNode) {
                placeholder.parentNode.removeChild(placeholder);
            }
            placeholder = null;
            draggedModule = null;

            // Remove any lingering drag-over classes
            document.querySelectorAll('.drag-over').forEach(el => {
                el.classList.remove('drag-over');
            });
        }

        function handleDragOver(e) {
            e.preventDefault(); // Allow dropping
            e.stopPropagation();
            this.classList.add('drag-over');
        }

        function handlePinnedDragOver(e) {
            e.preventDefault();
            e.stopPropagation();

            const pinnedContent = e.currentTarget;
            const sourceContainer = e.dataTransfer.getData('source-container') ||
                (draggedModule ?
                    (draggedModule.closest('.pinned-content') ? 'pinned' : 'modules')
                    : 'unknown');

            // Handle placement for both pinned modules and modules from the modules list
            // Create placeholder if not exists
            if (!placeholder) {
                placeholder = document.createElement('div');
                placeholder.classList.add('module', 'placeholder');
                placeholder.style.height = '80px'; // Default height for placeholder
                placeholder.style.opacity = '0.4';
                placeholder.style.border = '2px dashed #888';
                placeholder.style.backgroundColor = '#f5f5f5';
            }

            const modules = Array.from(pinnedContent.querySelectorAll('.module:not(.dragging):not(.placeholder)'));

            // Find the module we're hovering over
            const targetModule = modules.find(module => {
                const rect = module.getBoundingClientRect();
                const midY = rect.y + rect.height / 2;
                return e.clientY < midY;
            });

            // Insert placeholder before the target module
            if (targetModule) {
                pinnedContent.insertBefore(placeholder, targetModule);
            } else if (modules.length > 0) {
                // If no target found, append to end
                pinnedContent.appendChild(placeholder);
            } else {
                // If pinned area is empty
                pinnedContent.appendChild(placeholder);
            }

            // Apply transition styles to modules for smooth animation
            modules.forEach(module => {
                module.style.transition = 'transform 0.2s ease';
            });
        }

        function handleDragLeave(e) {
            // Only remove the class if we're not entering a child element
            const relatedTarget = e.relatedTarget;
            if (!relatedTarget || !this.contains(relatedTarget)) {
                this.classList.remove('drag-over');
            }
        }

        function handleDrop(e) {
            e.preventDefault();
            e.stopPropagation();

            // Remove the drag-over class immediately
            this.classList.remove('drag-over');

            const sourceContainer = e.dataTransfer.getData('source-container');
            const moduleHTML = e.dataTransfer.getData('text/plain');

            // Get the pinned content container
            const pinnedContent = this.classList.contains('pinned-modules') ?
                this.querySelector('.pinned-content') : null;

            // Handle reordering within pinned container
            if (sourceContainer === 'pinned' && pinnedContent && draggedModule) {
                // Get the position from placeholder
                if (placeholder && placeholder.parentNode === pinnedContent) {
                    // Insert at placeholder position
                    // position cannot be above or below the original module
                    if (placeholder.nextSibling !== draggedModule && placeholder !== draggedModule) {
                        pinnedContent.insertBefore(draggedModule, placeholder.nextSibling);
                    }
                }
            }
            // Handle moving from modules to pinned
            else if (this.classList.contains('pinned-modules') && sourceContainer === 'modules') {
                const tempDiv = document.createElement('div');
                tempDiv.innerHTML = moduleHTML;
                const newModule = tempDiv.firstElementChild;

                // Re-initialize drag events
                newModule.setAttribute('draggable', 'true');
                newModule.addEventListener('dragstart', handleDragStart);
                newModule.addEventListener('dragend', handleDragEnd);

                if (pinnedContent) {
                    const moduleTitle = newModule.querySelector('.module-title')?.textContent;

                    if (moduleTitle) {
                        // Check for duplicates
                        const existingElements = pinnedContent.querySelectorAll('.module .module-title');
                        let isDuplicate = false;

                        for (const element of existingElements) {
                            if (element.textContent === moduleTitle) {
                                isDuplicate = true;
                                break;
                            }
                        }

                        if (!isDuplicate) {
                            // Insert at placeholder position if available
                            if (placeholder && placeholder.parentNode === pinnedContent) {
                                pinnedContent.insertBefore(newModule, placeholder);
                            } else {
                                pinnedContent.appendChild(newModule);
                            }
                        }
                    } else {
                        pinnedContent.appendChild(newModule);
                    }
                }
            }
            // Handle removing from pinned
            else if (this.classList.contains('modules') && sourceContainer === 'pinned') {
                if (draggedModule) {
                    draggedModule.remove();
                }
            }

            // Clean up
            if (placeholder && placeholder.parentNode) {
                placeholder.parentNode.removeChild(placeholder);
            }
        }
    }


    window.api = {
        addCategory: addCategory,
        addPinnedModule: addPinnedModule,
        addToggleModule: addToggleModule,
        createSettingGroup: createSettingGroup,
        createSettingElement: createSettingElement,
        addSettingToGroup: addSettingToGroup,
        createConfigGroup: createConfigGroup,
        addConfigGroup: addConfigGroup,
        createModule: createModule,
        createModuleSetting: createModuleSetting,
        showModuleSettings: showModuleSettings,
        createSettingFromData: createSettingFromData,
        moduleSettingsData: moduleSettingsData
    };


    // 原utils.js
    const GLFW_KEY_MAP = {
        32: "Space",
        65: "A",
        66: "B",
        67: "C",
        68: "D",
        69: "E",
        70: "F",
        71: "G",
        72: "H",
        73: "I",
        74: "J",
        75: "K",
        76: "L",
        77: "M",
        78: "N",
        79: "O",
        80: "P",
        81: "Q",
        82: "R",
        83: "S",
        84: "T",
        85: "U",
        86: "V",
        87: "W",
        88: "X",
        89: "Y",
        90: "Z",
        257: "Enter",
        258: "Tab",
        259: "Backspace",
        260: "Insert",
        261: "Delete",
        262: "Right Arrow",
        263: "Left Arrow",
        264: "Down Arrow",
        265: "Up Arrow",
        266: "Page Up",
        267: "Page Down",
        268: "Home",
        269: "End",
        340: "Left Shift",
        341: "Left Control",
        342: "Left Alt",
        343: "Left Super",
        344: "Right Shift",
        345: "Right Control",
        346: "Right Alt",
        347: "Right Super",
        348: "Menu"
    };

    /**
     * 获取一个GLFW键码的字符串表示
     * @param keyCode GLFW键码
     * @param defaultValue 如果找不到对应的表示，默认值
     * @return String
     */
    function getGLFWKeyName(keyCode, defaultValue) {
        return GLFW_KEY_MAP[keyCode] || defaultValue;
    }



// Helper function to update module state
    function updateModuleState(selector, moduleName, enabled) {
        const elements = document.querySelectorAll(selector);
        for (const element of elements) {
            if (element.textContent.trim() === moduleName) {
                const moduleElement = element.closest('.module');
                if (moduleElement) {
                    moduleElement.setAttribute('data-enabled', enabled);
                    if (enabled) {
                        moduleElement.classList.add('module-enabled');
                    } else {
                        moduleElement.classList.remove('module-enabled');
                    }
                }
                break;
            }
        }
    }
    // Test data generation function
    function generateTestData() {
        console.log("Generating test data...");
        
        // Clear existing data first
        const moduleContainer = document.querySelector(".modules.module-container");
        const pinnedContainer = document.querySelector(".pinned-content");
        const sideModulesContainer = document.querySelector("#submenu-3 .sidemodules .modules-wrapper");
        const moduleSettingsContainer = document.querySelector("#submenu-3 .settings-wrapper");
        const configContainer = document.querySelector("#submenu-4");
        
        if (moduleContainer) moduleContainer.innerHTML = '';
        if (pinnedContainer) pinnedContainer.innerHTML = '';
        if (sideModulesContainer) sideModulesContainer.innerHTML = '';
        if (moduleSettingsContainer) {
            moduleSettingsContainer.innerHTML = '<p style="padding: 20px; text-align: center; color: #888;">Select a module from the left to view its settings</p>';
        }
        
        // Clear config container but preserve existing structure
        if (configContainer) {
            const configsContainer = configContainer.querySelector('.configs-container');
            if (configsContainer) {
                configsContainer.innerHTML = '';
            }
        }
        
        // Generate test categories with modules
        const testCategories = [
            {
                name: "Combat",
                description: "Combat enhancement modules",
                modules: [
                    {
                        name: "AutoAttack",
                        description: "Automatically attack nearby enemies",
                        enabled: true,
                        settings: {
                            individual: {
                                "bool:enabled": { value: true, description: "Enable auto attack" },
                                "number:range": { value: 5.0, min: 1.0, max: 10.0, inc: 0.5, description: "Attack range" }
                            },
                            groups: {
                                "Targeting": {
                                    "bool:autoTarget": { value: true, description: "Auto target nearest enemy" },
                                    "mode:priority": { value: "Nearest", modes: ["Nearest", "Strongest", "Weakest"], description: "Target priority" }
                                },
                                "Advanced": {
                                    "text:customCommand": { value: "/attack", description: "Custom attack command" },
                                    "bool:throughWalls": { value: false, description: "Attack through walls" },
                                    "number:cooldown": { value: 100, min: 0, max: 1000, inc: 50, description: "Attack cooldown (ms)" }
                                }
                            }
                        }
                    },
                    {
                        name: "KillAura",
                        description: "Attack multiple enemies simultaneously",
                        enabled: false,
                        settings: {
                            "bool:throughWalls": { value: false, description: "Attack through walls" },
                            "number:maxTargets": { value: 3, min: 1, max: 8, inc: 1, description: "Maximum targets" },
                            "mode:rotationMode": { value: "Smooth", modes: ["Instant", "Smooth", "None"], description: "Rotation mode" }
                        }
                    },
                    {
                        name: "CriticalHits",
                        description: "Enhance critical hit chances",
                        enabled: true,
                        settings: {
                            "bool:onlyWhenJumping": { value: true, description: "Only crit when jumping" },
                            "number:chance": { value: 85, min: 0, max: 100, inc: 5, description: "Critical hit chance %" }
                        }
                    }
                ]
            },
            {
                name: "Movement",
                description: "Movement and navigation modules",
                modules: [
                    {
                        name: "Speed",
                        description: "Increase movement speed",
                        enabled: true,
                        settings: {
                            "number:multiplier": { value: 2.5, min: 1.0, max: 5.0, inc: 0.1, description: "Speed multiplier" },
                            "bool:onGround": { value: true, description: "Only when on ground" },
                            "mode:type": { value: "Vanilla", modes: ["Vanilla", "Strafe", "Bhop"], description: "Speed type" }
                        }
                    },
                    {
                        name: "Fly",
                        description: "Creative-like flight",
                        enabled: false,
                        settings: {
                            "number:speed": { value: 1.0, min: 0.1, max: 3.0, inc: 0.1, description: "Flight speed" },
                            "bool:antiKick": { value: true, description: "Anti-kick protection" },
                            "mode:mode": { value: "Creative", modes: ["Creative", "Packet", "Damage"], description: "Flight mode" }
                        }
                    },
                    {
                        name: "Step",
                        description: "Step up blocks automatically",
                        enabled: false,
                        settings: {
                            "number:height": { value: 1.0, min: 0.5, max: 2.5, inc: 0.1, description: "Step height" },
                            "bool:reverse": { value: false, description: "Reverse step down" }
                        }
                    }
                ]
            },
            {
                name: "Visual",
                description: "Visual enhancement modules",
                modules: [
                    {
                        name: "ESP",
                        description: "See entities through walls",
                        enabled: true,
                        settings: {
                            individual: {
                                "bool:enabled": { value: true, description: "Enable ESP" },
                                "number:range": { value: 50, min: 10, max: 200, inc: 10, description: "ESP range" },
                                "mode:style": { value: "Box", modes: ["Box", "Outline", "Glow"], description: "ESP style" }
                            },
                            groups: {
                                "Entity Types": {
                                    "bool:players": { value: true, description: "Show players" },
                                    "bool:mobs": { value: false, description: "Show hostile mobs" },
                                    "bool:animals": { value: false, description: "Show animals" },
                                    "bool:items": { value: true, description: "Show dropped items" }
                                },
                                "Visual Settings": {
                                    "bool:showNames": { value: true, description: "Show entity names" },
                                    "bool:showHealth": { value: true, description: "Show health bars" },
                                    "number:opacity": { value: 80, min: 10, max: 100, inc: 5, description: "ESP opacity %" }
                                }
                            }
                        }
                    },
                    {
                        name: "Fullbright",
                        description: "Maximum brightness everywhere",
                        enabled: false,
                        settings: {
                            "number:gamma": { value: 15.0, min: 1.0, max: 20.0, inc: 0.5, description: "Gamma level" },
                            "bool:affectSky": { value: false, description: "Affect sky brightness" }
                        }
                    },
                    {
                        name: "NoHurtCam",
                        description: "Disable hurt camera shake",
                        enabled: true,
                        settings: {
                            "bool:keepRedTint": { value: false, description: "Keep red damage tint" }
                        }
                    }
                ]
            },
            {
                name: "Utility",
                description: "Utility and convenience modules",
                modules: [
                    {
                        name: "AutoTool",
                        description: "Automatically select best tool",
                        enabled: true,
                        settings: {
                            "bool:switchBack": { value: true, description: "Switch back after use" },
                            "bool:includeWeapons": { value: false, description: "Include weapons in selection" }
                        }
                    },
                    {
                        name: "ChestStealer",
                        description: "Automatically loot chests",
                        enabled: false,
                        settings: {
                            "number:delay": { value: 100, min: 0, max: 1000, inc: 50, description: "Steal delay (ms)" },
                            "bool:closeWhenDone": { value: true, description: "Close chest when done" },
                            "text:ignoreItems": { value: "dirt,cobblestone", description: "Items to ignore (comma separated)" }
                        }
                    }
                ]
            }
        ];

        // Generate the test data
        testCategories.forEach(category => {
            // Add category to toggle page (submenu-1)
            addCategory(category.name, category.description);
            
            // Add modules for this category to the modules panel (submenu-3)
            category.modules.forEach(module => {
                // Store module settings data for later use
                moduleSettingsData[module.name] = module.settings;
                
                // Add to toggle modules (submenu-1)
                addToggleModule(module.name, module.description);
                
                // Add to side modules (submenu-3 left panel)
                createModule(module.name, module.description);
                
                // Set module state
                updateModuleState('.module-container .module .module-title', module.name, module.enabled);
                updateModuleState('#submenu-3 .sidemodules .modules-wrapper .module a', module.name, module.enabled);
                
                // Add to pinned if enabled
                if (module.enabled) {
                    addPinnedModule(module.name, module.description);
                }
            });
        });
        
        // Generate test config groups for submenu-4
        const testConfigGroups = [
            {
                title: "General Settings",
                settings: [
                    { type: 'checkbox', title: "Auto Save", description: "Automatically save configuration changes", value: true },
                    { type: 'dropdown', title: "Theme", description: "Application color theme", value: "Dark", options: { name: "theme", options: [{ label: "Light", value: "Light" }, { label: "Dark", value: "Dark" }, { label: "Auto", value: "Auto" }] } },
                    { type: 'dropdown', title: "Language", description: "Interface language", value: "English", options: { name: "language", options: [{ label: "English", value: "English" }, { label: "Spanish", value: "Spanish" }, { label: "French", value: "French" }] } }
                ]
            },
            {
                title: "Performance",
                settings: [
                    { type: 'range', title: "Max FPS", description: "Maximum frames per second limit", value: 144, options: { min: 30, max: 240, step: 1 } },
                    { type: 'range', title: "Memory Usage", description: "Maximum memory usage limit (GB)", value: 2, options: { min: 1, max: 8, step: 1 } },
                    { type: 'dropdown', title: "CPU Priority", description: "Process CPU priority level", value: "Normal", options: { name: "cpu-priority", options: [{ label: "Low", value: "Low" }, { label: "Normal", value: "Normal" }, { label: "High", value: "High" }] } }
                ]
            },
            {
                title: "Graphics",
                settings: [
                    { type: 'dropdown', title: "Anti-Aliasing", description: "Anti-aliasing quality setting", value: "MSAA 4x", options: { name: "anti-aliasing", options: [{ label: "Off", value: "Off" }, { label: "MSAA 2x", value: "MSAA 2x" }, { label: "MSAA 4x", value: "MSAA 4x" }, { label: "MSAA 8x", value: "MSAA 8x" }] } },
                    { type: 'range', title: "Render Distance", description: "Maximum render distance (chunks)", value: 16, options: { min: 2, max: 32, step: 2 } },
                    { type: 'checkbox', title: "VSync", description: "Vertical synchronization", value: false }
                ]
            },
            {
                title: "Security",
                settings: [
                    { type: 'checkbox', title: "Auto Update", description: "Automatically check for updates", value: true },
                    { type: 'checkbox', title: "Telemetry", description: "Send usage data for improvement", value: false },
                    { type: 'checkbox', title: "Debug Mode", description: "Enable advanced debugging features", value: false }
                ]
            }
        ];

        // Add config groups to submenu-4
        testConfigGroups.forEach(group => {
            addConfigGroup(group.title, group.settings);
        });
        
        console.log("Test data generation complete!");
    }

    // Add to window.api for external access
    window.api.generateTestData = generateTestData;
    
    // Auto-generate test data on load (comment out if not wanted)
    setTimeout(generateTestData, 1000);

    setupDragAndDrop()
});