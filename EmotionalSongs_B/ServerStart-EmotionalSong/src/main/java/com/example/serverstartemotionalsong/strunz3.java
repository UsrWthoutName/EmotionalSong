package com.example.serverstartemotionalsong;

public class strunz3 {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/labb";
        DatabaseManager.LoadTable(url, "postgres", "postgres");
    }
}
