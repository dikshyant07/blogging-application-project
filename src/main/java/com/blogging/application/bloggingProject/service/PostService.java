package com.blogging.application.bloggingProject.service;

import com.blogging.application.bloggingProject.dtos.ApiResponse;
import com.blogging.application.bloggingProject.dtos.PostCreationDto;
import com.blogging.application.bloggingProject.dtos.PostCreationResponseDto;
import com.blogging.application.bloggingProject.exceptions.PostDoesNotExistsException;
import com.blogging.application.bloggingProject.exceptions.UserDoesNotExistsException;
import com.blogging.application.bloggingProject.mappers.PostMapper;
import com.blogging.application.bloggingProject.models.Category;
import com.blogging.application.bloggingProject.models.Post;
import com.blogging.application.bloggingProject.models.Tag;
import com.blogging.application.bloggingProject.models.User;
import com.blogging.application.bloggingProject.repository.CategoryRepository;
import com.blogging.application.bloggingProject.repository.PostRepository;
import com.blogging.application.bloggingProject.repository.TagRepository;
import com.blogging.application.bloggingProject.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Function;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final CategoryRepository categoryRepository;
    private final PostMapper postMapper;
    private final UserRepository userRepository;
    private final TagRepository tagRepository;
    private Function<String, Tag> stringToTag = s -> Tag.builder().name(s).build();

    @Autowired
    public PostService(PostRepository postRepository, CategoryRepository categoryRepository, PostMapper postMapper, UserRepository userRepository, TagRepository tagRepository) {
        this.postRepository = postRepository;
        this.categoryRepository = categoryRepository;
        this.postMapper = postMapper;
        this.userRepository = userRepository;
        this.tagRepository = tagRepository;
    }

    @Transactional
    public ApiResponse<PostCreationResponseDto> createPost(PostCreationDto postCreationDto) {
        Category category = categoryRepository.findByName(postCreationDto.getCategory()).orElseGet(() -> {
            Category newCategory = Category.builder().name(postCreationDto.getCategory()).build();
            return categoryRepository.save(newCategory);
        });
        User user = userRepository.findByEmail(postCreationDto.getUserEmail()).orElseThrow(() -> new UserDoesNotExistsException("User with this email does not exists so failed to create post"));
        Post post = postMapper.toPost(postCreationDto);
        post.setCategory(category);
        post.setUser(user);
        List<Tag> tags = postCreationDto.getTags().stream().map(stringToTag).toList();
        post.setTags(tagRepository.saveAll(tags));
        PostCreationResponseDto responseDto = postMapper.toDto(postRepository.save(post));
        return ApiResponse.<PostCreationResponseDto>builder()
                .status(true)
                .httpStatus(HttpStatus.CREATED)
                .message("Successfully created the post")
                .response(responseDto)
                .build();

    }

    public ApiResponse<String> getNoOfComments(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostDoesNotExistsException("Post with this id does not exists"));
        int comments = post.getComments().size();
        return ApiResponse.<String>builder()
                .status(true)
                .httpStatus(HttpStatus.OK)
                .message("Successfully parsed the number of comments for this post")
                .response("The no of comments is " + comments)
                .build();
    }

    public ApiResponse<String> getNoOfLikes(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new PostDoesNotExistsException("Post with this id does not exists"));
        int likes = post.getLikes().size();
        return ApiResponse.<String>builder()
                .status(true)
                .httpStatus(HttpStatus.OK)
                .message("Successfully parsed the number of likes for this post")
                .response("The no of comments is " + likes)
                .build();
    }
}
