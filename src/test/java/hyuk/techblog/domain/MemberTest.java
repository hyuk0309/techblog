package hyuk.techblog.domain;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

@DataJpaTest
class MemberTest {

	@Autowired EntityManager em;

	@Test
	@Rollback(value = false)
	void saveMember() {
		//given
		Member member = Member.createMember("testId", "testPassword", "testNickName");

		//when
		em.persist(member);
		Member findMember = em.find(Member.class, member.getId());

		//then
		Assertions.assertEquals("testId", findMember.getLoginId());
		Assertions.assertEquals("testPassword", findMember.getPassword());
		Assertions.assertEquals("testNickName", findMember.getNickName());
	}

}
