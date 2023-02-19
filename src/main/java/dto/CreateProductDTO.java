package dto;

import java.math.BigDecimal;

import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
public class CreateProductDTO {
	
	@NotEmpty
	private String name;
	
	private String description;
	
	private BigDecimal price;
	
	private Long category_id;
	
	private Integer quantity;
	
	private Integer sold;
	
	private String photo;
	
	private Boolean shipping;
}
