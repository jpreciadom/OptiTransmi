package UserDataConfig;

import Base.BasePackage;

public class ChangeUserInfo extends BasePackage {
    private final String userName;
    private final String correo;
    private final String password;

    public ChangeUserInfo(int idRequest, String userName, String correo, String password){
        super(3, idRequest);
        this.userName = userName;
        this.correo = correo;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public String getCorreo() {
        return correo;
    }

    public String getPassword() {
        return password;
    }
}
