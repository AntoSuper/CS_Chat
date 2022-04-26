public class Utente {

    private String nickname;
    private Messaggio lastMessage;
    private String ID;

    public Utente (String nickname) {
        this.nickname=nickname;
        this.lastMessage = new Messaggio("", "public", this, null);
        this.ID = new String("");
    }

    public Utente (String ID, String x) {
        if (x.equals("ID")) {
            this.ID = ID;
        }
    }
    
    public String getNickname () {
        return nickname;
    }

    public Messaggio getLastMessage () {
        return lastMessage;
    }

    public void setLastMessage (Messaggio lastMessage) {
        this.lastMessage=lastMessage;
    }

    public String getID () {
        return ID;
    }

    public void setID (String ID) {
        this.ID = ID;
    }

    public boolean equals (Utente x) {
        if (this.ID.equals(x.getID())) {
            return true;
        }
        return false;
    }

}
