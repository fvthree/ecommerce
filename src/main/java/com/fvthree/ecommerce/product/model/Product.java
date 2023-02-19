package com.fvthree.ecommerce.product.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fvthree.ecommerce.category.model.Category;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="product",
		uniqueConstraints = {
			@UniqueConstraint(columnNames="name"),
})
public class Product implements Serializable {

	private static final long serialVersionUID = 7780201460448513395L;

	@Id
	@SequenceGenerator(
            name = "product_sequence",
            sequenceName = "product_sequence",
            allocationSize = 1
    )
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "product_sequence")
	@Column(name="product_id")
	private Long id;
	
	@Column(name="name", nullable=false)
	private String name;
	
	@Column(name="slug", nullable=false)
	private String slug;
	
	@Column(name="description", nullable=false)
	private String description;
	
	@Column(name="price", nullable=false)
	private BigDecimal price;
	
	@ManyToOne
	@JoinColumn(name="category_id")
	private Category category;
	
	@Column(name="quantity", nullable=false)
	private Integer quantity;
	
	@Column(name="sold", nullable=false)
	private Integer sold;
	
	@Column(name="photo", nullable=false)
	private String photo;
	
	@Column(name="shipping", nullable=false)
	private Boolean shipping;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(updatable = false, name="date_created")
    private LocalDateTime dateCreated;
    
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @Column(name="last_updated")
    private LocalDateTime lastUpdated;

    @PrePersist
	public void setDateCreated() {
		this.dateCreated = LocalDateTime.now();
	}
    
    @PreUpdate
	public void setLastUpdated() {
		this.lastUpdated = LocalDateTime.now();
	}
}
