package com.example.book.migration

import com.mongodb.client.model.Filters
import com.mongodb.client.model.Updates
import io.mongock.api.annotations.ChangeUnit
import io.mongock.api.annotations.Execution
import io.mongock.api.annotations.RollbackExecution
import org.springframework.data.mongodb.core.MongoTemplate

@ChangeUnit(id = "addBookItemsField", order = "1", author = "zhenjia")
class AddBookItemsChangeUnit(
    private val mongoTemplate: MongoTemplate
) {

    /** This is the method with the migration code  */
    @Execution
    fun addBookItemsField() {
        val bookCollection = mongoTemplate.getCollection("book")
        bookCollection.updateMany(
            Filters.exists("BookItems", false),
            Updates.set("BookItems", null)
        )
    }

    /**
     * This method is mandatory even when transactions are enabled.
     * They are used in the undo operation and any other scenario where transactions are not an option.
     * However, note that when transactions are avialble and Mongock need to rollback, this method is ignored.
     */
    @RollbackExecution
    fun rollback() {
        val bookCollection = mongoTemplate.getCollection("book")
        bookCollection.updateMany(
            Filters.exists("BookItems", true),
            Updates.unset("BookItems")
        )
    }
}
