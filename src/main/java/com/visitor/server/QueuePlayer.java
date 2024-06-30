package com.visitor.server;

public class QueuePlayer {
    public String username;
    public String[] decklist;

    public QueuePlayer(String username, String[] decklist) {
        this.username = username;
        this.decklist = decklist;
    }
}
