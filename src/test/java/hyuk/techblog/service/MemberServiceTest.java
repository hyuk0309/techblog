package hyuk.techblog.service;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import hyuk.techblog.domain.Member;
import hyuk.techblog.dto.member.MemberDto;
import hyuk.techblog.exception.member.DuplicateLoginIdException;
import hyuk.techblog.exception.member.DuplicateNickNameException;
import hyuk.techblog.exception.member.InvalidPasswordException;
import hyuk.techblog.exception.member.NonExistLoginIdException;
import hyuk.techblog.repository.MemberRepository;

@DataJpaTest
class MemberServiceTest {

	@Autowired EntityManager em;
	MemberService memberService;
	MemberRepository memberRepository;

	@BeforeEach
	public void init() {
		memberRepository = new MemberRepository(em);
		memberService = new MemberService(memberRepository);
	}

	/**
	 * 정상 회원가입
	 */
	@Test
	void testJoin() {
		//given
		MemberDto memberDto = new MemberDto("testId", "testPw", "testNickName");

		//when
		Long joinId = memberService.join(memberDto);

		//then
		Member findMember = memberRepository.findById(joinId);

		assertAll(
			() -> assertEquals(memberDto.getLoginId(), findMember.getLoginId()),
			() -> assertEquals(memberDto.getPassword(), findMember.getPassword()),
			() ->assertEquals(memberDto.getNickName(), findMember.getNickName())
		);
	}

	/**
	 * 회원가입 과정에서 아이디 중복
	 */
	@Test
	void testDuplicateLoginId() {
		//given
		MemberDto memberDto1 = new MemberDto("testId1", "testPw1", "testNickName1");
		memberService.join(memberDto1);

		MemberDto memberDto2 = new MemberDto(memberDto1.getLoginId(), "testPw2", "testNickName2");

		//when
		assertThrows(
			DuplicateLoginIdException.class,
			() -> memberService.join(memberDto2)
		);

		//then
	}

	/**
	 * 회원가입 과정에서 닉네임 중복
	 */
	@Test
	void testDuplicateNickName() {
		//given
		MemberDto memberDto1 = new MemberDto("testId1", "testPw1", "testNickName1");
		memberService.join(memberDto1);

		MemberDto memberDto2 = new MemberDto("testId2", "testPw2", memberDto1.getNickName());

		//when
		assertThrows(
			DuplicateNickNameException.class,
			() -> memberService.join(memberDto2)
		);

		//then
	}

	/**
	 * 정상 로그인
	 */
	@Test
	void testSuccessLogin() {
		//given
		MemberDto memberDto1 = new MemberDto("testId1", "testPw1", "testNickName1");
		memberService.join(memberDto1);

		String loginId = "testId1";
		String password = "testPw1";

		//when
		Long loginMemberId = memberService.login(loginId, password);

		//then
		Member findMember = memberRepository.findById(loginMemberId);
		assertAll(
			() -> assertEquals(findMember.getLoginId(), memberDto1.getLoginId()),
			() -> assertEquals(findMember.getPassword(), memberDto1.getPassword()),
			() -> assertEquals(findMember.getNickName(), memberDto1.getNickName())
		);
	}

	/**
	 * 로그인에서 존재하지 않는 아이디
	 */
	@Test
	void testNoExistLoginId() {
		//given
		MemberDto memberDto1 = new MemberDto("testId1", "testPw1", "testNickName1");
		memberService.join(memberDto1);

		String loginId = "testId2";
		String password = "testPw1";

		//when
		assertThrows(NonExistLoginIdException.class,
			() -> memberService.login(loginId, password));
		//then
	}

	/**
	 * 로그인에서 비밀번호 틀림
	 */
	@Test
	void testWrongPassword() {
		//given
		MemberDto memberDto1 = new MemberDto("testId1", "testPw1", "testNickName1");
		memberService.join(memberDto1);

		String loginId = "testId1";
		String password = "testPw2";

		//when
		assertThrows(InvalidPasswordException.class,
			() -> memberService.login(loginId, password));
		//then
	}

	/**
	 * 닉네임 변경
	 */
	@Test
	void testUpdateNickName() {
		//given
		MemberDto memberDto1 = new MemberDto("testId1", "testPw1", "testNickName1");
		Long saveId = memberService.join(memberDto1);

		String nickName = "hyuk";

		//when
		memberService.updateNickName(saveId, nickName);

		//then
		assertEquals("hyuk", memberRepository.findById(saveId).getNickName());
	}

	/**
	 * 중복된 닉네임으로 변경하는 경우
	 */
	@Test
	void testDuplicateNickNameUpdate() {
		//given
		MemberDto memberDto1 = new MemberDto("testId1", "testPw1", "testNickName1");
		Long saveId = memberService.join(memberDto1);

		MemberDto memberDto2 = new MemberDto("testId2", "testPw2", "testNickName2");
		memberService.join(memberDto2);

		String nickName = memberDto2.getNickName();

		//when
		assertThrows(DuplicateNickNameException.class,
			() -> memberService.updateNickName(saveId, nickName));
		//then
	}
}
