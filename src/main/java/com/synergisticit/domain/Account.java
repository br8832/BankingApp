package com.synergisticit.domain;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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
public class Account {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@ToString.Exclude
	@JsonIgnore
	@ManyToOne
	private Branch branch;
	@Enumerated(EnumType.STRING) // for enum
	private AccountType accountType;
	private String holder;
	// "yyyy-MM-dd"
	@DateTimeFormat(iso=DateTimeFormat.ISO.DATE)
	private LocalDate dateOpened;
	private Double balance;
	@ToString.Exclude
	@JsonIgnore
	@ManyToOne
	private Customer customer;
	
	public String identifier()
	{
		return this.holder+" "+this.branch.getName() + " "+this.accountType;
	}
}
