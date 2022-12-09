conn = new Mongo();
db = conn.getDB("bookDatabase");

db.bookDatabase.createIndex({ "uuid": 1}, { unique: false });

//TODO:could not initiate db and user in the beginning