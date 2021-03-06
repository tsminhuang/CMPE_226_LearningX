package com.learnx.demo.repository;

import com.learnx.demo.entity.Rating;
import java.math.BigDecimal;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RatingRepository {

    private final EntityManager em;

    @Autowired
    public RatingRepository(EntityManager em) {
        this.em = em;
    }

    @Transactional
    public Rating save(Rating entity) {
        String sql = "INSERT INTO Rating(studentId, courseId, rate) " +
                "VALUES( :studentId,  :courseId, :rate)";
        Query query = em.createNativeQuery(sql)
                .setParameter("studentId", entity.getStudentId())
                .setParameter("courseId", entity.getCourseId())
                .setParameter("rate", entity.getRate());

        if (query.executeUpdate() == 0) {
            return null;
        }

        Rating saveEntity = new Rating();
        saveEntity.setCourseId(entity.getCourseId());
        saveEntity.setStudentId(entity.getStudentId());
        saveEntity.setRate(entity.getRate());

        return saveEntity;
    }

    @Transactional
    public Rating update(Rating newEntity) {
        String sql = "UPDATE Rating SET rate =:rate " +
                "WHERE studentId = :studentId AND courseId = :courseId";
        Query query = em.createNativeQuery(sql)
                .setParameter("rate", newEntity.getRate())
                .setParameter("studentId", newEntity.getStudentId())
                .setParameter("courseId", newEntity.getCourseId());

        if (query.executeUpdate() == 0) {
            return null;
        }

        return newEntity;
    }

    public int getAvgRatingByCourseId(int courseId) {
        String sql = "SELECT rate FROM AverageRating WHERE id = :courseId";
        Query query = em.createNativeQuery(sql)
                .setParameter("courseId", courseId);

        List<?> result = query.getResultList();
        if (result.size() != 1) {
            return 0;
        }
        BigDecimal bi = (BigDecimal) result.get(0);

        return bi.intValue();
    }

    public List<Rating> findRatingByCourseId(int courseId) {
        String sql = "SELECT R.* FROM Rating R WHERE R.courseId = :courseId";
        Query query = em.createNativeQuery(sql, Rating.class)
                .setParameter("courseId", courseId);

        return RepositoryUtil.castAll(query.getResultList(), Rating.class);
    }

    @Transactional
    public void delete(int studentId, int courseId) {
        String sql = "DELETE FROM Rating WHERE studeutId =:studentId AND courseId = :courseId";
        Query query = em.createNativeQuery(sql)
                .setParameter("studentId", studentId)
                .setParameter("courseId", courseId);

        query.executeUpdate();
    }
}
