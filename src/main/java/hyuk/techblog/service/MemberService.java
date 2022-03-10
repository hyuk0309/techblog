package hyuk.techblog.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hyuk.techblog.domain.Member;
import hyuk.techblog.dto.member.MemberDto;
import hyuk.techblog.exception.member.DuplicateLoginIdException;
import hyuk.techblog.exception.member.DuplicateNickNameException;
import hyuk.techblog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;

	/**
	 * 회원 가입
	 */
	@Transactional
	public Long join(MemberDto memberDto) {

		validateDuplicateLoginId(memberDto.getLoginId());
		validateDuplicateNickName(memberDto.getNickName());

		Member member = Member.createMember(memberDto.getLoginId(), memberDto.getPassword(), memberDto.getNickName());
		memberRepository.save(member);
		return member.getId();
	}

	private void validateDuplicateLoginId(String loginId) {
		List<Member> members = memberRepository.findByLoginId(loginId);
		if (members.size() > 0) {
			throw new DuplicateLoginIdException();
		}
	}

	private void validateDuplicateNickName(String nickName) {
		List<Member> members = memberRepository.findByNickName(nickName);
		if (members.size() > 0) {
			throw new DuplicateNickNameException();
		}
	}

}
