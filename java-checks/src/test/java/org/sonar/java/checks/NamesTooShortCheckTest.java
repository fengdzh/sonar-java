/*
 * SonarQube Java
 * Copyright (C) 2012 SonarSource
 * dev@sonar.codehaus.org
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
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.java.checks;

import com.sonar.sslr.squid.checks.CheckMessagesVerifierRule;
import org.junit.Rule;
import org.junit.Test;
import org.sonar.java.JavaAstScanner;
import org.sonar.squid.api.SourceFile;

import java.io.File;

public class NamesTooShortCheckTest {

  @Rule
  public CheckMessagesVerifierRule checkMessagesVerifier = new CheckMessagesVerifierRule();

  @Test
  public void detected() {
    SourceFile file = JavaAstScanner.scanSingleFile(new File("src/test/files/checks/NamesTooShortCheck.java"), new NamesTooShortCheck());
    checkMessagesVerifier.verify(file.getCheckMessages())
        .next().atLine(1).withMessage("Rename 'A' to a meaningful name of at least 3 characters.")
        .next().atLine(7).withMessage("Rename 'fo' to a meaningful name of at least 3 characters.")
        .next().atLine(26)
        .next().atLine(27)
        .next().atLine(30)
        .next().atLine(37)
        .next().atLine(38)
        .next().atLine(43).withMessage("Rename 'a' to a meaningful name of at least 3 characters.")
        .next().atLine(43).withMessage("Rename 'b' to a meaningful name of at least 3 characters.")
        .next().atLine(54);
  }

}
