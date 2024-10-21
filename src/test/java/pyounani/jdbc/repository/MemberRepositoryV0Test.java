package pyounani.jdbc.repository;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import pyounani.jdbc.domain.Member;

import java.sql.SQLException;
import java.util.NoSuchElementException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Slf4j
class MemberRepositoryV0Test {

    MemberRepositoryV0 repository = new MemberRepositoryV0();

    @Test
    void crud() throws SQLException {
        // save
        Member member = new Member("memberV1", 10000);
        repository.save(member);

        // findById
        Member findMember = repository.findById(member.getMemberId());
        log.info("findMember = {}", findMember);

        assertThat(findMember).isEqualTo(member);
        assertThat(findMember).isNotSameAs(member); // 인스턴스는 다르다.

        // update: money 10000 -> 20000
        repository.update(member.getMemberId(), 20000);
        Member updatedMember = repository.findById(member.getMemberId());
        assertThat(updatedMember.getMoney()).isEqualTo(20000);

        // delete
        repository.delete(member.getMemberId());
        assertThrows(NoSuchElementException.class, () -> {
            repository.findById(member.getMemberId());
        });
    }
}