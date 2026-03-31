public class Choice {
    private String text;
    private int nextSceneId;

    public Choice(String text, int nextSceneId) {
        this.text = text;
        this.nextSceneId = nextSceneId;
    }

    public String getText() {
        return text;
    }

    public int getNextSceneId() {
        return nextSceneId;
    }

    @Override
    public String toString() {
        return text;
    }
}
