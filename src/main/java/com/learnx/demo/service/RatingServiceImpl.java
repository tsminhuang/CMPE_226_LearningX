package com.learnx.demo.service;

import com.learnx.demo.model.Rating;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingServiceImpl implements RatingService {
    @Override
    public Rating create(Rating newRate) {
        return null;
    }

    @Override
    public Rating update(Rating rate) {
        return null;
    }

    @Override
    public List<Rating> listRatingsByCourseId(int courseId) {
        return null;
    }

    @Override
    public int getAverageRatingByCourseId(int courseId) {
        return 0;
    }
}
