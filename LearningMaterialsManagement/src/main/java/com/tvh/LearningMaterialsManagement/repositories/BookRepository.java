/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.tvh.LearningMaterialsManagement.repositories;

import com.tvh.LearningMaterialsManagement.models.Book;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author Huy
 */
@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {

    public Page<Book> findAll(Pageable pageable);

    //Truy vấn này không có phân trang
    // Paginated search
    @Query("SELECT b FROM Book b "
            + "LEFT JOIN b.detailAuthorBookSet dA "
            + "LEFT JOIN b.detailCategoryBookSet dC "
            + "WHERE "
            + "(:name IS NULL OR b.name LIKE %:name%) AND "
            + "(:namePublisher IS NULL OR b.publisherId.namePublisher LIKE %:namePublisher%) AND "
            + "(:fullNameAuthor IS NULL OR dA.authorId.fullNameAuthor LIKE %:fullNameAuthor%) AND "
            + "(:nameCategory IS NULL OR dC.categoryId.nameCategory LIKE %:nameCategory%) AND "
            + "(:minPrice IS NULL OR b.price >= :minPrice) AND "
            + "(:maxPrice IS NULL OR b.price <= :maxPrice)")
    List<Book> findBooksByCriteria(@Param("name") String name,
            @Param("namePublisher") String namePublisher,
            @Param("fullNameAuthor") String fullNameAuthor,
            @Param("nameCategory") String nameCategory,
            @Param("minPrice") Long minPrice,
            @Param("maxPrice") Long maxPrice);

    //Truy vấn này có phân trang
    // Paginated version of the search
    @Query("SELECT b FROM Book b "
            + "LEFT JOIN b.detailAuthorBookSet dA "
            + "LEFT JOIN b.detailCategoryBookSet dC "
            + "WHERE "
            + "(:name IS NULL OR b.name LIKE %:name%) AND "
            + "(:namePublisher IS NULL OR b.publisherId.namePublisher LIKE %:namePublisher%) AND "
            + "(:fullNameAuthor IS NULL OR dA.authorId.fullNameAuthor LIKE %:fullNameAuthor%) AND "
            + "(:nameCategory IS NULL OR dC.categoryId.nameCategory LIKE %:nameCategory%) AND "
            + "(:minPrice IS NULL OR b.price >= :minPrice) AND "
            + "(:maxPrice IS NULL OR b.price <= :maxPrice)")
    Page<Book> findAllByCriteria(Pageable pageable, @Param("name") String name,
            @Param("namePublisher") String namePublisher,
            @Param("fullNameAuthor") String fullNameAuthor,
            @Param("nameCategory") String nameCategory,
            @Param("minPrice") Long minPrice,
            @Param("maxPrice") Long maxPrice);

}
