package hyuk.techblog.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hyuk.techblog.domain.Category;
import hyuk.techblog.domain.Member;
import hyuk.techblog.exception.category.DuplicateCategoryException;
import hyuk.techblog.repository.CategoryRepository;
import hyuk.techblog.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

	private final CategoryRepository categoryRepository;
	private final MemberRepository memberRepository;

	public Long saveCategory(Long memberId, String name) {

		Member member = memberRepository.findById(memberId);

		validateDuplicateCategory(member, name);

		Category category = Category.createCategory(member, name);
		categoryRepository.save(category);

		return category.getId();
	}

	private void validateDuplicateCategory(Member member, String name) {
		List<Category> categories = categoryRepository.findByMemberAndName(member, name);
		if (categories.size() != 0) {
			throw new DuplicateCategoryException();
		}
	}
}
