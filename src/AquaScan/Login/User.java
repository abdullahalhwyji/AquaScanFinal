package AquaScan.Login;

public class User {

    private String name;
    private String address;
    private String email;
    private String password;
    private String photoPath;

    // Constructor
    public User(String name, String address, String email, String password, String photoPath) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.password = password;
        this.photoPath = photoPath;
    }

    // Getters and Setters for all fields

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhotoPath() {
        return photoPath;
    }

    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }
    // Add constructors, getters, and setters
}
