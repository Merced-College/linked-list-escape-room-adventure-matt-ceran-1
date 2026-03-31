import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class GameLoader {

    public static SceneLinkedList loadScenes(String fileName) {
        SceneLinkedList scenes = new SceneLinkedList();

        try {
            Scanner fileScanner = new Scanner(new File(fileName));

            if (fileScanner.hasNextLine()) {
                fileScanner.nextLine(); // skip header
            }

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                String[] parts = line.split(",");

                int sceneId = Integer.parseInt(parts[0]);
                String title = parts[1];
                String description = parts[2];
                String imagePath = parts[3];
                String itemName = parts[4];
                String itemDescription = parts[5];

                Item item = null;
                if (!itemName.equalsIgnoreCase("none")) {
                    item = new Item(itemName, itemDescription);
                }

                Scene scene = new Scene(sceneId, title, description, imagePath, item);

                String choice1Text = parts[6];
                int choice1NextId = Integer.parseInt(parts[7]);
                String choice2Text = parts[8];
                int choice2NextId = Integer.parseInt(parts[9]);

                scene.addChoice(new Choice(choice1Text, choice1NextId));
                scene.addChoice(new Choice(choice2Text, choice2NextId));

                scenes.add(scene);
            }

            fileScanner.close();

        } catch (FileNotFoundException e) {
            System.out.println("Could not find file: " + fileName);
        }

        return scenes;
    }
}
