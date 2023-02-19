package dto;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class CreateCategoryDTO {
	@NotEmpty
	private String name;
}
