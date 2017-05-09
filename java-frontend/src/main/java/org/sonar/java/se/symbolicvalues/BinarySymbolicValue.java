/*
 * SonarQube Java
 * Copyright (C) 2012-2017 SonarSource SA
 * mailto:info AT sonarsource DOT com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */
package org.sonar.java.se.symbolicvalues;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import org.sonar.java.se.ProgramState;
import org.sonar.plugins.java.api.semantic.Symbol;

import javax.annotation.Nullable;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BinarySymbolicValue extends SymbolicValue {

  SymbolicValue leftOp;
  @Nullable
  Symbol leftSymbol;
  SymbolicValue rightOp;
  @Nullable
  Symbol rightSymbol;

  @Override
  public boolean references(SymbolicValue other) {
    return leftOp.equals(other) || rightOp.equals(other) || leftOp.references(other) || rightOp.references(other);
  }

  @Override
  public void computedFrom(List<SymbolicValue> symbolicValues) {
    List<ProgramState.SymbolicValueSymbol> symbolicValueSymbols = symbolicValues.stream()
      .map(sv -> new ProgramState.SymbolicValueSymbol(sv, null))
      .collect(Collectors.toList());
    computedFromSymbols(symbolicValueSymbols);
  }

  @Override
  public void computedFromSymbols(List<ProgramState.SymbolicValueSymbol> valueSymbols) {
    Preconditions.checkArgument(valueSymbols.size() == 2);
    Preconditions.checkState(leftOp == null && rightOp == null, "Operands already set!");
    rightOp = valueSymbols.get(0).symbolicValue();
    rightSymbol = valueSymbols.get(0).symbol();
    leftOp = valueSymbols.get(1).symbolicValue();
    leftSymbol = valueSymbols.get(1).symbol();
  }

  @Override
  public List<SymbolicValue> computedFrom() {
    return ImmutableList.of(leftOp, rightOp);
  }

  public SymbolicValue getLeftOp() {
    return leftOp;
  }

  public SymbolicValue getRightOp() {
    return rightOp;
  }

}
