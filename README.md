Tried in LockscreenActivity without success:

setShowWhenLocked(true)
setTurnScreenOn(true)

val keyguardManager = getSystemService(KEYGUARD_SERVICE) as KeyguardManager
keyguardManager.requestDismissKeyguard(this, null)
