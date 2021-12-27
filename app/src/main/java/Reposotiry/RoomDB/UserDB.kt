package Reposotiry.RoomDB

import androidx.room.Database
import androidx.room.RoomDatabase


@Database(entities = [UserEntity::class], version = 1)

abstract class UserDB : RoomDatabase(){
    abstract fun userDoa() : UserDao

}