package hyuk.techblog.dto.member;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MemberDto {

	private String loginId;
	private String password;
	private String nickName;

	public MemberDto(String loginId, String password, String nickName) {
		this.loginId = loginId;
		this.password = password;
		this.nickName = nickName;
	}
}
