package com.cainiao5.hym.service.repo

import android.content.Context
import androidx.annotation.Keep
import androidx.lifecycle.LiveData
import androidx.room.*

/**
 *  3.定义 database
 *
 * */
@Database(entities = [CniaoUserInfo::class],version  =1,exportSchema = false )
abstract class CniaoDatabase :RoomDatabase(){


    abstract val userDao: UserDao
    companion object{
        private const val CNIAO_DB_NAME = "cniao_db"
        @Volatile
        private var instance: CniaoDatabase?=null

       @Synchronized
       fun getInstance(context: Context): CniaoDatabase {
           //如果instance 不为空直接返回instance对象,如果instance为空创建数据库Room对象it  赋值给instance

            return  instance ?:Room.databaseBuilder(context,
                  CniaoDatabase::class.java,
                  CNIAO_DB_NAME
            )
                 .build().also {
                    instance = it }

       }
    }
}


/**
 * 1.实体类的定义；用户登录信息实体类
 *
 * */
@Entity(tableName = "tb_cniao_user")
data class CniaoUserInfo(
    @PrimaryKey //表的主键
    val id :Int,
    val course_permission: Boolean,  // 是否拥有该课程的学习权限
    val token: String?,   // 登录凭证，与用户相关的接口都需要传该token值。（PS:没有课程学习权限的用户不返回该字段）
    @Embedded //User里 的id 与 CniaoUserInfo的id 重复了,所以User里id 字段需要 取别名为cniao_user_id
    val user: User?  // 用户信息
){
    @Keep
   data class User(
        @ColumnInfo(name = "cniao_user_id")
        val id: Int,//用户id
        val logo_url: String?,//用户头像
        val username: String?,//用户名
        val reg_time: String?,//用户注册时间
        val is_bind_phone:Boolean?  // 是否已经绑定手机
    ) {}


}




/**
 * 2. 定义Dao层
 *
 * */
@Dao
interface  UserDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: CniaoUserInfo)

     @Update(onConflict = OnConflictStrategy.REPLACE)
     fun updateUser(user: CniaoUserInfo)

    @Delete
    fun deleteUser(user: CniaoUserInfo)


    // 条件的判断 = , in , like
    @Query("select * from tb_cniao_user where id=:id")
    fun queryLiveDataUser(id:Int=0): LiveData<CniaoUserInfo>


    @Query("select * from tb_cniao_user where id=:id")
    fun queryUserInfo(id: Int = 0): CniaoUserInfo?
}









