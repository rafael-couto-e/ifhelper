package ifrs.canoas.model;

public class Disciplina {
    private int id;
    private String shortname;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getShortname() {
        return shortname;
    }

    public void setShortname(String shortname) {
        this.shortname = shortname;
    }

    @Override
    public String toString() {
        return "Curso{" +
                "id=" + id +
                ", shortname='" + shortname + '\'' +
                '}';
    }
}
