# Glide 모듈 유지
-keep public class * extends com.bumptech.glide.module.AppGlideModule
-keep class com.bumptech.glide.GeneratedAppGlideModuleImpl

# Firebase In-App Messaging
-keep class com.google.firebase.inappmessaging.** { *; }

# Android 기본 ProGuard 규칙
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider

# 모든 public 클래스 및 멤버를 유지
-keep public class * {
    public *;
}

# 모든 public 인터페이스 및 멤버를 유지
-keep public interface * {
    public *;
}

# 안드로이드 X 지원 라이브러리
-dontwarn android.support.**
-dontwarn androidx.**
-keep class android.support.** { *; }
-keep class androidx.** { *; }
