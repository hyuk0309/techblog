package hyuk.techblog.domain;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class CategoryTest {

	@Autowired EntityManager em;

	@Test
	void testPersist() {
		//given
		Member member = Member.createMember("testId", "testPw", "testName");
		em.persist(member);

		Category category = Category.createCategory("backend");

		//when
		Member findMember = em.find(Member.class, member.getId());
		findMember.addCategory(category);
		em.persist(category);

		em.flush();
		em.clear();

		//then
		Assertions.assertNotNull(category.getId());
	}

}
