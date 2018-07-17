package com.example.fcl.dadademo.model

import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import com.example.fcl.dadademo.util.IAccount
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

/**
 * Created by jack on 2017/10/12.
 * Copyright  2017 jack.huang@dadaabc.com. All rights reserved.
 */
data class AccountSwitch(
    val data: List<Account>
)

data class Account(

    @Expose(serialize = false, deserialize = false)
    var isChecked: Boolean = false,
    @Expose
    @SerializedName("s_id")
    var id: Int,
    @Expose
    @SerializedName("s_mobile")
    var mobile: String,
    @Expose
    @SerializedName("s_name")
    var name: String?,
    @Expose
    @SerializedName("s_english_name")
    var englishName: String?,
    @Expose
    @SerializedName("s_sex")
    var sex: Int?,
    @Expose
    @SerializedName("s_logo")
    var logo: String?,
    @Expose
    @SerializedName("s_birthday")
    var birthday: String?,
    @Expose
    @SerializedName("s_is_call")
    var call: Int?,
    @SerializedName("s_remind")
    @Expose
    var remind: String?,
    @SerializedName("s_token")
    @Expose
    var token: String,
    @SerializedName("s_qq")
    @Expose
    var qq: String?,
    @SerializedName("s_email")
    @Expose
    var email: String?,
    @SerializedName("s_address")
    @Expose
    var address: String?,
    @SerializedName("s_local")
    @Expose
    var local: String?,
    @SerializedName("s_zipcode")
    @Expose
    var zipCode: String?,
    @SerializedName("s_is_vip")
    @Expose
    var isVip: Int?,
    @SerializedName("cookie_uid")
    @Expose
    var cookie: String,
    @SerializedName("s_coins")
    @Expose
    var coins: Int?,
    @SerializedName("s_parents")
    @Expose
    var parentsName: String?,
    @SerializedName("s_one_to_one")
    @Expose
    var oneToOne: Int?,
    @SerializedName("s_mix")
    @Expose
    var mix: String?,
    @SerializedName("is_perfect")
    @Expose
    var isPerfect: Int?
) : IAccount, Parcelable {

    fun isVip(): Boolean {
        return isVip == 1
    }

    constructor(parcel: Parcel) : this(
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeByte(if (isChecked) 1 else 0)
        parcel.writeInt(id)
        parcel.writeString(mobile)
        parcel.writeString(name)
        parcel.writeString(englishName)
        parcel.writeValue(sex)
        parcel.writeString(logo)
        parcel.writeString(birthday)
        parcel.writeValue(call)
        parcel.writeString(remind)
        parcel.writeString(token)
        parcel.writeString(qq)
        parcel.writeString(email)
        parcel.writeString(address)
        parcel.writeString(local)
        parcel.writeString(zipCode)
        parcel.writeValue(isVip)
        parcel.writeString(cookie)
        parcel.writeValue(coins)
        parcel.writeString(parentsName)
        parcel.writeValue(oneToOne)
        parcel.writeString(mix)
        parcel.writeValue(isPerfect)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Creator<Account> {
        override fun createFromParcel(parcel: Parcel): Account {
            return Account(parcel)
        }

        override fun newArray(size: Int): Array<Account?> {
            return arrayOfNulls(size)
        }
    }
}

data class AccountToken(
    @SerializedName("s_token")
    @Expose
    var token: String
)
