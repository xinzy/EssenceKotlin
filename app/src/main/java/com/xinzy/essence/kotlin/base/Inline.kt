package com.xinzy.essence.kotlin.base

import android.app.Activity
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast

/**
 * Created by Xinzy on 2017/6/14.
 */

inline fun <reified T : View> View.find(id: Int): T = findViewById(id) as T
inline fun <reified T : View> Activity.find(id: Int): T = findViewById(id) as T
inline fun <reified T : View> Fragment.find(id: Int): T = view?.findViewById(id) as T


fun Activity.dp2px(dp: Int) = (resources.displayMetrics.density * dp + 0.5f).toInt()

fun Activity.toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_LONG).show()

fun snack(view: View, msg: String) = Snackbar.make(view, msg, Snackbar.LENGTH_LONG).show()