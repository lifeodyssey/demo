db.auth('qa_mongo_username', 'qa_mongo_pwd')

db = db.getSiblingDB('book')

db.createUser({
    user: 'bookadmin',
    pwd: 'bookpwd',
    roles: [
        {
            role: 'root',
            db: 'book',
        },
    ],
});