package com.sunbasedata.cms.pojo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class JwtResponse {

	private String username;
	private String token;
}
