package com.haedus.ui;

import com.haedus.ui.components.ModulePanel;
import com.haedus.ui.components.NavigationBar;
import com.haedus.ui.components.SettingsPanel;
import com.haedus.ui.model.Category;
import com.haedus.ui.model.TestData;
import com.haedus.ui.util.ColorPalette;
import io.github.humbleui.skija.BackendRenderTarget;
import io.github.humbleui.skija.DirectContext;
import io.github.humbleui.skija.FramebufferFormat;
import io.github.humbleui.skija.Surface;
import io.github.humbleui.skija.SurfaceColorFormat;
import io.github.humbleui.skija.SurfaceOrigin;
import io.github.humbleui.skija.ColorSpace;
import io.github.humbleui.skija.Canvas;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryUtil;

import java.util.List;

/**
 * Creates the application window and drives the rendering loop.  This
 * class is responsible for initialising GLFW and Skija, handling
 * resize events, and orchestrating the drawing of high‑level UI
 * components.  The layout of the navigation bar, module list and
 * settings panel adapts to the current window size on each frame.
 */
public class MainWindow {
    private long window;
    private DirectContext context;
    private BackendRenderTarget renderTarget;
    private Surface surface;
    private Canvas canvas;
    private List<Category> categories;
    private NavigationBar navigationBar;
    private ModulePanel modulePanel;
    private SettingsPanel settingsPanel;

    /**
     * Starts the application by creating a window, loading test data
     * and running the event loop.  This method blocks until the
     * window is closed.
     */
    public void start() {
        // Redirect GLFW errors to the console for easier debugging
        GLFWErrorCallback.createPrint(System.err).set();
        if (!GLFW.glfwInit()) {
            throw new IllegalStateException("Unable to initialise GLFW");
        }

        // Create the window; allow resizing but keep hidden until fully initialised
        int initialWidth = 1280;
        int initialHeight = 720;
        window = GLFW.glfwCreateWindow(initialWidth, initialHeight, "Haedus UI", MemoryUtil.NULL, MemoryUtil.NULL);
        if (window == 0) {
            throw new RuntimeException("Failed to create GLFW window");
        }
        GLFW.glfwMakeContextCurrent(window);
        // Enable v‑sync
        GLFW.glfwSwapInterval(1);
        GLFW.glfwShowWindow(window);
        // Initialise OpenGL function pointers
        GL.createCapabilities();
        // Load the native Skija library unless static loading is disabled
        if ("false".equals(System.getProperty("skija.staticLoad"))) {
            // no-op if library is already loaded
        }

        // Create GPU context
        context = DirectContext.makeGL();

        // Prepare dummy data for demonstration
        categories = TestData.generate();
        // Instantiate UI components with placeholder bounds; real
        // dimensions are assigned in updateLayout()
        navigationBar = new NavigationBar(0, 0, initialWidth, 80, categories);
        modulePanel   = new ModulePanel(0, 80, 400, initialHeight - 80, categories);
        settingsPanel = new SettingsPanel(400, 80, initialWidth - 400, initialHeight - 80, categories);

        // Create initial render target and surface
        updateRenderTarget(initialWidth, initialHeight);
        updateLayout(initialWidth, initialHeight);

        // Listen for framebuffer size changes to recreate Skija surfaces
        GLFW.glfwSetFramebufferSizeCallback(window, (win, w, h) -> {
            updateRenderTarget(w, h);
            updateLayout(w, h);
        });

        // Main loop
        while (!GLFW.glfwWindowShouldClose(window)) {
            renderFrame();
            GLFW.glfwPollEvents();
        }

        // Cleanup
        dispose();
    }

    /**
     * Recreates the Skija surface for the given dimensions.  This
     * method should be called whenever the framebuffer size changes.
     * @param width framebuffer width in pixels
     * @param height framebuffer height in pixels
     */
    private void updateRenderTarget(int width, int height) {
        // Dispose previous resources
        if (surface      != null) surface.close();
        if (renderTarget != null) renderTarget.close();

        // Obtain the current framebuffer id; the default framebuffer is 0 on most systems
        int fbId = GL11.glGetInteger(GL30.GL_FRAMEBUFFER_BINDING);

        // Create Skija render target backed by the default framebuffer
        // (uses the raw FBO handle + FramebufferFormat enum)
        renderTarget = BackendRenderTarget.makeGL(
                width, height,
                /*samples*/ 0, /*stencil*/ 8,
                fbId,
                FramebufferFormat.GR_GL_RGBA8
        );  // :contentReference[oaicite:2]{index=2}

        // Create a surface with a 32‑bit RGBA format and sRGB colour space
        surface = Surface.makeFromBackendRenderTarget(
                context,
                renderTarget,
                SurfaceOrigin.BOTTOM_LEFT,
                SurfaceColorFormat.RGBA_8888,
                ColorSpace.getSRGB()
        );  // :contentReference[oaicite:3]{index=3}

        canvas = surface.getCanvas();
    }

    /**
     * Updates the positions and sizes of UI components based on the
     * current window dimensions.
     * @param width window width
     * @param height window height
     */
    private void updateLayout(float width, float height) {
        float navHeight   = 80f;
        float modulesWidth = Math.min(340f, width * 0.35f);
        navigationBar.setBounds(0f, 0f,      width,     navHeight);
        modulePanel.setBounds(0f,            navHeight, modulesWidth, height - navHeight);
        settingsPanel.setBounds(modulesWidth, navHeight, width - modulesWidth, height - navHeight);
    }

    /**
     * Draws one frame by clearing the background, rendering each
     * component and flushing the Skija context.
     */
    private void renderFrame() {
        // Fill the background
        canvas.clear(ColorPalette.BACKGROUND);
        // Draw UI elements
        navigationBar.render(canvas);
        modulePanel.render(canvas);
        settingsPanel.render(canvas);
        // Flush GPU commands and present
        context.flush();
        GLFW.glfwSwapBuffers(window);
    }

    /**
     * Releases Skija and GLFW resources.  Should be invoked when the
     * window is closing.
     */
    private void dispose() {
        if (surface      != null) surface.close();
        if (renderTarget != null) renderTarget.close();
        if (context      != null) context.close();
        GLFW.glfwDestroyWindow(window);
        GLFW.glfwTerminate();
    }
}
