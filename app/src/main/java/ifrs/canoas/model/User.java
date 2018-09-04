package ifrs.canoas.model;

import java.io.Serializable;

public class User implements Serializable{
    private int userid;
    private String username;
    private String fullname;
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void setUserid(int userId){
        this.userid = userId;
    }

    public int getUserid() {
        return userid;
    }



    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    @Override
    public String toString() {
        return "UsuarioCompleto{" +
                "userId=" + userid +
                ", username='" + username + '\'' +
                ", fullname='" + fullname + '\'' +
                '}';
    }
}
