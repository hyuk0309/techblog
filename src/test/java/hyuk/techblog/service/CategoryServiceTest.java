package hyuk.techblog.service;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import hyuk.techblog.domain.Category;
import hyuk.techblog.domain.Member;
import hyuk.techblog.exception.category.DuplicateCategoryException;
import hyuk.techblog.repository.CategoryRepository;
import hyuk.techblog.repository.MemberRepository;

@DataJpaTest
public class CategoryServiceTest {

	@Autowired
	EntityManager em;
	CategoryService categoryService;

	@BeforeEach
	public void init() {
		categoryService = new CategoryService(new CategoryRepository(em), new MemberRepository(em));
	}

	@DisplayName("카테고리 생성 - 정상 동작")
	@Test
	void createCategory() {
		//given
		Member member = Member.createMember("testId", "testPw", "testName");
		em.persist(member);

		Long memberId = member.getId();
		String name = "back-end";

		//when
		Long saveId = categoryService.saveCategory(memberId, name);

		//then
		Category findCategory = em.find(Category.class, saveId);
		Assertions.assertThat(findCategory.getId()).isEqualTo(saveId);
	}

	@DisplayName("카테고리 생성 - 중복 카테고리")
	@Test
	void duplicateCategory() {
		//given
		Member member = Member.createMember("testId", "testPw", "testName");
		em.persist(member);

		Category category = Category.createCategory(member, "back-end");
		em.persist(category);

		Long memberId = member.getId();
		String name = "back-end";

		//when
		//then
		Assertions.assertThatThrownBy(() -> categoryService.saveCategory(memberId, name))
			.isInstanceOf(DuplicateCategoryException.class);
	}

	@DisplayName("카테고리 수정 - 정상")
	@Test
	void updateCategory() {
		//given
		Member member = Member.createMember("testId", "testPw", "testName");
		em.persist(member);

		Category category = Category.createCategory(member, "back-end");
		em.persist(category);

		Long categoryId = category.getId();
		String name = "front-end";

		//when
		Long updateId = categoryService.updateCategory(categoryId, name);

		//then
		Category updateCategory = em.find(Category.class, updateId);
		Assertions.assertThat(updateCategory.getName()).isEqualTo(name);
	}

	@DisplayName("카테고리 수정 - 이름 중복")
	@Test
	void updateDuplicate() {
		//given
		Member member = Member.createMember("testId", "testPw", "testName");
		em.persist(member);

		Category category1 = Category.createCategory(member, "back-end");
		em.persist(category1);

		Category category2 = Category.createCategory(member, "front-end");
		em.persist(category2);

		//"back-end" -> "front-end" 변경 => 예외 발생
		Long categoryId = category1.getId();
		String name = "front-end";

		//when
		//then
		Assertions.assertThatThrownBy(() -> categoryService.updateCategory(categoryId, name))
			.isInstanceOf(DuplicateCategoryException.class);
	}

}
