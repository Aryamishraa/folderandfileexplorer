import java.util.ArrayList;
import java.util.List;

public class FileNode {

    private String name;
    private boolean folder;
    private FileNode parent;
    private List<FileNode> children;

    public FileNode(String name, boolean folder) {
        this.name = name;
        this.folder = folder;
        this.children = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public boolean isFolder() {
        return folder;
    }

    public FileNode getParent() {
        return parent;
    }

    public List<FileNode> getChildren() {
        return children;
    }

    public void addChild(FileNode child) {
        child.parent = this;
        children.add(child);
    }

    public void removeChild(FileNode child) {
        children.remove(child);
    }

    public boolean hasChild(String name) {
        for (FileNode child : children) {
            if (child.name.equalsIgnoreCase(name)) {
                return true;
            }
        }
        return false;
    }
}