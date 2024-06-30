package com.visitor.db;
/*
 * Copyright 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.*;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;
import com.visitor.helpers.Hashmap;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

/**
 * A simple Quick start application demonstrating how to connect to Firestore
 * and add and query documents.
 */
public class FirestoreDb {

    private final Firestore db;

    public FirestoreDb(String serviceAccountPath, String projectId) throws Exception {
        InputStream serviceAccount = new FileInputStream(serviceAccountPath);
        GoogleCredentials credentials = GoogleCredentials.fromStream(serviceAccount);
        FirebaseOptions options = new FirebaseOptions.Builder()
                .setProjectId(projectId)
                .setCredentials(credentials)
                .build();
        FirebaseApp.initializeApp(options);

        db = FirestoreClient.getFirestore();
    }

    Firestore getDb() {
        return db;
    }

    /**
     * Add named test documents with fields first, last, middle (optional), born.
     *
     * @param docName document name
     */
    void createDocument(String collectionName, String docName, Hashmap<String, Object> data) throws Exception {
        // [START fs_add_data_1]
        // [START firestore_setup_dataset_pt1]
        DocumentReference docRef = db.collection(collectionName).document(docName);
        //asynchronously write data
        ApiFuture<WriteResult> result = docRef.set(data);
        // ...
        // result.get() blocks on response
        System.out.println("Update time : " + result.get().getUpdateTime());
        // [END firestore_setup_dataset_pt1]
        // [END fs_add_data_1]
    }

    void retrieveAllDocuments(String collectionName) throws Exception {
        // [START fs_get_all]
        // asynchronously retrieve all users
        ApiFuture<QuerySnapshot> query = db.collection(collectionName).get();
        // ...
        // query.get() blocks on response
        QuerySnapshot querySnapshot = query.get();
        List<QueryDocumentSnapshot> documents = querySnapshot.getDocuments();
        for (QueryDocumentSnapshot document : documents) {
            /*
            System.out.println("User: " + document.getId());
            System.out.println("First: " + document.getString("first"));
            if (document.contains("middle")) {
                System.out.println("Middle: " + document.getString("middle"));
            }
            System.out.println("Last: " + document.getString("last"));
            System.out.println("Born: " + document.getLong("born"));
            */
        }
        // [END fs_get_all]
    }

    /**
     * Closes the gRPC channels associated with this instance and frees up their resources.
     */
    void close() throws Exception {
        db.close();
    }
}
