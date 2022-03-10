package hyuk.techblog.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import hyuk.techblog.domain.Member;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class MemberRepository {

	private final EntityManager em;

	public void save(Member member) {
		em.persist(member);
	}

	public List<Member> findByLoginId(String loginId) {
		return em.createQuery(
				"select m"
					+ " from Member m"
					+ " where m.loginId = :loginId", Member.class)
			.setParameter("loginId", loginId)
			.getResultList();
	}

	public List<Member> findByNickName(String nickName) {
		return em.createQuery(
				"select m"
					+ " from Member m"
					+ " where m.nickName = :nickName", Member.class)
			.setParameter("nickName", nickName)
			.getResultList();
	}

}
