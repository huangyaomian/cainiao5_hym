package com.cainiao5.hym.common.db

import androidx.room.*
import androidx.room.ForeignKey.SET_DEFAULT

/**
 * author: huangyaomian
 * created on: 2021/6/14 2:02 下午
 * description:临时演示的demo的entity
 */
@Entity(tableName = "db_user"
    ,foreignKeys = [ForeignKey(
    entity = Book::class,
    parentColumns = ["bid"],
    childColumns = ["bookId"],
    onDelete = SET_DEFAULT
)]
    ,indices = [Index("uid"), Index("bookId")]) //room数据库的注解标志，数据表entity （tableName="db_user",indices = {@Index(value= "uname",unique=true)})
class DbUser {
    @PrimaryKey(autoGenerate = true)
    var uid = 0

    @ColumnInfo(name = "uname")
    var name: String? = null

    @ColumnInfo
    var city: String? = null

    @ColumnInfo
    var age = 0

    //如此数据表中不会有@Ignore标记的属性字段
    @Ignore
    var isSingle = false

    @Embedded
    var body: Child? = null //孩子

    var bookId:Int = 0

    override fun toString(): String {
        return "DbUser{" +
                "uid = $uid" +
                ", name = $name" +
                ", city = $city" +
                ", age = $age" +
                ", single = $isSingle" +
                ", body = $body" +
                "}"

    }
}

data class Child (
    val cid: Int,
    val cname: String,
    val cAge: Int,
    val sex: Int
)

@Entity
data class Book(
    @PrimaryKey
    val bid:Int,
    val name:String,
    val price: Double
)

/**
 * 这个注解，表明该数据类是sql的执行结果数据，可用于其他的dao操作，用于class较为合适，而不是其他 class
 * you can select from a databaseview similar to an entity,but you can not insert, delete or update into a
 * databaseview
 */
@DatabaseView("select uname,name from db_user,book where uid=3 or bookId=3",viewName = "tempBean")
class TempBean {
    var uname = ""
    var name = ""

    override fun toString(): String {
        return "TempBean(uname = $uname, name = $name)"
    }
}