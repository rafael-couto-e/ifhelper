package ifrs.canoas.model.portal;

public class User {

    public static String token = "";

    private int userid;
    private String fullname;
    private String userpictureurl;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        User.token = token;
    }
}
