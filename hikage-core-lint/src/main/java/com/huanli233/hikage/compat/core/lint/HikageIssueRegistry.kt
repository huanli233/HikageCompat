@file:Suppress("unused")

package com.huanli233.hikage.compat.core.lint

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
        HikageableBeyondScopeDetector.Companion.ISSUE,
        HikageableFunctionsDetector.Companion.ISSUE,
        HikageSafeTypeCastDetector.Companion.ISSUE,
        WidgetsUsageDetector.Companion.ISSUE
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