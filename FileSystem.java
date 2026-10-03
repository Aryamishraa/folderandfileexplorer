import java.util.ArrayList;
import java.util.List;

public class FileSystem {

    private FileNode root;
    private FileNode current;

    public FileSystem() {
        root = new FileNode("Root", true);
        current = root;

        // Sample data
        createFolder("Documents");
        createFolder("Pictures");
        createFolder("Music");

        openFolder("Documents");
        createFile("Resume.pdf");
        createFile("Assignment.docx");

        createFolder("Projects");
        openFolder("Projects");
        createFile("Java.java");
        createFile("Report.pdf");

        goBack();
        goBack();
    }

    public FileNode getRoot() {
        return root;
    }

    public FileNode getCurrent() {
        return current;
    }

    // Create Folder
    public boolean createFolder(String name) {

        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        name = name.trim();

        if (current.hasChild(name)) {
            return false;
        }

        FileNode folder = new FileNode(name, true);
        current.addChild(folder);

        return true;
    }

    // Create File
    public boolean createFile(String name) {

        if (name == null || name.trim().isEmpty()) {
            return false;
        }

        name = name.trim();

        if (current.hasChild(name)) {
            return false;
        }

        FileNode file = new FileNode(name, false);
        current.addChild(file);

        return true;
    }

    // Create a file inside a folder found anywhere in the tree
    public boolean createFileInFolder(String folderName, String fileName) {

        FileNode folder = searchDFS(folderName);

        if (folder == null || !folder.isFolder()) {
            return false;
        }

        if (fileName == null || fileName.trim().isEmpty()) {
            return false;
        }

        fileName = fileName.trim();

        if (folder.hasChild(fileName)) {
            return false;
        }

        folder.addChild(new FileNode(fileName, false));
        return true;
    }

    // Open Folder
    public boolean openFolder(String name) {

        for (FileNode child : current.getChildren()) {

            if (child.isFolder()
                    && child.getName().equalsIgnoreCase(name)) {

                current = child;
                return true;
            }
        }

        FileNode folder = searchDFS(name);

        if (folder != null && folder.isFolder()) {
            current = folder;
            return true;
        }

        return false;
    }

    // Go to Parent Folder
    public boolean goBack() {

        if (current.getParent() != null) {
            current = current.getParent();
            return true;
        }

        return false;
    }

    // DFS Search
    public FileNode searchDFS(String name) {
        return searchDFS(root, name);
    }

    private FileNode searchDFS(FileNode node, String name) {

        if (node.getName().equalsIgnoreCase(name)) {
            return node;
        }

        for (FileNode child : node.getChildren()) {

            FileNode result = searchDFS(child, name);

            if (result != null) {
                return result;
            }
        }

        return null;
    }

    // Get Full Path
    public String getPath(FileNode node) {

        List<String> pathParts = new ArrayList<>();

        while (node != null) {
            pathParts.add(0, node.getName());
            node = node.getParent();
        }

        return "/" + String.join("/", pathParts);
    }

    // Delete using DFS
    public boolean delete(String name) {

        FileNode node = searchDFS(name);

        if (node == null || node == root) {
            return false;
        }

        node.getParent().removeChild(node);

        // If current folder was deleted, return to parent
        if (isInside(node, current)) {
            current = node.getParent();
        }

        return true;
    }

    // Check whether current is inside a deleted folder
    private boolean isInside(FileNode node, FileNode target) {

        FileNode temp = target;

        while (temp != null) {

            if (temp == node) {
                return true;
            }

            temp = temp.getParent();
        }

        return false;
    }

    // DFS Traversal
    public List<String> getDFSTraversal() {

        List<String> result = new ArrayList<>();

        dfsTraversal(root, result);

        return result;
    }

    private void dfsTraversal(FileNode node, List<String> result) {

        result.add(node.getName());

        for (FileNode child : node.getChildren()) {
            dfsTraversal(child, result);
        }
    }

    // Display Tree
    public String getTreeText() {

        StringBuilder result = new StringBuilder();

        buildTree(root, "", result);

        return result.toString();
    }

    private void buildTree(
            FileNode node,
            String indent,
            StringBuilder result) {

        String icon = node.isFolder() ? "[Folder] " : "[File] ";

        result.append(indent)
              .append(icon)
              .append(node.getName())
              .append("\n");

        for (FileNode child : node.getChildren()) {

            buildTree(
                    child,
                    indent + "    ",
                    result
            );
        }
    }

    // Count Files
    public int countFiles() {
        return countFiles(root);
    }

    private int countFiles(FileNode node) {

        int count = 0;

        if (!node.isFolder()) {
            count++;
        }

        for (FileNode child : node.getChildren()) {
            count += countFiles(child);
        }

        return count;
    }

    // Count Folders
    public int countFolders() {
        return countFolders(root);
    }

    private int countFolders(FileNode node) {

        int count = node.isFolder() ? 1 : 0;

        for (FileNode child : node.getChildren()) {
            count += countFolders(child);
        }

        return count;
    }

    // Tree Height
    public int getTreeHeight() {
        return getHeight(root);
    }

    private int getHeight(FileNode node) {

        if (node.getChildren().isEmpty()) {
            return 0;
        }

        int maxHeight = 0;

        for (FileNode child : node.getChildren()) {

            int height = getHeight(child);

            if (height > maxHeight) {
                maxHeight = height;
            }
        }

        return maxHeight + 1;
    }
}