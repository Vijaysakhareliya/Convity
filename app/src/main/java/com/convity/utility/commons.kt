package com.convity.utility

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.util.Patterns
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.convity.BuildConfig
import com.convity.custom.SafeClickListener
import com.convity.presentation.base.BaseActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import java.io.InputStream
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*


fun View.gone() {
    this.visibility = View.GONE
}

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.invisible() {
    this.visibility = View.INVISIBLE
}

fun Context.toast(message: CharSequence): Toast = Toast
    .makeText(this, message, Toast.LENGTH_SHORT)
    .apply {
        show()
    }

fun Context.toast(message: Int): Toast = toast(getStringValue(message))

fun View.snackbar(message: CharSequence) = Snackbar
    .make(this, message, Snackbar.LENGTH_SHORT)
    .apply { show() }

/**
 * For show dialog
 *
 * @param title - title which shown in dialog (application name)
 * @param msg - message which shown in dialog
 * @param positiveText - positive button text
 * @param listener - positive button listener
 * @param negativeText - negative button text
 * @param negativeListener - negative button listener
 * @param icon - drawable icon which shown is dialog
 */
fun Context.showDialog(
    title: String? = "App",
    msg: String,
    positiveText: String? = "OK",
    listener: DialogInterface.OnClickListener? = null,
    negativeText: String? = "Cancel",
    negativeListener: DialogInterface.OnClickListener? = null,
    icon: Int? = null,
) {
    if (BaseActivity.dialogShowing) {
        return
    }
    val builder = AlertDialog.Builder(this)
    builder.setTitle(title)
    builder.setMessage(msg)
    builder.setCancelable(false)
    builder.setPositiveButton(positiveText) { dialog, which ->
        BaseActivity.dialogShowing = false
        listener?.onClick(dialog, which)
    }
    if (negativeListener != null) {
        builder.setNegativeButton(negativeText) { dialog, which ->
            BaseActivity.dialogShowing = false
            negativeListener.onClick(dialog, which)
        }
    }
    if (icon != null) {
        builder.setIcon(icon)
    }
    builder.create().show()
    BaseActivity.dialogShowing = true
}

/**
 * For validate email-id
 *
 * @return email-id is valid or not
 */
fun String.isValidEmail(): Boolean {
    return !TextUtils.isEmpty(this) && Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

/**
 * For validate phone
 *
 * @return phone is valid or not
 */
fun String.isValidPhone(): Boolean {
    return !TextUtils.isEmpty(this) && Patterns.PHONE.matcher(this).matches()
}

/**
 * isNetworkAvailable - Check if there is a NetworkConnection
 * @return boolean
 */
fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val networkInfo = connectivityManager.activeNetworkInfo
    return networkInfo != null && networkInfo.isConnected
}

/**
 * compare string
 * @return boolean
 */
infix fun String.shouldBeSame(other: String) = this == other

/**
 * For launch other activity
 */
inline fun <reified T : Any> Activity.launchActivity(
    requestCode: Int = -1,
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {},
) {

    val intent = newIntent<T>(this)
    intent.init()
    startActivityForResult(intent, requestCode, options)
}

inline fun <reified T : Any> Context.launchActivity(
    options: Bundle? = null,
    noinline init: Intent.() -> Unit = {},
) {

    val intent = newIntent<T>(this)
    intent.init()
    startActivity(intent, options)
}

inline fun <reified T : Any> newIntent(context: Context): Intent =
    Intent(context, T::class.java)


/**
 * Get String from strings.xml file using resource
 */
fun Context.getStringValue(resourceId: Int): String {
    return resources.getString(resourceId)
}

/**
 * load image
 */
fun Context.loadImage(imagePath: String, imageView: AppCompatImageView) {
    Glide.with(this)
        .load(imagePath)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(imageView)
}

fun Context.loadRoundedCornerImage(imagePath: String, imageView: AppCompatImageView) {
    val requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .priority(Priority.HIGH)

    Glide.with(this).load(imagePath)
        .transform(CenterCrop(), RoundedCorners(10))
        .apply(requestOptions)
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(imageView)
}

/**
 * path from resources
 */
fun pathFromResources(resourceId: Int): String {
    return "android.resource://${BuildConfig.APPLICATION_ID}/$resourceId"
}

/**
 * prevent double click
 */
fun View.setOnSafeClickListener(
    onSafeClick: (View) -> Unit,
) {
    setOnClickListener(SafeClickListener { v ->
        onSafeClick(v)
    })
}

/**
 * for check string
 */
fun checkStringValue(text: String?): Boolean {
    return !(text == null || text.trim { it <= ' ' } == "null" || text.trim { it <= ' ' }.isEmpty())
}

/**
 * for return string with suggested value
 */
fun checkStringReturnValue(text: String?, stReturnString: String = ""): String {
    return if (text == null || text.trim { it <= ' ' } == "null" || text.trim { it <= ' ' }
            .isEmpty()) {
        stReturnString
    } else {
        text
    }
}

/**
 * value set in decimal format
 * like 1.1111 to 1.11
 */
fun setValueInDecimalFormat(value: Double): String {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.CEILING
    return df.format(value)
}

/**
 * convert object to string
 */
fun convertObjectToString(value: Any): String {
    return Gson().toJson(value)
}

/**
 * get Unique id from Android phone
 */
fun Context.getUniqueId(): String {
    return Settings.Secure.getString(contentResolver, Settings.Secure.ANDROID_ID)
}

fun getCurrentTime(): Long {
    val calendar = Calendar.getInstance()
    return calendar.timeInMillis
}

fun Context.readJSONFromAsset(): String? {
    var json: String? = null
    try {
        val inputStream: InputStream = this.assets.open("selector.json")
        json = inputStream.bufferedReader().use { it.readText() }
    } catch (ex: Exception) {
        ex.printStackTrace()
        return null
    }
    return json
}

fun Activity.setWindowFlag(bits: Int, on: Boolean) {
    val win: Window = window
    val winParams: WindowManager.LayoutParams = win.attributes
    if (on) {
        winParams.flags = winParams.flags or bits
    } else {
        winParams.flags = winParams.flags and bits.inv()
    }
    win.attributes = winParams
}

fun AppCompatTextView.makeLinks(
    underline: Boolean,
    vararg links: Pair<String, View.OnClickListener>
) {
    val spannableString = SpannableString(this.text)
    var startIndexOfLink = -1
    for (link in links) {
        val clickableSpan = object : ClickableSpan() {
            override fun updateDrawState(textPaint: TextPaint) {
                textPaint.color = textPaint.linkColor
                textPaint.isUnderlineText = underline
            }

            override fun onClick(view: View) {
                Selection.setSelection((view as TextView).text as Spannable, 0)
                view.invalidate()
                link.second.onClick(view)
            }
        }
        startIndexOfLink = this.text.toString().indexOf(link.first, startIndexOfLink + 1)
        spannableString.setSpan(
            clickableSpan, startIndexOfLink, startIndexOfLink + link.first.length,
            Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
        )
    }
    this.movementMethod =
        LinkMovementMethod.getInstance() // without LinkMovementMethod, link can not click
    this.setText(spannableString, TextView.BufferType.SPANNABLE)
}

fun validatePassword(password: String): Boolean {
    val passwordPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20})";
    return passwordPattern.matches(password.toRegex())
}

fun Activity.checkPermission(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        Environment.isExternalStorageManager()
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        val result = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.READ_EXTERNAL_STORAGE
        )
        val result1 = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
        result == PackageManager.PERMISSION_GRANTED && result1 == PackageManager.PERMISSION_GRANTED
    } else {
        true
    }
}

fun Activity.requestPermission() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        try {
            val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
            intent.addCategory("android.intent.category.DEFAULT")
            intent.data =
                Uri.parse(String.format("package:%s", applicationContext.packageName))
            startActivityForResult(intent, 2296)
        } catch (e: java.lang.Exception) {
            val intent = Intent()
            intent.action = Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION
            startActivityForResult(intent, 2296)
        }
    } else {
        //below android 11
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            1
        )
    }
}