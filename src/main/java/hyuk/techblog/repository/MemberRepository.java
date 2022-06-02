package hyuk.techblog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import hyuk.techblog.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByLoginId(String loginId);

	Optional<Member> findByNickName(String nickName);

	Optional<Member> findByLoginIdAndPassword(String loginId, String password);

	Optional<Member> findById(Long id);
}

