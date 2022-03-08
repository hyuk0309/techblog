package hyuk.techblog.domain;

import static org.junit.jupiter.api.Assertions.*;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
class NoteTest {

	@Autowired EntityManager em;

	@Test
	void testPersist() {
		//given
		Note note = Note.createNote("this is test contents");

		//when
		em.persist(note);

		//then
		//SQL 확인용
		//em.flush();

		assertNotNull(note.getId());
	}
}