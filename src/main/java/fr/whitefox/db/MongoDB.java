package fr.whitefox.db;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import fr.whitefox.Configuration;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;

public class MongoDB {
    private static MongoClient instance = null;
    private static final Configuration config = Configuration.getInstance();
    private static final MongoClient mongoClient = MongoDB.getInstance();
    private static final MongoDatabase database = mongoClient.getDatabase("Dev");
    private static final MongoCollection<Document> collection = database.getCollection("RankBot");

    public static MongoClient getInstance() {
        if (instance == null) {
            try {
                instance = new MongoClient(new MongoClientURI(config.getProperty("bdd")));
            } catch (ExceptionInInitializerError | IllegalArgumentException e) {
                System.out.println("[ERROR] Vous devez définir le lien de connexion de la base de données MongoDB en faisant la commande Discord /config bdd LIEN_MONGODB");
            }
        }
        return instance;
    }

    public static boolean isRegister(long userId) {
        Bson filtre = eq("discordId", userId);
        long count = collection.countDocuments(filtre);
        return count > 0;
    }

    public static void register(long userId, int xp, int level) {
        Document document = new Document();
        document.put("discordId", userId);
        document.put("xp", xp);
        document.put("level", level);
        collection.insertOne(document);
    }

    public static void incrementXp(long userId, int xp) {
        Bson filtre = eq("discordId", userId);
        Document update = new Document("$inc", new Document("xp", xp));
        collection.updateOne(filtre, update);
    }

    public static void replaceXp(long userId, int xp) {
        Bson filtre = eq("discordId", userId);
        Document update = new Document("$set", new Document("xp", xp));
        collection.updateOne(filtre, update);
    }

    public static void replaceLevel(long userId, int level) {
        Bson filtre = eq("discordId", userId);
        Document update = new Document("$set", new Document("level", level));
        collection.updateOne(filtre, update);
    }

    public static int getXp(long userId) {
        Bson filtre = eq("discordId", userId);
        Document document = collection.find(filtre).iterator().next();
        return document.getInteger("xp");
    }

    public static int getLevel(long userId) {
        Bson filtre = eq("discordId", userId);
        Document document = collection.find(filtre).iterator().next();
        return document.getInteger("level");
    }

    public static String getRankList() {
        List<Document> results = new ArrayList<>();
        collection.find().sort(descending("level", "xp")).into(results);
        StringBuilder msg = new StringBuilder();
        int count = 0;
        for (Document result : results) {
            count++;
            msg.append("`#").append(count).append("` <@").append(result.get("discordId")).append("> » Niveau **").append(result.get("level")).append("**\n");
            if (count == 10) return msg.toString();
        }

        return msg.toString();
    }

    public static int getRank(long userId) {
        List<Document> results = new ArrayList<>();
        collection.find().sort(descending("level", "xp")).into(results);
        int count = 0;
        int rank = 0;
        for (Document result : results) {
            count++;
            if (Long.parseLong(result.get("discordId").toString()) == userId) {
                rank = count;
            }
        }

        return rank;
    }
}