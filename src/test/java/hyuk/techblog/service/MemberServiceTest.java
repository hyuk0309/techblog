package hyuk.techblog.service;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.Transactional;

import hyuk.techblog.domain.Member;
import hyuk.techblog.dto.member.MemberDto;
import hyuk.techblog.exception.member.DuplicateLoginIdException;
import hyuk.techblog.exception.member.DuplicateNickNameException;
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
	 * 아이디 중복
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
	 * 닉네임 중복
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

}
