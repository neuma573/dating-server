package com.example.dating.web;


import com.example.dating.common.DocumentLinkToRef;
import com.example.dating.config.AuthUser;
import com.example.dating.domain.posts.*;
import com.example.dating.exception.response.ResponseData;
import com.example.dating.exception.response.ResponseService;
import com.example.dating.exception.response.ResponseStatus;
import com.example.dating.domain.user.User;
import com.example.dating.service.posts.PostsService;
import com.example.dating.web.dto.PostsSaveRequestDto;
import com.example.dating.web.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityManager;
import javax.validation.Valid;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/posts")
public class PostController {

    private final ResponseService responseService;
    private final PostsService postsService;
    private final PostAssembler postAssembler;
    private final PostAdapterAssembler postAdapterAssembler;
    private final EntityManager entityManager;

    @GetMapping
    public ResponseEntity getPageableList(Pageable pageable,
                                          PagedResourcesAssembler pagedResourcesAssembler,
                                          @AuthUser User user) {

        Page<Post> posts = postsService.findAll(pageable);

        Page<PostAdapter> postAdapters = posts.map(p -> p.toAdapter(Optional.ofNullable(user)));

        PagedModel<PostModel> postModels = pagedResourcesAssembler.toModel(postAdapters, this.postAdapterAssembler);
        postModels.add(linkTo(DocumentLinkToRef.class).slash("docs/index.html#resources-post").withRel("documentation_url"));

        return ResponseEntity.ok(postModels);
    }

    @GetMapping("/{id}")
    public ResponseEntity findById(@PathVariable Long id,
                                   @AuthUser User user) {

        Post post = postsService.findById(id);

        PostModel postModel = postAdapterAssembler.toModel(post.toAdapter(Optional.ofNullable(user)));

        return ResponseEntity.ok(postModel);
    }


    @PostMapping
    public ResponseEntity save(@Valid @RequestBody PostsSaveRequestDto postsSaveRequestDto,
                               @AuthUser User user,
                               Errors errors) {

        if (errors.hasErrors()) {
            ResponseData<Errors> response = responseService.create(ResponseStatus.INVALID_REQUEST_PARAMETER_ERROR, errors);
            return ResponseEntity.badRequest().body(response);
        }

        // ????????? ?????? ?????? (save ??????) -> getComment??? ??????.
        Post save = postsService.save(postsSaveRequestDto, user.getEmail());
        PostModel model = postAssembler.toModel(save);
        model.add(linkTo(DocumentLinkToRef.class).slash("docs/index.html#_post").withRel("documentation_url"));
        return ResponseEntity.ok(model);
    }

    @PutMapping("/{id}")
    public ResponseEntity update(@PathVariable Long id, @Valid @RequestBody PostsUpdateRequestDto requestDto, @AuthUser User user) {
        Post update = postsService.update(id, requestDto, user.getEmail());
        PostModel model = postAssembler.toModel(update);
        model.add(linkTo(DocumentLinkToRef.class).slash("docs/index.html#_update_2").withRel("documentation_url"));
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id, @AuthUser User user) {
        postsService.delete(id, user.getEmail());
        return ResponseEntity.noContent().build();

    }
}
