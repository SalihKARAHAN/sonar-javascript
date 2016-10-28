/*
 * SonarQube JavaScript Plugin
 * Copyright (C) 2011-2016 SonarSource SA
 * mailto:contact AT sonarsource DOT com
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
package org.sonar.javascript.checks.verifier;

import com.google.common.base.Charsets;
import com.google.common.base.Throwables;
import com.sonar.sslr.api.typed.ActionParser;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.sonar.api.batch.fs.InputFile;
import org.sonar.api.config.MapSettings;
import org.sonar.api.config.Settings;
import org.sonar.javascript.visitors.JavaScriptVisitorContext;
import org.sonar.javascript.parser.JavaScriptParserBuilder;
import org.sonar.javascript.tree.symbols.type.JQuery;
import org.sonar.plugins.javascript.api.tree.ScriptTree;
import org.sonar.plugins.javascript.api.tree.Tree;

class TestUtils {

  protected static final ActionParser<Tree> p = JavaScriptParserBuilder.createParser(Charsets.UTF_8);

  private TestUtils() {
  }

  public static JavaScriptVisitorContext createContext(InputFile file) {
    try {
      ScriptTree scriptTree = (ScriptTree) p.parse(file.contents());
      return new JavaScriptVisitorContext(scriptTree, file, settings());
    } catch (IOException e) {
      throw Throwables.propagate(e);
    }
  }

  private static Settings settings() {
    Settings settings = new MapSettings();

    Map<String, String> properties = new HashMap<>();
    properties.put(JQuery.JQUERY_OBJECT_ALIASES, JQuery.JQUERY_OBJECT_ALIASES_DEFAULT_VALUE);
    settings.addProperties(properties);

    return settings;
  }

}
