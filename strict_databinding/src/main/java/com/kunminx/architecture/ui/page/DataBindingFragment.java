/*
 *
 *  * Copyright 2018-present KunMinX
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *    http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.kunminx.architecture.ui.page;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;


/**
 * Create by KunMinX at 19/7/11
 */
public abstract class DataBindingFragment<VB extends ViewDataBinding> extends Fragment {

  protected AppCompatActivity mActivity;
  private VB mBinding;

  @Override
  public void onAttach(@NonNull Context context) {
    super.onAttach(context);
    mActivity = (AppCompatActivity) context;
  }

  protected abstract void initViewModel();

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    initViewModel();
  }

  protected abstract DataBindingConfig getDataBindingConfig();

  protected VB getBinding() {
    return mBinding;
  }

  @Nullable
  @Override
  public View onCreateView(
          @NonNull LayoutInflater inflater,
          @Nullable ViewGroup container,
          @Nullable Bundle savedInstanceState
  ) {

    DataBindingConfig dataBindingConfig = getDataBindingConfig();

    VB binding = DataBindingUtil.inflate(inflater, dataBindingConfig.getLayout(), container, false);
    binding.setLifecycleOwner(getViewLifecycleOwner());
    binding.setVariable(dataBindingConfig.getVmVariableId(), dataBindingConfig.getStateViewModel());
    SparseArray<Object> bindingParams = dataBindingConfig.getBindingParams();
    for (int i = 0, length = bindingParams.size(); i < length; i++) {
      binding.setVariable(bindingParams.keyAt(i), bindingParams.valueAt(i));
    }
    mBinding = binding;
    return binding.getRoot();
  }

  @Override
  public void onDestroyView() {
    super.onDestroyView();
    mBinding.unbind();
    mBinding = null;
  }
}
