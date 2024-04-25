package com.synergisticit.domain;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString  // When there is association on one entity to an other the @ToString causes StackOverFlowError
@Getter
@Setter
@Entity
public class Role {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="roleId")
	private Long id;
	@NotEmpty
	@Column(name="roleName")
	private String name;
	@ToString.Exclude
	@JsonIgnore  //To avoid infinite recursion, the List(User> should not be serialized with role object.
	@ManyToMany(mappedBy="roles")
	private List<User> users = new ArrayList<>();

}
