package com.example.demo.migration

// @ChangeUnit(id = "book-initializer", order = "1", author = "zhenjia")
// class BookInitializerChangeUnit(
//    private val mongoTemplate: MongoTemplate
// ) {
//
//    @BeforeExecution
//    fun beforeExecution(mongoTemplate: MongoTemplate) {
//        mongoTemplate.createCollection("BookDatabase")
//    }
//
//    @RollbackBeforeExecution
//    fun rollbackBeforeExecution(mongoTemplate: MongoTemplate) {
//        mongoTemplate.dropCollection("BookDatabase")
//    }
//
//    /** This is the method with the migration code  */
//    @Execution
//    fun changeSet() {
//        val jsonFile = ResourceUtils
//            .getFile("data/System Design Interview.json")
//            .toPath()
//        Files.readString(jsonFile).forEach { it ->
//            mongoTemplate.save(it, "Book")
//        }
//    }
//
//    /**
//     * This method is mandatory even when transactions are enabled.
//     * They are used in the undo operation and any other scenario where transactions are not an option.
//     * However, note that when transactions are avialble and Mongock need to rollback, this method is ignored.
//     */
//    @RollbackExecution
//    fun rollback() {
//        mongoTemplate.dropCollection("Book")
//    }
// }
