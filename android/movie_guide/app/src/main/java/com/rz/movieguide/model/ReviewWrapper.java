package com.rz.movieguide.model;

import com.squareup.moshi.Json;

import java.util.List;

public class ReviewWrapper {

    @Json(name = "results")
    List<Review> reviews;

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public String toString() {
        return "ReviewWrapper{" +
                "reviews=" + reviews +
                '}';
    }

}
