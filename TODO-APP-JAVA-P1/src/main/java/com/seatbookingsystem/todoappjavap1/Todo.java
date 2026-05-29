package com.seatbookingsystem.todoappjavap1;

public class Todo {
    private static int counter = 0;

    private final int id;
    private String text;
    private boolean completed;

    public Todo(String text) {
        this.id = ++counter;
        this.text = text;
        this.completed = false;
    }

    public int getId()            { return id; }
    public String getText()       { return text; }
    public void setText(String t) { this.text = t; }
    public boolean isCompleted()  { return completed; }
    public void toggle()          { this.completed = !this.completed; }
}
