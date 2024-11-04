package org.example.project.dataBaseTest


import org.example.project.database.model.Product
import org.example.project.database.repo.ProductRepository
import org.example.project.database.service.ProductService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mockito.`when`
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest

@SpringBootTest
class ProductServiceTest{
    @MockBean
    lateinit var productRepository: ProductRepository
    @Autowired
    lateinit var productService: ProductService

    @BeforeEach
    fun beforeTest() {
        productRepository.save(Product(1, "Оправа Vogue VO5161", 10999, "/img/Оправа1.png", "/home", 10))
        productRepository.save(Product(2, "Оправа Ray-Ban Aviator RB3025", 10500, "/img/Оправа2.png", "/home", 15))
        productRepository.save(Product(3, "Оправа Oakley OX5132", 17800, "/img/оправа3.png", "/home", 13))
        productRepository.save(Product(4, "Оправа Prada PR 15VS", 32400, "/img/оправа4.png", "/home", 9))
        productRepository.save(Product(5, "Оправа Gucci GG0061S", 23250, "/img/оправа5.png", "/home", 21))
    }

    @Test
    fun find_products_by_page_test(){
        val pageRequest = PageRequest.of(1, 2)
        val expectedProducts = listOf(
                Product(3, "Оправа Oakley OX5132", 17800, "/img/оправа3.png", "/home", 13),
                Product(4, "Оправа Prada PR 15VS", 32400, "/img/оправа4.png", "/home", 9)
        )
        val expectedPage = PageImpl(expectedProducts, pageRequest, 5)

        `when`(productRepository.findItemsByNameContainingIgnoreCase("", pageRequest)).thenReturn(expectedPage)

        // Act
        val actualPage = productService.findProductsByPage(1, 2, "")

        // Assert
        assert(actualPage.content.size == expectedProducts.size)
        assert(actualPage.content.containsAll(expectedProducts))
    }

    @Test
    fun find_products_count_test(){
        // Arrange
        val searchValue = ""
        `when`(productRepository.countProductsByNameContainingIgnoreCase(searchValue)).thenReturn(5)

        // Act
        val actualCount = productService.getTotalProductCount(searchValue)

        // Assert
        assertEquals(5, actualCount)
    }
}

//    init{
//        extension(SpringExtension)
//
//        beforeTest {
//            productRepository.save(Product(1, "Оправа Vogue VO5161", 10999, "/img/Оправа1.png", "/home", 10))
//            productRepository.save(Product(2, "Оправа Ray-Ban Aviator RB3025", 10500, "/img/Оправа2.png", "/home", 15))
//            productRepository.save(Product(3, "Оправа Oakley OX5132", 17800, "/img/оправа3.png", "/home", 13))
//            productRepository.save(Product(4, "Оправа Prada PR 15VS", 32400, "/img/оправа4.png", "/home", 9))
//            productRepository.save(Product(5, "Оправа Gucci GG0061S", 23250, "/img/оправа5.png", "/home", 21))
//        }
//
//        test("Getting products for a specific page without search") {
//            val products = productService.findProductsByPage(0, 2, "")
//            println(products.toList())
//        }
//    }