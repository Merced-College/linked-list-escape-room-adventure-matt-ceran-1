import java.util.Scanner;

public class AdventureGame {
    private SceneLinkedList scenes;
    private Player player;
    private Scene currentScene;
    private Scanner scanner;

    public AdventureGame() {
        scenes = GameLoader.loadScenes("data/scenes.csv");
        player = new Player();
        currentScene = scenes.findSceneById(1);
        scanner = new Scanner(System.in);
    }

    public void play() {
        System.out.println("Welcome to Escape Room Adventure!");
        System.out.println("Collect the correct items before reaching the exit.\n");

        while (currentScene != null) {
            displayCurrentScene();

            if (currentScene.getSceneId() == 5) {
                handleFinalRoom();
                break;
            }

            if (currentScene.getItem() != null) {
                handleItemPickup();
            }

            handleChoices();
        }

        scanner.close();
    }

    private void displayCurrentScene() {
        System.out.println("\n==============================");
        System.out.println(currentScene.getTitle());
        System.out.println("==============================");
        System.out.println(currentScene.getDescription());
        System.out.println();
        System.out.println(player.getInventoryText());
        System.out.println();
    }

    private void handleItemPickup() {
        Item item = currentScene.getItem();

        System.out.println("You found an item: " + item.getName());
        System.out.print("Would you like to pick it up? (yes/no): ");
        String answer = scanner.nextLine();

        if (answer.equalsIgnoreCase("yes")) {
            player.addItem(item);
            currentScene.removeItem();
            System.out.println(item.getName() + " added to your inventory.");
        }
    }

    private void handleChoices() {
        System.out.println("Choose an option:");
        for (int i = 0; i < currentScene.getChoices().size(); i++) {
            System.out.println((i + 1) + ". " + currentScene.getChoices().get(i).getText());
        }

        System.out.print("Enter choice number: ");
        int userChoice = Integer.parseInt(scanner.nextLine());

        if (userChoice >= 1 && userChoice <= currentScene.getChoices().size()) {
            int nextId = currentScene.getChoices().get(userChoice - 1).getNextSceneId();
            currentScene = scenes.findSceneById(nextId);
        } else {
            System.out.println("Invalid choice. Try again.");
        }
    }

    private void handleFinalRoom() {
        System.out.println("You reached the exit door.");

        boolean hasKeycard = player.hasItem("Keycard");
        boolean hasCodeNote = player.hasItem("Code Note");

        if (hasKeycard && hasCodeNote) {
            System.out.println("You used the Keycard and the Code Note to unlock the exit.");
            System.out.println("You escaped. You win!");
        } else {
            System.out.println("The exit will not open.");
            System.out.println("You are missing the required items.");
            System.out.println("To win, you need: Keycard and Code Note.");
        }
    }
}
