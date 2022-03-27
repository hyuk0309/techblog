package hyuk.techblog.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import hyuk.techblog.domain.Category;
import hyuk.techblog.domain.Member;

@DataJpaTest
public class CategoryRepositoryTest {

	@Autowired
	EntityManager em;
	CategoryRepository categoryRepository;

	@BeforeEach
	public void init() {
		categoryRepository = new CategoryRepository(em);
	}

	@DisplayName("member_id, name 이용해 category 테이블 조회")
	@Test
	void selectCategoryUsingMember_IdAndName() {
		//given
		Member member = Member.createMember("testId", "testPw", "testName");
		em.persist(member);

		Category category = Category.createCategory(member, "back-end");
		categoryRepository.save(category);

		//when
		List<Category> categories = categoryRepository.findByMemberAndName(member, category.getName());

		//then
		Assertions.assertThat(categories.size()).isEqualTo(1);
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

	@DisplayName("category id로 조회")
	@Test
	void findById() {
		//given
		Member member = Member.createMember("testId", "testPw", "testName");
		em.persist(member);

		Category category = Category.createCategory(member, "back-end");
		em.persist(category);

		em.flush();
		em.clear();

		//when
		Category findCategory = categoryRepository.findById(category.getId());

		//then
		Assertions.assertThat(findCategory).isEqualTo(em.find(Category.class, findCategory.getId()));
	}

	@DisplayName("특정 회원의 category 전체 조회")
	@Test
	void findByMember() {
		//given
		Member member = Member.createMember("testId", "testPw", "testName");
		em.persist(member);

		Category category1 = Category.createCategory(member, "back-end");
		em.persist(category1);

		Category category2 = Category.createCategory(member, "front-end");
		em.persist(category2);

		//when
		List<Category> categories = categoryRepository.findByMember(member);

		//then
		Assertions.assertThat(categories).contains(category1, category2);
	}

}
