package org.zerock.mreview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;

import java.util.List;
import java.util.stream.IntStream;

@SpringBootTest
public class ReviewRepositoryTests {

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertReview() {

        IntStream.rangeClosed(1, 200).forEach(i -> {

            Long mno = (long)(Math.random() * 100) + 1;

            Long mid = (long)(Math.random() * 100) + 1;

            Member member = Member.builder()
                                .mid(mid)
                                .build();

            Movie movie = Movie.builder()
                                .mno(mno)
                                .build();

            int grade = (int)(Math.random() * 5) + 1;

            Review review = Review.builder()
                    .movie(movie)
                    .member(member)
                    .grade(grade)
                    .text("리뷰입니다 " + i)
                    .build();

            reviewRepository.save(review);

        });

    }

    @Test
    public void testGetMovieReviews() {

        long targetMno = 3L;
        Movie movie = Movie.builder()
                        .mno(targetMno)
                        .build();

        List<Review> result = reviewRepository.findByMovie(movie);

        result.forEach(review -> {
            System.out.print(review.getReviewnum());
            System.out.print("\t" + review.getGrade());
            System.out.print("\t" + review.getText());
            System.out.print("\t" + review.getMember().getNickname());
            System.out.println("\t ----------------");
        });

    }

    @Commit
    @Transactional
    @Test
    public void testDeleteReviews() {

        /*
        *   FK를 가지고 참조하는쪽(리뷰)를 먼저 삭제하고
        *   PK를 가진쪽(멤버)를 삭제한다.
        * */
        long targetMid = 2L;

        Member member = Member.builder()
                            .mid(targetMid)
                            .build();

        reviewRepository.deleteByMember(member);
        memberRepository.deleteById(targetMid);


    }

}
