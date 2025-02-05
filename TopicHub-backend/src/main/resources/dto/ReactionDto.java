package com.example.topichubbackend.dto;

import lombok.*;



@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ReactionDto {

   private Boolean isSubscribe;
   private Boolean isMarked;
}
