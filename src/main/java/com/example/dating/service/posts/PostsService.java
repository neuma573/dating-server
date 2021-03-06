package com.example.dating.service.posts;


import com.example.dating.domain.posts.PostRepository;
import com.example.dating.domain.posts.Post;
import com.example.dating.domain.user.UserRepository;
import com.example.dating.exception.exceptions.EmailSigninFailedException;
import com.example.dating.exception.exceptions.PostsNotFoundException;
import com.example.dating.exception.exceptions.UserNotFoundException;
import com.example.dating.web.dto.PostsSaveRequestDto;
import com.example.dating.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PostsService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Transactional
    public Post save(PostsSaveRequestDto requestDto, String userEmail){
        Post post = userRepository.findByEmail(userEmail)
                .map(u -> postRepository.saveAndFlush(requestDto.toEntity(u)))
                .orElseThrow(UserNotFoundException::new);
        return post;
    }

    @Transactional
    public Post update(Long postId, PostsUpdateRequestDto requestDto, String userEmail) {

        Post post = postRepository.findById(postId).orElseThrow(PostsNotFoundException::new);

        if(!post.isEqualUserEmail(userEmail)){
            throw new EmailSigninFailedException();
        }

        post.update(requestDto.getContent());

        return post;
    }

    @Transactional
    public Post findById(Long postId){
        return postRepository.findById(postId).orElseThrow(PostsNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Post> findAllDesc(){
        return postRepository.findAllDesc();
    }

    @Transactional(readOnly = true)
    public Page<Post> findAll(Pageable pageable){
        return postRepository.findAll(pageable);
    }

    @Transactional
    public void delete(Long postId, String userEmail){
        Post post = postRepository.findById(postId).orElseThrow(PostsNotFoundException::new
        );

        if(!post.isEqualUserEmail(userEmail)){
            throw new EmailSigninFailedException();
        }

        postRepository.delete(post);
    }

}
