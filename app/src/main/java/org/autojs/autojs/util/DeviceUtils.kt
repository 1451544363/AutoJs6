package org.autojs.autojs.util

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.telephony.TelephonyManager
import androidx.annotation.IntRange
import org.autojs.autojs6.R
import java.util.Arrays

object DeviceUtils {

    @JvmStatic
    fun getDeviceSummary(context: Context) = DeviceInfo(context).toString()

    fun getDeviceSummaryWithSimpleAppInfo(context: Context) = DeviceInfo(context).toStringWithSimpleAppInfo()

    // @Reference to com.heinrichreimersoftware.androidissuereporter.model.DeviceInfo
    //  ! https://github.com/heinrichreimer/android-issue-reporter/blob/master/library/src/main/java/com/heinrichreimersoftware/androidissuereporter/model/DeviceInfo.java
    private class DeviceInfo(private val context: Context) {

        private val versionCode: Int
        private val versionName: String?
        private val buildVersion = Build.VERSION.INCREMENTAL
        private val releaseVersion = Build.VERSION.RELEASE

        @IntRange(from = 0)
        private val sdkVersion = Build.VERSION.SDK_INT
        private val buildID = Build.DISPLAY
        private val brand = Build.BRAND
        private val manufacturer = Build.MANUFACTURER
        private val device = Build.DEVICE
        private val model = Build.MODEL
        private val product = Build.PRODUCT
        private val hardware = Build.HARDWARE

        private val abis = Build.SUPPORTED_ABIS
        private val abis32Bits = Build.SUPPORTED_32_BIT_ABIS
        private val abis64Bits = Build.SUPPORTED_64_BIT_ABIS

        init {
            @Suppress("DEPRECATION")
            try {
                context.packageManager.getPackageInfo(context.packageName, 0)
            } catch (e: PackageManager.NameNotFoundException) {
                null
            }.let {
                versionCode = it?.versionCode ?: -1
                versionName = it?.versionName
            }
        }

        override fun toString() = """
            ${context.getString(R.string.text_android_build_version)}: $buildVersion
            ${context.getString(R.string.text_android_release_version)}: $releaseVersion
            ${context.getString(R.string.text_android_sdk_version)}: $sdkVersion
            ${context.getString(R.string.text_android_build_id)}: $buildID
            ${context.getString(R.string.text_device_brand)}: $brand
            ${context.getString(R.string.text_device_manufacturer)}: $manufacturer
            ${context.getString(R.string.text_device_name)}: $device
            ${context.getString(R.string.text_device_model)}: $model
            ${context.getString(R.string.text_device_product_name)}: $product
            ${context.getString(R.string.text_device_hardware_name)}: $hardware
            ${context.getString(R.string.text_device_hardware_serial_number)}: ${getSerial()}
            ${context.getString(R.string.text_device_imei)}: ${getIMEI(context)}
            ${context.getString(R.string.text_abis)}: ${Arrays.toString(abis)}
            ${context.getString(R.string.text_abis_32bit)}: ${Arrays.toString(abis32Bits)}
            ${context.getString(R.string.text_abis_64bit)}: ${Arrays.toString(abis64Bits)}
            """.trimIndent()

        fun toStringWithSimpleAppInfo() = """
            ${context.getString(R.string.text_app_version_name)}: $versionName
            ${context.getString(R.string.text_app_version_code)}: $versionCode
        """.trimIndent() + "\n" + toString()
    }

    @JvmStatic
    @Suppress("DEPRECATION")
    @SuppressLint("HardwareIds")
    fun getSerial() = try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) Build.getSerial() else Build.SERIAL
    } catch (e: SecurityException) {
        null
    }

    @JvmStatic
    @Suppress("DEPRECATION")
    @SuppressLint("HardwareIds")
    fun getIMEI(context: Context) = try {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) telephonyManager.imei else telephonyManager.deviceId
    } catch (e: SecurityException) {
        null
    }

}