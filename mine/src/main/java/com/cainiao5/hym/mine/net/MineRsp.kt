package com.cainiao5.hym.mine.net

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.Parcelize
/**
 *  des: 用户信息bean
 * */
@Keep
@Parcelize
data class UserInfoRsp(
    var addr: String?,
    var alipay: String?,
    var channel: String?,
    var city: String?,
    var company: String?,
    var confirmed: Boolean,
    var desc: String?,
    var device: String?,
    var education: String?,
    var email: String?,
    var focus_it: String?,
    var follower_count: Int,
    var following_count: Int,
    var form_id: Long,
    var form_id_time: String?,
    var id: Long,
    var invite_code: String?,
    var inviter_id: Long,
    var is_teacher: Boolean,
    var job: String?,
    var label: String?,
    var login_time: String?,
    var logo_url: String?,
    var major: String?,
    var mobi: String?,
    var province: String?,
    var qq: String?,
    var question_collect_qa: List<String>?,
    var real_name: String?,
    var reg_app: String?,
    var reg_time: String?,
    var school: String?,
    var unique_code: String?,
    var username: String?,
    var wechat_id: String?,
    var work_years: String?
) : Parcelable