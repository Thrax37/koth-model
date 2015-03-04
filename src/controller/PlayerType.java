package controller;
import java.util.Scanner;

public class PlayerType {
	private final int id;
	private Player owner;

	public PlayerType(int id, Player owner) {
		super();
		this.id = id;
		this.owner = owner;
	}

	public Player getOwner() {
		return owner;
	}

	public void setOwner(Player owner) {
		this.owner = owner;
	}

	public int getId() {
		return id;
	}

	public String getCommand(String args) throws Exception {
		//neutral player
		if ("".equals(owner.getCmd())) {
			return "W";
		}
		Process proc = null;
		Scanner stdin = null;
		try {
			proc = Runtime.getRuntime().exec(owner.getCmd() + " " + args);
			stdin = new Scanner(proc.getInputStream());
			StringBuilder response = new StringBuilder();
			while (stdin.hasNext()) {
				response.append(stdin.next()).append(' ');
			}
			return response.toString();	
		} finally {
			if (stdin != null) stdin.close();
			if (proc != null) proc.destroy();
		}
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}

	@Override
	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (other != null && other instanceof PlayerType) {
			return getId() == ((PlayerType) other).getId();
		}
		return false;
	}
	
	@Override
	public String toString() {
		//return "Id: " + id + " Owner: " + owner.getDisplayName();
		return owner.getDisplayName();
	}
	
}
