package com.cainiao5.hym.common.db

import androidx.room.*

/**
 * author: huangyaomian
 * created on: 2021/6/14 2:13 下午
 * description:demo演示的dao接口文件
 */

@Dao
interface UserDao{
    //查询所有数据，若返回livedata则为 livedata<list<dbuser>>
    @Query(value = "select * from db_user")
    fun getAll() : List<DbUser?>?

    @Query(value = "select * from db_user where uid in (:userIds)")
    fun loadAllByIds(userIds: IntArray?): List<DbUser?>?

    @Query(value = "select * from db_user where uname like :name and age like :age limit 1")
    fun findByName(name: String?, age: Int):DbUser?

    @Query(value = "select * from db_user where uid like :id")
    fun getUserById(id:Int):DbUser?

    @Insert
    fun insertAll(vararg users:DbUser?)//支持可变参数

    @Delete
    fun delete(user:DbUser) //删除制定的user

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(user: DbUser)//更新，若出现冲突，则使用替换策略，还有其他策略可以选择

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertJUser(user:JUser)

    /**
     * 查询表tb_juser的数据,返回集合
     * */
    @Query("select * from tb_juser")
    fun queryJuser(): List<JUser>

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertBook(book: Book)

    /**
     *  表tb_juser插入实体对象JUser
     * */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertJuser(user: JUser)

    @Query("select * from book")
    fun getBook():List<Book>

    @Query(value = "select * from tempBean")
    fun queryUserBook():List<TempBean?>?

}