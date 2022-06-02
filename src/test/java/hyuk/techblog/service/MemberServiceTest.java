package hyuk.techblog.service;

import static hyuk.techblog.exception.Message.*;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import hyuk.techblog.domain.Member;
import hyuk.techblog.dto.member.MemberDto;
import hyuk.techblog.exception.member.DuplicateLoginIdException;
import hyuk.techblog.exception.member.DuplicateNickNameException;
import hyuk.techblog.exception.member.InvalidLoginIdOrPassword;
import hyuk.techblog.repository.MemberRepository;

@DataJpaTest
class MemberServiceTest {

	@Autowired
	MemberRepository memberRepository;

	MemberService memberService;

	@BeforeEach
	public void init() {
		memberService = new MemberService(memberRepository);
	}

	@Test
	@DisplayName("회원 가입 성공")
	void testJoinSuccess() {
		//given
		MemberDto memberDto = new MemberDto("testId", "testPw", "testNickName");

		//when
		Long joinId = memberService.join(memberDto);

		//then
		Member findMember = memberRepository.findById(joinId).get();

		assertAll(
			() -> assertThat(findMember.getId()).isNotNull(),
			() -> assertThat(findMember.getLoginId()).isEqualTo(memberDto.getLoginId()),
			() -> assertThat(findMember.getPassword()).isEqualTo(memberDto.getPassword()),
			() -> assertThat(findMember.getNickName()).isEqualTo(memberDto.getNickName())
		);
	}

	@Test
	@DisplayName("회원 가입 실패 - 로그인 아이디 중복")
	void testJoinFailBecauseDuplicateLoginId() {
		//given
		String loginId = "loginId";

		MemberDto memberDto1 = new MemberDto(loginId, "testPw1", "testNickName1");
		memberService.join(memberDto1);

		MemberDto memberDto2 = new MemberDto(loginId, "testPw2", "testNickName2");

		//when then
		assertThatThrownBy(() -> memberService.join(memberDto2))
			.isInstanceOf(DuplicateLoginIdException.class)
			.hasMessageContaining(DUPLICATE_LOGIN_ID_EXP_MSG.getText());
	}

	@Test
	@DisplayName("회원 가입 실패 - 닉네임 중복")
	void testJoinFailBecauseDuplicateNickName() {
		//given
		String nickName = "nickName";
		MemberDto memberDto1 = new MemberDto("testId1", "testPw1", nickName);
		memberService.join(memberDto1);

		MemberDto memberDto2 = new MemberDto("testId2", "testPw2", nickName);

		//when then
		assertThatThrownBy(() -> memberService.join(memberDto2))
			.isInstanceOf(DuplicateNickNameException.class)
			.hasMessageContaining(DUPLICATE_NICK_NAME_EXP_MSG.getText());
	}

	@Test
	@DisplayName("로그인 성공")
	void testLoginSuccess() {
		//given
		MemberDto memberDto = new MemberDto("testId1", "testPw1", "testNickName1");
		memberService.join(memberDto);

		String loginId = memberDto.getLoginId();
		String password = memberDto.getPassword();

		//when
		Long loginMemberId = memberService.login(loginId, password);

		//then
		assertThat(loginMemberId).isNotNull();
	}

	@Test
	@DisplayName("로그인 실패 - 유요하지 않은 로그인 아이디")
	void testLoginFailBecauseInvalidLoginId() {
		//given
		MemberDto memberDto = new MemberDto("testId1", "testPw1", "testNickName1");
		memberService.join(memberDto);

		String invalidLoginId = "invalidLoginId";
		String password = memberDto.getPassword();

		//when then
		assertThatThrownBy(() -> memberService.login(invalidLoginId, password))
			.isInstanceOf(InvalidLoginIdOrPassword.class)
			.hasMessageContaining(INVALID_LOGIN_ID_OR_PASSWORD_EXP_MSG.getText());
	}

	@Test
	@DisplayName("로그인 실패 - 유효하지 않은 비밀번호")
	void testLoginFailBecauseInvalidPassword() {
		//given
		MemberDto memberDto = new MemberDto("testId1", "testPw1", "testNickName1");
		memberService.join(memberDto);

		String loginId = memberDto.getLoginId();
		String invalidPassword = "invalidPassword";

		//when then
		assertThatThrownBy(() -> memberService.login(loginId, invalidPassword))
			.isInstanceOf(InvalidLoginIdOrPassword.class)
			.hasMessageContaining(INVALID_LOGIN_ID_OR_PASSWORD_EXP_MSG.getText());
	}

	@Test
	@DisplayName("닉네임 변경 성공")
	void testUpdateNickNameSuccess() {
		//given
		MemberDto memberDto = new MemberDto("testId1", "testPw1", "testNickName1");
		Long saveId = memberService.join(memberDto);

		String updateNickName = "updateNickName";

		//when
		Long updatedMemberId = memberService.updateNickName(saveId, updateNickName);

		//then
		Member member = memberRepository.findById(updatedMemberId).get();
		assertThat(member.getNickName()).isEqualTo(updateNickName);
	}

	@Test
	@DisplayName("닉네임 변경 실패 - 중복된 닉네임")
	void testUpdateNickNameFailBecauseDuplicateNickName() {
		//given
		MemberDto memberDto1 = new MemberDto("testId1", "testPw1", "testNickName1");
		Long saveId = memberService.join(memberDto1);

		MemberDto memberDto2 = new MemberDto("testId2", "testPw2", "testNickName2");
		memberService.join(memberDto2);

		String updateNickName = memberDto2.getNickName();

		//when then
		assertThatThrownBy(() -> memberService.updateNickName(saveId, updateNickName))
			.isInstanceOf(DuplicateNickNameException.class)
			.hasMessageContaining(DUPLICATE_NICK_NAME_EXP_MSG.getText());
	}

	@Test
	@DisplayName("회원 탈퇴")
	void testRemoveMember() {
		//given
		MemberDto memberDto = new MemberDto("testId1", "testPw1", "testNickName1");
		Long saveId = memberService.join(memberDto);

		//when
		memberService.removeMember(saveId);

		//then
		assertThat(memberRepository.findById(saveId)).isEmpty();
	}
}
