package ifrs.canoas.model.portal;

/**
 * Created by marcio on 27/08/17.
 */

public class Mensagem {

    private int id;
    private String userfromfullname;
    private String smallmessage;
    private int useridfrom;
    private String subject;
    private String fullmessage;
    //private int timecreated;
    //private int timeread;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserfromfullname() {
        return userfromfullname;
    }

    public void setUserfromfullname(String userfromfullname) {
        this.userfromfullname = userfromfullname;
    }

    public String getSmallmessage() {
        return smallmessage;
    }

    public void setSmallmessage(String smallmessage) {
        this.smallmessage = smallmessage;
    }

    public int getUseridfrom() {
        return useridfrom;
    }

    public void setUseridfrom(int useridfrom) {
        this.useridfrom = useridfrom;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getFullmessage() {
        return fullmessage;
    }

    public void setFullmessage(String fullmessage) {
        this.fullmessage = fullmessage;
    }


    @Override
    public String toString() {
        return "Mensagem{" +
                "id=" + id +
                ", userfromfullname='" + userfromfullname + '\'' +
                ", smallmessage='" + smallmessage + '\'' +
                ", useridfrom=" + useridfrom +
                ", subject='" + subject + '\'' +
                ", fullmessage='" + fullmessage + '\'' +
                '}';
    }
}
