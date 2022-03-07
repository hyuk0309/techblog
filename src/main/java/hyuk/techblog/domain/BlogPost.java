package hyuk.techblog.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name = "blog_post")
@Getter
public class BlogPost {

	@Id @GeneratedValue
	@Column(name = "blog_post_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "note_id")
	private Note note;

	@Column(nullable = false)
	private String url;

	private int readCount;

	@OneToMany(mappedBy = "blogPost", cascade = CascadeType.ALL)
	private List<BlogPostCategory> blogPostCategories = new ArrayList<>();

	/* 연관관계 편의 메서드 */
	public void addCategory(BlogPostCategory blogPostCategory) {
		blogPostCategories.add(blogPostCategory);
		blogPostCategory.setBlogPost(this);
	}

}
