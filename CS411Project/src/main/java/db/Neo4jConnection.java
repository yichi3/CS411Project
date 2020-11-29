package db;
import entity.User;
import org.neo4j.driver.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static db.MySQLDBUtil.*;
import static org.neo4j.driver.Values.parameters;

public class Neo4jConnection implements AutoCloseable {
    private final Driver driver;

    public Neo4jConnection( String uri, String user, String password ) {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    }

    @Override
    public void close() {
        driver.close();
    }

    public boolean addUser(User user) {
        try (Session session = driver.session()) {
            // first search if user already exists
            String search = String.format("MATCH (a:User) WHERE a.gameID = '%s'" +
                    "RETURN a.gameID", user.getGameID());
            Result result = session.run(search);
            if (result.hasNext()) {
                return false;
            }
            // second, insert user information
            String query = String.format("CREATE (a:User {gameID: '%s', password: '%s', email: '%s', phone: '%s'})",
                    user.getGameID(), user.getPassword(), user.getEmail(), user.getPhone());
            session.run(query);
            return true;
        }
    }

    public User getUserInfo(String gameID) {
        try (Session session = driver.session()) {
            String search = String.format("MATCH (a:User) WHERE a.gameID = '%s'" +
                    "RETURN a.gameID", gameID);
            Result result = session.run(search);
            if (!result.hasNext()) {
                return new User();
            }
            Record record = result.next();
            return getUserFromRecord(record);
        }
    }

//    public boolean updateUser(User user) {
//        try (Session session = driver.session()) {
//            String delete = String.format("MATCH (a:User) WHERE a.gameID = '%s'" +
//                    "DETACH DELETE a", user.getGameID());
//            session.run(delete);
//            String query = String.format("CREATE (a:User {gameID: '%s', password: '%s', email: '%s', phone: '%s'})",
//                    user.getGameID(), user.getPassword(), user.getEmail(), user.getPhone());
//            session.run(query);
//            return true;
//        }
//    }

    public void deleteUser(String gameID) {
        try (Session session = driver.session()) {
            String delete = String.format("MATCH (a:User) WHERE a.gameID = '%s'" +
                    "DETACH DELETE a", gameID);
            session.run(delete);
        }
    }

    public boolean loginCheck(String gameID, String password) {
        try (Session session = driver.session()) {
            String search = String.format("MATCH (a:User) WHERE a.gameID = '%s'" +
                    "RETURN a.gameID AS gameID, a.password AS password", gameID);
            Result result = session.run(search);
            if (!result.hasNext()) {
                return false;
            }
            String storedPassword = result.next().get("password").asString();
            return storedPassword.equals(password);
        }
    }

    public boolean userBindPosition(User user) {
        String gameID = user.getGameID();
        String position = user.getFavoritePosition();
        try (Session session = driver.session()) {
            String search = String.format("MATCH (a:User)-[r:PositionAt]->(b:Position) WHERE a.gameID = '%s'" +
                    "AND b.position = '%s' RETURN a.gameID", gameID, position);
            Result result = session.run(search);
            if (result.hasNext()) {
                return false;
            }
            String query = String.format("MATCH (a:User), (b:Position) WHERE a.gameID = '%s' AND b.position = '%s' CREATE" +
                            "(a)-[r:PositionAt]->(b)",
                    gameID, position);
            session.run(query);
            return true;
        }
    }

    public boolean userBindChampion(User user) {
        String gameID = user.getGameID();
        String champion = user.getFavoriteChampion();
        try (Session session = driver.session()) {
            String search = String.format("MATCH (a:User)-[r:Plays]->(b:Champion) WHERE a.gameID = '%s'" +
                    "AND b.name = '%s' RETURN a.gameID", gameID, champion);
            Result result = session.run(search);
            if (result.hasNext()) {
                return false;
            }
            String query = String.format("MATCH (a:User), (b:Champion) WHERE a.gameID = '%s' AND b.name = '%s' CREATE" +
                            "(a)-[r:Plays]->(b)",
                    gameID, champion);
            session.run(query);
            return true;
        }
    }

    public List<User> getUsersByPosition(String position) {
        try (Session session = driver.session()) {
            return session.readTransaction( tx -> {
                List<User> users = new ArrayList<>();
                Result result = tx.run("MATCH (c:Champion)<-[:Plays]-(a:User)-[:PositionAt]->(b:Position) " +
                        "WHERE b.position = $position RETURN a.gameID AS gameID, a.email as email, " +
                        "a.phone AS phone, b.position AS position, c.name as champion " +
                        "ORDER BY a.gameID", parameters("position", position));
                while (result.hasNext()) {
                    Record record = result.next();
                    users.add(getUserFromRecord(record));
                }
                return users;
            } );
        }
    }

    public List<User> getUsersByChampion(String champion) {
        try (Session session = driver.session()) {
            return session.readTransaction( tx -> {
                List<User> users = new ArrayList<>();
                Result result = tx.run("MATCH (c:Champion)<-[:Plays]-(a:User)-[:PositionAt]->(b:Position) " +
                        "WHERE c.name = $name RETURN a.gameID AS gameID, a.email as email, " +
                        "a.phone AS phone, b.position AS position, c.name as champion " +
                        "ORDER BY a.gameID", parameters("name", champion));
                while (result.hasNext())
                {
                    Record record = result.next();
                    users.add(getUserFromRecord(record));
                }
                return users;
            } );
        }
    }

    private User getUserFromRecord(Record record) {
        return new User.UserBuilder()
                .setGameID(record.get("gameID").asString())
                .setEmail(record.get("email").asString())
                .setPhone(record.get("phone").asString())
                .setFavoriteChampion(record.get("champion").asString())
                .setFavoritePosition(record.get("position").asString())
                .build();
    }

    public static void main(String[] args) throws Exception
    {
        try ( Neo4jConnection con = new Neo4jConnection(Neo4jURI, Neo4jUSERNAME, Neo4jPASSWORD) )
        {
//            String gameID = "Uzi";
//            String password = "123";
//            String email = "a@a.com";
//            String phone = "456";
//            String position = "bottom";
//            String champion = "Vayne";
//            User newUser = new User.UserBuilder()
//                    .setGameID(gameID)
//                    .setEmail(email)
//                    .setPhone(phone)
//                    .setPassword(password)
//                    .setFavoritePosition(position)
//                    .setFavoriteChampion(champion)
//                    .build();
//            gameID = "Lwx";
//            password = "123";
//            email = "a@a.com";
//            phone = "456";
//            position = "bottom";
//            champion = "Vayne";
//            User newUser = new User.UserBuilder()
//                    .setGameID(gameID)
//                    .setEmail(email)
//                    .setPhone(phone)
//                    .setPassword(password)
//                    .setFavoritePosition(position)
//                    .setFavoriteChampion(champion)
//                    .build();
//            System.out.println(con.addUser(newUser));
//            System.out.println(con.userBindChampion(newUser));
//            System.out.println(con.userBindPosition(newUser));
            List<User> usersByChampion = con.getUsersByChampion("Vayne");
            List<User> usersByPosition = con.getUsersByPosition("bottom");
            System.out.println(Arrays.toString(usersByChampion.toArray()));
            System.out.println(Arrays.toString(usersByPosition.toArray()));
        }
    }
}