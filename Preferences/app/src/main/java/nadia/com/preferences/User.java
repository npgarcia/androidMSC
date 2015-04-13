package nadia.com.preferences;

/**
 * Created by ngarcia on 3/11/15.
 */
public class User {
    private String usuario;
    private String password;
    private boolean isLogged;

    public User(String usuario, String password, boolean isLogged) {
        this.usuario = usuario;
        this.password = password;
        this.isLogged = isLogged;
    }

    public boolean isLogged() {
        return isLogged;
    }

    public void setLogged(boolean isLogged) {
        this.isLogged = isLogged;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
