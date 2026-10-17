package com.rider.voice.offline_operation.droidkaigi_gvp

enum class NetworkPolicy {
    ONLINE_ALLOWED,
    OFFLINE_REQUIRED
}

class OfflineNetworkPolicy {

    @Volatile
    private var policy = NetworkPolicy.OFFLINE_REQUIRED

    fun setPolicy(value: NetworkPolicy) {
        policy = value
    }

    fun getPolicy(): NetworkPolicy = policy

    fun isNetworkUseAllowed(): Boolean {
        return policy == NetworkPolicy.ONLINE_ALLOWED
    }

    fun requireNetworkAllowed() {
        check(isNetworkUseAllowed()) {
            "Network access is disabled by the current policy."
        }
    }
}
