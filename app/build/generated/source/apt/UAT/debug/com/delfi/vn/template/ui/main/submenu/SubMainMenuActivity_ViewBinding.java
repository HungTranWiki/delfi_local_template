// Generated code from Butter Knife. Do not modify!
package com.delfi.vn.template.ui.main.submenu;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.delfi.vn.template.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class SubMainMenuActivity_ViewBinding implements Unbinder {
  private SubMainMenuActivity target;

  @UiThread
  public SubMainMenuActivity_ViewBinding(SubMainMenuActivity target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public SubMainMenuActivity_ViewBinding(SubMainMenuActivity target, View source) {
    this.target = target;

    target.recyclerView = Utils.findRequiredViewAsType(source, R.id.recyclerView, "field 'recyclerView'", RecyclerView.class);
  }

  @Override
  @CallSuper
  public void unbind() {
    SubMainMenuActivity target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.recyclerView = null;
  }
}
