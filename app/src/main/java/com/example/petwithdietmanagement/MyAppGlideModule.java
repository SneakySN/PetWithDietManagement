package com.example.petwithdietmanagement;

import android.content.Context;
import com.bumptech.glide.Glide;
import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.integration.okhttp3.OkHttpUrlLoader;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.module.AppGlideModule;
import com.bumptech.glide.request.RequestOptions;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.OkHttpClient;

@GlideModule
public final class MyAppGlideModule extends AppGlideModule {
    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        builder.setDefaultRequestOptions(
                new RequestOptions()
                        .placeholder(R.drawable.camera)
                        .error(R.drawable.camera)
        );
        builder.setMemoryCache(new LruResourceCache(10 * 1024 * 1024));
    }

    @Override
    public void registerComponents(Context context, Glide glide, com.bumptech.glide.Registry registry) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        registry.replace(com.bumptech.glide.load.model.GlideUrl.class, InputStream.class,
                new OkHttpUrlLoader.Factory((Call.Factory) client));
    }
}
