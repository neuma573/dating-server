package com.example.dating.service.comments;

import com.example.dating.domain.comments.Comment;
import com.example.dating.domain.comments.CommentRepository;
import com.example.dating.domain.posts.Post;
import com.example.dating.domain.posts.PostRepository;
import com.example.dating.domain.user.User;
import com.example.dating.domain.user.UserRepository;
import com.example.dating.exception.exceptions.CommentNotFoundException;
import com.example.dating.exception.exceptions.EmailSigninFailedException;
import com.example.dating.exception.exceptions.PostsNotFoundException;
import com.example.dating.exception.exceptions.UserNotFoundException;
import com.example.dating.web.dto.CommentsSaveRequestDto;
import com.example.dating.web.dto.CommentsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@RequiredArgsConstructor
@Service
public class CommentsService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final PostRepository postRepository;

    @Transactional
    public Comment save(CommentsSaveRequestDto requestDto, Long postId, String userEmail){

        User user = userRepository.findByEmail(userEmail).orElseThrow(UserNotFoundException::new);
        Post post = postRepository.findById(postId).orElseThrow(PostsNotFoundException::new);

        return commentRepository.save(requestDto.toEntity(user, post));
    }

    @Transactional
    public Comment update(CommentsUpdateRequestDto requestDto, Long postId, Long commentId, String userEmail) {

        Post post = postRepository.findById(postId).orElseThrow(PostsNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        if(!isEqualUser(comment,userEmail)){
            throw new EmailSigninFailedException();
        }
        comment.update(requestDto.getContent());

        return comment;
    }

    @Transactional
    public Comment findById(Long postId, Long commentId){
        Post post = postRepository.findById(postId).orElseThrow(PostsNotFoundException::new);
        Comment entity = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);
        return entity;
    }

    @Transactional(readOnly = true)
    public List<Comment> findAllDesc(){
        return commentRepository.findAllDesc();
    }

    @Transactional
    public void delete(Long postId, Long commentId, String userEmail){

        Post post = postRepository.findById(postId).orElseThrow(PostsNotFoundException::new);
        Comment comment = commentRepository.findById(commentId).orElseThrow(CommentNotFoundException::new);

        if(!isEqualUser(comment,userEmail)){
            throw new EmailSigninFailedException();
        }

        commentRepository.delete(comment);
    }

    // Post 작성자와 접속user Id가 같은지
    private boolean isEqualUser(Comment comment, String userEmail){
        return userEmail.equals(comment.getUser().getEmail());
    }
}
