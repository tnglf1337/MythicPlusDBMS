public class User {
    private final String USERNAME;
    private final String PASSWORD;

    public User(String USERNAME, String PASSWORD) {
        this.USERNAME = USERNAME;
        this.PASSWORD = PASSWORD;
    }

    public String getName() {
        return USERNAME;
    }

    public String getPassword() {
        return PASSWORD;
    }
}