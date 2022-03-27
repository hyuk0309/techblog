package hyuk.techblog.dto.category;

import hyuk.techblog.domain.Category;

public class CategoryDTO {

	private String categoryName;

	public CategoryDTO(Category category) {
		this.categoryName = category.getName();
	}
}
