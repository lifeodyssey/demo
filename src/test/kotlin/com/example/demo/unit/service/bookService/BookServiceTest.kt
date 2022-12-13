package com.example.demo.unit.service.bookService

import com.example.demo.mapper.toBook
import com.example.demo.repository.BookRepository
import com.example.demo.service.BookService
import models.dto.AuthorDto
import models.dto.BookDto
import models.dto.DetailDto
import models.dto.RatesDto
import models.entity.Book
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.catchThrowable
import org.bson.types.ObjectId
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.any
import org.mockito.kotlin.doNothing
import org.mockito.kotlin.times
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever
import java.util.*
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@ExtendWith(MockitoExtension::class)
internal class BookServiceTest {
    private val title = "System Design Interview – An insider's guide"
    private val author = AuthorDto("Alex Xu")
    private val rates = RatesDto()
    private val abstract =
        "System Design Interview - An Insider's Guide (Volume 1)\n\nSystem design interviews are the most difficult to tackle of all technical interview questions. This book is Volume 1 of the System Design Interview - An insider’s guide series that provides a reliable strategy and knowledge base for approaching a broad range of system design questions. This book provides a step-by-step framework for how to tackle a system design question. It includes many real-world examples to illustrate the systematic approach, with detailed steps that you can follow.\n\nWhat’s inside?\n- An insider’s take on what interviewers really look for and why.\n- A 4-step framework for solving any system design interview question.\n- 16 real system design interview questions with detailed solutions.\n- 188 diagrams to visually explain how different systems work.\n\nTable Of Contents\nChapter 1: Scale From Zero To Millions Of Users\nChapter 2: Back-of-the-envelope Estimation\nChapter 3: A Framework For System Design Interviews\nChapter 4: Design A Rate Limiter\nChapter 5: Design Consistent Hashing\nChapter 6: Design A Key-value Store\nChapter 7: Design A Unique Id Generator In Distributed Systems\nChapter 8: Design A Url Shortener\nChapter 9: Design A Web Crawler\nChapter 10: Design A Notification System\nChapter 11: Design A News Feed System\nChapter 12: Design A Chat System\nChapter 13: Design A Search Autocomplete System\nChapter 14: Design Youtube\nChapter 15: Design Google Drive\nChapter 16: The Learning Continues"
    private val detail = DetailDto(isbn = "979-8664653403")

    @Mock
    lateinit var bookRepository: BookRepository

    @InjectMocks
    lateinit var bookService: BookService

//    @Captor
//    lateinit var bookCaptor: ArgumentCaptor<Book>
    private lateinit var savedBookId: String
    private lateinit var savedBook: Book
    private lateinit var bookDto: BookDto
    @BeforeEach
    fun prepareDatInDB() {
        bookDto = BookDto(
            title = title,
            authors = listOf(author),
            rates = rates,
            abstract = abstract,
            details = detail
        )

        savedBook = bookDto.toBook().copy(
            bookId = ObjectId().toString()
        )
        savedBookId= savedBook.bookId!!
    }

    @Test
    fun `createBook should return book if saved`() {
        // Given
//        `when`(bookRepository.save(bookCaptor.capture())).thenReturn(savedBook)
        `when`(bookRepository.save(any())).thenReturn(savedBook)

        // When
        val createBook = bookService.createBook(bookDto)
        // Then
        verify(bookRepository).save(any()) // todo:verify other output
        assertEquals(createBook,savedBook)
    }

    @Test
    fun `findBookById should return book if found`() {
        whenever(bookRepository.findById(savedBookId)).thenReturn(Optional.of(savedBook))
        val foundedBook = bookService.findBookById(savedBookId)

        verify(bookRepository).findById(savedBookId)
        assertThat(foundedBook).isNotNull
        assertEquals(savedBook, foundedBook)
    }

    @Test
    fun `findBookById should throw exception if book not found`() {
        val wrongBookId = ObjectId().toString()
        whenever(bookRepository.findById(wrongBookId)).thenReturn(Optional.empty())
        assertThrows<Exception> { bookService.findBookById(wrongBookId) }

        val thrownException= catchThrowable { bookService.findBookById(wrongBookId) }
        verify(bookRepository,times(2)).findById(wrongBookId)
        assertEquals("Book with id $wrongBookId not found",thrownException.message)
    }

    @Test
    fun `findAllBooks should return all the books in the db`() {
        whenever(bookRepository.findAll()).thenReturn(listOf(savedBook))
        val bookList = bookService.findAllBooks()

        verify(bookRepository).findAll()
        assertTrue(bookList.isNotEmpty())
        assertEquals(1, bookList.size)
        assertEquals(savedBook, bookList[0])
    }

    @Test
    fun `updateBookById should return book if updated`() {
        val updatedBookDto = bookDto.copy(title = "system design volume 22")
        val updateBook=savedBook.copy(title="system design volume 22")
        whenever(bookRepository.findById(savedBookId)).thenReturn(Optional.of(savedBook))
        `when`(bookRepository.save(any())).thenReturn(updateBook)
        val updateResult = bookService.updateBookById(savedBookId,updatedBookDto)

        verify(bookRepository).findById(savedBookId)
        verify(bookRepository).save(any())
        assertThat(updateBook).isNotNull
        assertEquals(updateBook, updateResult)
    }

    @Test
    fun `updateBookById should throw exception if book not found`() {
        val wrongBookId = ObjectId().toString()
        whenever(bookRepository.findById(wrongBookId)).thenReturn(Optional.empty())
        assertThrows<Exception> { bookService.updateBookById(wrongBookId, bookDto) }

        val thrownException= catchThrowable { bookService.updateBookById(wrongBookId,bookDto) }
        verify(bookRepository,times(2)).findById(wrongBookId)
        assertEquals("Book with id $wrongBookId not found",thrownException.message)
    }

    @Test
    fun `deleteBookById should return nothing if founded`() {

        whenever(bookRepository.findById(savedBookId)).thenReturn(Optional.of(savedBook))
        doNothing().`when`(bookRepository).deleteById(savedBookId)
        bookService.deleteBookById(savedBookId)
        verify(bookRepository).findById(savedBookId)
        verify(bookRepository).deleteById(savedBookId)
    }

    @Test
    fun `deleteBookById should throw exception if book not found`() {
        val wrongBookId = ObjectId().toString()
        whenever(bookRepository.findById(wrongBookId)).thenReturn(Optional.empty())
        assertThrows<Exception> { bookService.deleteBookById(wrongBookId) }

        val thrownException= catchThrowable { bookService.deleteBookById(wrongBookId) }
        verify(bookRepository, times(2)).findById(wrongBookId)
        assertEquals("Book with id $wrongBookId not found",thrownException.message)
    }

    @Test
    fun `deleteAllBooks should delete all the books in db`() {
        // Given
        bookRepository.save(bookDto.toBook())
        bookRepository.save(bookDto.toBook())
        // When
        bookRepository.deleteAll()
        // Then
        assertEquals(0,bookRepository.findAll().size)

    }
}
