package entity;

import java.sql.Timestamp;

public class User {
    /**
     * 用户注册
     */
    private String id;
    private String username;
    private String password;
    private String email;
    private String tel;
    private String address;
    private String machineType;
    private String machineId;
    private String newPassword;
    private Timestamp timestamp;

    public User() {
        this.id = "";
        this.username = "";
        this.password = "";
        this.email = "";
        this.tel = "";
        this.address = "";
        this.machineType = "";
        this.machineId = "";
        this.newPassword = "";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getMachineType() {
        return machineType;
    }

    public void setMachineType(String machineType) {
        this.machineType = machineType;
    }

    public String getMachineId() {
        return machineId;
    }

    public void setMachineId(String machineId) {
        this.machineId = machineId;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", tel='" + tel + '\'' +
                ", address='" + address + '\'' +
                ", machineType='" + machineType + '\'' +
                ", machineId='" + machineId + '\'' +
                ", newPassword='" + newPassword + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
