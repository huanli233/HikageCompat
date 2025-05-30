/*
 * Hikage - An Android responsive UI building tool.
 * Copyright (C) 2019 HighCapable
 * https://github.com/BetterAndroid/Hikage
 *
 * Apache License Version 2.0
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * This file is created by fankes on 2025/3/12.
 */
package com.highcapable.hikage.core.extension

import android.content.res.ColorStateList
import android.graphics.Bitmap
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import androidx.annotation.ColorRes
import androidx.annotation.DimenRes
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes
import androidx.annotation.StringRes
import com.highcapable.hikage.core.Hikage

/**
 * An extension for [Hikage] view scope to provide resources.
 */
interface ResourcesScope {

    /**
     * Get the string from [resId].
     * @param resId the resource id.
     * @param formatArgs the format arguments.
     * @return [String]
     */
    fun stringResource(@StringRes resId: Int, vararg formatArgs: Any): String

    /**
     * Get the string from [resId].
     * Alias of [stringResource].
     * @param resId the resource id.
     * @param formatArgs the format arguments.
     * @return [String]
     */
    fun stringRes(@StringRes resId: Int, vararg formatArgs: Any): String = stringResource(resId)

    /**
     * Get the color from [resId].
     * @param resId the resource id.
     * @return [Int]
     */
    fun colorResource(@ColorRes resId: Int): Int

    /**
     * Get the color from [resId].
     * Alias of [colorResource].
     * @param resId the resource id.
     * @return [Int]
     */
    fun colorRes(@ColorRes resId: Int): Int = colorResource(resId)

    /**
     * Get the [ColorStateList] from [resId].
     * @param resId the resource id.
     * @return [Int]
     */
    fun stateColorResource(@ColorRes resId: Int): ColorStateList

    /**
     * Get the [ColorStateList] from [resId].
     * Alias of [stateColorResource].
     * @param resId the resource id.
     * @return [Int]
     */
    fun stateColorRes(@ColorRes resId: Int): ColorStateList = stateColorResource(resId)

    /**
     * Get the [Drawable] from [resId].
     * @param resId the resource id.
     * @return [Drawable]
     */
    fun drawableResource(@DrawableRes resId: Int): Drawable

    /**
     * Get the [Drawable] from [resId].
     * Alias of [drawableResource].
     * @param resId the resource id.
     * @return [Drawable]
     */
    fun drawableRes(@DrawableRes resId: Int): Drawable = drawableResource(resId)

    /**
     * Get the [Bitmap] from [resId].
     * @param resId the resource id.
     * @return [Bitmap]
     */
    fun bitmapResource(@DrawableRes resId: Int): Bitmap

    /**
     * Get the [Bitmap] from [resId].
     * Alias of [bitmapResource].
     * @param resId the resource id.
     * @return [Bitmap]
     */
    fun bitmapRes(@DrawableRes resId: Int): Bitmap = bitmapResource(resId)

    /**
     * Get the dimension from [resId].
     * @param resId the resource id.
     * @return [Float]
     */
    fun dimenResource(@DimenRes resId: Int): Float

    /**
     * Get the dimension from [resId].
     * Alias of [dimenResource].
     * @param resId the resource id.
     * @return [Float]
     */
    fun dimenRes(@DimenRes resId: Int): Float = dimenResource(resId)

    /**
     * Get the font from [resId].
     * @param resId the resource id.
     * @return [Typeface] or null.
     */
    fun fontResource(@FontRes resId: Int): Typeface?

    /**
     * Get the font from [resId].
     * Alias of [fontResource].
     * @param resId the resource id.
     * @return [Typeface] or null.
     */
    fun fontRes(@FontRes resId: Int): Typeface? = fontResource(resId)
}