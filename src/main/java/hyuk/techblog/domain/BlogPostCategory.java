package hyuk.techblog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name = "blog_post_category")
@Getter
public class BlogPostCategory {

	@Id @GeneratedValue
	@Column(name = "blog_post_category_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "blog_post_id", nullable = false)
	private BlogPost blogPost;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	protected void setBlogPost(BlogPost blogPost) {
		this.blogPost = blogPost;
	}
}
