package com.innoventes.test.app.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class CompanyDTO {

	private Long id;

	@NotBlank(message = "Company name should not be blank")
	private String companyName;

	//TODO: RegEx can be add for email validation
	@Email(message = "Invalid email address")
	private String email;

	@Min(value = 0)
	private Integer strength;
	
	private String webSiteURL;

	private String companyCode;
}
