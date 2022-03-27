package hyuk.techblog.repository;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import hyuk.techblog.domain.Category;
import hyuk.techblog.domain.Member;

@DataJpaTest
public class CategoryRepositoryTest {

	@Autowired EntityManager em;
	CategoryRepository categoryRepository;

	@BeforeEach
	public void init() {
		categoryRepository = new CategoryRepository(em);
	}

	@DisplayName("member_id, name 이용해 category 테이블 조회")
	@Test
	void selectCategoryUsingMember_IdAndName() {
		//given
		//when
		//then
	}

	@DisplayName("category 저장")
	@Test
	void saveCategory() {
		//given
		Member member = Member.createMember("testId", "testPw", "testName");
		em.persist(member);

		Category category = Category.createCategory(member, "back-end");

		//when
		categoryRepository.save(category);

		//then
		em.flush(); //insert query 확인용

		Category findCategory = em.find(Category.class, category.getId());
		Assertions.assertThat(findCategory.getId()).isEqualTo(category.getId());
	}

}
