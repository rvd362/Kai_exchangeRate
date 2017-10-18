package com.example.twkai.kai_exchangerate.base;

import android.app.Fragment;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Kai on 2017/06/08.
 */

public abstract class BaseFragment<F extends ViewDataBinding> extends Fragment {
    protected boolean isVisible, isPrepared;
//    protected View contentView;
    protected F dataBind;
    @Nullable
    @Override
    public final View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBind = DataBindingUtil.inflate(inflater, getContentView(), container, false);
//        contentView = inflater.inflate(getContentView(), null);
        isPrepared = true;
        init();
        onVisible();
        return dataBind.getRoot();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    private void onVisible() {
        if (!isPrepared || !isVisible) {
            return;
        }
        lazyLoad();
    }

    /**
     * fragment处于可见状态时自动调用该方法，实现懒加载，一般用作网络请求
     */
    protected abstract void lazyLoad();

    /**
     * 获取界面主布局
     * @return
     */
    protected abstract int getContentView();

    /**
     * 界面不可见时自动调用
     */
    protected void onInvisible() {
    }

    /**
     * 初始化操作
     */
    protected abstract void init();

//    /**
//     * findViewbyid的实现，用法  TextView textview = $(R.id.tv);
//     * @param id
//     * @param <T>
//     * @return
//     */
//    protected <T extends View> T $(@IdRes int id) {
//        return (T) contentView.findViewById(id);
//    }

}
