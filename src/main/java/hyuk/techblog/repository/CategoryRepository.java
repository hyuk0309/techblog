package hyuk.techblog.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import hyuk.techblog.domain.Category;
import hyuk.techblog.domain.Member;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

	private final EntityManager em;

	public void save(Category category) {
		em.persist(category);
	}

	public List<Category> findByMemberAndName(Member member, String name) {
		return em.createQuery(
				"select c "
					+ " from Category c"
					+ " where c.member = :member and c.name = :name", Category.class)
			.setParameter("member", member)
			.setParameter("name", name)
			.getResultList();
	}

	public Category findById(Long id) {
		return em.find(Category.class, id);
	}

	public List<Category> findByMember(Member member) {
		return em.createQuery(
				"select c"
					+ " from Category c"
					+ " where c.member = :member", Category.class)
			.setParameter("member", member)
			.getResultList();
	}
}
