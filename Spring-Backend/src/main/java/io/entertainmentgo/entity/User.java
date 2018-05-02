package io.entertainmentgo.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Data;

@Entity
@Table
@Data
public class User implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name="myuuid",strategy="uuid2")
	@GeneratedValue(generator="myuuid")
	private String userId;
	private String userName;
	private String emailId;
	@JsonProperty(access=Access.WRITE_ONLY)
	private String password;
	private String createdOn;
	private String role;
}
