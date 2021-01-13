package com.example.myworld.database


class DatabaseHelperImpl(private val myWorldDB: MyWorldDB) : DatabaseHelper {
    override suspend fun insertAuth(authEntity: AuthEntity) =
        myWorldDB.authDao().insertDao(authEntity)

    override suspend fun getAuthById(userId: Int): AuthEntity? =
        myWorldDB.authDao().getAuthById(userId)

    override suspend fun getAllAuth(): List<AuthEntity> = myWorldDB.authDao().getAllAuth()
    override suspend fun updateEmailById(email: String, user_id: Int) {
        myWorldDB.authDao().updateEmailById(email, user_id)
    }

    override suspend fun updateUsernameById(username: String, user_id: Int) {
        myWorldDB.authDao().updateUsernameById(username, user_id)
    }

    override suspend fun updateProfilePictureById(profile_picture: String, user_id: Int) {
        myWorldDB.authDao().updateProfilePictureById(profile_picture, user_id)
    }

    override suspend fun updateGenderById(gender: String, user_id: Int) {
        myWorldDB.authDao().updateGenderById(gender, user_id)
    }

    override suspend fun updateBirthDateById(birth_date: String, user_id: Int) {
        myWorldDB.authDao().updateBirthDateById(birth_date, user_id)
    }
}