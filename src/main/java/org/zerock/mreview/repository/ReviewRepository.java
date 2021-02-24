package org.zerock.mreview.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.zerock.mreview.entity.Member;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.Review;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    /*
    *   해당 설정을 하지 않고 쿼리를 실행하면 no session, 즉 Member 엔티티를
    *   불러올 수 없는 에러가 발생한다.
    *   @EntityGraph를 이용하여 Movie 객체를 가져올 때 Member 객체도 함께 
    *   로딩한다. (LEFT OUTER JOIN으로 처리)
    *   EntityGraphType.FETCH = Member를 Eager로 처리, 나머지는 Lazy 처리
    *   EntityGraphType.LOAD = Member를 Eager로 처리, 나머지는 기본방식으로 처리
    * */
    @EntityGraph(attributePaths = {"member"}, type = EntityGraph.EntityGraphType.FETCH)
    List<Review> findByMovie(Movie movie);


    /*
    *   update, delete를 실행하기 위해 @Modifying 어노테이션 선언.
    *
    *   쿼리메서드로 delete를 실행하면 참조한 테이블의 row 갯수 만큼 delete 쿼리문이
    *   실행된다(비효율)
    *   @Query를 이용해 where절을 지정하면 한번만 쿼리문이 날라간다.
    */
    @Modifying
    @Query("DELETE FROM Review r " +
            "WHERE r.member = :member")
    void deleteByMember(Member member);

}
