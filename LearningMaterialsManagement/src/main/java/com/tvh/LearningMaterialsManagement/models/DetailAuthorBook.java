/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.models;

import jakarta.persistence.Basic;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import java.io.Serializable;

/**
 *
 * @author Huy
 */
@Entity
@Table(name = "detail_author_book")
@NamedQueries({
    @NamedQuery(name = "DetailAuthorBook.findAll", query = "SELECT d FROM DetailAuthorBook d"),
    @NamedQuery(name = "DetailAuthorBook.findById", query = "SELECT d FROM DetailAuthorBook d WHERE d.id = :id"),
    @NamedQuery(name = "DetailAuthorBook.findByContent", query = "SELECT d FROM DetailAuthorBook d WHERE d.content = :content")})
public class DetailAuthorBook implements Serializable {

    @Size(max = 75)
    @Column(name = "content")
    private String content;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "author_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Author authorId;
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Book bookId;

    public DetailAuthorBook() {
    }

    public DetailAuthorBook(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Author getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Author authorId) {
        this.authorId = authorId;
    }

    public Book getBookId() {
        return bookId;
    }

    public void setBookId(Book bookId) {
        this.bookId = bookId;
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
        if (!(object instanceof DetailAuthorBook)) {
            return false;
        }
        DetailAuthorBook other = (DetailAuthorBook) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tvh.LearningMaterialsManagement.models.DetailAuthorBook[ id=" + id + " ]";
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    
}
