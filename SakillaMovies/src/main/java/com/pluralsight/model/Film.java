package com.pluralsight.model;

public class Film {
        private int filmId;
        private String title;
        private String description;

        public Film(int filmId, String title, String description) {
            this.filmId = filmId;
            this.title = title;
            this.description = description;
        }

        public int getFilmId() {
            return filmId;
        }

        public String getTitle() {
            return title;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return String.format("%d: %s %s\n", filmId, title, description);
        }
    }

