package com.shadliq.palaces.dto.response;




import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseDTO {
	private Integer code;
	private Object message;

	public ErrorResponseDTO(Integer errorCode, Object message) {
		this.code = errorCode;
		this.message = message;
	}
}
