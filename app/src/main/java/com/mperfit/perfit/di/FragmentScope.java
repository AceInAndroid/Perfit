package com.mperfit.perfit.di;

import java.lang.annotation.Retention;

import javax.inject.Scope;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Created by zhangbing on 16/8/7.
 */

@Scope
@Retention(RUNTIME)
public @interface FragmentScope {
}
