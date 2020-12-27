package gameManager;

public class Player {
	private String username;
	private playerType player;
	enum playerType{
		COMPUTER, HUMAN;
	}
	
	public Player (String uname, playerType ptype) {
		this.username = uname;
		this.player = ptype;
	}
	
	public playerType getPlayerType() {
		return player;
	}
	
	public String getUsername() {
		return username;
	}
	
}
