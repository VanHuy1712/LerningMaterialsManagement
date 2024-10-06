/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Basic;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.io.Serializable;
import java.util.Set;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author Huy
 */
@Entity
@Table(name = "book")
@NamedQueries({
    @NamedQuery(name = "Book.findAll", query = "SELECT b FROM Book b"),
    @NamedQuery(name = "Book.findById", query = "SELECT b FROM Book b WHERE b.id = :id"),
    @NamedQuery(name = "Book.findByName", query = "SELECT b FROM Book b WHERE b.name = :name"),
    @NamedQuery(name = "Book.findByPrice", query = "SELECT b FROM Book b WHERE b.price = :price"),
    @NamedQuery(name = "Book.findByDescription", query = "SELECT b FROM Book b WHERE b.description = :description")})
public class Book implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "price")
    private long price;
    @Size(max = 225)
    @Column(name = "description")
    private String description;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 125)
    @Column(name = "avatar_book")
    private String avatarBook;
    @Column(name = "amount")
    private Integer amount;
    @JoinColumn(name = "publisher_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Publisher publisherId;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bookId")
    @JsonIgnore
    private Set<DetailReceiptBook> detailReceiptBookSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bookId")
    @JsonIgnore
    private Set<DetailCategoryBook> detailCategoryBookSet;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "bookId")
    @JsonIgnore
    private Set<DetailAuthorBook> detailAuthorBookSet;
    
    @Transient
    private MultipartFile file;
    
    /**
     * @return the file
     */
    public MultipartFile getFile() {
        return file;
    }

    /**
     * @param file the file to set
     */
    public void setFile(MultipartFile file) {
        this.file = file;
    }

    public Book() {
    }

    public Book(Integer id) {
        this.id = id;
    }

    public Book(Integer id, String name, long price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Set<DetailReceiptBook> getDetailReceiptBookSet() {
        return detailReceiptBookSet;
    }

    public void setDetailReceiptBookSet(Set<DetailReceiptBook> detailReceiptBookSet) {
        this.detailReceiptBookSet = detailReceiptBookSet;
    }

    public Set<DetailCategoryBook> getDetailCategoryBookSet() {
        return detailCategoryBookSet;
    }

    public void setDetailCategoryBookSet(Set<DetailCategoryBook> detailCategoryBookSet) {
        this.detailCategoryBookSet = detailCategoryBookSet;
    }

    public Set<DetailAuthorBook> getDetailAuthorBookSet() {
        return detailAuthorBookSet;
    }

    public void setDetailAuthorBookSet(Set<DetailAuthorBook> detailAuthorBookSet) {
        this.detailAuthorBookSet = detailAuthorBookSet;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Book)) {
            return false;
        }
        Book other = (Book) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tvh.LearningMaterialsManagement.models.Book[ id=" + id + " ]";
    }


    public String getAvatarBook() {
        return avatarBook;
    }

    public void setAvatarBook(String avatarBook) {
        this.avatarBook = avatarBook;
    }


    public Publisher getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Publisher publisherId) {
        this.publisherId = publisherId;
    }


    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
