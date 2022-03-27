package hyuk.techblog.repository;

import static org.assertj.core.api.Assertions.*;

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
		assertThat(categories.size()).isEqualTo(1);
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
		assertThat(findCategory.getId()).isEqualTo(category.getId());
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
		assertThat(findCategory).isEqualTo(em.find(Category.class, findCategory.getId()));
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
		assertThat(categories).contains(category1, category2);
	}

	@DisplayName("카테고리 삭제")
	@Test
	void remove() {
		//given
		Member member = Member.createMember("testId", "testPw", "testName");
		em.persist(member);

		Category category = Category.createCategory(member, "back-end");
		em.persist(category);

		//when
		categoryRepository.remove(category);

		em.flush();
		em.clear();

		//then
		assertThat(em.find(Category.class, category.getId())).isNull();
	}

}
