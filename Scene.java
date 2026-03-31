import java.util.ArrayList;

public class Scene {
    private int sceneId;
    private String title;
    private String description;
    private String imagePath;
    private ArrayList<Choice> choices;
    private Item item;

    public Scene(int sceneId, String title, String description, String imagePath, Item item) {
        this.sceneId = sceneId;
        this.title = title;
        this.description = description;
        this.imagePath = imagePath;
        this.item = item;
        this.choices = new ArrayList<>();
    }

    public void addChoice(Choice choice) {
        choices.add(choice);
    }

    public int getSceneId() {
        return sceneId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImagePath() {
        return imagePath;
    }

    public ArrayList<Choice> getChoices() {
        return choices;
    }

    public Item getItem() {
        return item;
    }

    public void removeItem() {
        item = null;
    }

    @Override
    public String toString() {
        return "Scene " + sceneId + ": " + title;
    }
}
