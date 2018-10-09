package com.gd.intern.dawidlibrarytest.model;

import java.util.Objects;

public class GenreRest {
    private String genre;

    public GenreRest() {}

    public GenreRest(String genre) {
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GenreRest genreRest = (GenreRest) o;
        return Objects.equals(genre, genreRest.genre);
    }

    @Override
    public int hashCode() {
        return Objects.hash(genre);
    }

    @Override
    public String toString() {
        return "GenreRest{" +
                "genre='" + genre + '\'' +
                '}';
    }
}
