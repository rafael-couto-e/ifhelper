package ifrs.canoas.model;

public class Course {
    private String fullname;

    public Course() {
    }

    public Course(String fullname) {
        this.fullname = fullname;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }
}
