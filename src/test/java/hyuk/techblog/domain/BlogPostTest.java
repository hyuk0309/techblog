package hyuk.techblog.domain;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class BlogPostTest {

	@Autowired EntityManager em;

	@Test
	void testPersist() {
		//given
		Member member = Member.createMember("testId", "testPw", "testName");
		em.persist(member);

		BlogPost blogPost = BlogPost.createBlogPost(member, "www.test.blog.com");

		//when
		em.persist(blogPost);

		//then
		assertNotNull(blogPost.getId());
	}

	@Test
	void testAddCategory() {
		//given
		Category category = Category.createCategory("backend");
		BlogPostCategory blogPostCategory = BlogPostCategory.createBlogPostCategory(category);
		BlogPost blogPost = BlogPost.createBlogPost(null, "www.naver.tech");

		//when
		blogPost.addCategory(blogPostCategory);

		//then
		Assertions.assertAll(
			() -> assertEquals(blogPostCategory, blogPost.getBlogPostCategories().get(0)),
			() -> assertEquals(blogPost, blogPostCategory.getBlogPost())
		);
	}
}
