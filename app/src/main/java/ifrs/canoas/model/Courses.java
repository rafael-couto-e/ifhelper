package ifrs.canoas.model;

import java.util.List;

public class Courses {
    private String firstname;
    private int userid;
    private List<Course> courses;

    public Courses(String firstname, int userId, List<Course> courses) {
        this.firstname = firstname;
        this.userid = userId;
        this.courses = courses;
    }

    public Courses() {
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public int getUserId() {
        return userid;
    }

    public void setUserId(int userId) {
        this.userid = userId;
    }

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }
}
