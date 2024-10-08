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
import java.math.BigInteger;

/**
 *
 * @author Huy
 */
@Entity
@Table(name = "detail_receipt_book")
@NamedQueries({
    @NamedQuery(name = "DetailReceiptBook.findAll", query = "SELECT d FROM DetailReceiptBook d"),
    @NamedQuery(name = "DetailReceiptBook.findById", query = "SELECT d FROM DetailReceiptBook d WHERE d.id = :id"),
    @NamedQuery(name = "DetailReceiptBook.findByContent", query = "SELECT d FROM DetailReceiptBook d WHERE d.content = :content")})
public class DetailReceiptBook implements Serializable {

    @Column(name = "total_unit_price")
    private long totalUnitPrice;
    @Size(max = 100)
    @Column(name = "content")
    private String content;
    @Column(name = "quantity")
    private Integer quantity;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "book_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Book bookId;
    @JoinColumn(name = "receipt_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Receipt receiptId;

    public DetailReceiptBook() {
    }

    public DetailReceiptBook(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Book getBookId() {
        return bookId;
    }

    public void setBookId(Book bookId) {
        this.bookId = bookId;
    }

    public Receipt getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(Receipt receiptId) {
        this.receiptId = receiptId;
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
        if (!(object instanceof DetailReceiptBook)) {
            return false;
        }
        DetailReceiptBook other = (DetailReceiptBook) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.tvh.LearningMaterialsManagement.models.DetailReceiptBook[ id=" + id + " ]";
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    // Getter và Setter cho totalUnitPrice
    public long getTotalUnitPrice() {
        return totalUnitPrice;
    }

    public void setTotalUnitPrice(long totalUnitPrice) {
        this.totalUnitPrice = totalUnitPrice;
    }

//    public BigInteger getTotalUnitPrice() {
//        return totalUnitPrice;
//    }
//
//    public void setTotalUnitPrice(BigInteger totalUnitPrice) {
//        this.totalUnitPrice = totalUnitPrice;
//    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
