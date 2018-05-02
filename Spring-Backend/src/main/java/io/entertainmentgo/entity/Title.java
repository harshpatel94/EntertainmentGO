package io.entertainmentgo.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import lombok.Data;

@Entity
@Table
@Data
public class Title implements Serializable{

	private static final long serialVersionUID = 1L;
	
	@Id
	@GenericGenerator(name="myuuid",strategy="uuid2")
	@GeneratedValue(generator="myuuid")
	private String titleId;
	private String title;
	private int year;
	private double averageRating;
	private String rated;
	private String released;
	private String runtime;
	private String director;
	private String writers;
	private String actors;
	private String plot;
	private String language;
	private String country;
	private String awards;
	private String poster;
	private String metascore;
	private Double imdbRating;
	private int imdbVotes;
	private String imdbID;
	private String type;
	private String addedOn;
	@ManyToMany(cascade={javax.persistence.CascadeType.DETACH,javax.persistence.CascadeType.MERGE,javax.persistence.CascadeType.PERSIST,javax.persistence.CascadeType.REFRESH},fetch=FetchType.EAGER)
	@JoinTable(name="title_genre",joinColumns={
			@JoinColumn(referencedColumnName="titleId")
	},inverseJoinColumns={
			@JoinColumn(referencedColumnName="genreId")
	})
	private List<Genre> genre;

}
