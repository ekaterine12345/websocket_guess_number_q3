package com.example.jvm2_quizz.models;

public class Message {
    private String from;
    private String content;
    private String to;

    public Message() {
    }

    public Message(String from, String content, String to) {
        this.from = from;
        this.content = content;
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    @Override
    public String toString() {
        return "Message{" +
                "from='" + from + '\'' +
                ", content='" + content + '\'' +
                ", to='" + to + '\'' +
                '}';
    }
}
