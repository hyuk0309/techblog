package hyuk.techblog.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Getter;

@Entity
@Table(name = "note")
@Getter
public class Note {

	@Id @GeneratedValue
	@Column(name = "note_id")
	private Long id;

	@Lob
	private String content;
}
