public class Utente {

    private String nickname;
    private String ID;

    public Utente (String nickname) {
        this.nickname=nickname;
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

    public String getID () {
        return ID;
    }

    public void setID (String ID) {
        this.ID = ID;
    }

}
