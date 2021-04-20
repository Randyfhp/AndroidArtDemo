package com.example.module_base.base.app.performance

import com.github.moduth.blockcanary.BlockCanaryContext

class AppBlockCanaryContext : BlockCanaryContext() {

    /**
     * 卡顿时间阈值（ms）
     */
    override fun provideBlockThreshold(): Int {
        return super.provideBlockThreshold() + 200
    }

    override fun provideQualifier(): String {
        return super.provideQualifier()
    }
}