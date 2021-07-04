/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Objects;

/**
 *
 * @author Your Name <Sebastián Navarro Martínez>
 */
public class Security {
    
    private String user;
    private String password;
    private String kindUser;

    public Security(String user, String password, String kindUser) {
        this.user = user;
        this.password = password;
        this.kindUser = kindUser;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getKindUser() {
        return kindUser;
    }

    public void setKindUser(String kindUser) {
        this.kindUser = kindUser;
    }

    @Override
    public String toString() {
        return "Security{" + "user:" + user + ", password:" + password + ", tipoUsuario " + kindUser;
    }

    public String[] getDataName(){
        String[] dataName = {"User","Password","KindUser"};
        return dataName;
    }
    
    public String[] getData(){
        String[] data = {this.getUser(), this.getPassword(),this.getKindUser()};
        return data;
    }

    
    
    
}
