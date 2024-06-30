package com.visitor.db;

import com.visitor.helpers.Arraylist;
import com.visitor.helpers.Hashmap;

import java.util.UUID;

public class MetricsDb {
    final static String KEY_FILE = "resources/visitor-metrics-key.json";
    final static String DB_NAME = "visitor-metrics";
    final static String COLLECTION_NAME = "matches";

    static FirestoreDb db;

    static {
        try {
            db = new FirestoreDb(KEY_FILE, DB_NAME);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addMatchHistory(UUID gameId, Arraylist<String> matchHistory) {
        Hashmap<String, Object> data = new Hashmap<>();
        data.putIn("time", java.time.Clock.systemUTC().instant())
                .putIn("history", matchHistory);
        try {
            db.createDocument(COLLECTION_NAME, gameId.toString(), data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
