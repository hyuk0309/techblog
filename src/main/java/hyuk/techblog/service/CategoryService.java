package hyuk.techblog.service;

import static java.util.stream.Collectors.*;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hyuk.techblog.domain.Category;
import hyuk.techblog.domain.Member;
import hyuk.techblog.dto.category.CategoryDto;
import hyuk.techblog.exception.category.DuplicateCategoryException;
import hyuk.techblog.repository.CategoryRepository;
import hyuk.techblog.repository.MemberRepositoryImp;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

	private final CategoryRepository categoryRepository;
	private final MemberRepositoryImp memberRepository;

	/**
	 *	새로운 카테고리 저장
	 */
	@Transactional
	public Long saveCategory(Long memberId, String name) {

		Member member = memberRepository.findById(memberId);

		validateDuplicateCategory(member, name);

		Category category = Category.createCategory(member, name);
		categoryRepository.save(category);

		return category.getId();
	}

	/**
	 *	카테고리 수정
	 */
	@Transactional
	public Long updateCategory(Long categoryId, String name) {
		Category category = categoryRepository.findById(categoryId);

		if (category.getName().equals(name)) {
			return category.getId();
		}

		validateDuplicateCategory(category.getMember(), name);

		category.changeName(name);
		return category.getId();
	}

	private void validateDuplicateCategory(Member member, String name) {
		List<Category> categories = categoryRepository.findByMemberAndName(member, name);
		if (categories.size() != 0) {
			throw new DuplicateCategoryException();
		}
	}

	/**
	 *	특정 맴버 카테고리 전체 조회
	 */
	public List<CategoryDto> findMemberCategories(Long memberId) {
		return categoryRepository.findByMember(memberRepository.findById(memberId)).stream()
			.map(c -> new CategoryDto(c))
			.collect(toList());
	}

	/**
	 *	카테고리 삭제
	 */
	@Transactional
	public void removeCategory(Long id) {
		categoryRepository.remove(categoryRepository.findById(id));
	}
}
