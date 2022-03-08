package hyuk.techblog.domain;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class MemberTest {

	@Autowired EntityManager em;

	@Test
	void saveMember() {
		//given
		Member member = Member.createMember("testId", "testPassword", "testNickName");

		//when
		em.persist(member);
		Member findMember = em.find(Member.class, member.getId());

		//then
		assertEquals("testId", findMember.getLoginId());
		assertEquals("testPassword", findMember.getPassword());
		assertEquals("testNickName", findMember.getNickName());
	}

}
