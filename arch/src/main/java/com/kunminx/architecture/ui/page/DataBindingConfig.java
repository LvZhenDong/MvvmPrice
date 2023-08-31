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

import android.util.SparseArray;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;

public class DataBindingConfig {

  private final int layout;

  private final int vmVariableId;

  private final ViewModel stateViewModel;

  private final SparseArray<Object> bindingParams = new SparseArray<>();

  public DataBindingConfig(@NonNull Integer layout,
                           @NonNull Integer vmVariableId,
                           @NonNull ViewModel stateViewModel) {
    this.layout = layout;
    this.vmVariableId = vmVariableId;
    this.stateViewModel = stateViewModel;
  }

  public int getLayout() {
    return layout;
  }

  public int getVmVariableId() {
    return vmVariableId;
  }

  public ViewModel getStateViewModel() {
    return stateViewModel;
  }

  public SparseArray<Object> getBindingParams() {
    return bindingParams;
  }

  public DataBindingConfig addBindingParam(@NonNull Integer variableId,
                                           @NonNull Object object) {
    if (bindingParams.get(variableId) == null) {
      bindingParams.put(variableId, object);
    }
    return this;
  }
}
