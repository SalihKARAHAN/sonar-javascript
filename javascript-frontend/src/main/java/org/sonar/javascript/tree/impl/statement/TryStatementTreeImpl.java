/*
 * SonarQube JavaScript Plugin
 * Copyright (C) 2011-2017 SonarSource SA
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
package org.sonar.javascript.tree.impl.statement;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterators;
import java.util.Iterator;
import javax.annotation.Nullable;
import org.sonar.javascript.tree.impl.JavaScriptTree;
import org.sonar.javascript.tree.impl.lexical.InternalSyntaxToken;
import org.sonar.plugins.javascript.api.tree.Tree;
import org.sonar.plugins.javascript.api.tree.lexical.SyntaxToken;
import org.sonar.plugins.javascript.api.tree.statement.BlockTree;
import org.sonar.plugins.javascript.api.tree.statement.CatchBlockTree;
import org.sonar.plugins.javascript.api.tree.statement.TryStatementTree;
import org.sonar.plugins.javascript.api.visitors.DoubleDispatchVisitor;

public class TryStatementTreeImpl extends JavaScriptTree implements TryStatementTree {

  private SyntaxToken tryKeyword;
  private BlockTree block;
  @Nullable
  private CatchBlockTree catchBlock;
  @Nullable
  private SyntaxToken finallyKeyword;
  @Nullable
  private BlockTree finallyBlock;


  public TryStatementTreeImpl(CatchBlockTreeImpl catchBlock) {
    this.catchBlock = catchBlock;

  }

  public TryStatementTreeImpl(InternalSyntaxToken finallyKeyword, BlockTreeImpl finallyBlock) {
    this.finallyKeyword = finallyKeyword;
    this.finallyBlock = finallyBlock;

  }

  public TryStatementTreeImpl complete(CatchBlockTreeImpl catchBlock) {
    Preconditions.checkState(this.catchBlock == null, "Catch block already completed");
    this.catchBlock = catchBlock;

    return this;
  }

  public TryStatementTreeImpl complete(InternalSyntaxToken tryKeyword, BlockTreeImpl block) {
    Preconditions.checkState(this.tryKeyword == null, "Already completed");
    this.tryKeyword = tryKeyword;
    this.block = block;

    return this;
  }

  @Override
  public Kind getKind() {
    return Kind.TRY_STATEMENT;
  }

  @Override
  public SyntaxToken tryKeyword() {
    return tryKeyword;
  }

  @Override
  public BlockTree block() {
    return block;
  }

  @Nullable
  @Override
  public CatchBlockTree catchBlock() {
    return catchBlock;
  }

  @Nullable
  @Override
  public SyntaxToken finallyKeyword() {
    return finallyKeyword;
  }

  @Nullable
  @Override
  public BlockTree finallyBlock() {
    return finallyBlock;
  }

  @Override
  public Iterator<Tree> childrenIterator() {
    return Iterators.forArray(
      tryKeyword,
      block,
      catchBlock,
      finallyKeyword,
      finallyBlock);
  }

  @Override
  public void accept(DoubleDispatchVisitor visitor) {
    visitor.visitTryStatement(this);
  }
}
