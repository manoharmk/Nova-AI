package com.manohar.nova.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object for chat responses.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChatResponse {

    private String reply;
}
