package entity;

import org.json.JSONObject;


public class User {
    private final String userName;
    private final String firstName;
    private final String lastName;
    private final String phoneNumber;
    private final String email;


    private User(User.UserBuilder builder) {
        this.userName = builder.userName;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.phoneNumber = builder.phoneNumber;
        this.email = builder.email;

    }

    public String getUserName(int userName) {
        return this.userName;
    }

    public String getFirstName(String firstName) {
        return this.firstName;
    }

    public String getLastName(String lastName) {
        return this.lastName;
    }

    public String getPhoneNumber(String phoneNumber) {
        return this.phoneNumber;
    }

    public String getEmail(String email) {
        return this.email;
    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("user_name", userName);
        obj.put("first_name", firstName);
        obj.put("last_name", lastName);
        obj.put("phone_number", phoneNumber);
        obj.put("email", email);
        return obj;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public static class UserBuilder {
        private String userName;
        private String firstName;
        private String lastName;
        private String phoneNumber;
        private String email;

        public UserBuilder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserBuilder getFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder getLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder getPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
            return this;
        }

        public UserBuilder getEmail(String email) {
            this.email = email;
            return this;
        }


        public User build() {
            return new User(this);
        }

    }
}
