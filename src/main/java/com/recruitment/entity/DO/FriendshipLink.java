package com.recruitment.entity.DO;

import java.sql.Timestamp;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.ToString;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class FriendshipLink {

    private Long id;
    private String linkName;
    private String linkUrl;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}