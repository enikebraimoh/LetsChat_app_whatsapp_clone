package com.enikebraimoh.flashchatnewfirebase;


class model {

    private String message;
    private String author;

    model(String message, String author) {
        this.message = message;
        this.author = author;
    }

    public model() {


    }

    public String getMessage() {
        return message;
    }

    public String getAuthor() {
        return author;
    }
}
