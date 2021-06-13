package com.hym.netdemo.model

/**
 * author: huangyaomian
 * created on: 2021/6/12 9:48 下午
 * description:数据响应的封装类，密封类sealed 其实可以使用kotlin标准库中的Result
 */
//region 第一种形式的封装
sealed class DataResult<out R> {
    //成功状态的的时候
    data class Success<out T>(val data:T): DataResult<T>()

    //错误，失败的时候
    data class Error(val exception:Exception): DataResult<Nothing>()

    //加载数据中
    object Loading : DataResult<Nothing>()

    override fun toString(): String {
        return when(this){
            is Success<*> -> "Success[data=$data]"
            is Error -> "Error [exception=$exception]"
            Loading -> "Loading"
        }
    }
}

/**
 * 返回结果如果是Success类，且data非null，才认为是成功的。
 */
val DataResult<*>.succeeded: Boolean
    get() = this is DataResult.Success && data != null

//endregion


//region 第二种形式的封装：Resource 形式 数据封装

//资源获取状态
enum class Status {
    SUCCESS,
    ERROR,
    LOADING
}

/**
 * 数据封装
 */
data class Resource<out T>(val status:Status, val data: T, val message: String?){
    companion object{
        fun <T> success(data: T?): Resource<T?> {
            return Resource(Status.SUCCESS,data,"Resource Success")
        }

        fun <T> error(msg: String, data: T?):Resource<T?>{
            return Resource(Status.ERROR,data,msg)
        }

        fun <T> loading(data: T?): Resource<T?>{
            return Resource(Status.LOADING,data,null)
        }


    }
}

//endregion