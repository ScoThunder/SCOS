package es.source.code.model;

/**
 * Created by Hander on 16/6/17.
 * <p/>
 * Email : hander_wei@163.com
 */
public class User {

    String userName;
    String password;
    boolean oldUser;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isOldUser() {
        return oldUser;
    }

    public void setOldUser(boolean oldUser) {
        this.oldUser = oldUser;
    }
}
