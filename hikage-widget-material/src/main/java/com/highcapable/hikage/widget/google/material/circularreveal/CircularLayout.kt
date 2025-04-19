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
 * This file is created by fankes on 2025/3/3.
 */
@file:Suppress("unused")
@file:JvmName("CircularRevealLayoutDeclaration")

package com.highcapable.hikage.widget.google.material.circularreveal

import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.circularreveal.CircularRevealFrameLayout
import com.google.android.material.circularreveal.CircularRevealGridLayout
import com.google.android.material.circularreveal.CircularRevealLinearLayout
import com.google.android.material.circularreveal.CircularRevealRelativeLayout
import com.google.android.material.circularreveal.cardview.CircularRevealCardView
import com.google.android.material.circularreveal.coordinatorlayout.CircularRevealCoordinatorLayout
import com.highcapable.hikage.annotation.HikageViewDeclaration

@HikageViewDeclaration(CircularRevealGridLayout::class, GridLayout.LayoutParams::class)
private object CircularRevealGridLayoutDeclaration

@HikageViewDeclaration(CircularRevealRelativeLayout::class, RelativeLayout.LayoutParams::class)
private object CircularRevealRelativeLayoutDeclaration

@HikageViewDeclaration(CircularRevealFrameLayout::class, FrameLayout.LayoutParams::class)
private object CircularRevealFrameLayoutDeclaration

@HikageViewDeclaration(CircularRevealLinearLayout::class, LinearLayout.LayoutParams::class)
private object CircularRevealLinearLayoutDeclaration

@HikageViewDeclaration(CircularRevealCoordinatorLayout::class, CoordinatorLayout.LayoutParams::class)
private object CircularRevealCoordinatorLayoutDeclaration

@HikageViewDeclaration(CircularRevealCardView::class, FrameLayout.LayoutParams::class)
private object CircularRevealCardViewDeclaration