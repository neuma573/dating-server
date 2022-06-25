package com.example.dating.domain.posts;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import org.springframework.hateoas.RepresentationModel;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostAdapter extends RepresentationModel<PostAdapter> {
	private Long id;
	private String content;
	private Long likes;
	private String userEmail;
	private String userNickName;
	private String modifyDate;
	private Long comments;
	private Boolean isLike;
	private Boolean isWriter;

	public PostAdapter setIsLike(boolean bool) {
		isLike = bool;
		return this;
	}

	public PostAdapter setIsWriter(boolean bool) {
		isWriter = bool;
		return this;
	}
}
