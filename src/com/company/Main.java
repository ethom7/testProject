package com.company;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.mindrot.jbcrypt.BCrypt;

import java.io.IOException;

import static com.mongodb.client.model.Filters.eq;

/**
 * Project is created as a test for methods to be implemented for the Primrose application.
 *
 *
 */




public class Main {

    private static MongoCollection<Document> collection = MongoConnector.getInstance().getMongoCollection("user");

    public static void main(String[] args) {


        if (checkHashedPassword("evthomps", "TeamPrimrose@2")) {
            System.out.println("Success!");
        }
        else {
            System.out.println("Failure!");
        }



        /*
        String date1 = "05/19/1986";

        LocalDate ld = LocalDate.parse(date1, DateTimeFormat.forPattern("MM/dd/yyyy"));

        System.out.println(ld);


        Document doc = new Document("date", ld.toDate());
        collection.insertOne(doc);
        */
    }

    // Method accepts userName and password input, will return true if password is valid for the user.
    // Password entry is in plain text.
    private static boolean checkHashedPassword(String userName, String password) {
        User user = null;
        ObjectMapper mapper = new ObjectMapper();

        Bson filter = eq("userName", userName);
        Bson projection = Projections.exclude("_id");

        MongoCursor<Document> itr = collection.find(filter)
                .projection(projection)
                .iterator();

        try {
            while (itr.hasNext()) {
                Document cur = itr.next();
                String hashed = cur.getString("password");
                if (BCrypt.checkpw(password, hashed)) {

                    try {
                        user = mapper.readValue(cur.toJson(), User.class);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    return true;
                }

            }
        } finally {  // always use when iterating with MongoCursor
            itr.close();  // ensure the cursor is closed in all situations, incase of an exception or break in loop
        }

        return false;

    }
}
