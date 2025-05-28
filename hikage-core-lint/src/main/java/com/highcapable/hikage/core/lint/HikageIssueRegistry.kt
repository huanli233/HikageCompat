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
 * This file is created by fankes on 2025/3/14.
 */
@file:Suppress("unused")

package com.highcapable.hikage.core.lint

import com.android.tools.lint.client.api.IssueRegistry
import com.android.tools.lint.client.api.Vendor
import com.android.tools.lint.detector.api.CURRENT_API
import com.highcapable.hikage.core.lint.detector.HikageSafeTypeCastDetector
import com.highcapable.hikage.core.lint.detector.HikageableBeyondScopeDetector
import com.highcapable.hikage.core.lint.detector.HikageableFunctionsDetector
import com.highcapable.hikage.core.lint.detector.WidgetsUsageDetector
import com.huanli233.hikage.compat.generated.HikageCoreLintProperties

class HikageIssueRegistry : IssueRegistry() {

    override val issues get() = listOf(
        HikageableBeyondScopeDetector.ISSUE,
        HikageableFunctionsDetector.ISSUE,
        HikageSafeTypeCastDetector.ISSUE,
        WidgetsUsageDetector.ISSUE
    )

    override val minApi = HikageCoreLintProperties.PROJECT_HIKAGE_CORE_LINT_MIN_API
    override val api = CURRENT_API
    override val vendor = Vendor(
        vendorName = HikageCoreLintProperties.PROJECT_NAME,
        identifier = HikageCoreLintProperties.PROJECT_HIKAGE_CORE_LINT_IDENTIFIER,
        feedbackUrl = "${HikageCoreLintProperties.PROJECT_URL}/issues",
        contact = HikageCoreLintProperties.PROJECT_URL
    )
}