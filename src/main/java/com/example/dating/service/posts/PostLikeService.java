package com.example.dating.service.posts;


import com.example.dating.domain.posts.Post;
import com.example.dating.domain.posts.PostRepository;
import com.example.dating.domain.posts.like.PostLike;
import com.example.dating.domain.posts.like.PostLikeRepository;
import com.example.dating.domain.user.User;
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
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PostLikeService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostLikeRepository postLikeRepository;

    @Transactional
    public PostLike like(Long postId, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Post post = postRepository.findById(postId).orElseThrow(PostsNotFoundException::new);
        Optional<PostLike> byUserIdAndPostId = postLikeRepository.findByUserIdAndPostId(user.getId(), post.getId());

        return byUserIdAndPostId.orElse(postLikeRepository.save(
                PostLike.builder()
                        .post(post)
                        .user(user)
                        .build()));

    }

    @Transactional
    public void unLike(Long postId, String userEmail) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Post post = postRepository.findById(postId).orElseThrow(PostsNotFoundException::new);
        postLikeRepository.deleteByUserIdAndPostId(user.getId(), postId);
    }

    @Transactional
    public Boolean findByUserEmailAndPostId(String userEmail, Long postId) {

        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Post post = postRepository.findById(postId).orElseThrow(PostsNotFoundException::new);
        return postLikeRepository.findByUserIdAndPostId(postId, user.getId())
                .map(i -> true)
                .orElse(false);
    }
}
