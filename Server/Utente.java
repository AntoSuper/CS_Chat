public class Utente {

    private String nickname;
    private Messaggio lastMessage;
    private String ID;
    private Data data;
    private int richiamo = 0;

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

    public Data getData () {
        return data;
    }

    public void setData (Data data) {  
        this.data=data;
    }

    public String getID () {
        return ID;
    }

    public void setID (String ID) {
        this.ID = ID;
    }

    public int getRichiamo () {
        return richiamo;
    }

    public void setRichiamo (int richiamo) {
        this.richiamo = richiamo;
    }

    public boolean equals (Utente x) {
        if (this.ID.equals(x.getID())) {
            return true;
        }
        return false;
    }

}
