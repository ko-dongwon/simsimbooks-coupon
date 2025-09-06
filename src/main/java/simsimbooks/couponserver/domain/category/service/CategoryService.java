package simsimbooks.couponserver.domain.category.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import simsimbooks.couponserver.common.util.DtoMapper;
import simsimbooks.couponserver.domain.category.dto.CategoryCreateRequest;
import simsimbooks.couponserver.domain.category.dto.CategoryResponse;
import simsimbooks.couponserver.domain.category.dto.CategoryUpdateRequest;
import simsimbooks.couponserver.domain.category.entity.Category;
import simsimbooks.couponserver.domain.category.exception.CategoryNotFoundException;
import simsimbooks.couponserver.domain.category.repository.CategoryRepository;

import java.util.Objects;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {
    private final CategoryRepository categoryRepository;

    /**
     * CREATE
     */
    @Transactional
    public Long createCategory(CategoryCreateRequest requestDto) {
        Category parent = null;

        // 부모 카테고리를 조회 후 있으면 참조하도록, 없으면 null
        if (Objects.nonNull(requestDto.getParentId())) {
            parent = categoryRepository.findById(requestDto.getParentId()).orElseThrow(() -> new CategoryNotFoundException("부모 카테고리를 찾을 수 없습니다."));
        }

        Category save = categoryRepository.save(Category.of(requestDto.getName(), parent));

        return save.getId();
    }

    /**
     * READ
     */
    public CategoryResponse getCategory(Long categoryId) {

        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);

        return DtoMapper.toDto(category, CategoryResponse.class);
    }

    @Transactional
    public CategoryResponse updateCategory(Long categoryId, CategoryUpdateRequest requestDto) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        
        if (Objects.nonNull(requestDto.getParentId())) {
            Category parent = categoryRepository.findById(requestDto.getParentId()).orElseThrow(() -> new CategoryNotFoundException("부모 카테고리를 찾을 수 없습니다."));
            category.changeParent(parent);
        }

        category.changeName(requestDto.getName());

        return DtoMapper.toDto(category, CategoryResponse.class);
    }

    @Transactional
    public void deleteCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId).orElseThrow(CategoryNotFoundException::new);
        categoryRepository.delete(category);
    }



}
