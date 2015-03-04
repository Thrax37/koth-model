package controller;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Game {
	private static Player[] players = {

	};
	
	// Game Parameters
	private static final int ROUNDS = 50;
	
	// Console
	private static final boolean DEBUG = false;
	private static final boolean GAME_MESSAGES = true;
	
	private final List<PlayerType> playerTypes = new ArrayList<PlayerType>();
	private int round = 0;
	
	public Game() {
		for (int i = 0; i < players.length; i++) {
			players[i].setId(i);
		}
	}
	
	public static void main(String... args) {
		// Starting
		new Game().run();
	}	
	
	public void run() {
			
		if (GAME_MESSAGES) 
			System.out.println("Starting a new game...");
		
		this.initialize();
		
		if (GAME_MESSAGES) 
			System.out.println("Game begins.");
							
		for (round = 1; round <= ROUNDS; round++) {
			if (GAME_MESSAGES) {
				System.out.println("-- Round : " +  round + " --");
			}
			if (!makeTurns()) break; //break if only no player left or monster dead
		}
	
		printResults();
	}
	
	private void initialize() {		
		
		for (int i = 0; i < players.length; i++) {
			try {
				String preparation = players[i].prepare();
				
				if (GAME_MESSAGES) System.out.println(players[i].getDisplayName() + " preparation.");

				PlayerType playerType = new PlayerType(i, players[i]);
				playerTypes.add(playerType);
				
			} catch (Exception e) {
				if (DEBUG) {
					System.out.println("Exception in initialize() by " + players[i].getDisplayName());
					e.printStackTrace();
				}
			}
		}
		
		Collections.shuffle(playerTypes);
	}	
	
	private boolean makeTurns() {
		
		for (PlayerType playerType : playerTypes) {
					
			Player owner = playerType.getOwner();
			try {
				String request = round + ";" + owner.getId();
				String response = playerType.getCommand(request);
				if (DEBUG) {
					System.out.println("Request : " + request);
					System.out.println("Response : " + response);
				}
				switch (response.charAt(0)) {
					default: executeWait(new Command(response, playerType)); break;
				}
			} catch (Exception e) {
				if (DEBUG) {
					System.out.println("Exception in makeTurns() by " + owner.getDisplayName());
					e.printStackTrace();
				}
			}
		
			
			if (gameOver())
				return false;
		}
		
		return true;
	}
	
	private boolean gameOver() {
		return false;
	}
	
	private void executeWait(Command command) {
	}
	
	
	private void printResults() {
		
		List<Score> scores = new ArrayList<Score>();
		
		System.out.println("********** FINISH **********");
		
		
		for (Player player : players) {
			scores.add(new Score(player, player.getScore()));
		}
		
		//sort descending
		Collections.sort(scores, Collections.reverseOrder());
		
		for (int i = 0; i < scores.size(); i++) {
			Score score = scores.get(i);
			System.out.println(i+1 + ". " + score.print());
		}
		
	}
	
}

