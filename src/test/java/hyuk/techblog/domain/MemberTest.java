package hyuk.techblog.domain;

import static org.junit.jupiter.api.Assertions.*;

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

	/**
	 * member 엔티티의 연관관계편의 메서드 테스트
	 */
	@Test
	void testAddCategory() {
		//given
		Member member = Member.createMember("testId", "testPassword", "testNickName");
		Category category = Category.createCategory("backend");

		//when
		member.addCategory(category);

		//then
		assertAll(
			() -> assertEquals(category, member.getCategories().get(0)),
			() -> assertEquals(member, category.getMember())
		);
	}

}
