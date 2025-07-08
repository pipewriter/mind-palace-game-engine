import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.CefSettings;
import org.cef.browser.CefFrame;
import org.cef.browser.CefBrowser;
import org.cef.callback.CefMenuModel;
import org.cef.handler.CefContextMenuHandlerAdapter;
import org.cef.handler.CefFocusHandlerAdapter;
import org.cef.callback.CefContextMenuParams;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.util.List;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.net.URL;

public class ParkerSetter {
    private Set<String> previousSet;
    private GridBrowserPanel gridBrowserPanel;

    // Constructor
    public ParkerSetter(GridBrowserPanel gridBrowserPanel) {
        this.previousSet = new HashSet<>();
        this.gridBrowserPanel = gridBrowserPanel;
    }

    // Method to update the set each frame
    public void updateSet(Set<String> currentSet) {
        // Calculate added and removed elements
        Set<String> addedElements = new HashSet<>(currentSet);
        addedElements.removeAll(previousSet);

        Set<String> removedElements = new HashSet<>(previousSet);
        removedElements.removeAll(currentSet);

        // Trigger events based on the differences
        if (!addedElements.isEmpty()) {
            onElementsAdded(addedElements);
        }
        if (!removedElements.isEmpty()) {
            onElementsRemoved(removedElements);
        }

        // Update the previous set for the next frame
        previousSet = new HashSet<>(currentSet);
    }

    // Event handler for added elements
    protected void onElementsAdded(Set<String> addedElements) {
        for (String url : addedElements) {
            gridBrowserPanel.addURL(url);
        }
    }

    // Event handler for removed elements
    protected void onElementsRemoved(Set<String> removedElements) {
        for (String url : removedElements) {
            gridBrowserPanel.removeURL(url);
        }
    }

    public static ParkerSetter myset = null;

    // Main method
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Create the main frame
            JFrame frame = new JFrame("Browser Grid");

            // Create the grid browser panel
            GridBrowserPanel gridBrowserPanel = new GridBrowserPanel(3, 3);

            // Add the grid panel to the frame
            frame.getContentPane().add(gridBrowserPanel.getPanel(), BorderLayout.CENTER);

            // Set frame properties
            frame.setSize(1200, 800);
            frame.setVisible(true);
            frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

            // Add a window listener to properly dispose of CEF on close
            frame.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    gridBrowserPanel.dispose();
                }
            });

            // Create ParkerSetter instance
            ParkerSetter parkerSetter = new ParkerSetter(gridBrowserPanel);
            myset = parkerSetter;
            // Initial set of URLs
            Set<String> set1 = new HashSet<>();
            set1.add("https://github.com/viglucci/app-jcef-example/blob/master/src/main/java/org/cef/CefClient.java");
            set1.add("https://www.youtube.com/watch?v=RtGM8mqAaQU");
            parkerSetter.updateSet(set1);

            // Simulate delay and update the set
            javax.swing.Timer timer = new javax.swing.Timer(10000, e -> {
                Set<String> set2 = new HashSet<>();
                set2.add("https://github.com/viglucci/app-jcef-example/blob/master/src/main/java/org/cef/CefClient.java");
                set2.add("https://whatismyviewport.com/");
                set2.add("https://www.example.com");
                parkerSetter.updateSet(set2);
            });
            timer.setRepeats(false);
            timer.start();

            // Another delay and update
            javax.swing.Timer timer2 = new javax.swing.Timer(20000, e -> {
                Set<String> set3 = new HashSet<>();
                set3.add("https://whatismyviewport.com/");
                set3.add("https://www.example.com");
                parkerSetter.updateSet(set3);
            });
            timer2.setRepeats(false);
            timer2.start();
        });
    }

    // Class that manages the grid of browsers
    static class GridBrowserPanel {
        private JPanel gridPanel;
        private int rows;
        private int cols;
        private Set<String> urls; // The set of currently loaded URLs
        private Map<String, CefBrowser> urlToBrowser; // Map from URL to Browser
        private Map<CefBrowser, String> browserToUrl; // Map from Browser to URL
        private CefApp cefApp;
        private List<CefBrowser> browserList;
        private Map<CefBrowser, CefClient> browserToClient;

        public GridBrowserPanel(int rows, int cols) {
            this.rows = rows;
            this.cols = cols;
            this.urls = new HashSet<>();
            this.urlToBrowser = new HashMap<>();
            this.browserToUrl = new HashMap<>();
            this.browserList = new ArrayList<>();
            this.browserToClient = new HashMap<>();

            // Initialize CEF settings
            CefSettings settings = new CefSettings();
            settings.windowless_rendering_enabled = false; // Disable off-screen rendering
                
            // Set unique cache paths to avoid conflicts
            String userDir = System.getProperty("user.home");
            System.out.println(userDir);
            String uniqueId = UUID.randomUUID().toString();
            uniqueId = "poop2";
            settings.root_cache_path = userDir + "/root_cache_" + uniqueId;
            settings.cache_path = settings.root_cache_path + "/cache";

            cefApp = CefApp.getInstance(settings);

            // Create the grid panel
            gridPanel = new JPanel(new GridLayout(rows, cols));

            int totalCells = rows * cols;

            // Initialize the grid with browsers loading about:blank
            for (int i = 0; i < totalCells; i++) {
                // Create a client
                CefClient client = cefApp.createClient();

                // Add the focus handler
                client.addFocusHandler(new CefFocusHandlerAdapter() {
                    @Override
                    public void onTakeFocus(CefBrowser browser, boolean next) {
                        // Handle focus if necessary
                        return;
                    }

                    @Override
                    public boolean onSetFocus(CefBrowser browser, FocusSource source) {
                        // Prevent the browser from getting focus if needed
                        return true;
                    }

                    @Override
                    public void onGotFocus(CefBrowser browser) {
                        // Handle focus if necessary
                    }
                });

                // Add the custom context menu handler
                client.addContextMenuHandler(new CustomContextMenuHandler());

                // Create a browser with about:blank
                CefBrowser browser = client.createBrowser("about:blank", false, false);
                Component browserUI = browser.getUIComponent();
                browserList.add(browser);
                browserToUrl.put(browser, "about:blank"); // Initially, browser displays about:blank
                browserToClient.put(browser, client);

                gridPanel.add(browserUI);
            }
        }

        public JPanel getPanel() {
            return gridPanel;
        }

        public void addURL(String url) {
            if (urls.contains(url)) {
                return; // URL already added
            }

            // Find a browser that is currently displaying about:blank
            CefBrowser availableBrowser = null;
            for (CefBrowser browser : browserList) {
                String browserUrl = browserToUrl.get(browser);
                if ("about:blank".equals(browserUrl)) {
                    availableBrowser = browser;
                    break;
                }
            }

            if (availableBrowser == null) {
                System.out.println("No available browsers to load the URL.");
                return;
            }

            // Load the URL in the browser
            availableBrowser.loadURL(url);

            // Update mappings
            urls.add(url);
            urlToBrowser.put(url, availableBrowser);
            browserToUrl.put(availableBrowser, url);
        }

        public void removeURL(String url) {
            if (!urls.contains(url)) {
                return; // URL not present
            }

            CefBrowser browser = urlToBrowser.get(url);
            if (browser == null) {
                return;
            }

            // Load about:blank in the browser
            browser.loadURL("about:blank");

            // Update mappings
            urls.remove(url);
            urlToBrowser.remove(url);
            browserToUrl.put(browser, "about:blank");
        }

        public void dispose() {
            // Dispose of all browsers and clients
            for (CefBrowser browser : browserList) {
                browser.close(true);
                CefClient client = browserToClient.get(browser);
                if (client != null) {
                    client.dispose();
                }
            }
            cefApp.dispose();
        }
    }

    public static class CustomContextMenuHandler extends CefContextMenuHandlerAdapter {
        @Override
        public void onBeforeContextMenu(CefBrowser browser, CefFrame frame,
                                        CefContextMenuParams params,
                                        CefMenuModel model) {
            // Clear the default context menu
            model.clear();

            // Check if the context is over an image
            if (params.getMediaType() == CefContextMenuParams.MediaType.CM_MEDIATYPE_VIDEO) {
                model.addItem(5, "Video to ParkerSpace");
            }
            else
            if (params.getMediaType() == CefContextMenuParams.MediaType.CM_MEDIATYPE_IMAGE) {
                // Context menu for images
                model.addItem(4, "Image to Inventory");
            } else {
                // Context menu for non-images
                model.addItem(0, "Link to Map");
                model.addItem(1, "Link to Inventory");
                model.addItem(2, "Page to Map");
                model.addItem(3, "Page to Inventory");
            }
        }

        @Override
        public boolean onContextMenuCommand(CefBrowser browser, CefFrame frame,
                                            CefContextMenuParams params,
                                            int commandId,
                                            int eventFlags) {
            switch (commandId) {
                case 0:
                    System.out.println(params.getLinkUrl());
                    Main.link_into_map(params.getLinkUrl());
                    return true;
                case 1:
                    System.out.println(params.getLinkUrl());
                    Main.link_into_inventory(params.getLinkUrl());
                    // params.getPageUrl();
                    //     System.out.println(params.getPageUrl());
                    //     System.out.println(params.getSelectionText());
                    //     System.out.println(params.getLinkUrl());
                    // SwingUtilities.invokeLater(() -> {
                    //     JOptionPane.showMessageDialog(null, "Link to ParkerSpace selected!");
                    //     System.out.println(params.getPageUrl());
                    //     System.out.println(params.getSelectionText());
                    // });
                    return true;
                case 2:
                    Main.link_into_map(params.getPageUrl());
                    // SwingUtilities.invokeLater(() -> {
                    //     JOptionPane.showMessageDialog(null, "Screenshot to ParkerSpace selected!");
                    // });
                    return true;
                case 3:
                    Main.link_into_inventory(params.getPageUrl());
                    // SwingUtilities.invokeLater(() -> {
                    //     JOptionPane.showMessageDialog(null, "Selection to ParkerSpace selected!");
                    // });
                    return true;
                case 4:
                    // Get the image URL from the context menu parameters
                    String imageUrl = params.getSourceUrl();
                    if (imageUrl != null && !imageUrl.isEmpty()) {
                        // Start a background thread to download the image
                        new Thread(() -> {
                            try {
                                // Download the image
                                System.out.println(imageUrl);
                                BufferedImage image = ImageIO.read(new URL(imageUrl));
                                System.out.println(image);
                                Main.bi_into_inventory(image);
                                // Path downloadedImage = downloadImage(imageUrl);
                                // Optionally, process the downloaded image here

                                // Update the UI on the EDT
                                // SwingUtilities.invokeLater(() -> {
                                //     JOptionPane.showMessageDialog(null, "Image downloaded to: " + downloadedImage.toAbsolutePath());
                                // });
                            } catch (Exception e) {
                                e.printStackTrace();
                                // Show error message on the EDT
                                SwingUtilities.invokeLater(() -> {
                                    JOptionPane.showMessageDialog(null, "Failed to download image: " + e.getMessage());
                                });
                            }
                        }).start();
                    } else {
                        // No image URL available
                        SwingUtilities.invokeLater(() -> {
                            JOptionPane.showMessageDialog(null, "No image URL available to download.");
                        });
                    }
                    return true;
                default:
                    return false;
            }
        }
    }
}