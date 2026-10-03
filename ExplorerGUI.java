import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ExplorerGUI extends JFrame {

        private static final Color INK = new Color(29, 48, 51);
        private static final Color BLUE = new Color(25, 126, 114);
        private static final Color PALE_BLUE = new Color(231, 246, 241);
        private static final Color PAGE = new Color(247, 251, 249);
        private static final Color BORDER = new Color(205, 224, 218);
        private static final Color MUTED = new Color(91, 111, 110);

    private FileSystem fileSystem;

    private JLabel pathLabel;
    private JTextArea displayArea;
    private JLabel statisticsLabel;

    public ExplorerGUI() {

        fileSystem = new FileSystem();

        setTitle("File & Folder Explorer - Tree & DFS");
        setSize(980, 680);
        setMinimumSize(new Dimension(760, 520));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        createGUI();

        refreshDisplay();
    }

    private void createGUI() {

        getContentPane().setBackground(PAGE);
        setLayout(new BorderLayout(0, 0));

        JPanel topPanel = new JPanel(new BorderLayout(18, 0));
        topPanel.setBackground(new Color(238, 248, 245));
        topPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createMatteBorder(0, 0, 1, 0, BORDER),
                BorderFactory.createEmptyBorder(20, 26, 18, 26)));

        JLabel titleLabel = new JLabel("FILE & FOLDER EXPLORER");
        titleLabel.setForeground(INK);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 25));

        JLabel subtitleLabel = new JLabel("Tree navigation and depth-first search");
        subtitleLabel.setForeground(MUTED);
        subtitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JPanel heading = new JPanel();
        heading.setOpaque(false);
        heading.setLayout(new BoxLayout(heading, BoxLayout.Y_AXIS));
        heading.add(titleLabel);
        heading.add(Box.createVerticalStrut(5));
        heading.add(subtitleLabel);

        pathLabel = new JLabel();
        pathLabel.setOpaque(true);
        pathLabel.setBackground(PALE_BLUE);
        pathLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(183, 218, 208)),
                BorderFactory.createEmptyBorder(8, 14, 8, 14)));
        pathLabel.setForeground(BLUE);
        pathLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        pathLabel.setHorizontalAlignment(SwingConstants.RIGHT);

        topPanel.add(heading, BorderLayout.WEST);
        topPanel.add(pathLabel, BorderLayout.EAST);

        add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));
        centerPanel.setBackground(PAGE);
        centerPanel.setBorder(BorderFactory.createEmptyBorder(18, 24, 14, 24));

        displayArea = new JTextArea();
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 15));
        displayArea.setForeground(new Color(35, 61, 61));
        displayArea.setBackground(new Color(253, 255, 254));
        displayArea.setMargin(new Insets(18, 20, 18, 20));
        displayArea.setEditable(false);
        displayArea.setLineWrap(false);

        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(1, 1, 1, 1)));
        scrollPane.getViewport().setBackground(new Color(253, 255, 254));
        scrollPane.setColumnHeaderView(createSectionLabel("FILE SYSTEM CONTENT"));

        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new BorderLayout(0, 10));
        actionPanel.setBackground(PAGE);
        actionPanel.setBorder(BorderFactory.createEmptyBorder(0, 24, 18, 24));

        JPanel navigationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        navigationPanel.setBackground(PAGE);

        JPanel fileActionsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        fileActionsPanel.setBackground(PAGE);

        JPanel analysisPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 0));
        analysisPanel.setBackground(PAGE);

        JLabel manageLabel = createGroupLabel("NAVIGATE");
        JLabel fileActionsLabel = createGroupLabel("FILE ACTIONS");
        JLabel toolsLabel = createGroupLabel("VIEW & ANALYZE");

        JButton createFolderButton = createButton("+  Create Folder", BLUE);
        JButton createFileButton = createButton("+  Create File", BLUE);
        JButton addFileToFolderButton = createButton("Add File to Folder", new Color(45, 145, 137));
        JButton openButton = createButton("Open Folder", new Color(42, 137, 91));
        JButton backButton = createButton("Go Back", new Color(84, 108, 108));
        JButton searchButton = createButton("Search DFS", new Color(58, 111, 132));
        JButton deleteButton = createButton("Delete", new Color(186, 79, 72));
        JButton treeButton = createButton("Show Tree", new Color(47, 110, 105));
        JButton dfsButton = createButton("DFS Traversal", new Color(47, 110, 105));
        JButton statsButton = createButton("Statistics", new Color(47, 110, 105));
        JButton exitButton = createButton("Exit", new Color(84, 99, 99));

        navigationPanel.add(createFolderButton);
        navigationPanel.add(createFileButton);
        navigationPanel.add(openButton);
        navigationPanel.add(backButton);

        fileActionsPanel.add(addFileToFolderButton);
        fileActionsPanel.add(searchButton);
        fileActionsPanel.add(deleteButton);

        analysisPanel.add(treeButton);
        analysisPanel.add(dfsButton);
        analysisPanel.add(statsButton);
        analysisPanel.add(exitButton);

        JPanel commandRows = new JPanel();
        commandRows.setBackground(PAGE);
        commandRows.setLayout(new BoxLayout(commandRows, BoxLayout.Y_AXIS));
        commandRows.add(manageLabel);
        commandRows.add(Box.createVerticalStrut(6));
        commandRows.add(navigationPanel);
        commandRows.add(Box.createVerticalStrut(12));
        commandRows.add(fileActionsLabel);
        commandRows.add(Box.createVerticalStrut(6));
        commandRows.add(fileActionsPanel);
        commandRows.add(Box.createVerticalStrut(12));
        commandRows.add(toolsLabel);
        commandRows.add(Box.createVerticalStrut(6));
        commandRows.add(analysisPanel);

        JPanel bottomPanel = new JPanel(new BorderLayout(0, 8));
        bottomPanel.setBackground(PAGE);

        statisticsLabel = new JLabel();
        statisticsLabel.setOpaque(true);
        statisticsLabel.setBackground(PALE_BLUE);
        statisticsLabel.setForeground(new Color(37, 92, 82));
        statisticsLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        statisticsLabel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(183, 218, 208)),
                BorderFactory.createEmptyBorder(9, 14, 9, 14)));
        bottomPanel.add(commandRows, BorderLayout.CENTER);
        bottomPanel.add(statisticsLabel, BorderLayout.SOUTH);
        actionPanel.add(bottomPanel, BorderLayout.CENTER);
        add(actionPanel, BorderLayout.SOUTH);

        // Button actions

        createFolderButton.addActionListener(e ->
                createFolder());

        createFileButton.addActionListener(e ->
                createFile());

        addFileToFolderButton.addActionListener(e ->
                addFileToFolder());

        openButton.addActionListener(e ->
                openFolder());

        backButton.addActionListener(e ->
                goBack());

        searchButton.addActionListener(e ->
                search());

        deleteButton.addActionListener(e ->
                delete());

        treeButton.addActionListener(e ->
                showTree());

        dfsButton.addActionListener(e ->
                showDFS());

        statsButton.addActionListener(e ->
                showStatistics());

        exitButton.addActionListener(e ->
                System.exit(0));
    }

        private JLabel createSectionLabel(String text) {

                JLabel label = new JLabel("  " + text);
                label.setOpaque(true);
                label.setBackground(PALE_BLUE);
                label.setForeground(new Color(37, 92, 82));
                label.setFont(new Font("SansSerif", Font.BOLD, 12));
                label.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));
                return label;
        }

        private JLabel createGroupLabel(String text) {

                JLabel label = new JLabel(text);
                label.setForeground(MUTED);
                label.setFont(new Font("SansSerif", Font.BOLD, 11));
                label.setBorder(BorderFactory.createEmptyBorder(0, 2, 0, 0));
                return label;
        }

        private JButton createButton(String text, Color color) {

                JButton button = new RoundedButton(text, color);
                button.setFont(new Font("SansSerif", Font.BOLD, 12));
                button.setForeground(Color.WHITE);
                button.setFocusPainted(false);
                button.setBorder(BorderFactory.createEmptyBorder(10, 16, 10, 16));
                button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

        private static class RoundedButton extends JButton {

                private final Color buttonColor;

                RoundedButton(String text, Color buttonColor) {
                        super(text);
                        this.buttonColor = buttonColor;
                        setContentAreaFilled(false);
                        setOpaque(false);
                        setBorderPainted(false);
                }

                @Override
                protected void paintComponent(Graphics graphics) {

                        Graphics2D graphics2D = (Graphics2D) graphics.create();
                        graphics2D.setRenderingHint(
                                        RenderingHints.KEY_ANTIALIASING,
                                        RenderingHints.VALUE_ANTIALIAS_ON
                        );

                        Color fill = getModel().isRollover()
                                        ? buttonColor.brighter()
                                        : buttonColor;
                        graphics2D.setColor(fill);
                        graphics2D.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
                        graphics2D.dispose();

                        super.paintComponent(graphics);
                }
        }

    private void createFolder() {

        String name = JOptionPane.showInputDialog(
                this,
                "Enter folder name:"
        );

        if (name == null) {
            return;
        }

        if (fileSystem.createFolder(name)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Folder created successfully!"
            );

            refreshDisplay();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Folder could not be created.\n"
                    + "Name may be empty or already exists.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void createFile() {

        String name = JOptionPane.showInputDialog(
                this,
                "Enter file name:"
        );

        if (name == null) {
            return;
        }

        if (fileSystem.createFile(name)) {

            JOptionPane.showMessageDialog(
                    this,
                    "File created successfully!"
            );

            refreshDisplay();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "File could not be created.\n"
                    + "Name may be empty or already exists.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

        private void addFileToFolder() {

                String folderName = JOptionPane.showInputDialog(
                                this,
                                "Enter the folder name:",
                                "Add File to Folder",
                                JOptionPane.PLAIN_MESSAGE
                );

                if (folderName == null || folderName.trim().isEmpty()) {
                        return;
                }

                String fileName = JOptionPane.showInputDialog(
                                this,
                                "Enter the file name:",
                                "Add File to Folder",
                                JOptionPane.PLAIN_MESSAGE
                );

                if (fileName == null || fileName.trim().isEmpty()) {
                        return;
                }

                if (fileSystem.createFileInFolder(folderName.trim(), fileName.trim())) {
                        JOptionPane.showMessageDialog(
                                        this,
                                        "File added to the folder successfully!",
                                        "Add File to Folder",
                                        JOptionPane.INFORMATION_MESSAGE
                        );
                        refreshDisplay();
                } else {
                        JOptionPane.showMessageDialog(
                                        this,
                                        "The folder was not found, or the file already exists.",
                                        "Unable to Add File",
                                        JOptionPane.ERROR_MESSAGE
                        );
                }
        }

    private void openFolder() {

        String name = JOptionPane.showInputDialog(
                this,
                "Enter the folder name to open:",
                "Open Folder",
                JOptionPane.PLAIN_MESSAGE
        );

        if (name == null || name.trim().isEmpty()) {
            return;
        }

        if (fileSystem.openFolder(name.trim())) {

            refreshDisplay();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Folder not found in current location.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void goBack() {

        if (fileSystem.goBack()) {

            refreshDisplay();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "You are already at Root."
            );
        }
    }

    private void search() {

        String name = JOptionPane.showInputDialog(
                this,
                "Enter file/folder name to search:"
        );

        if (name == null || name.trim().isEmpty()) {
            return;
        }

        FileNode result =
                fileSystem.searchDFS(name.trim());

        if (result != null) {

            String message =
                    "SEARCH USING DFS\n\n"
                    + "Found: " + result.getName() + "\n"
                    + "Type: "
                    + (result.isFolder()
                    ? "Folder"
                    : "File")
                    + "\n"
                    + "Path: "
                    + fileSystem.getPath(result);

            JOptionPane.showMessageDialog(
                    this,
                    message,
                    "Search Result",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "File/Folder not found.",
                    "Search Result",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    }

    private void delete() {

        String name = JOptionPane.showInputDialog(
                this,
                "Enter file/folder name to delete:"
        );

        if (name == null || name.trim().isEmpty()) {
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Delete \"" + name + "\"?",
                "Confirm Delete",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }

        if (fileSystem.delete(name.trim())) {

            JOptionPane.showMessageDialog(
                    this,
                    "Deleted successfully!"
            );

            refreshDisplay();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "File/Folder not found.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void showTree() {

        displayArea.setText(
                "COMPLETE FILE SYSTEM TREE\n"
                + "=========================\n\n"
                + fileSystem.getTreeText()
        );
    }

    private void showDFS() {

        List<String> traversal =
                fileSystem.getDFSTraversal();

        StringBuilder result =
                new StringBuilder();

        result.append(
                "DFS TRAVERSAL\n"
                + "=============\n\n"
        );

        for (int i = 0; i < traversal.size(); i++) {

            result.append(i + 1)
                  .append(". ")
                  .append(traversal.get(i))
                  .append("\n");
        }

        displayArea.setText(result.toString());
    }

    private void showStatistics() {

        int files = fileSystem.countFiles();
        int folders = fileSystem.countFolders();
        int height = fileSystem.getTreeHeight();

        String message =
                "FILE SYSTEM STATISTICS\n\n"
                + "Total Files   : " + files + "\n"
                + "Total Folders : " + folders + "\n"
                + "Tree Height   : " + height;

        JOptionPane.showMessageDialog(
                this,
                message,
                "Statistics",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void refreshDisplay() {

        pathLabel.setText(
                "Current Path: "
                + fileSystem.getPath(
                        fileSystem.getCurrent()
                )
        );

        displayArea.setText(
                fileSystem.getTreeText()
        );

        statisticsLabel.setText(
                "  Files: "
                + fileSystem.countFiles()
                + "     Folders: "
                + fileSystem.countFolders()
                + "     Tree Height: "
                + fileSystem.getTreeHeight()
                + "  "
        );
    }
}