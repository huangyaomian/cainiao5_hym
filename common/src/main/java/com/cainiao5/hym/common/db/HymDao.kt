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

}