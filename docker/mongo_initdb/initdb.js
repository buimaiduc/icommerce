db = db.getSiblingDB('db');
db.createUser(
    {
      user: "user",
      pwd: "root",
      roles: [ { role: "dbOwner", db: "test" } ]
    }
);
