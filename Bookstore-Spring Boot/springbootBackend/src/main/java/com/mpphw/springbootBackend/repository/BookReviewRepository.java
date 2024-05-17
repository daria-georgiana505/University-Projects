package com.mpphw.springbootBackend.repository;

import com.mpphw.springbootBackend.model.BookReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookReviewRepository extends JpaRepository<BookReview, Integer> {

}
