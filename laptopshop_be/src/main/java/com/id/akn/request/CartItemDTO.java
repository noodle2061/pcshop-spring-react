package com.id.akn.request;

import lombok.*;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartItemDTO {
	private String id;
	private Integer laptopId;
	private String laptopModel;
	private Long laptopPrice;
	private Float discountPercent;
	private Byte colorId;
	private String colorName;
	private short quantity;
	private short stock;
	private String firstImageUrl;
}
