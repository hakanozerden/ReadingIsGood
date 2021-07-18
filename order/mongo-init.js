db.createUser(
        {
            user: "readingisgoodUser",
            pwd: "password",
            roles: [
                {
                    role: "readWrite",
                    db: "readingisgood"
                }
            ]
        }
);