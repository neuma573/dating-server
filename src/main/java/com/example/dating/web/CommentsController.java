package com.example.dating.web;


import com.example.dating.common.DocumentLinkToRef;
import com.example.dating.config.AuthUser;
import com.example.dating.domain.comments.Comment;
import com.example.dating.domain.comments.CommentModel;
import com.example.dating.domain.comments.CommentResponseDto;
import com.example.dating.domain.comments.CommentAssembler;
import com.example.dating.domain.posts.Post;
import com.example.dating.exception.response.ResponseData;
import com.example.dating.exception.response.ResponseService;
import com.example.dating.exception.response.ResponseStatus;
import com.example.dating.domain.user.User;
import com.example.dating.service.comments.CommentsService;
import com.example.dating.service.posts.PostsService;
import com.example.dating.web.dto.CommentsSaveRequestDto;
import com.example.dating.web.dto.CommentsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
public class CommentsController {

    private final ResponseService responseService;
    private final CommentsService commentsService;
    private final PostsService postsService;
    private final CommentAssembler commentAssembler;

    @GetMapping("/v1/posts/{postId}/comments")
    public ResponseEntity getList(@PathVariable Long postId, @AuthUser User user) {

        Post post = postsService.findById(postId);

        List<CommentResponseDto> collect = post.getComments().stream()
                .map(c -> new CommentResponseDto(c,Optional.ofNullable(user)))
                .collect(Collectors.toList());

        CollectionModel<CommentModel> commentModels = commentAssembler.toCollectionModel(collect);
        commentModels.add(linkTo(DocumentLinkToRef.class).slash("docs/index.html#_get_3").withRel("documentation_url"));
        return ResponseEntity.ok(commentModels);
    }

    @GetMapping("/v1/posts/{postId}/comments/{commentId}")
    public ResponseEntity findById(@PathVariable Long postId,
                                   @PathVariable Long commentId,
                                   @AuthUser User user) {

        Comment byId = commentsService.findById(postId, commentId);
        CommentModel commentModel = commentAssembler.toModel(new CommentResponseDto(byId, Optional.ofNullable(user)));
        return ResponseEntity.ok(commentModel);
    }

    @PostMapping("/v1/posts/{postId}/comments")
    public ResponseEntity save(@Valid @RequestBody CommentsSaveRequestDto requestDto,
                               @PathVariable Long postId,
                               @AuthUser User user,
                               Errors errors) {

        if (errors.hasErrors()) {
            ResponseData<Errors> response = responseService.create(ResponseStatus.INVALID_REQUEST_PARAMETER_ERROR, errors);
            return ResponseEntity.badRequest().body(response);
        }

        Comment save = commentsService.save(requestDto, postId, user.getEmail());
        CommentModel commentModel = commentAssembler.toModel(new CommentResponseDto(save, Optional.ofNullable(user)));
        commentModel.add(linkTo(DocumentLinkToRef.class).slash("docs/index.html#_post_2").withRel("documentation_url"));
        return ResponseEntity.ok(commentModel);
    }

    @PutMapping("/v1/posts/{postId}/comments/{commentId}")
    public ResponseEntity update(@Valid @RequestBody CommentsUpdateRequestDto requestDto,
                                 @PathVariable Long postId,
                                 @PathVariable Long commentId,
                                 @AuthUser User user,
                                 Errors errors) {

        if (errors.hasErrors()) {
            ResponseData<Errors> response = responseService.create(ResponseStatus.INVALID_REQUEST_PARAMETER_ERROR, errors);
            return ResponseEntity.badRequest().body(response);
        }

        Comment update = commentsService.update(requestDto, postId, commentId, user.getEmail());
        CommentModel commentModel = commentAssembler.toModel(new CommentResponseDto(update, Optional.ofNullable(user)));
        commentModel.add(linkTo(DocumentLinkToRef.class).slash("docs/index.html#_update_3").withRel("documentation_url"));
        return ResponseEntity.ok(commentModel);
    }

    @DeleteMapping("/v1/posts/{postId}/comments/{commentId}")
    public ResponseEntity delete(@PathVariable Long postId,
                                 @PathVariable Long commentId,
                                 @AuthUser User user) {

        commentsService.delete(postId, commentId, user.getEmail());
        return ResponseEntity.noContent().build();

    }
}
