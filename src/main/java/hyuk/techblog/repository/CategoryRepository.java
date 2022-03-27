package hyuk.techblog.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import hyuk.techblog.domain.Category;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CategoryRepository {

	private final EntityManager em;

	public void save(Category category) {
		em.persist(category);
	}


}
