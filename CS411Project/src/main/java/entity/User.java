package entity;

import org.json.JSONObject;

public class User {
    private final String userName;
    private final String firstName;
    private final String lastName;
    private final String email;
    private final String phone;

    private User(UserBuilder builder) {
        this.userName = builder.userName;
        this.firstName = builder.firstName;
        this.lastName = builder.lastName;
        this.email = builder.email;
        this.phone = builder.phone;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }

    public JSONObject toJSONObject() {
        JSONObject obj = new JSONObject();
        obj.put("user_name", userName);
        obj.put("first_name", firstName);
        obj.put("last_name", lastName);
        obj.put("email", email);
        obj.put("phone", phone);
        return obj;
    }

    public static class UserBuilder {
        private String userName;
        private String firstName;
        private String lastName;
        private String email;
        private String phone;

        public UserBuilder setUserName(String userName) {
            this.userName = userName;
            return this;
        }

        public UserBuilder setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public UserBuilder setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public UserBuilder setEmail(String email) {
            this.email = email;
            return this;
        }

        public UserBuilder setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public User build() {
            return new User(this);
        }

    }
}
