package hyuk.techblog.domain;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class BlogPostCategoryTest {

	@Autowired EntityManager em;

	@Test
	void testPersist() {
		//given
		//member 영속화
		Member member = Member.createMember("testId", "testPw", "testName");
		em.persist(member);

		//category 영속화 & member에 카테고리 추가
		Member findMember = em.find(Member.class, member.getId());
		Category category = Category.createCategory(findMember, "backend");
		em.persist(category);

		//blogPost 영속화
		BlogPost blogPost = BlogPost.createBlogPost(findMember, "www.test.blog");
		em.persist(blogPost);

		em.flush();
		em.clear();

		//when
		BlogPost findBlogPost = em.find(BlogPost.class, blogPost.getId());

		BlogPostCategory blogPostCategory = BlogPostCategory.createBlogPostCategory(em.find(Category.class, category.getId()));
		findBlogPost.addBlogPostCategory(blogPostCategory);
		em.persist(blogPostCategory);

		//then
		Assertions.assertNotNull(blogPostCategory.getId());
	}
}