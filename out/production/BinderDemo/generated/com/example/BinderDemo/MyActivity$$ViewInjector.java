// Generated code from Butter Knife. Do not modify!
package com.example.BinderDemo;

import android.view.View;
import butterknife.ButterKnife.Finder;

public class MyActivity$$ViewInjector {
  public static void inject(Finder finder, final com.example.BinderDemo.MyActivity target, Object source) {
    View view;
    view = finder.findById(source, 2131034112);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131034112' for field 'mStart' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mStart = (android.widget.Button) view;
    view = finder.findById(source, 2131034114);
    if (view == null) {
      throw new IllegalStateException("Required view with id '2131034114' for field 'mStop' was not found. If this view is optional add '@Optional' annotation.");
    }
    target.mStop = (android.widget.Button) view;
  }

  public static void reset(com.example.BinderDemo.MyActivity target) {
    target.mStart = null;
    target.mStop = null;
  }
}
