package Reposotiry.RoomDB

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class UserEntity(
    @PrimaryKey val CurrentUser_ID :String,
    @ColumnInfo (name = "FirstName") var FirstName:String,
    @ColumnInfo (name = "LastName") var LastName :String,
    @ColumnInfo (name = "PhoneNumber") var PhoneNumber:String,
    @ColumnInfo (name = "Sex") val Sex:String,
    @ColumnInfo (name = "ProfileImage") var ProfileImage:String

) {
}