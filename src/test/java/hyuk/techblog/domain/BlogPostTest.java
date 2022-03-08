package hyuk.techblog.domain;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

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
		Member member = Member.createMember("testId", "testPw", "testName");
		Category category = Category.createCategory(member, "backend");

		BlogPost blogPost = BlogPost.createBlogPost(null, "www.naver.tech");

		//when
		BlogPostCategory blogPostCategory = BlogPostCategory.createBlogPostCategory(category);
		blogPost.addBlogPostCategory(blogPostCategory);

		//then
		Assertions.assertAll(
			() -> assertEquals(blogPostCategory, blogPost.getBlogPostCategories().get(0)),
			() -> assertEquals(blogPost, blogPostCategory.getBlogPost())
		);
	}

	@Test
	void testCascade() {
		//given
		//Test에 필요한 member, category 영속화
		Member member = Member.createMember("testId", "testPw", "testName");
		em.persist(member);

		Member findMember = em.find(Member.class, member.getId());
		Category category = Category.createCategory(findMember, "backend");
		em.persist(category);

		BlogPostCategory blogPostCategory = BlogPostCategory.createBlogPostCategory(category);
		BlogPost blogPost = BlogPost.createBlogPost(member, "www.tech.blog", blogPostCategory);

		//when
		em.persist(blogPost);

		//then
		//em.flush();
		assertAll(
			() -> assertNotNull(blogPost.getId()),
			() -> assertNotNull(blogPostCategory.getId())
		);
	}
}
