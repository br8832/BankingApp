package com.synergisticit.domain;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable //don't need to store in db
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Address {
	@NotEmpty
	private String line1;
	private String line2;
	@NotEmpty
	private String city;
	@NotEmpty
	private String state;
	@NotEmpty
	private String country;
	@NotEmpty
	private String zip;

	public String readable() {
		return line1+" "+city+","+state+" "+zip;
	}
}
