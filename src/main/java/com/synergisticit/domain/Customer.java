package com.synergisticit.domain;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotEmpty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class Customer {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String name;
	@Enumerated(EnumType.STRING)
	private Gender gender;
	// "yyyy-MM-dd"
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
	private LocalDate dob;
	@NotEmpty
	private String mobile;
	@Embedded
	private Address address;
	
	private String SSN;
	@Builder.Default
	@OneToMany(mappedBy="customer")
	private List<Account> accounts = new ArrayList<>();
	@OneToOne
	private User user;
}
