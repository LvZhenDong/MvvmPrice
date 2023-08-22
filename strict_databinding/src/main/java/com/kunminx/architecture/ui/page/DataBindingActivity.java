package com.kunminx.architecture.ui.page;

import android.os.Bundle;
import android.util.SparseArray;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

public abstract class DataBindingActivity<VB extends ViewDataBinding> extends AppCompatActivity {

  private VB mBinding;

  protected abstract void initViewModel();

  protected abstract DataBindingConfig getDataBindingConfig();

  protected VB getBinding() {
    return mBinding;
  }

  @Override
  protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initViewModel();
    DataBindingConfig dataBindingConfig = getDataBindingConfig();

    VB binding = DataBindingUtil.setContentView(this, dataBindingConfig.getLayout());
    binding.setLifecycleOwner(this);
    binding.setVariable(dataBindingConfig.getVmVariableId(), dataBindingConfig.getStateViewModel());
    SparseArray<Object> bindingParams = dataBindingConfig.getBindingParams();
    for (int i = 0, length = bindingParams.size(); i < length; i++) {
      binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i));
    }
    mBinding = binding;
  }

  @Override
  protected void onDestroy() {
    super.onDestroy();
    mBinding.unbind();
    mBinding = null;
  }
}
