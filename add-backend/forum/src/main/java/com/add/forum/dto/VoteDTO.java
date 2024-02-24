package com.add.forum.dto;

import com.add.forum.constants.VoteType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VoteDTO {

    private VoteType voteType;

    private String postId;

    private String userId;
}
