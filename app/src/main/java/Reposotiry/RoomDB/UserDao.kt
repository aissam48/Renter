package Reposotiry.RoomDB

import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update


interface UserDao {


    @Query("SELECT * FROM userentity WHERE CurrentUser_ID == :CurrentUser_ID")
    fun GetUser(CurrentUser_ID:String):List<UserEntity>

    @Insert
    fun InsertUser(userEntity: UserEntity)

    @Update
    fun UpdateUser(userEntity: UserEntity)
}