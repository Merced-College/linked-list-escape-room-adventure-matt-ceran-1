import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;

// GUI version of the Escape Room Adventure game
// Uses the same Scene, SceneLinkedList, Player, and GameLoader classes
// from the console version - just swaps out the input/output for Swing
public class AdventureGameGUI extends JFrame implements ActionListener {

	// game data
	private SceneLinkedList scenes;
	private Player player;
	private Scene currentScene;

	// GUI components
	private JLabel titleLabel;
	private JTextArea descriptionArea;
	private JTextArea inventoryArea;
	private JButton choice1Button;
	private JButton choice2Button;
	private JButton pickUpButton;
	private JLabel messageLabel;

	public AdventureGameGUI() {
		// load up the scenes from the CSV file just like the console version
		scenes = GameLoader.loadScenes("data/scenes.csv");
		player = new Player();
		currentScene = scenes.findSceneById(1);

		// set up the main window
		setTitle("Escape Room Adventure");
		setSize(650, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout(10, 10));

		// title at the top
		titleLabel = new JLabel("", SwingConstants.CENTER);
		titleLabel.setFont(new Font("SansSerif", Font.BOLD, 22));
		add(titleLabel, BorderLayout.NORTH);

		// description in the middle
		descriptionArea = new JTextArea();
		descriptionArea.setEditable(false);
		descriptionArea.setLineWrap(true);
		descriptionArea.setWrapStyleWord(true);
		descriptionArea.setFont(new Font("SansSerif", Font.PLAIN, 16));
		JScrollPane descScroll = new JScrollPane(descriptionArea);
		add(descScroll, BorderLayout.CENTER);

		// inventory on the right side
		inventoryArea = new JTextArea(10, 15);
		inventoryArea.setEditable(false);
		inventoryArea.setBorder(BorderFactory.createTitledBorder("Inventory"));
		add(inventoryArea, BorderLayout.EAST);

		// bottom panel holds the buttons and the message label
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(2, 1));

		JPanel buttonPanel = new JPanel();
		choice1Button = new JButton("Choice 1");
		choice2Button = new JButton("Choice 2");
		pickUpButton = new JButton("Pick Up Item");

		// hook up the buttons to this class as the listener
		choice1Button.addActionListener(this);
		choice2Button.addActionListener(this);
		pickUpButton.addActionListener(this);

		buttonPanel.add(choice1Button);
		buttonPanel.add(choice2Button);
		buttonPanel.add(pickUpButton);

		messageLabel = new JLabel(" ", SwingConstants.CENTER);

		bottomPanel.add(buttonPanel);
		bottomPanel.add(messageLabel);
		add(bottomPanel, BorderLayout.SOUTH);

		// show the first room
		updateScene();
		setVisible(true);
	}

	// figure out which button was clicked and do the right thing
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();

		if (source == choice1Button) {
			goToNextScene(0);
		} else if (source == choice2Button) {
			goToNextScene(1);
		} else if (source == pickUpButton) {
			pickUpItem();
		}
	}

	// refreshes the screen to show whatever the current scene is
	private void updateScene() {
		if (currentScene == null) {
			return;
		}

		titleLabel.setText(currentScene.getTitle());
		descriptionArea.setText(currentScene.getDescription());
		inventoryArea.setText(player.getInventoryText());
		messageLabel.setText(" ");

		// update button text based on the choices in this scene
		if (currentScene.getChoices().size() >= 1) {
			choice1Button.setText(currentScene.getChoices().get(0).getText());
		}
		if (currentScene.getChoices().size() >= 2) {
			choice2Button.setText(currentScene.getChoices().get(1).getText());
		}

		// only show the pick up button if there's actually an item here
		if (currentScene.getItem() != null) {
			pickUpButton.setVisible(true);
		} else {
			pickUpButton.setVisible(false);
		}

		// check if we made it to the exit door
		if (currentScene.getSceneId() == 5) {
			checkWinCondition();
		}
	}

	// moves the player to the next scene based on which choice they picked
	private void goToNextScene(int choiceIndex) {
		if (choiceIndex < currentScene.getChoices().size()) {
			Choice picked = currentScene.getChoices().get(choiceIndex);
			int nextId = picked.getNextSceneId();
			currentScene = scenes.findSceneById(nextId);
			updateScene();
		}
	}

	// adds the current scene's item to the player's inventory
	private void pickUpItem() {
		Item item = currentScene.getItem();
		if (item != null) {
			player.addItem(item);
			currentScene.removeItem();
			messageLabel.setText("Picked up: " + item.getName());
			inventoryArea.setText(player.getInventoryText());
			pickUpButton.setVisible(false);
		}
	}

	// checks if the player has the right items to escape
	private void checkWinCondition() {
		boolean hasKeycard = player.hasItem("Keycard");
		boolean hasCodeNote = player.hasItem("Code Note");

		if (hasKeycard && hasCodeNote) {
			JOptionPane.showMessageDialog(this,
					"You used the Keycard and the Code Note to unlock the exit.\n"
							+ "You escaped. You win!",
					"You Win!", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		} else {
			messageLabel.setText("The door won't open. You need a Keycard and a Code Note.");
		}
	}

	public static void main(String[] args) {
		// start up the game window
		AdventureGameGUI game = new AdventureGameGUI();
	}
}