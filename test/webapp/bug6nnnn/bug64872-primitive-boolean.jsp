<%--
 Licensed to the Apache Software Foundation (ASF) under one or more
  contributor license agreements.  See the NOTICE file distributed with
  this work for additional information regarding copyright ownership.
  The ASF licenses this file to You under the Apache License, Version 2.0
  (the "License"); you may not use this file except in compliance with
  the License.  You may obtain a copy of the License at

      http://www.apache.org/licenses/LICENSE-2.0

  Unless required by applicable law or agreed to in writing, software
  distributed under the License is distributed on an "AS IS" BASIS,
  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  See the License for the specific language governing permissions and
  limitations under the License.
--%>
<%@ taglib uri="http://tomcat.apache.org/tag-setters" prefix="ts" %>
<html>
  <head><title>Bug 64872 boolean false test case</title></head>
  <body>
  <%
  for (int i=0; i < 1000000; i++) {
  %>
    <p>01 The value of foo is [<ts:tagPrimitiveBoolean foo="${false}" />]</p>
    <p>02 The value of foo is [<ts:tagPrimitiveBoolean foo="${'false'}" />]</p>
    <p>03 The value of foo is [<ts:tagPrimitiveBoolean foo='${"false"}' />]</p>
    <p>04 The value of foo is [<ts:tagPrimitiveBoolean foo="${true}" />]</p>
    <p>05 The value of foo is [<ts:tagPrimitiveBoolean foo="${'true'}" />]</p>
    <p>06 The value of foo is [<ts:tagPrimitiveBoolean foo='${"true"}' />]</p>
    <%--
    <p>04 The value of foo is [<ts:tagBoolean foo="false" />]</p>
    --%>
  <%
  }
  %>
  </body>
</html>