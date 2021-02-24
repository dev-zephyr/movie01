package org.zerock.mreview.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.zerock.mreview.entity.Member;

import java.util.stream.IntStream;

@SpringBootTest
public class MemberRepositoryTests {

    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void insertMembers() {

        IntStream.rangeClosed(1, 100).forEach(i -> {

            Member member = Member.builder()
                                .email("r" + 1 + "@zephyr.com")
                                .pw("1111")
                                .nickname("r" + 1)
                                .build();

            memberRepository.save(member);

        });

    }
}
