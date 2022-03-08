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
@Table(name = "category")
@Getter
public class Category {

	@Id @GeneratedValue
	@Column(name = "category_id")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id", nullable = false)
	private Member member;

	@Column(nullable = false)
	private String name;

	protected Category (){
	}

	public static Category createCategory(String name) {
		Category category = new Category();
		category.name = name;
		return category;
	}

	protected void setMember(Member member) {
		this.member = member;
	}
}
