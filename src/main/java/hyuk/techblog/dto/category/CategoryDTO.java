package hyuk.techblog.dto.category;

import hyuk.techblog.domain.Category;

public class CategoryDto {

	private String categoryName;

	public CategoryDto(Category category) {
		this.categoryName = category.getName();
	}
}
