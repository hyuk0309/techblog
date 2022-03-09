package hyuk.techblog.domain;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;

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

		String categoryName = "backend";

		//when
		Member findMember = em.find(Member.class, member.getId());
		Category category = Category.createCategory(findMember, categoryName);
		em.persist(category);

		em.flush();
		em.clear();

		//then
		assertNotNull(category.getId());
	}

	@Test
	void testCreateCategory() {
		//given
		Member member = Member.createMember("testId", "testPw", "testName");

		//when
		Category category = Category.createCategory(member, "backend");

		//then
		assertEquals(member, category.getMember());
		assertEquals(category, member.getCategories().get(0));
	}
}
