package com.github.supercodingfinalprojectbackend.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Mentors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Mentor extends CommonEntity{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "mentor_id", nullable = false)
	private Long id;

	@Column(name = "name")
	private String name;

	@Column(name = "nickname")
	private String nickname;

	@Column(name = "email")
	private String email;

	@Column(name = "introduction")
	private String introduction;

	@Column(name = "is_incumbent")
	private Boolean isIncumbent;

	@Column(name = "searchable")
	private Boolean searchable;
}
