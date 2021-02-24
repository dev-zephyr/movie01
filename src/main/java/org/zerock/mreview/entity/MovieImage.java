package org.zerock.mreview.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString(exclude = "movie")
public class MovieImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inum;

    private String uuid;

    private String imgName;

    /*
    *  년/월/일의 폴더구조(경로)
    */
    private String path;

    /*
    *  다수의 영화 이미지 : 하나의 영화
    *            N     :     1
    */
    @ManyToOne(fetch = FetchType.LAZY)
    private Movie movie;


}
