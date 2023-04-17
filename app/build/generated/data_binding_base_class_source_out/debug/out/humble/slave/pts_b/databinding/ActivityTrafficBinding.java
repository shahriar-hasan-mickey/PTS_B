// Generated by view binder compiler. Do not edit!
package humble.slave.pts_b.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import humble.slave.pts_b.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityTrafficBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final ImageView goBack;

  @NonNull
  public final RelativeLayout invisibleActionBar;

  private ActivityTrafficBinding(@NonNull LinearLayout rootView, @NonNull ImageView goBack,
      @NonNull RelativeLayout invisibleActionBar) {
    this.rootView = rootView;
    this.goBack = goBack;
    this.invisibleActionBar = invisibleActionBar;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityTrafficBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityTrafficBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_traffic, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityTrafficBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.goBack;
      ImageView goBack = ViewBindings.findChildViewById(rootView, id);
      if (goBack == null) {
        break missingId;
      }

      id = R.id.invisibleActionBar;
      RelativeLayout invisibleActionBar = ViewBindings.findChildViewById(rootView, id);
      if (invisibleActionBar == null) {
        break missingId;
      }

      return new ActivityTrafficBinding((LinearLayout) rootView, goBack, invisibleActionBar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
