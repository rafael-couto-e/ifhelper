package ifrs.canoas.model;

/**
 * Created by Aluno on 06/06/2017.
 */
public class User {

    private String token;
    private String error;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    @Override
    public String toString() {
        return "User{" +
                "token='" + token + '\'' +
                ", error='" + error + '\'' +
                '}';
    }
}
