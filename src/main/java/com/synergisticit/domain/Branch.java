package com.synergisticit.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name="Branch") // like with the below
public class Branch {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	//@Column(name="branchName") // force the column in the database to be branchName instead of name
	private String name;
	// @Column(name="branchAddress")
	@Embedded
	@Valid
	private Address address;
	@ToString.Exclude
	@JsonIgnore
	@OneToMany(mappedBy="branch") // the one is mapped by the branch Attribute
	@Builder.Default
	private List<Account> accounts = new ArrayList<>();
}
