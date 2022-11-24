package com.spring.cinemaproject.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "films")
public class Films {
    @Id
    @Column(name = "filmID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer filmID;

//    @Column(name = "film_name")
    @Column(name = "filmName")
    private String filmName;

    @Column(name = "filmName1")
    private String filmName1;

    @Column(name = "filmDescription",length = 500)
    private String filmDescription;

    @Column(name = "filmDescription1", length = 500)
    private String filmDescription1;

    @Column(name = "urlTrailer")
    private String urlTrailer;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "releaseDate")
    private Date releaseDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Column(name = "endDate")
    private Date endDate;

    @Column(name = "runtime")
    private Integer runtime;

    @Column(name = "rated")
    private float rated;

    @Column(name = "image")
    private String image;

    @Column(name = "price")
    private float price;

    @Column(name = "status")
    private boolean status;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "directorID")
    private Directors directors;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "producerID")
    private Producers producers;

    @JsonBackReference
    @OneToMany(mappedBy = "films",cascade = CascadeType.ALL)
    private Collection<Schedules> schedules;

    @JsonBackReference
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "genredetails",
            joinColumns = @JoinColumn(name = "filmID"),
            inverseJoinColumns = @JoinColumn(name = "genreID")
    )
    private Set<Genres> genres=new HashSet<>();

    @JsonBackReference
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name = "roomdetails",
            joinColumns = @JoinColumn(name = "filmID"),
            inverseJoinColumns = @JoinColumn(name = "roomID")
    )
    private Set<Rooms> rooms =new HashSet<>();

    public Collection<Schedules> getSchedules() {
        return schedules;
    }

    public void setSchedules(Collection<Schedules> schedules) {
        this.schedules = schedules;
    }

    public Integer getRuntime() {
        return runtime;
    }

    public void setRuntime(Integer runtime) {
        this.runtime = runtime;
    }

    public Producers getProducers() {
        return producers;
    }

    public void setProducers(Producers producers) {
        this.producers = producers;
    }

    public Directors getDirectors() {
        return directors;
    }

    public void setDirectors(Directors directors) {
        this.directors = directors;
    }

    public Films (){

     }

    public Films(Integer filmID, String filmName, String filmName1, String filmDescription, String filmDescription1, String urlTrailer, Date releaseDate, Date endDate, Integer runtime, float rated, String image, float price, boolean status, Directors directors, Producers producers, Collection<Schedules> schedules, Set<Genres> genres, Set<Rooms> rooms) {
        this.filmID = filmID;
        this.filmName = filmName;
        this.filmName1 = filmName1;
        this.filmDescription = filmDescription;
        this.filmDescription1 = filmDescription1;
        this.urlTrailer = urlTrailer;
        this.releaseDate = releaseDate;
        this.endDate = endDate;
        this.runtime = runtime;
        this.rated = rated;
        this.image = image;
        this.price = price;
        this.status = status;
        this.directors = directors;
        this.producers = producers;
        this.schedules = schedules;
        this.genres = genres;
        this.rooms = rooms;
    }

    public Integer getFilmID() {
        return filmID;
    }

    public void setFilmID(Integer filmID) {
        this.filmID = filmID;
    }

    public String getFilmName() {
        return filmName;
    }

    public void setFilmName(String filmName) {
        this.filmName = filmName;
    }


    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public float getRated() {
        return rated;
    }

    public void setRated(float rated) {
        this.rated = rated;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getFilmName1() {
        return filmName1;
    }

    public void setFilmName1(String filmName1) {
        this.filmName1 = filmName1;
    }

    public String getFilmDescription() {
        return filmDescription;
    }

    public void setFilmDescription(String filmDescription) {
        this.filmDescription = filmDescription;
    }

    public String getFilmDescription1() {
        return filmDescription1;
    }

    public void setFilmDescription1(String filmDescription1) {
        this.filmDescription1 = filmDescription1;
    }

    public String getUrlTrailer() {
        return urlTrailer;
    }

    public void setUrlTrailer(String urlTrailer) {
        this.urlTrailer = urlTrailer;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Set<Genres> getGenres() {
        return genres;
    }

    public void setGenres(Set<Genres> genres) {
        this.genres = genres;
    }

    public Set<Rooms> getRooms() {
        return rooms;
    }

    public void setRooms(Set<Rooms> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "Films{" +
                "filmID=" + filmID +
                ", filmName='" + filmName + '\'' +
                ", filmName1='" + filmName1 + '\'' +
                ", filmDescription='" + filmDescription + '\'' +
                ", filmDescription1='" + filmDescription1 + '\'' +
                ", urlTrailer='" + urlTrailer + '\'' +
                ", releaseDate=" + releaseDate +
                ", endDate=" + endDate +
                ", runtime=" + runtime +
                ", rated=" + rated +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", status=" + status +
                ", directors=" + directors +
                ", producers=" + producers +
                ", schedules=" + schedules +
                ", genres=" + genres +
                ", rooms=" + rooms +
                '}';
    }
    @Transient
    public String getImagePath(){
        if(image == null || filmID == null) {
            return null;
        }
        return "/film-img/"+filmID +"/"+ image;
    }
}
