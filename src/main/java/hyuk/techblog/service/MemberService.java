package hyuk.techblog.service;

import static hyuk.techblog.exception.Message.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hyuk.techblog.domain.Member;
import hyuk.techblog.dto.member.MemberDto;
import hyuk.techblog.exception.member.DuplicateLoginIdException;
import hyuk.techblog.exception.member.DuplicateNickNameException;
import hyuk.techblog.exception.member.InvalidIdException;
import hyuk.techblog.exception.member.InvalidLoginIdOrPassword;
import hyuk.techblog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

	private final MemberRepository memberRepository;

	/**
	 * 회원 가입 API
	 * @param memberDto
	 * @return id
	 */
	@Transactional
	public Long join(MemberDto memberDto) {
		validateDuplicateLoginId(memberDto.getLoginId());
		validateDuplicateNickName(memberDto.getNickName());

		Member member = Member.createMember(memberDto.getLoginId(), memberDto.getPassword(), memberDto.getNickName());
		Member savedMember = memberRepository.save(member);
		return savedMember.getId();
	}

	private void validateDuplicateLoginId(String loginId) {
		memberRepository.findByLoginId(loginId)
			.ifPresent(member -> {
				throw new DuplicateLoginIdException(DUPLICATE_LOGIN_ID_EXP_MSG);
			});
	}

	/**
	 * 로그인 API
	 * @param loginId
	 * @param password
	 * @return id
	 */
	public Long login(String loginId, String password) {
		Member member = memberRepository.findByLoginIdAndPassword(loginId, password)
			.orElseThrow(() -> new InvalidLoginIdOrPassword(INVALID_LOGIN_ID_OR_PASSWORD_EXP_MSG));
		return member.getId();
	}

	/**
	 * 닉네임 변경 API
	 * @param id
	 * @param updateNickName
	 * @return id
	 */
	@Transactional
	public Long updateNickName(Long id, String updateNickName) {
		validateDuplicateNickName(updateNickName);

		Member member = memberRepository.findById(id)
			.orElseThrow(() -> new InvalidIdException(INVALID_ID_EXP_MSG));

		member.changeNickName(updateNickName);
		return member.getId();
	}

	/**
	 * 회원 탈퇴 API
	 * @param id
	 */
	@Transactional
	public void removeMember(Long id) {
		memberRepository.deleteById(id);
	}

	private void validateDuplicateNickName(String nickName) {
		memberRepository.findByNickName(nickName)
			.ifPresent(member -> {
				throw new DuplicateNickNameException(DUPLICATE_NICK_NAME_EXP_MSG);
			});
	}
}
