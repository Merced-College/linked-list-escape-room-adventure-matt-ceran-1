import java.util.ArrayList;

public class Player {
    private ArrayList<Item> inventory;

    public Player() {
        inventory = new ArrayList<>();
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public ArrayList<Item> getInventory() {
        return inventory;
    }

    public boolean hasItem(String itemName) {
        for (Item item : inventory) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    public String getInventoryText() {
        if (inventory.isEmpty()) {
            return "Inventory: empty";
        }

        String result = "Inventory:\n";
        for (Item item : inventory) {
            result += "- " + item.getName() + "\n";
        }
        return result;
    }
}
