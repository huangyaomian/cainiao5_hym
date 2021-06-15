package com.cainiao5.hym.common

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.cainiao5.hym.common.db.*


class MainActivity : AppCompatActivity() {

    private val tvInsert: TextView by lazy { findViewById(R.id.tv_insert_room) }
    private val tvDelete: TextView by lazy { findViewById(R.id.tv_delete_room) }
    private val tvUpdate: TextView by lazy { findViewById(R.id.tv_update_room) }
    private val tvQueryID: TextView by lazy { findViewById(R.id.tv_query_id_room) }
    private val tvSize: TextView by lazy { findViewById(R.id.tv_size_room) }
    private val tvAll: TextView by lazy { findViewById(R.id.tv_all_room) }

    private var instance: UserDatabase? = null
    private var userDao: UserDao? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        instance = UserDatabase.getInstance(this)
        deleteDatabase(UserDatabase.DB_NAME)//测试时每次都删除表
        userDao = instance?.userDao
    }

    fun insert(view: View?) {
        val sb = StringBuilder()
        val users = arrayListOf<DbUser>()
        var user: DbUser
        for (i in 0..4) {
            user = DbUser()
            user.age = 20 + i
            user.city = "北京 $i"
            user.name = "小明 $i"
            user.isSingle = i % 2 == 0
            userDao?.insertBook(Book(i,"红楼梦 $i", 78.0 + i))
            users.add(user)
            sb.appendLine(user.toString())
        }

        userDao?.insertJUser(JUser(30))

        val rowIds = userDao?.insertAll(*(users.toTypedArray()))
        sb.append("rawId = ${rowIds.toString()}")
        tvInsert.text = sb.toString()
        getAll()
    }

    private fun getAll() {
        val all = userDao!!.getAll() ?: return
        val sb = java.lang.StringBuilder()
        all.forEach { user ->
            sb.append("uid: ${user?.uid}")
                .append("姓名： ${user?.name}")
                .append("年龄： ${user?.age}")
                .append("城市： ${user?.city}")
                .append("body： ${user?.body.toString()}")
                .append("书籍： ${user?.bookId.toString()}")
                .append("single： ${user?.isSingle}")
                .append("\n")
        }
        val text = "All Size: ${all.size}"
        tvSize.text = text
        userDao?.queryJuser()?.apply {
            sb.appendLine("JUser num $size ${joinToString { it.toString() }}")
        }

        userDao?.queryUserBook()?.apply {
            sb.appendLine("book num $size ${joinToString { it.toString() }}")
        }
        tvAll.text = sb.toString()
    }

    fun delete(view:View?){
        val user = userDao!!.findByName("小明 " + 3,23 )
        if (user != null) {
            userDao!!.delete(user)
        }

        tvDelete.text = user.toString()
        getAll()
    }

    fun update(view:View?){
        val user = userDao!!.findByName("小明 " + 2, 22) ?: return
        user.age = 33
        user.city = "上海"
        user.name = "zhangsan"
        user.isSingle = true
        userDao!!.update(user)
        tvUpdate.text = user.toString()
        getAll()
    }

    fun queryId(view : View){
        val userById = userDao!!.getUserById(3)
        if (userById != null){
            tvQueryID.text = userById.toString()
        }else{
            Toast.makeText(this,"id=3 de user not find ", Toast.LENGTH_LONG).show()
        }
        getAll()
    }

    fun queryAll(view: View?){
        getAll()
    }
}
