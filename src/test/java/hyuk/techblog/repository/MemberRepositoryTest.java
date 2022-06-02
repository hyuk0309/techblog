package hyuk.techblog.repository;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import hyuk.techblog.domain.Member;

@DataJpaTest
class MemberRepositoryTest {

	@Autowired
	MemberRepository memberRepository;

	@Autowired
	EntityManager em;

	Member member;

	@BeforeEach
	void setUp() {
		member = Member.createMember(
			"loginId",
			"password",
			"nickName");

		memberRepository.save(member);

		em.flush();
		em.clear();
	}

	@Test
	@DisplayName("findByLoginId 쿼리 확인")
	void testFindByLoginId() {
		memberRepository.findByLoginId(member.getLoginId());
	}
}
