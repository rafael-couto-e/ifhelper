package ifrs.canoas.model.portal;

/**
 * Created by marcio on 27/08/17.
 */

public class Curso {

    private int id;
    private String shortname;
    private String fullname;
    private boolean visible;
    private String format;
    private boolean showgrades;
    private String lang;
    private boolean enablecompletion;
    private int category;

    @Override
    public String toString() {
        return "Curso Bonitinho{" +
                "id=" + id +
                ", shortname='" + shortname + '\'' +
                ", fullname='" + fullname + '\'' +
                ", visible=" + visible +
                ", format='" + format + '\'' +
                ", showgrades=" + showgrades +
                ", lang='" + lang + '\'' +
                ", enablecompletion=" + enablecompletion +
                ", category=" + category +
                '}';
    }
}
