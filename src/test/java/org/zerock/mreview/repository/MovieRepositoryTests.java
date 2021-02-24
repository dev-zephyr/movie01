package org.zerock.mreview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;
import org.zerock.mreview.entity.Movie;
import org.zerock.mreview.entity.MovieImage;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

@SpringBootTest
public class MovieRepositoryTests {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private MovieImageRepository movieImageRepository;


    /*
    *   SpringBootTest에서 @Transactional을 사용하게 되면
    *   test가 끝난 뒤 자동적으로 rollback 하도록 설계되어있다.
    *   실습을 위한 더미데이터를 DB에 삽입하는 과정이므로 이럴때
    *   @Commit 어노테이션을 활용해 강제로 Commit한다.
    */
    @Commit
    @Transactional
    @Test
    public void insertMovies() {

        IntStream.rangeClosed(1, 100).forEach(i -> {

            Movie movie = Movie.builder()
                            .title("title" + i)
                            .build();

            movieRepository.save(movie);

            int count = (int)(Math.random() * 5) + 1;

            for(int j=0; j<count; j++) {
                MovieImage movieImage = MovieImage.builder()
                                            .uuid(UUID.randomUUID().toString())
                                            .movie(movie)
                                            .imgName("test" + j + ".jpg")
                                            .build();
                movieImageRepository.save(movieImage);
            }

        });

    }

    @Test
    public void testListPage() {

        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by(Sort.Direction.DESC, "mno"));

        Page<Object[]> result = movieRepository.getListPage(pageRequest);

        for(Object[] objects : result.getContent()) {
            System.out.println(Arrays.toString(objects));
        }

    }

    @Test
    public void testGetMovieWithImages() {

        Long target = 90L;

        List<Object[]> result = movieRepository.getMovieWithImages(target);

        for(Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }

    }

    @Test
    public void testGetMovieWithAll() {

        Long target = 90L;

        List<Object[]> result = movieRepository.getMovieWithAll(target);

        for(Object[] arr : result) {
            System.out.println(Arrays.toString(arr));
        }

    }

}
