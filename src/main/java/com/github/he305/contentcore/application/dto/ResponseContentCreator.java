package com.github.he305.contentcore.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseContentCreator {
    private String name;
    private List<ResponseContentAccount> content;
}
