package hyuk.techblog.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name = "member")
@Getter
public class Member {

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(nullable = false, unique = true)
	private String loginId;

	@Column(nullable = false)
	private String password;

	@Column(nullable = false, unique = true)
	private String nickName;

	@OneToMany(mappedBy = "member")
	private List<Category> categories = new ArrayList<>();

	protected Member() {
	}

	public static Member createMember(String loginId, String password, String nickName) {
		Member member = new Member();
		member.loginId = loginId;
		member.password = password;
		member.nickName = nickName;
		return member;
	}

	/* 비지니스 로직 */
	public void changeNickName(String nickName) {
		this.nickName = nickName;
	}
}
